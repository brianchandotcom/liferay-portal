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

package com.liferay.portlet.rss.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Raymond Augé
 */
public class RSSPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "rss";

	public RSSPortletDataHandler() {
		setDataLevel(DataLevel.PORTLET_INSTANCE);
		setDataPortletPreferences("footerArticleValues", "headerArticleValues");
		setExportControls(new PortletDataHandlerControl[0]);
		setPublishToLiveByDefault(PropsValues.RSS_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue(
			"expandedItemsPerChannel", StringPool.BLANK);
		portletPreferences.setValue("feedImageAlignment", StringPool.BLANK);
		portletPreferences.setValues(
			"footerArticleValues", new String[] {"0", ""});
		portletPreferences.setValues(
			"headerArticleValues", new String[] {"0", ""});
		portletPreferences.setValue("itemsPerChannel", StringPool.BLANK);
		portletPreferences.setValue("showFeedDescription", StringPool.BLANK);
		portletPreferences.setValue("showFeedImage", StringPool.BLANK);
		portletPreferences.setValue("showFeedItemAuthor", StringPool.BLANK);
		portletPreferences.setValue("showFeedPublishedDate", StringPool.BLANK);
		portletPreferences.setValue("showFeedTitle", StringPool.BLANK);
		portletPreferences.setValue("titles", StringPool.BLANK);
		portletPreferences.setValue("urls", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessExportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		String[] footerArticleValues = portletPreferences.getValues(
			"footerArticleValues", new String[] {"0", ""});
		String[] headerArticleValues = portletPreferences.getValues(
			"headerArticleValues", new String[] {"0", ""});

		String footerArticleId = footerArticleValues[1];
		String headerArticleId = headerArticleValues[1];

		if (Validator.isNull(footerArticleId) &&
			Validator.isNull(headerArticleId)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"No article ids found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		long footerArticleGroupId = GetterUtil.getLong(footerArticleValues[0]);
		long headerArticleGroupId = GetterUtil.getLong(headerArticleValues[0]);

		if ((footerArticleGroupId <= 0) && (headerArticleGroupId <= 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ids found in preferences of portlet " +
						portletId);
			}

			return portletPreferences;
		}

		List<JournalArticle> articles = new ArrayList<JournalArticle>(2);

		JournalArticle footerArticle = null;

		try {
			footerArticle = JournalArticleLocalServiceUtil.getLatestArticle(
				footerArticleGroupId, footerArticleId,
				WorkflowConstants.STATUS_APPROVED);

			articles.add(footerArticle);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No approved article found with group id " +
						footerArticleGroupId + " and article id " +
							footerArticleId);
			}
		}

		JournalArticle headerArticle = null;

		try {
			headerArticle = JournalArticleLocalServiceUtil.getLatestArticle(
				headerArticleGroupId, headerArticleId,
				WorkflowConstants.STATUS_APPROVED);

			articles.add(headerArticle);
		}
		catch (NoSuchArticleException nsae) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No approved article found with group id " +
						headerArticleGroupId + " and article id " +
							headerArticleId);
			}
		}

		if (articles.isEmpty()) {
			return portletPreferences;
		}

		for (JournalArticle article : articles) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, portletId, article);
		}

		return portletPreferences;
	}

	@Override
	protected PortletPreferences doProcessImportPortletPreferences(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		StagedModelDataHandlerUtil.importReferenceStagedModels(
			portletDataContext, JournalArticle.class);

		Layout layout = LayoutLocalServiceUtil.getLayout(
			portletDataContext.getPlid());

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		String[] footerArticleValues = portletPreferences.getValues(
			"footerArticleValues", new String[] {"0", ""});

		String footerArticleId = footerArticleValues[1];

		footerArticleId = MapUtil.getString(
			articleIds, footerArticleId, footerArticleId);

		if (Validator.isNotNull(footerArticleId)) {
			footerArticleId = MapUtil.getString(
				articleIds, footerArticleId, footerArticleId);

			portletPreferences.setValues(
				"footerArticleValues",
				new String[] {
					String.valueOf(portletDataContext.getScopeGroupId()),
					footerArticleId
				});

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				portletDataContext.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, footerArticleId, true);
		}

		String[] headerArticleValues = portletPreferences.getValues(
			"headerArticleValues", new String[] {"0", ""});

		String headerArticleId = headerArticleValues[1];

		headerArticleId = MapUtil.getString(
			articleIds, headerArticleId, headerArticleId);

		if (Validator.isNotNull(headerArticleId)) {
			portletPreferences.setValues(
				"headerArticleValues",
				new String[] {
					String.valueOf(portletDataContext.getScopeGroupId()),
					headerArticleId
				});

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				portletDataContext.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, headerArticleId, true);
		}

		return portletPreferences;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RSSPortletDataHandler.class);

}