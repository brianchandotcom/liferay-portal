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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.ManifestSummary;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureExportActionableDynamicQuery;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMTemplateExportActionableDynamicQuery;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;
import com.liferay.portlet.journal.service.persistence.JournalArticleExportActionableDynamicQuery;
import com.liferay.portlet.journal.service.persistence.JournalFeedExportActionableDynamicQuery;
import com.liferay.portlet.journal.service.persistence.JournalFolderExportActionableDynamicQuery;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior to
 * import.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Wesley Gong
 * @author Hugo Huijser
 * @author Daniel Kocsis
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalContentPortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "journal";

	public JournalPortletDataHandler() {
		setDataLocalized(true);
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "web-content", true, false, null,
				JournalArticle.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "structures", true, false, null,
				DDMStructure.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "feeds", true, false, null,
				JournalFeed.class.getName()),
			new PortletDataHandlerBoolean(NAMESPACE, "embedded-assets"),
			new PortletDataHandlerBoolean(
				NAMESPACE, "version-history",
				PropsValues.JOURNAL_PUBLISH_VERSION_HISTORY_BY_DEFAULT));
		setExportMetadataControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "web-content", true,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(NAMESPACE, "categories"),
					new PortletDataHandlerBoolean(NAMESPACE, "comments"),
					new PortletDataHandlerBoolean(NAMESPACE, "ratings"),
					new PortletDataHandlerBoolean(NAMESPACE, "tags")
				}));
		setImportControls(getExportControls());
		setPublishToLiveByDefault(
			PropsValues.JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				JournalPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		JournalArticleLocalServiceUtil.deleteArticles(
			portletDataContext.getScopeGroupId());

		DDMTemplateLocalServiceUtil.deleteTemplates(
			portletDataContext.getScopeGroupId());

		DDMStructureLocalServiceUtil.deleteStructures(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			JournalPermission.RESOURCE_NAME,
			portletDataContext.getScopeGroupId());

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "feeds")) {
			ActionableDynamicQuery feedActionableDynamicQuery =
				new JournalFeedExportActionableDynamicQuery(portletDataContext);

			feedActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "structures")) {
			List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

			ActionableDynamicQuery ddmStructureActionableDynamicQuery =
				getDDMStructureActionableDynamicQuery(
					portletDataContext, ddmTemplates, true);

			ddmStructureActionableDynamicQuery.performActions();

			// Export templates that belong to structures

			for (DDMTemplate ddmTemplate : ddmTemplates) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, ddmTemplate);
			}

			// Export templates that do not belong to structures

			ActionableDynamicQuery ddmTemplateActionableDynamicQuery =
				getDDMTemplateActionableDynamicQuery(portletDataContext);

			ddmTemplateActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			ActionableDynamicQuery folderActionableDynamicQuery =
				getFolderActionableDynamicQuery(portletDataContext);

			folderActionableDynamicQuery.performActions();

			ActionableDynamicQuery articleActionableDynamicQuery =
				getArticleActionableDynamicQuery(portletDataContext);

			articleActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			JournalPermission.RESOURCE_NAME,
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		if (portletDataContext.getBooleanParameter(NAMESPACE, "feeds")) {
			Element feedsElement = portletDataContext.getImportDataGroupElement(
				JournalFeed.class);

			List<Element> feedElements = feedsElement.elements();

			for (Element feedElement : feedElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, feedElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "structures")) {
			Element ddmStructuresElement =
				portletDataContext.getImportDataGroupElement(
					DDMStructure.class);

			List<Element> ddmStructureElements =
				ddmStructuresElement.elements();

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
		}

		if (!portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			return portletPreferences;
		}

		Element foldersElement = portletDataContext.getImportDataGroupElement(
			JournalFolder.class);

		List<Element> folderElements = foldersElement.elements();

		for (Element folderElement : folderElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, folderElement);
		}

		Element articlesElement = portletDataContext.getImportDataGroupElement(
			JournalArticle.class);

		List<Element> articleElements = articlesElement.elements();

		for (Element articleElement : articleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, articleElement);
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext)
		throws Exception {

		ActionableDynamicQuery articleActionableDynamicQuery =
			getArticleActionableDynamicQuery(portletDataContext);

		articleActionableDynamicQuery.performCount();

		List<DDMTemplate> ddmTemplates = new ArrayList<DDMTemplate>();

		ActionableDynamicQuery ddmStructureActionableDynamicQuery =
			getDDMStructureActionableDynamicQuery(
				portletDataContext, ddmTemplates, false);

		ddmStructureActionableDynamicQuery.performActions();

		ddmStructureActionableDynamicQuery.performCount();

		ActionableDynamicQuery ddmTemplateActionableDynamicQuery =
			getDDMTemplateActionableDynamicQuery(portletDataContext);

		long ddmTemplatesCount =
			ddmTemplateActionableDynamicQuery.performCount() +
			ddmTemplates.size();

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		manifestSummary.addModelCount(
			DDMTemplate.class, JournalArticle.class, ddmTemplatesCount);

		ActionableDynamicQuery feedActionableDynamicQuery =
			new JournalFeedExportActionableDynamicQuery(portletDataContext);

		feedActionableDynamicQuery.performCount();

		ActionableDynamicQuery folderActionableDynamicQuery =
			getFolderActionableDynamicQuery(portletDataContext);

		folderActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getArticleActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new JournalArticleExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				super.addCriteria(dynamicQuery);

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.in(
						new Integer[] {
							WorkflowConstants.STATUS_APPROVED,
							WorkflowConstants.STATUS_EXPIRED
						}));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				JournalArticle article = (JournalArticle)object;

				boolean latestVersion = false;

				try {
					latestVersion =
						JournalArticleLocalServiceUtil.isLatestVersion(
							article.getGroupId(), article.getArticleId(),
							article.getVersion(),
							WorkflowConstants.STATUS_APPROVED);
				}
				catch (Exception e) {
				}

				if (portletDataContext.getBooleanParameter(
						NAMESPACE, "version-history") || latestVersion) {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, article);
				}
			}

		};
	}

	protected ActionableDynamicQuery getDDMStructureActionableDynamicQuery(
			final PortletDataContext portletDataContext,
			final List<DDMTemplate> ddmTemplates, final boolean export)
		throws SystemException {

		return new DDMStructureExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				super.addCriteria(dynamicQuery);

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = PortalUtil.getClassNameId(
					JournalArticle.class);

				dynamicQuery.add(classNameIdProperty.eq(classNameId));
			}

			@Override
			protected String getManifestSummaryKey() {
				return ManifestSummary.getManifestSummaryKey(
					DDMStructure.class.getName(),
					JournalArticle.class.getName());
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				DDMStructure ddmStructure = (DDMStructure)object;

				if (export) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddmStructure);
				}

				try {
					ddmTemplates.addAll(ddmStructure.getTemplates());
				}
				catch (SystemException se) {
				}
			}

		};
	}

	protected ActionableDynamicQuery getDDMTemplateActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new DDMTemplateExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				super.addCriteria(dynamicQuery);

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long classNameId = PortalUtil.getClassNameId(
					DDMStructure.class);

				dynamicQuery.add(classNameIdProperty.eq(classNameId));

				Property classPKProperty = PropertyFactoryUtil.forName(
					"classPK");

				dynamicQuery.add(classPKProperty.eq(-1L));
			}

			@Override
			protected String getManifestSummaryKey() {
				return ManifestSummary.getManifestSummaryKey(
					DDMTemplate.class.getName(),
					JournalArticle.class.getName());
			}

		};
	}

	protected ActionableDynamicQuery getFolderActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new JournalFolderExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				super.addCriteria(dynamicQuery);

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.ne(WorkflowConstants.STATUS_IN_TRASH));
			}

		};
	}

}