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

package com.liferay.document.library.web.internal.lar;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.exportimport.data.handler.DLExportableRepositoryPublisher;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Conjunction;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.Disjunction;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.LiferayRepositoryDefiner;
import com.liferay.portal.repository.temporaryrepository.TemporaryFileEntryRepositoryDefiner;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Sampsa Sohlman
 * @author Mate Thurzo
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
	},
	service = PortletDataHandler.class
)
public class DLAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "document_library";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String getSchemaVersion() {
		return SCHEMA_VERSION;
	}

	@Override
	public String getServiceName() {
		return DLConstants.SERVICE_NAME;
	}

	@Override
	public boolean isSupportsDataStrategyMirrorWithOverwriting() {
		return true;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		setDataLocalized(true);
		setDataPortletPreferences("rootFolderId");
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DLFileEntryType.class),
			new StagedModelType(DLFileShortcut.class),
			new StagedModelType(DLFileEntryConstants.getClassName()),
			new StagedModelType(DLFolderConstants.getClassName()),
			new StagedModelType(
				Repository.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "repositories", true, false, null,
				Repository.class.getName(),
				StagedModelType.REFERRER_CLASS_NAME_ALL),
			new PortletDataHandlerBoolean(
				NAMESPACE, "folders", true, false, null,
				DLFolderConstants.getClassName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "documents", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "previews-and-thumbnails")
				},
				DLFileEntryConstants.getClassName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "document-types", true, false, null,
				DLFileEntryType.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "shortcuts", true, false, null,
				DLFileShortcutConstants.getClassName()));
		setPublishToLiveByDefault(PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT);
		setRank(90);
		setStagingControls(getExportControls());

		_dlExportableRepositoryPublishers = ServiceTrackerListFactory.open(
			bundleContext, DLExportableRepositoryPublisher.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlExportableRepositoryPublishers != null) {
			_dlExportableRepositoryPublishers.close();
		}
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				DLPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_dlAppLocalService.deleteAll(portletDataContext.getScopeGroupId());
		_dlFileEntryTypeLocalService.deleteFileEntryTypes(
			portletDataContext.getScopeGroupId());

		if (portletPreferences == null) {
			return portletPreferences;
		}

		portletPreferences.setValue("enable-comment-ratings", StringPool.BLANK);
		portletPreferences.setValue("fileEntriesPerPage", StringPool.BLANK);
		portletPreferences.setValue("fileEntryColumns", StringPool.BLANK);
		portletPreferences.setValue("folderColumns", StringPool.BLANK);
		portletPreferences.setValue("foldersPerPage", StringPool.BLANK);
		portletPreferences.setValue("rootFolderId", StringPool.BLANK);
		portletPreferences.setValue("showFoldersSearch", StringPool.BLANK);
		portletPreferences.setValue("showSubfolders", StringPool.BLANK);

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DLConstants.RESOURCE_NAME);

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			ActionableDynamicQuery folderActionableDynamicQuery =
				getFolderActionableDynamicQuery(portletDataContext);

			folderActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "documents")) {
			ActionableDynamicQuery fileEntryActionableDynamicQuery =
				getFileEntryActionableDynamicQuery(portletDataContext);

			fileEntryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "document-types")) {

			ActionableDynamicQuery fileEntryTypeActionableDynamicQuery =
				getDLFileEntryTypeActionableDynamicQuery(portletDataContext);

			fileEntryTypeActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "repositories")) {
			ActionableDynamicQuery repositoryActionableDynamicQuery =
				getRepositoryActionableDynamicQuery(portletDataContext);

			repositoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "shortcuts")) {
			ActionableDynamicQuery fileShortcutActionableDynamicQuery =
				getDLFileShortcutActionableDynamicQuery(portletDataContext);

			fileShortcutActionableDynamicQuery.performActions();
		}

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPortletPermissions(DLConstants.RESOURCE_NAME);

		if (portletDataContext.getBooleanParameter(NAMESPACE, "folders")) {
			Element foldersElement =
				portletDataContext.getImportDataGroupElement(DLFolder.class);

			List<Element> folderElements = foldersElement.elements();

			for (Element folderElement : folderElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, folderElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "documents")) {
			Element fileEntriesElement =
				portletDataContext.getImportDataGroupElement(DLFileEntry.class);

			List<Element> fileEntryElements = fileEntriesElement.elements();

			for (Element fileEntryElement : fileEntryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "document-types")) {

			Element fileEntryTypesElement =
				portletDataContext.getImportDataGroupElement(
					DLFileEntryType.class);

			List<Element> fileEntryTypeElements =
				fileEntryTypesElement.elements();

			for (Element fileEntryTypeElement : fileEntryTypeElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileEntryTypeElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "repositories")) {
			Element repositoriesElement =
				portletDataContext.getImportDataGroupElement(Repository.class);

			List<Element> repositoryElements = repositoriesElement.elements();

			for (Element repositoryElement : repositoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, repositoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "shortcuts")) {
			Element fileShortcutsElement =
				portletDataContext.getImportDataGroupElement(
					DLFileShortcut.class);

			List<Element> fileShortcutElements =
				fileShortcutsElement.elements();

			for (Element fileShortcutElement : fileShortcutElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, fileShortcutElement);
			}
		}

		return portletPreferences;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		if (ExportImportDateUtil.isRangeFromLastPublishDate(
				portletDataContext)) {

			_staging.populateLastPublishDateCounts(
				portletDataContext,
				new StagedModelType[] {
					new StagedModelType(DLFileEntry.class.getName()),
					new StagedModelType(DLFileEntryType.class.getName()),
					new StagedModelType(DLFileShortcut.class.getName()),
					new StagedModelType(DLFolder.class.getName()),
					new StagedModelType(Repository.class.getName())
				});

			return;
		}

		ActionableDynamicQuery dlFileShortcutActionableDynamicQuery =
			getDLFileShortcutActionableDynamicQuery(portletDataContext);

		dlFileShortcutActionableDynamicQuery.performCount();

		ActionableDynamicQuery fileEntryActionableDynamicQuery =
			getFileEntryActionableDynamicQuery(portletDataContext);

		fileEntryActionableDynamicQuery.performCount();

		ActionableDynamicQuery fileEntryTypeActionableDynamicQuery =
			getDLFileEntryTypeActionableDynamicQuery(portletDataContext);

		fileEntryTypeActionableDynamicQuery.performCount();

		ActionableDynamicQuery folderActionableDynamicQuery =
			getFolderActionableDynamicQuery(portletDataContext);

		folderActionableDynamicQuery.performCount();

		ActionableDynamicQuery repositoryActionableDynamicQuery =
			getRepositoryActionableDynamicQuery(portletDataContext);

		repositoryActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getDLFileEntryTypeActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryTypeLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("groupId");

				dynamicQuery.add(
					property.in(
						new Long[] {portletDataContext.getScopeGroupId()}));
			});

		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntryType dlFileEntryType) -> {
				if (dlFileEntryType.isExportable()) {
					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, dlFileEntryType);
				}
			});

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDLFileShortcutActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_dlFileShortcutLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("active");

				dynamicQuery.add(property.eq(Boolean.TRUE));
			});

		exportActionableDynamicQuery.setPerformActionMethod(
			(DLFileShortcut dlFileShortcut) -> {
				FileShortcut fileShortcut = _dlAppLocalService.getFileShortcut(
					dlFileShortcut.getFileShortcutId());

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileShortcut);
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileShortcutConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getFileEntryActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			portletDataContext);

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_dlFileEntryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				DynamicQuery fileVersionDynamicQuery =
					DynamicQueryFactoryUtil.forClass(
						DLFileVersion.class, "dlFileVersion",
						PortalClassLoaderUtil.getClassLoader());

				fileVersionDynamicQuery.setProjection(
					ProjectionFactoryUtil.property("fileEntryId"));

				fileVersionDynamicQuery.add(
					RestrictionsFactoryUtil.eqProperty(
						"dlFileVersion.fileEntryId", "this.fileEntryId"));
				fileVersionDynamicQuery.add(
					RestrictionsFactoryUtil.eqProperty(
						"dlFileVersion.version", "this.version"));

				Property fileVersionStatusProperty =
					PropertyFactoryUtil.forName("dlFileVersion.status");

				StagedModelDataHandler<?> stagedModelDataHandler =
					StagedModelDataHandlerRegistryUtil.
						getStagedModelDataHandler(DLFileEntry.class.getName());

				fileVersionDynamicQuery.add(
					fileVersionStatusProperty.in(
						stagedModelDataHandler.getExportableStatuses()));

				Criterion fileVersionModifiedDateCriterion =
					portletDataContext.getDateRangeCriteria(
						"dlFileVersion.modifiedDate");
				Criterion fileVersionStatusDateCriterion =
					portletDataContext.getDateRangeCriteria(
						"dlFileVersion.statusDate");
				Criterion modifiedDateCriterion =
					portletDataContext.getDateRangeCriteria(
						"this.modifiedDate");

				if ((fileVersionStatusDateCriterion != null) &&
					(modifiedDateCriterion != null)) {

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					disjunction.add(fileVersionModifiedDateCriterion);
					disjunction.add(fileVersionStatusDateCriterion);
					disjunction.add(modifiedDateCriterion);

					fileVersionDynamicQuery.add(disjunction);
				}

				Property fileEntryIdProperty = PropertyFactoryUtil.forName(
					"fileEntryId");

				dynamicQuery.add(
					fileEntryIdProperty.in(fileVersionDynamicQuery));

				Property repositoryIdProperty = PropertyFactoryUtil.forName(
					"repositoryId");

				dynamicQuery.add(
					repositoryIdProperty.in(exportableRepositoryIds));
			});
		exportActionableDynamicQuery.setPerformActionMethod(
			(DLFileEntry dlFileEntry) -> {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					dlFileEntry.getFileEntryId());

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, fileEntry);
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileEntryConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getFolderActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			portletDataContext);

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_dlFolderLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Property property = PropertyFactoryUtil.forName("repositoryId");

				dynamicQuery.add(property.in(exportableRepositoryIds));
			});

		exportActionableDynamicQuery.setPerformActionMethod(
			(DLFolder dlFolder) -> {
				if (dlFolder.isInTrash()) {
					return;
				}

				Folder folder = _dlAppLocalService.getFolder(
					dlFolder.getFolderId());

				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, folder);
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFolderConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRepositoryActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = _getExportableRepositoryIds(
			portletDataContext);

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			_repositoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				addCriteriaMethod.addCriteria(dynamicQuery);

				Conjunction conjunction = RestrictionsFactoryUtil.conjunction();

				Property classNameIdProperty = PropertyFactoryUtil.forName(
					"classNameId");

				long liferayRepositoryClassNameId = _portal.getClassNameId(
					LiferayRepositoryDefiner.CLASS_NAME);

				conjunction.add(
					classNameIdProperty.ne(liferayRepositoryClassNameId));

				long tempFileRepositoryClassNameId = _portal.getClassNameId(
					TemporaryFileEntryRepositoryDefiner.CLASS_NAME);

				conjunction.add(
					classNameIdProperty.ne(tempFileRepositoryClassNameId));

				dynamicQuery.add(conjunction);

				Disjunction disjunction = RestrictionsFactoryUtil.disjunction();

				Property portletIdProperty = PropertyFactoryUtil.forName(
					"portletId");

				disjunction.add(portletIdProperty.isNull());
				disjunction.add(portletIdProperty.eq(StringPool.BLANK));
				disjunction.add(
					portletIdProperty.eq(DLPortletKeys.DOCUMENT_LIBRARY_ADMIN));
				disjunction.add(
					portletIdProperty.like(
						DLPortletKeys.DOCUMENT_LIBRARY + "%"));

				Property repositoryIdProperty = PropertyFactoryUtil.forName(
					"repositoryId");

				disjunction.add(
					repositoryIdProperty.in(exportableRepositoryIds));

				dynamicQuery.add(disjunction);
			});

		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				_portal.getClassNameId(Repository.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	private Collection<Long> _getExportableRepositoryIds(
		PortletDataContext portletDataContext) {

		Collection<Long> exportableRepositoryIds = new HashSet<>();

		exportableRepositoryIds.add(portletDataContext.getScopeGroupId());

		for (DLExportableRepositoryPublisher dlExportableRepositoryPublisher :
				_dlExportableRepositoryPublishers) {

			dlExportableRepositoryPublisher.publish(
				portletDataContext.getScopeGroupId(),
				exportableRepositoryIds::add);
		}

		return exportableRepositoryIds;
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	private ServiceTrackerList
		<DLExportableRepositoryPublisher, DLExportableRepositoryPublisher>
			_dlExportableRepositoryPublishers;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private Staging _staging;

}