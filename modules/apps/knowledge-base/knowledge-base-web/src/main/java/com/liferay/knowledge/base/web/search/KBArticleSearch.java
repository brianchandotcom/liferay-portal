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

package com.liferay.knowledge.base.web.search;

import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.constants.KBPortletKeys;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.util.KnowledgeBaseUtil;
import com.liferay.knowledge.base.util.comparator.KBObjectsTitleComparator;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

/**
 * @author Peter Shin
 * @author Brian Wing Shun Chan
 */
public class KBArticleSearch extends SearchContainer<Object> {

	public static final String EMPTY_RESULTS_MESSAGE = "no-articles-were-found";

	public KBArticleSearch(
		PortletRequest portletRequest, PortletURL iteratorURL) {

		super(
			portletRequest, null, null, DEFAULT_CUR_PARAM, DEFAULT_DELTA,
			iteratorURL, null, EMPTY_RESULTS_MESSAGE);

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(
					portletRequest);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(portletRequest);

			String portletOrderByCol = portletPreferences.getValue(
				"kbArticlesOrderByCol", StringPool.BLANK);
			String portletOrderByType = portletPreferences.getValue(
				"kbArticlesOrderByType", StringPool.BLANK);

			String oldOrderByCol = preferences.getValue(
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "kb-articles-order-by-col",
				portletOrderByCol);
			String oldOrderByType = preferences.getValue(
				KBPortletKeys.KNOWLEDGE_BASE_ADMIN, "kb-articles-order-by-type",
				portletOrderByType);

			String orderByCol = ParamUtil.getString(
				portletRequest, "orderByCol", oldOrderByCol);
			String orderByType = ParamUtil.getString(
				portletRequest, "orderByType", oldOrderByType);

			if (!Objects.equals(orderByCol, oldOrderByCol) ||
				!Objects.equals(orderByType, oldOrderByType)) {

				preferences.setValue(
					KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
					"kb-articles-order-by-col", orderByCol);
				preferences.setValue(
					KBPortletKeys.KNOWLEDGE_BASE_ADMIN,
					"kb-articles-order-by-type", orderByType);
			}

			long kbFolderClassNameId = PortalUtil.getClassNameId(
				KBFolderConstants.getClassName());

			long parentResourceClassNameId = ParamUtil.getLong(
				portletRequest, "parentResourceClassNameId",
				kbFolderClassNameId);

			OrderByComparator<Object> orderByComparator = null;

			if (parentResourceClassNameId ==
					PortalUtil.getClassNameId(KBFolder.class)) {

				orderByComparator = new KBObjectsTitleComparator<>(false, true);
			}
			else {
				orderByComparator =
					KnowledgeBaseUtil.getKBArticleOrderByComparator(
						orderByCol, orderByType);
			}

			setOrderByCol(orderByCol);
			setOrderByType(orderByType);
			setOrderByComparator(orderByComparator);
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KBArticleSearch.class);

}