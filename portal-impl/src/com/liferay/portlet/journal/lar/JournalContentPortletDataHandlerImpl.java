/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

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
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 * @see    com.liferay.portlet.journal.lar.JournalPortletDataHandlerImpl
 */
public class JournalContentPortletDataHandlerImpl
	extends BasePortletDataHandler {

	@Override
	public String[] getDataPortletPreferences() {
		return new String[] {"groupId", "articleId", "templateId"};
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles, _embeddedAssets
		};
	}

	@Override
	public PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "web-content", true,
				JournalPortletDataHandlerImpl.getMetadataControls()),
			new PortletDataHandlerBoolean(
				_NAMESPACE, "folders-and-documents", true,
				DLPortletDataHandlerImpl.getMetadataControls()
			)
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_selectedArticles
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[] {
			new PortletDataHandlerBoolean(
				_NAMESPACE, "web-content", true,
				JournalPortletDataHandlerImpl.getMetadataControls()),
			new PortletDataHandlerBoolean(
				_NAMESPACE, "folders-and-documents", true,
				DLPortletDataHandlerImpl.getMetadataControls()
			)
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isAlwaysStaged() {
		return _ALWAYS_STAGED;
	}

	@Override
	public boolean isDataLocalized() {
		return _DATA_LOCALIZED;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return _PUBLISH_TO_LIVE_BY_DEFAULT;
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
			"com.liferay.portlet.journal",
			portletDataContext.getScopeGroupId());

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

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("journal-content-data");

		if (article == null) {
			portletDataContext.setScopeGroupId(previousScopeGroupId);

			return document.formattedString();
		}

		String path = JournalPortletDataHandlerImpl.getArticlePath(
			portletDataContext, article);

		Element articleElement = rootElement.addElement("article");

		articleElement.addAttribute("path", path);

		Element dlFileEntryTypesElement = rootElement.addElement(
			"dl-file-entry-types");
		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element dlRepositoriesElement = rootElement.addElement(
			"dl-repositories");
		Element dlRepositoryEntriesElement = rootElement.addElement(
			"dl-repository-entries");

		JournalPortletDataHandlerImpl.exportArticle(
			portletDataContext, rootElement, rootElement, rootElement,
			dlFileEntryTypesElement, dlFoldersElement, dlFilesElement,
			dlFileRanksElement, dlRepositoriesElement,
			dlRepositoryEntriesElement, article, false);

		String defaultTemplateId = article.getTemplateId();
		String preferenceTemplateId = portletPreferences.getValue(
			"templateId", null);

		if (Validator.isNotNull(defaultTemplateId) &&
			Validator.isNotNull(preferenceTemplateId) &&
			!defaultTemplateId.equals(preferenceTemplateId)) {

			JournalTemplate template =
				JournalTemplateLocalServiceUtil.getTemplate(
					article.getGroupId(), preferenceTemplateId, true);

			JournalPortletDataHandlerImpl.exportTemplate(
				portletDataContext, rootElement, dlFileEntryTypesElement,
				dlFoldersElement, dlFilesElement, dlFileRanksElement,
				dlRepositoriesElement, dlRepositoryEntriesElement, template);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (Validator.isNull(data)) {
			return null;
		}

		long previousScopeGroupId = portletDataContext.getScopeGroupId();

		long importGroupId = GetterUtil.getLong(
			portletPreferences.getValue("groupId", null));

		if (importGroupId == portletDataContext.getSourceGroupId()) {
			portletDataContext.setScopeGroupId(portletDataContext.getGroupId());
		}

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		JournalPortletDataHandlerImpl.importReferencedData(
			portletDataContext, rootElement);

		Element structureElement = rootElement.element("structure");

		if (structureElement != null) {
			JournalPortletDataHandlerImpl.importStructure(
				portletDataContext, structureElement);
		}

		List<Element> templateElements = rootElement.elements("template");

		if (templateElements != null) {
			for (Element templateElement : templateElements) {
				JournalPortletDataHandlerImpl.importTemplate(
					portletDataContext, templateElement);
			}
		}

		Element articleElement = rootElement.element("article");

		if (articleElement != null) {
			JournalPortletDataHandlerImpl.importArticle(
				portletDataContext, articleElement);
		}

		String articleId = portletPreferences.getValue("articleId", null);

		if (Validator.isNotNull(articleId) && (articleElement != null)) {
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

		String templateId = portletPreferences.getValue("templateId", null);

		if (Validator.isNotNull(templateId)) {
			Map<String, String> templateIds =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					JournalTemplate.class + ".templateId");

			templateId = MapUtil.getString(templateIds, templateId, templateId);

			portletPreferences.setValue("templateId", templateId);
		}
		else {
			portletPreferences.setValue("templateId", StringPool.BLANK);
		}

		portletDataContext.setScopeGroupId(previousScopeGroupId);

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final boolean _ALWAYS_STAGED = true;

	private static final boolean _DATA_LOCALIZED = true;

	private static final String _NAMESPACE = "journal";

	private static final boolean _PUBLISH_TO_LIVE_BY_DEFAULT = true;

	private static Log _log = LogFactoryUtil.getLog(
		JournalContentPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static PortletDataHandlerBoolean _selectedArticles =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "selected-web-content", true, true);

}