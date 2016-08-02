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

package com.liferay.journal.exportimport.data.handler;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.journal.configuration.JournalServiceConfigurationValues;
import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.permission.JournalPermission;
import com.liferay.journal.util.JournalContent;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior to
 * import.
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * <p>
 * For a better understanding of this class, see
 * <code>com.liferay.journal.content.web.lar.JournalContentPortletDataHandler</code>
 * located in Liferay Portal's external <code>modules</code> directory.
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
 * @author László Csontos
 * @author Mate Thurzo
 * @see    com.liferay.journal.internal.exportimport.creation.strategy.JournalCreationStrategy
 * @see    PortletDataHandler
 */
@Component(
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = PortletDataHandler.class
)
public class JournalPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "journal";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Activate
	protected void activate() {
		setDataLocalized(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DDMStructure.class, JournalArticle.class),
			new StagedModelType(DDMTemplate.class, DDMStructure.class),
			new StagedModelType(JournalArticle.class),
			new StagedModelType(JournalArticle.class, DDMStructure.class),
			new StagedModelType(JournalFeed.class),
			new StagedModelType(JournalFolder.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "web-content", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content"),
					new PortletDataHandlerBoolean(
						NAMESPACE, "version-history",
						JournalServiceConfigurationValues.
							PUBLISH_VERSION_HISTORY_BY_DEFAULT)
				},
				JournalArticle.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "structures", true, false, null,
				DDMStructure.class.getName(), JournalArticle.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "templates", true, false, null,
				DDMTemplate.class.getName(), DDMStructure.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "feeds", true, false, null,
				JournalFeed.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "folders", true, false, null,
				JournalFolder.class.getName()));
		setPublishToLiveByDefault(
			JournalServiceConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);
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

		_journalArticleLocalService.deleteArticles(
			portletDataContext.getScopeGroupId());

		_journalFolderLocalService.deleteFolders(
			portletDataContext.getGroupId());

		_ddmTemplateLocalService.deleteTemplates(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class));

		_ddmStructureLocalService.deleteStructures(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(
			JournalPermission.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "feeds")) {
			ActionableDynamicQuery feedActionableDynamicQuery =
				_journalFeedLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			feedActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			ActionableDynamicQuery folderActionableDynamicQuery =
				_journalFolderLocalService.getExportActionableDynamicQuery(
					portletDataContext);

			folderActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "structures")) {
			ActionableDynamicQuery ddmStructureActionableDynamicQuery =
				getDDMStructureActionableDynamicQuery(portletDataContext);

			ddmStructureActionableDynamicQuery.performActions();

			// Export DDM structure default values

			ActionableDynamicQuery
				ddmStructureDefaultValueActionableDynamicQuery =
					getDDMStructureDefaultValuesActionableDynamicQuery(
						portletDataContext);

			ddmStructureDefaultValueActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			ActionableDynamicQuery ddmTemplateActionableDynamicQuery =
				getDDMTemplateActionableDynamicQuery(portletDataContext);

			ddmTemplateActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
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

		portletDataContext.importPortletPermissions(
			JournalPermission.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "feeds")) {
			Element feedsElement = portletDataContext.getImportDataGroupElement(
				JournalFeed.class);

			List<Element> feedElements = feedsElement.elements();

			for (Element feedElement : feedElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, feedElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(
					JournalFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		Element articlesElement = portletDataContext.getImportDataGroupElement(
			JournalArticle.class);

		List<Element> articleElements = articlesElement.elements();

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

			// Importing DDM structure default values

			for (Element articleElement : articleElements) {
				String className = articleElement.attributeValue("class-name");

				if (Validator.isNotNull(className) &&
					className.equals(DDMStructure.class.getName())) {

					StagedModelDataHandlerUtil.importStagedModel(
						portletDataContext, articleElement);
				}
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "templates")) {
			Element ddmTemplatesElement =
				portletDataContext.getImportDataGroupElement(DDMTemplate.class);

			List<Element> ddmTemplateElements = ddmTemplatesElement.elements();

			for (Element ddmTemplateElement : ddmTemplateElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, ddmTemplateElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "web-content")) {
			for (Element articleElement : articleElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, articleElement);
			}

			_journalContent.clearCache();
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery articleActionableDynamicQuery =
			getArticleActionableDynamicQuery(portletDataContext);

		articleActionableDynamicQuery.performCount();

		ActionableDynamicQuery ddmStructureActionableDynamicQuery =
			getDDMStructureActionableDynamicQuery(portletDataContext);

		ddmStructureActionableDynamicQuery.performCount();

		ActionableDynamicQuery ddmTemplateActionableDynamicQuery =
			getDDMTemplateActionableDynamicQuery(portletDataContext);

		ddmTemplateActionableDynamicQuery.performCount();

		ActionableDynamicQuery feedActionableDynamicQuery =
			_journalFeedLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		feedActionableDynamicQuery.performCount();

		ActionableDynamicQuery folderActionableDynamicQuery =
			_journalFolderLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		folderActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getArticleActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_journalArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ExportActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					if (portletDataContext.getBooleanParameter(
							NAMESPACE, "version-history")) {

						return;
					}

					Class<?> clazz = getClass();

					DynamicQuery versionArticleDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							JournalArticle.class, "versionArticle",
							clazz.getClassLoader());

					versionArticleDynamicQuery.setProjection(
						ProjectionFactoryUtil.alias(
							ProjectionFactoryUtil.max("versionArticle.version"),
							"versionArticle.version"));

					// We need to use the "this" default alias to make sure the
					// database engine handles this subquery as a correlated
					// subquery

					versionArticleDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"this.resourcePrimKey",
							"versionArticle.resourcePrimKey"));

					Property workflowStatusProperty =
						PropertyFactoryUtil.forName("status");

					versionArticleDynamicQuery.add(
						workflowStatusProperty.in(
							_journalArticleStagedModelDataHandler.
								getExportableStatuses()));

					Property versionProperty = PropertyFactoryUtil.forName(
						"version");

					dynamicQuery.add(
						versionProperty.eq(versionArticleDynamicQuery));
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(JournalArticle.class.getName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDDMStructureActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmStructureLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long classNameId = PortalUtil.getClassNameId(
						JournalArticle.class);

					dynamicQuery.add(classNameIdProperty.eq(classNameId));
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMStructure.class.getName(), JournalArticle.class.getName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery
		getDDMStructureDefaultValuesActionableDynamicQuery(
			PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_journalArticleLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				JournalArticle.class.getName(), DDMStructure.class.getName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDDMTemplateActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_ddmTemplateLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					Property classPKProperty = PropertyFactoryUtil.forName(
						"classPK");

					disjunction.add(classPKProperty.eq(0L));

					DynamicQuery ddmStructureDynamicQuery =
						_ddmStructureLocalService.dynamicQuery();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long ddmStructureClassNameId = PortalUtil.getClassNameId(
						DDMStructure.class);

					dynamicQuery.add(
						classNameIdProperty.eq(ddmStructureClassNameId));

					long articleClassNameId = PortalUtil.getClassNameId(
						JournalArticle.class);

					ddmStructureDynamicQuery.add(
						classNameIdProperty.eq(articleClassNameId));

					ddmStructureDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("structureId"));

					disjunction.add(
						classPKProperty.in(ddmStructureDynamicQuery));

					dynamicQuery.add(disjunction);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				DDMTemplate.class.getName(), DDMStructure.class.getName()));

		return exportActionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMTemplateLocalService(
		DDMTemplateLocalService ddmTemplateLocalService) {

		_ddmTemplateLocalService = ddmTemplateLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleStagedModelDataHandler(
		JournalArticleStagedModelDataHandler
			journalArticleStagedModelDataHandler) {

		_journalArticleStagedModelDataHandler =
			journalArticleStagedModelDataHandler;
	}

	@Reference(unbind = "-")
	protected void setJournalContent(JournalContent journalContent) {
		_journalContent = journalContent;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMTemplateLocalService _ddmTemplateLocalService;
	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleStagedModelDataHandler
		_journalArticleStagedModelDataHandler;
	private JournalContent _journalContent;
	private JournalFeedLocalService _journalFeedLocalService;
	private JournalFolderLocalService _journalFolderLocalService;

}