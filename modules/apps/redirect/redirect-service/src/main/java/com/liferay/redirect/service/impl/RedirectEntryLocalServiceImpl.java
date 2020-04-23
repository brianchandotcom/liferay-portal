/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.redirect.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.ModelPermissions;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.RedirectConfiguration;
import com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException;
import com.liferay.redirect.exception.RequiredRedirectEntryDestinationURLException;
import com.liferay.redirect.exception.RequiredRedirectEntrySourceURLException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.RedirectNotFoundEntryLocalService;
import com.liferay.redirect.service.base.RedirectEntryLocalServiceBaseImpl;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.redirect.model.RedirectEntry",
	service = AopService.class
)
public class RedirectEntryLocalServiceImpl
	extends RedirectEntryLocalServiceBaseImpl {

	@Override
	public void addEntryResources(
			RedirectEntry entry, boolean addGroupPermissions,
			boolean addGuestPermissions)
		throws PortalException {

		resourceLocalService.addResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			RedirectEntry.class.getName(), entry.getRedirectEntryId(), false,
			addGroupPermissions, addGuestPermissions);
	}

	@Override
	public void addEntryResources(
			RedirectEntry entry, ModelPermissions modelPermissions)
		throws PortalException {

		resourceLocalService.addModelResources(
			entry.getCompanyId(), entry.getGroupId(), entry.getUserId(),
			RedirectEntry.class.getName(), entry.getRedirectEntryId(),
			modelPermissions);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, Date expirationDate,
			boolean permanent, String sourceURL, ServiceContext serviceContext)
		throws PortalException {

		_validate(destinationURL, sourceURL);

		if (redirectEntryPersistence.fetchByG_S(groupId, sourceURL) != null) {
			throw new DuplicateRedirectEntrySourceURLException();
		}

		RedirectEntry redirectEntry = redirectEntryPersistence.create(
			counterLocalService.increment());

		redirectEntry.setUuid(serviceContext.getUuid());

		redirectEntry.setGroupId(groupId);

		redirectEntry.setCompanyId(serviceContext.getCompanyId());
		redirectEntry.setUserId(serviceContext.getUserId());
		redirectEntry.setDestinationURL(destinationURL);
		redirectEntry.setExpirationDate(expirationDate);
		redirectEntry.setPermanent(permanent);
		redirectEntry.setSourceURL(sourceURL);

		redirectEntry = redirectEntryPersistence.update(redirectEntry);

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			addEntryResources(
				redirectEntry, serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			addEntryResources(
				redirectEntry, serviceContext.getModelPermissions());
		}

		RedirectNotFoundEntry redirectNotFoundEntry =
			_redirectNotFoundEntryLocalService.fetchRedirectNotFoundEntry(
				groupId, sourceURL);

		if (redirectNotFoundEntry != null) {
			_redirectNotFoundEntryLocalService.deleteRedirectNotFoundEntry(
				redirectNotFoundEntry);
		}

		return redirectEntry;
	}

	@Override
	public RedirectEntry fetchRedirectEntry(long groupId, String sourceURL) {
		return redirectEntryLocalService.fetchRedirectEntry(
			groupId, sourceURL, false);
	}

	@Override
	public RedirectEntry fetchRedirectEntry(
		long groupId, String sourceURL, boolean updateLastOccurrenceDate) {

		if (!_redirectConfiguration.isEnabled()) {
			return null;
		}

		RedirectEntry redirectEntry = redirectEntryPersistence.fetchByG_S(
			groupId, sourceURL);

		if (redirectEntry != null) {
			if (_isExpired(redirectEntry)) {
				return null;
			}

			if (updateLastOccurrenceDate &&
				((redirectEntry.getLastOccurrenceDate() == null) ||
				 !_isInTheSameDay(
					 redirectEntry.getLastOccurrenceDate(),
					 DateUtil.newDate()))) {

				redirectEntry.setLastOccurrenceDate(new Date());

				redirectEntry = redirectEntryLocalService.updateRedirectEntry(
					redirectEntry);
			}
		}

		return redirectEntry;
	}

	@Override
	public List<RedirectEntry> getRedirectEntries(
		long groupId, int start, int end,
		OrderByComparator<RedirectEntry> obc) {

		return redirectEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<RedirectEntry> getRedirectEntriesByGroupAndDestinationURL(
		long groupId, String destinationURL) {

		return ListUtil.filter(
			redirectEntryPersistence.findByG_D(groupId, destinationURL),
			redirectEntry -> !_isExpired(redirectEntry));
	}

	@Override
	public int getRedirectEntriesCount(long groupId) {
		return redirectEntryPersistence.countByGroupId(groupId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL, Date expirationDate,
			boolean permanent, String sourceURL)
		throws PortalException {

		_validate(destinationURL, sourceURL);

		RedirectEntry redirectEntry = getRedirectEntry(redirectEntryId);

		RedirectEntry existingRedirectEntry =
			redirectEntryPersistence.fetchByG_S(
				redirectEntry.getGroupId(), sourceURL);

		if ((existingRedirectEntry != null) &&
			(existingRedirectEntry.getRedirectEntryId() != redirectEntryId)) {

			throw new DuplicateRedirectEntrySourceURLException();
		}

		redirectEntry.setDestinationURL(destinationURL);
		redirectEntry.setExpirationDate(expirationDate);
		redirectEntry.setPermanent(permanent);
		redirectEntry.setSourceURL(sourceURL);

		return redirectEntryPersistence.update(redirectEntry);
	}

	private static Instant _getDayInstant(Date date) {
		Instant instant = date.toInstant();

		return instant.truncatedTo(ChronoUnit.DAYS);
	}

	private boolean _isExpired(RedirectEntry redirectEntry) {
		Date expirationDate = redirectEntry.getExpirationDate();

		if ((expirationDate != null) &&
			(DateUtil.compareTo(expirationDate, DateUtil.newDate()) <= 0)) {

			return true;
		}

		return false;
	}

	private boolean _isInTheSameDay(Date date1, Date date2) {
		Instant instant1 = _getDayInstant(date1);
		Instant instant2 = _getDayInstant(date2);

		return instant1.equals(instant2);
	}

	private void _validate(String destinationURL, String sourceURL)
		throws PortalException {

		if (Validator.isNull(destinationURL)) {
			throw new RequiredRedirectEntryDestinationURLException();
		}

		if (Validator.isNull(sourceURL)) {
			throw new RequiredRedirectEntrySourceURLException();
		}
	}

	@Reference
	private RedirectConfiguration _redirectConfiguration;

	@Reference
	private RedirectNotFoundEntryLocalService
		_redirectNotFoundEntryLocalService;

}