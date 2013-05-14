/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalContentSearch;
import com.liferay.portlet.journal.service.base.JournalContentSearchLocalServiceBaseImpl;

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
	public void checkContentSearches(long companyId)
		throws PortalException, SystemException {

		if (_log.isInfoEnabled()) {
			_log.info("Checking journal content search for " + companyId);
		}

		List<Layout> layouts = new ArrayList<Layout>();

		List<Group> groups = groupLocalService.search(
			companyId, null, null, null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {

			// Private layouts

			deleteOwnerContentSearches(group.getGroupId(), true);

			layouts.addAll(
				layoutLocalService.getLayouts(group.getGroupId(), true));

			// Public layouts

			deleteOwnerContentSearches(group.getGroupId(), false);

			layouts.addAll(
				layoutLocalService.getLayouts(group.getGroupId(), false));
		}

		for (Layout layout : layouts) {
			LayoutTypePortlet layoutTypePortlet =
				(LayoutTypePortlet)layout.getLayoutType();

			List<String> portletIds = layoutTypePortlet.getPortletIds();

			for (String portletId : portletIds) {
				String rootPortletId = PortletConstants.getRootPortletId(
					portletId);

				if (rootPortletId.equals(PortletKeys.JOURNAL_CONTENT)) {
					PortletPreferences preferences =
						portletPreferencesLocalService.getPreferences(
							layout.getCompanyId(),
							PortletKeys.PREFS_OWNER_ID_DEFAULT,
							PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
							layout.getPlid(), portletId);

					String articleId = preferences.getValue(
						"articleId", StringPool.BLANK);

					if (Validator.isNotNull(articleId)) {
						updateContentSearch(
							layout.getGroupId(), layout.isPrivateLayout(),
							layout.getLayoutId(), portletId, articleId);
					}
				}
			}
		}
	}

	@Override
	public void deleteArticleContentSearch(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws SystemException {

		JournalContentSearch contentSearch =
			journalContentSearchPersistence.fetchByG_P_L_P_A(
				groupId, privateLayout, layoutId, portletId, articleId);

		if (contentSearch != null) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteArticleContentSearches(long groupId, String articleId)
		throws SystemException {

		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_A(groupId, articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteLayoutContentSearches(
			long groupId, boolean privateLayout, long layoutId)
		throws SystemException {

		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_P_L(
				groupId, privateLayout, layoutId);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public void deleteOwnerContentSearches(long groupId, boolean privateLayout)
		throws SystemException {

		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_P(groupId, privateLayout);

		for (JournalContentSearch contentSearch : contentSearches) {
			deleteJournalContentSearch(contentSearch);
		}
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches()
		throws SystemException {

		return journalContentSearchPersistence.findAll();
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches(
			long groupId, String articleId)
		throws SystemException {

		return journalContentSearchPersistence.findByG_A(groupId, articleId);
	}

	@Override
	public List<JournalContentSearch> getArticleContentSearches(
			String articleId)
		throws SystemException {

		return journalContentSearchPersistence.findByArticleId(articleId);
	}

	@Override
	public List<Long> getLayoutIds(
			long groupId, boolean privateLayout, String articleId)
		throws SystemException {

		List<Long> layoutIds = new ArrayList<Long>();

		List<JournalContentSearch> contentSearches =
			journalContentSearchPersistence.findByG_P_A(
				groupId, privateLayout, articleId);

		for (JournalContentSearch contentSearch : contentSearches) {
			layoutIds.add(contentSearch.getLayoutId());
		}

		return layoutIds;
	}

	@Override
	public int getLayoutIdsCount(
			long groupId, boolean privateLayout, String articleId)
		throws SystemException {

		return journalContentSearchPersistence.countByG_P_A(
			groupId, privateLayout, articleId);
	}

	@Override
	public int getLayoutIdsCount(String articleId) throws SystemException {
		return journalContentSearchPersistence.countByArticleId(articleId);
	}

	@Override
	public List<JournalContentSearch> getPortletContentSearches(
			String portletId)
		throws SystemException {

		return journalContentSearchPersistence.findByPortletId(portletId);
	}

	@Override
	public JournalContentSearch updateContentSearch(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId)
		throws PortalException, SystemException {

		return updateContentSearch(
			groupId, privateLayout, layoutId, portletId, articleId, false);
	}

	@Override
	public JournalContentSearch updateContentSearch(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String articleId, boolean purge)
		throws PortalException, SystemException {

		if (purge) {
			journalContentSearchPersistence.removeByG_P_L_P(
				groupId, privateLayout, layoutId, portletId);
		}

		Group group = groupPersistence.findByPrimaryKey(groupId);

		JournalContentSearch contentSearch =
			journalContentSearchPersistence.fetchByG_P_L_P_A(
				groupId, privateLayout, layoutId, portletId, articleId);

		if (contentSearch == null) {
			long contentSearchId = counterLocalService.increment();

			contentSearch = journalContentSearchPersistence.create(
				contentSearchId);

			contentSearch.setGroupId(groupId);
			contentSearch.setCompanyId(group.getCompanyId());
			contentSearch.setPrivateLayout(privateLayout);
			contentSearch.setLayoutId(layoutId);
			contentSearch.setPortletId(portletId);
			contentSearch.setArticleId(articleId);
		}

		journalContentSearchPersistence.update(contentSearch);

		return contentSearch;
	}

	@Override
	public List<JournalContentSearch> updateContentSearch(
			long groupId, boolean privateLayout, long layoutId,
			String portletId, String[] articleIds)
		throws PortalException, SystemException {

		journalContentSearchPersistence.removeByG_P_L_P(
			groupId, privateLayout, layoutId, portletId);

		List<JournalContentSearch> contentSearches =
			new ArrayList<JournalContentSearch>();

		for (String articleId : articleIds) {
			JournalContentSearch contentSearch = updateContentSearch(
				groupId, privateLayout, layoutId, portletId, articleId, false);

			contentSearches.add(contentSearch);
		}

		return contentSearches;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentSearchLocalServiceImpl.class);

}