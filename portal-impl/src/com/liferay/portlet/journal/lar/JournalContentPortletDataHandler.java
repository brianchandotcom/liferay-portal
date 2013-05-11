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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandler;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * <p>
 * Provides the Journal Content portlet export and import functionality, which
 * is to clone the article, structure, and template referenced in the Journal
 * Content portlet if the article is associated with the layout's group. Upon
 * import, a new instance of the corresponding article, structure, and template
 * will be created or updated. The author of the newly created objects are
 * determined by the JournalCreationStrategy class defined in
 * <i>portal.properties</i>.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from from
 * <code>JournalPortletDataHandlerImpl</code> in that it only exports articles
 * referenced in Journal Content portlets. Articles not displayed in Journal
 * Content portlets will not be exported unless
 * <code>JournalPortletDataHandlerImpl</code> is activated.
 * </p>
 *
 * @author Joel Kozikowski
 * @author Raymond Augé
 * @author Bruno Farache
 * @author Daniel Kocsis
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see    com.liferay.portlet.journal.lar.JournalPortletDataHandler
 */
public class JournalContentPortletDataHandler
	extends JournalPortletDataHandler {

	public JournalContentPortletDataHandler() {
		setAlwaysStaged(true);
		setDataPortletPreferences("groupId", "articleId", "templateId");
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "selected-web-content", true, true),
				new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"));

		DLPortletDataHandler dlPortletDataHandler = new DLPortletDataHandler();

		setExportMetadataControls(
			ArrayUtil.append(
				getExportMetadataControls(),
				dlPortletDataHandler.getExportMetadataControls()));

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

		portletPreferences.setValue("groupId", StringPool.BLANK);
		portletPreferences.setValue("articleId", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			RESOURCE_NAME, portletDataContext.getScopeGroupId());

		String articleId = portletPreferences.getValue("articleId", null);

		if (articleId == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No article id found in preferences of portlet " +
						portletId);
			}

			return StringPool.BLANK;
		}

		long articleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", StringPool.BLANK));

		if (articleGroupId <= 0) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No group id found in preferences of portlet " + portletId);
			}

			return StringPool.BLANK;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		if (articleGroupId != portletDataContext.getScopeGroupId()) {
			portletDataContext.setScopeGroupId(articleGroupId);
		}
		else if (articleGroupId ==
					portletDataContext.getSourceCompanyGroupId()) {

			portletDataContext.setScopeGroupId(
				portletDataContext.getCompanyGroupId());
		}

		JournalArticle article = null;

		try {
			article = JournalArticleLocalServiceUtil.getLatestArticle(
				articleGroupId, articleId, WorkflowConstants.STATUS_APPROVED);
		}
		catch (NoSuchArticleException nsae) {
		}

		if (article == null) {
			try {
				article = JournalArticleLocalServiceUtil.getLatestArticle(
					articleGroupId, articleId,
					WorkflowConstants.STATUS_EXPIRED);
			}
			catch (NoSuchArticleException nsae) {
			}
		}

		Element rootElement = addExportDataRootElement(portletDataContext);

		if (article == null) {
			portletDataContext.setScopeGroupId(previousScopeGroupId);

			return getExportDataRootElementString(rootElement);
		}

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, article);

		String defaultTemplateId = article.getTemplateId();
		String preferenceTemplateId = portletPreferences.getValue(
			"ddmTemplateKey", null);

		if (Validator.isNotNull(defaultTemplateId) &&
			Validator.isNotNull(preferenceTemplateId) &&
			!defaultTemplateId.equals(preferenceTemplateId)) {

			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				article.getGroupId(),
				PortalUtil.getClassNameId(DDMStructure.class),
				preferenceTemplateId, true);

			StagedModelDataHandlerUtil.exportStagedModel(
				portletDataContext, ddmTemplate);

			Element articleElement = portletDataContext.getExportDataElement(
				article);

			portletDataContext.addReferenceElement(
				article, articleElement, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_STRONG, false);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			RESOURCE_NAME, portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		if (importGroupId == portletDataContext.getSourceGroupId()) {
			portletDataContext.setScopeGroupId(portletDataContext.getGroupId());
		}

		Element ddmStructuresElement =
			portletDataContext.getImportDataGroupElement(DDMStructure.class);

		List<Element> ddmStructureElements = ddmStructuresElement.elements();

		for (Element ddmStructureElement : ddmStructureElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmStructureElement);
		}

		Element ddmTemplatesElement =
			portletDataContext.getImportDataGroupElement(DDMTemplate.class);

		List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

		for (Element ddmTemplateElement : ddmTemplateElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, ddmTemplateElement);
		}

		Element articlesElement = portletDataContext.getImportDataGroupElement(
			JournalArticle.class);

		List<Element> articleElements = articlesElement.elements();

		if (!articleElements.isEmpty()) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, articleElements.get(0));
		}

		String articleId = portletPreferences.getValue("articleId", null);

		if (Validator.isNotNull(articleId)) {
			Map<String, String> articleIds =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					JournalArticle.class + ".articleId");

			articleId = MapUtil.getString(articleIds, articleId, articleId);

			portletPreferences.setValue("articleId", articleId);

			String importedArticleGroupId = String.valueOf(
				portletDataContext.getScopeGroupId());

			portletPreferences.setValue("groupId", importedArticleGroupId);

			Layout layout = LayoutLocalServiceUtil.getLayout(
				portletDataContext.getPlid());

			JournalContentSearchLocalServiceUtil.updateContentSearch(
				portletDataContext.getScopeGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), portletId, articleId, true);
		}
		else {
			portletPreferences.setValue("groupId", StringPool.BLANK);
			portletPreferences.setValue("articleId", StringPool.BLANK);
		}

		String ddmTemplateKey = portletPreferences.getValue(
			"ddmTemplateKey", null);

		if (Validator.isNotNull(ddmTemplateKey)) {
			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			ddmTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

			portletPreferences.setValue("ddmTemplateKey", ddmTemplateKey);
		}
		else {
			portletPreferences.setValue("ddmTemplateKey", StringPool.BLANK);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentPortletDataHandler.class);

}