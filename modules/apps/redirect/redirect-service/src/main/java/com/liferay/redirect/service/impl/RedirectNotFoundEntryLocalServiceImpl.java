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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.view.count.ViewCountManager;
import com.liferay.redirect.internal.configuration.RedirectConfiguration;
import com.liferay.redirect.model.RedirectNotFoundEntry;
import com.liferay.redirect.service.base.RedirectNotFoundEntryLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.redirect.internal.configuration.RedirectConfiguration",
	property = "model.class.name=com.liferay.redirect.model.RedirectNotFoundEntry",
	service = AopService.class
)
public class RedirectNotFoundEntryLocalServiceImpl
	extends RedirectNotFoundEntryLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectNotFoundEntry addOrUpdateRedirectNotFoundEntry(
		Group group, String url) {

		RedirectNotFoundEntry redirectNotFoundEntry =
			redirectNotFoundEntryPersistence.fetchByG_U(
				group.getGroupId(), url);

		if (redirectNotFoundEntry == null) {
			_deleteRedirectNotFoundEntries();

			redirectNotFoundEntry = redirectNotFoundEntryPersistence.create(
				counterLocalService.increment());

			redirectNotFoundEntry.setGroupId(group.getGroupId());
			redirectNotFoundEntry.setCompanyId(group.getCompanyId());
			redirectNotFoundEntry.setUrl(url);

			redirectNotFoundEntry = redirectNotFoundEntryPersistence.update(
				redirectNotFoundEntry);
		}

		_viewCountManager.incrementViewCount(
			redirectNotFoundEntry.getCompanyId(),
			_portal.getClassNameId(RedirectNotFoundEntry.class),
			redirectNotFoundEntry.getRedirectNotFoundEntryId(), 1);

		return redirectNotFoundEntry;
	}

	@Override
	public RedirectNotFoundEntry fetchRedirectNotFoundEntry(
		long groupId, String url) {

		return redirectNotFoundEntryPersistence.fetchByG_U(groupId, url);
	}

	@Override
	public List<RedirectNotFoundEntry> getRedirectNotFoundEntries(
		long groupId, Boolean ignored, Date minModifiedDate, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return redirectNotFoundEntryLocalService.dynamicQuery(
			_getRedirectNotFoundEntriesDynamicQuery(
				groupId, ignored, minModifiedDate, orderByComparator),
			start, end);
	}

	@Override
	public List<RedirectNotFoundEntry> getRedirectNotFoundEntries(
		long groupId, Date minModifiedDate, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return redirectNotFoundEntryLocalService.dynamicQuery(
			_getRedirectNotFoundEntriesDynamicQuery(
				groupId, null, minModifiedDate, orderByComparator),
			start, end);
	}

	@Override
	public List<RedirectNotFoundEntry> getRedirectNotFoundEntries(
		long groupId, int start, int end,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		return redirectNotFoundEntryPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getRedirectNotFoundEntriesCount(long groupId) {
		return redirectNotFoundEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getRedirectNotFoundEntriesCount(
		long groupId, Boolean ignored, Date minModifiedDate) {

		return GetterUtil.getInteger(
			redirectNotFoundEntryLocalService.dynamicQueryCount(
				_getRedirectNotFoundEntriesDynamicQuery(
					groupId, ignored, minModifiedDate)));
	}

	@Override
	public int getRedirectNotFoundEntriesCount(
		long groupId, Date minModifiedDate) {

		return GetterUtil.getInteger(
			redirectNotFoundEntryLocalService.dynamicQueryCount(
				_getRedirectNotFoundEntriesDynamicQuery(
					groupId, null, minModifiedDate)));
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public RedirectNotFoundEntry updateRedirectNotFoundEntry(
			long redirectNotFoundEntryId, boolean ignored)
		throws PortalException {

		RedirectNotFoundEntry redirectNotFoundEntry =
			redirectNotFoundEntryLocalService.getRedirectNotFoundEntry(
				redirectNotFoundEntryId);

		redirectNotFoundEntry.setIgnored(ignored);

		return redirectNotFoundEntryPersistence.update(redirectNotFoundEntry);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_redirectConfiguration = ConfigurableUtil.createConfigurable(
			RedirectConfiguration.class, properties);
	}

	private void _deleteRedirectNotFoundEntries() {
		ActionableDynamicQuery actionableDynamicQuery =
			redirectNotFoundEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				int maximumNumberOfRedirectNotFoundEntries =
					_redirectConfiguration.
						maximumNumberOfRedirectNotFoundEntries();

				dynamicQuery.setLimit(
					maximumNumberOfRedirectNotFoundEntries - 1,
					getRedirectNotFoundEntriesCount());
			});
		actionableDynamicQuery.setAddOrderCriteriaMethod(
			dynamicQuery -> dynamicQuery.addOrder(
				OrderFactoryUtil.desc("modifiedDate")));
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<RedirectNotFoundEntry>)
				this::deleteRedirectNotFoundEntry);

		try {
			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private DynamicQuery _getRedirectNotFoundEntriesDynamicQuery(
		long groupId, Boolean ignored, Date minModifiedDate) {

		DynamicQuery redirectNotFoundEntriesDynamicQuery =
			redirectNotFoundEntryLocalService.dynamicQuery();

		redirectNotFoundEntriesDynamicQuery.add(
			RestrictionsFactoryUtil.eq("groupId", groupId));

		if (ignored != null) {
			redirectNotFoundEntriesDynamicQuery.add(
				RestrictionsFactoryUtil.eq("ignored", ignored));
		}

		if (minModifiedDate != null) {
			redirectNotFoundEntriesDynamicQuery.add(
				RestrictionsFactoryUtil.gt("modifiedDate", minModifiedDate));
		}

		return redirectNotFoundEntriesDynamicQuery;
	}

	private DynamicQuery _getRedirectNotFoundEntriesDynamicQuery(
		long groupId, Boolean ignored, Date minModifiedDate,
		OrderByComparator<RedirectNotFoundEntry> orderByComparator) {

		DynamicQuery redirectNotFoundEntriesDynamicQuery =
			_getRedirectNotFoundEntriesDynamicQuery(
				groupId, ignored, minModifiedDate);

		if (orderByComparator != null) {
			OrderFactoryUtil.addOrderByComparator(
				redirectNotFoundEntriesDynamicQuery, orderByComparator);
		}
		else {
			redirectNotFoundEntriesDynamicQuery.addOrder(
				OrderFactoryUtil.asc("createDate"));
		}

		return redirectNotFoundEntriesDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RedirectNotFoundEntryLocalServiceImpl.class);

	@Reference
	private Portal _portal;

	private volatile RedirectConfiguration _redirectConfiguration;

	@Reference
	private ViewCountManager _viewCountManager;

}