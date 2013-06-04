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

package com.liferay.portlet.rss.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.DataLevel;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandler;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.lar.JournalPortletDataHandler;
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
			setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "selected-web-content", true, true, null,
				JournalArticle.class.getName()),
			new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"));

		JournalPortletDataHandler journalPortletDataHandler =
			new JournalPortletDataHandler();
		DLPortletDataHandler dlPortletDataHandler = new DLPortletDataHandler();

		PortletDataHandlerControl[] exportMetadataControls = ArrayUtil.append(
			journalPortletDataHandler.getExportMetadataControls(),
			dlPortletDataHandler.getExportMetadataControls());

		for (PortletDataHandlerControl portletDataHandlerControl :
				exportMetadataControls) {

			portletDataHandlerControl.setNamespace(NAMESPACE);
		}

		setExportMetadataControls(exportMetadataControls);

		setImportControls(getExportControls()[0]);
		setPublishToLiveByDefault(true);
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
	protected String doExportData(
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

			return StringPool.BLANK;
		}

		long footerArticleGroupId = GetterUtil.getLong(footerArticleValues[0]);
		long headerArticleGroupId = GetterUtil.getLong(headerArticleValues[0]);

		if ((footerArticleGroupId <= 0) && (headerArticleGroupId <= 0)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group ids found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
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

		if ((footerArticle == null) && (headerArticle == null)) {
			return StringPool.BLANK;
		}

		Element rootElement = addExportDataRootElement(portletDataContext);

		for (JournalArticle article : articles) {
			String path = ExportImportPathUtil.getModelPath(article);

			Element articleElement = null;

			if (article == footerArticle) {
				articleElement = rootElement.addElement("footer-article");
			}
			else {
				articleElement = rootElement.addElement("header-article");
			}

			articleElement.addAttribute("path", path);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, article);

			portletDataContext.addReferenceElement(
				article, articleElement, article,
				PortletDataContext.REFERENCE_TYPE_WEAK, false);
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		Layout layout = LayoutLocalServiceUtil.getLayout(
			portletDataContext.getPlid());

		Element rootElement = portletDataContext.getImportDataRootElement();

		Element footerArticleElement = rootElement.element("footer-article");

		importReferenceArticle(portletDataContext, footerArticleElement);

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

		Element headerArticleElement = rootElement.element("header-article");

		importReferenceArticle(portletDataContext, headerArticleElement);

		String[] headerArticleValues = portletPreferences.getValues(
			"headerArticleValues", new String[] {"0", ""});

		String headerArticleId = headerArticleValues[1];

		headerArticleId = MapUtil.getString(
			articleIds, headerArticleId, headerArticleId);

		if (Validator.isNotNull(headerArticleId)) {
			headerArticleId = MapUtil.getString(
				articleIds, headerArticleId, headerArticleId);

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

	protected void importReferenceArticle(
			PortletDataContext portletDataContext, Element parentElement)
		throws PortletDataException {

		List<Element> referenceDataElements =
			portletDataContext.getReferenceDataElements(
				parentElement, JournalArticle.class);

		if (referenceDataElements.isEmpty()) {
			return;
		}

		for (Element referenceDataElement : referenceDataElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, referenceDataElement);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		RSSPortletDataHandler.class);

}