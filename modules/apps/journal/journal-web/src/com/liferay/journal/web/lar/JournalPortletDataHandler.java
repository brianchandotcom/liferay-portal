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

package com.liferay.journal.web.lar;

import com.liferay.journal.web.configuration.JournalWebConfigurationValues;
import com.liferay.journal.web.constants.JournalPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.lar.xstream.XStreamAliasRegistryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.model.impl.JournalFeedImpl;
import com.liferay.portlet.journal.model.impl.JournalFolderImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.service.permission.JournalPermission;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

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
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
@Component(
	property = {"javax.portlet.name=" + JournalPortletKeys.JOURNAL},
	service = PortletDataHandler.class
)
public class JournalPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "journal";

	public JournalPortletDataHandler() {
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
						JournalWebConfigurationValues.
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
				JournalFeed.class.getName()));
		setPublishToLiveByDefault(
			JournalWebConfigurationValues.PUBLISH_TO_LIVE_BY_DEFAULT);

		XStreamAliasRegistryUtil.register(
			JournalArticleImpl.class, "JournalArticle");
		XStreamAliasRegistryUtil.register(JournalFeedImpl.class, "JournalFeed");
		XStreamAliasRegistryUtil.register(
			JournalFolderImpl.class, "JournalFolder");
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

		JournalFolderLocalServiceUtil.deleteFolders(
			portletDataContext.getGroupId());

		DDMTemplateLocalServiceUtil.deleteTemplates(
			portletDataContext.getScopeGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class));

		DDMStructureLocalServiceUtil.deleteStructures(
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
				JournalFeedLocalServiceUtil.getExportActionableDynamicQuery(
					portletDataContext);

			feedActionableDynamicQuery.performActions();
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
			ActionableDynamicQuery folderActionableDynamicQuery =
				JournalFolderLocalServiceUtil.getExportActionableDynamicQuery(
					portletDataContext);

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

		for (Element articleElement : articleElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, articleElement);
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
			JournalFeedLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		feedActionableDynamicQuery.performCount();

		ActionableDynamicQuery folderActionableDynamicQuery =
			JournalFolderLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		folderActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getArticleActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			JournalArticleLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					if (portletDataContext.getBooleanParameter(
							NAMESPACE, "version-history")) {

						return;
					}

					DynamicQuery versionArticleDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							JournalArticle.class, "versionArticle",
							PortalClassLoaderUtil.getClassLoader());

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

					Property versionProperty = PropertyFactoryUtil.forName(
						"version");

					dynamicQuery.add(
						versionProperty.eq(versionArticleDynamicQuery));
				}

			});

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDDMStructureActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			DDMStructureLocalServiceUtil.getExportActionableDynamicQuery(
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

		ExportActionableDynamicQuery actionableDynamicQuery =
			JournalArticleLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		actionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				JournalArticle.class.getName(), DDMStructure.class.getName()));

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDDMTemplateActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			DDMTemplateLocalServiceUtil.getExportActionableDynamicQuery(
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
						DDMStructureLocalServiceUtil.dynamicQuery();

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

}