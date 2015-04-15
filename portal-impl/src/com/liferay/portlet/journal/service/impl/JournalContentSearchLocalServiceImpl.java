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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.provider.DisplayInformationProvider;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.base.JournalContentSearchLocalServiceBaseImpl;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 * @author Wesley Gong
 */
public class JournalContentSearchLocalServiceImpl
	extends JournalContentSearchLocalServiceBaseImpl {

	@Override
	public void checkContentSearches(long companyId) throws PortalException {
		if (_log.isInfoEnabled()) {
			_log.info("Checking journal content search for " + companyId);
		}

		List<Layout> layouts = new ArrayList<>();

		List<Group> groups = groupLocalService.search(
			companyId, null, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			deleteOwnerContentSearches(group.getGroupId());

			layouts.addAll(layoutLocalService.getLayouts(group.getGroupId()));
		}

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List<String> portletIds = layoutTypePortlet.getPortletIds();

			for (String portletId : portletIds) {
				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				DisplayInformationProvider displayInformationProvider =
					_serviceTrackerMap.getService(rootPortletId);

				if (displayInformationProvider == null) {
					continue;
				}

				PortletPreferences portletPreferences =
					portletPreferencesLocalService.getPreferences(
						layout.getCompanyId(),
						PortletKeys.PREFS_OWNER_ID_DEFAULT,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layout.getPlid(),
						portletId);

				String classPK = displayInformationProvider.getClassPK(
					portletPreferences);

				updateContentSearch(
					layout.getGroupId(), layout.getLayoutId(), portletId,
					classPK);
			}
		}
	}

	@Override
	public void deleteArticleContentSearch(
		long groupId, long layoutId, String portletId) {

		journalContentSearchPersistence.removeByG_L_P(
			groupId, layoutId, portletId);
	}

	@Override
	public void deleteArticleContentSearch(
		long groupId, long layoutId, String portletId, String articleId) {

		JournalContentSearch contentSearch =
			journalContentSearchPersistence.fetchByG_L_P_A(
				groupId, layoutId, portletId, articleId);

		if (contentSearch != null) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteArticleContentSearches(long groupId, String articleId) {
		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_A(groupId, articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteLayoutContentSearches(long groupId, long layoutId) {
		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_L(groupId, layoutId);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteOwnerContentSearches(long groupId) {
		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByGroupId(groupId);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches() {
		return journalContentSearchPersistence.findAll();
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches(
		long groupId, String articleId) {

		return journalContentSearchPersistence.findByG_A(groupId, articleId);
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches(
		String articleId) {

		return journalContentSearchPersistence.findByArticleId(articleId);
	}

	@Override
	public List<Long> getLayoutIds(long groupId, String articleId) {
		List<Long> layoutIds = new ArrayList<>();

		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_A(groupId, articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			layoutIds.add(contentSearch.getLayoutId());
		}

		return layoutIds;
	}

	@Override
	public int getLayoutIdsCount(long groupId, String articleId) {
		return journalContentSearchPersistence.countByG_A(groupId, articleId);
	}

	@Override
	public int getLayoutIdsCount(String articleId) {
		return journalContentSearchPersistence.countByArticleId(articleId);
	}

	@Override
	public List<JournalContentSearch> getPortletContentSearches(
		String portletId) {

		return journalContentSearchPersistence.findByPortletId(portletId);
	}

	@Override
	public JournalContentSearch updateContentSearch(
			long groupId, long layoutId, String portletId, String articleId)
		throws PortalException {

		return updateContentSearch(
			groupId, layoutId, portletId, articleId, false);
	}

	@Override
	public JournalContentSearch updateContentSearch(
			long groupId, long layoutId, String portletId, String articleId,
			boolean purge)
		throws PortalException {

		if (purge) {
			journalContentSearchPersistence.removeByG_L_P(
				groupId, layoutId, portletId);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		JournalContentSearch contentSearch =
			journalContentSearchPersistence.fetchByG_L_P_A(
				groupId, layoutId, portletId, articleId);

		if (contentSearch == null) {
			long contentSearchId = counterLocalService.increment();

			contentSearch = journalContentSearchPersistence.create(
				contentSearchId);

			contentSearch.setGroupId(groupId);
			contentSearch.setCompanyId(group.getCompanyId());
			contentSearch.setLayoutId(layoutId);
			contentSearch.setPortletId(portletId);
			contentSearch.setArticleId(articleId);
		}

		journalContentSearchPersistence.update(contentSearch);

		return contentSearch;
	}

	@Override
	public List<JournalContentSearch> updateContentSearch(
			long groupId, long layoutId, String portletId, String[] articleIds)
		throws PortalException {

		journalContentSearchPersistence.removeByG_L_P(
			groupId, layoutId, portletId);

		List<JournalContentSearch> contentSearches = new ArrayList<>();

		for (String articleId : articleIds) {
			JournalContentSearch contentSearch = updateContentSearch(
				groupId, layoutId, portletId, articleId, false);

			contentSearches.add(contentSearch);
		}

		return contentSearches;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JournalContentSearchLocalServiceImpl.class);

	private static final ServiceTrackerMap<String, DisplayInformationProvider>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			DisplayInformationProvider.class, "javax.portlet.name");

	static {
		_serviceTrackerMap.open();
	}

}