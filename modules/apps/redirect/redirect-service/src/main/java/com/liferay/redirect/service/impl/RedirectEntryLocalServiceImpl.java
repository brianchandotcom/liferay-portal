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
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.redirect.configuration.RedirectConfiguration;
import com.liferay.redirect.exception.DuplicateRedirectEntrySourceURLException;
import com.liferay.redirect.exception.RequiredRedirectEntryDestinationURLException;
import com.liferay.redirect.exception.RequiredRedirectEntrySourceURLException;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.base.RedirectEntryLocalServiceBaseImpl;

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

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectEntry addRedirectEntry(
			long groupId, String destinationURL, Date expirationDate,
			String sourceURL, boolean temporary, ServiceContext serviceContext)
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
		redirectEntry.setSourceURL(sourceURL);
		redirectEntry.setTemporary(temporary);

		return redirectEntryPersistence.update(redirectEntry);
	}

	@Override
	public RedirectEntry fetchRedirectEntry(long groupId, String sourceURL) {
		if (!_redirectConfiguration.isEnabled()) {
			return null;
		}

		RedirectEntry redirectEntry = redirectEntryPersistence.fetchByG_S(
			groupId, sourceURL);

		if (redirectEntry != null) {
			Date expirationDate = redirectEntry.getExpirationDate();

			if ((expirationDate != null) &&
				(DateUtil.compareTo(expirationDate, DateUtil.newDate()) <= 0)) {

				return null;
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
	public int getRedirectEntriesCount(long groupId) {
		return redirectEntryPersistence.countByGroupId(groupId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectEntry updateRedirectEntry(
			long redirectEntryId, String destinationURL, Date expirationDate,
			String sourceURL, boolean temporary)
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
		redirectEntry.setSourceURL(sourceURL);
		redirectEntry.setTemporary(temporary);

		return redirectEntryPersistence.update(redirectEntry);
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

}