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

package com.liferay.document.library.web.lar;

import com.liferay.document.library.lar.xstream.FileEntryConverter;
import com.liferay.document.library.lar.xstream.FileVersionConverter;
import com.liferay.document.library.lar.xstream.FolderConverter;
import com.liferay.document.library.web.constants.DLPortletKeys;
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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.impl.RepositoryEntryImpl;
import com.liferay.portal.model.impl.RepositoryImpl;
import com.liferay.portal.repository.liferayrepository.LiferayRepositoryDefiner;
import com.liferay.portal.repository.temporaryrepository.TemporaryFileEntryRepositoryDefiner;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.constants.DLConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFileShortcutConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryTypeImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFileShortcutImpl;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileShortcutLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.permission.DLPermission;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerControl;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;
import com.liferay.portlet.exportimport.xstream.XStreamAliasRegistryUtil;
import com.liferay.portlet.exportimport.xstream.XStreamConverterRegistryUtil;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Sampsa Sohlman
 * @author Mate Thurzo
 * @author Zsolt Berentey
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN
	},
	service = PortletDataHandler.class
)
public class DLPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "document_library";

	public DLPortletDataHandler() {
		setDataLocalized(true);
		setDataPortletPreferences("rootFolderId");
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(DLFileEntryType.class),
			new StagedModelType(DLFileRank.class),
			new StagedModelType(DLFileShortcut.class),
			new StagedModelType(DLFileEntryConstants.getClassName()),
			new StagedModelType(DLFolderConstants.getClassName()),
			new StagedModelType(Repository.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "repositories", true, false, null,
				Repository.class.getName()),
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

		XStreamAliasRegistryUtil.register(DLFileEntryImpl.class, "DLFileEntry");
		XStreamAliasRegistryUtil.register(
			DLFileEntryTypeImpl.class, "DLFileEntryType");
		XStreamAliasRegistryUtil.register(
			DLFileShortcutImpl.class, "DLFileShortcut");
		XStreamAliasRegistryUtil.register(DLFolderImpl.class, "DLFolder");
		XStreamAliasRegistryUtil.register(RepositoryImpl.class, "Repository");
		XStreamAliasRegistryUtil.register(
			RepositoryEntryImpl.class, "RepositoryEntry");

		XStreamConverterRegistryUtil.register(new FileEntryConverter());
		XStreamConverterRegistryUtil.register(new FileVersionConverter());
		XStreamConverterRegistryUtil.register(new FolderConverter());
	}

	@Override
	public String getServiceName() {
		return DLConstants.SERVICE_NAME;
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

		DLAppLocalServiceUtil.deleteAll(portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			final PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPortletPermissions(DLPermission.RESOURCE_NAME);

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

		portletDataContext.importPortletPermissions(DLPermission.RESOURCE_NAME);

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
			final PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

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
			final PortletDataContext portletDataContext)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryTypeLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			actionableDynamicQuery.getAddCriteriaMethod();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property property = PropertyFactoryUtil.forName("groupId");

					dynamicQuery.add(
						property.in(
							new Long[] {portletDataContext.getScopeGroupId()}));
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFileEntryType dlFileEntryType = (DLFileEntryType)object;

					if (dlFileEntryType.isExportable()) {
						StagedModelDataHandlerUtil.exportStagedModel(
							portletDataContext, dlFileEntryType);
					}
				}

			});

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getDLFileShortcutActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws Exception {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			DLFileShortcutLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property property = PropertyFactoryUtil.forName("active");

					dynamicQuery.add(property.eq(Boolean.TRUE));
				}

			});
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFileShortcut dlFileShortcut = (DLFileShortcut)object;

					FileShortcut fileShortcut =
						DLAppLocalServiceUtil.getFileShortcut(
							dlFileShortcut.getFileShortcutId());

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, fileShortcut);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileShortcutConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getFileEntryActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws Exception {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					DynamicQuery fileVersionDynamicQuery =
						DynamicQueryFactoryUtil.forClass(
							DLFileVersion.class, "dlFileVersion",
							PortalClassLoaderUtil.getClassLoader());

					fileVersionDynamicQuery.setProjection(
						ProjectionFactoryUtil.property("fileEntryId"));

					fileVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"dlFileVersion.fileEntryId", "fileEntryId"));
					fileVersionDynamicQuery.add(
						RestrictionsFactoryUtil.eqProperty(
							"dlFileVersion.version", "version"));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					StagedModelDataHandler<?> stagedModelDataHandler =
						StagedModelDataHandlerRegistryUtil.
							getStagedModelDataHandler(
								DLFileEntry.class.getName());

					fileVersionDynamicQuery.add(
						statusProperty.in(
							stagedModelDataHandler.getExportableStatuses()));

					Criterion modifiedDateCriterion =
						portletDataContext.getDateRangeCriteria("modifiedDate");
					Criterion statusDateCriterion =
						portletDataContext.getDateRangeCriteria("statusDate");

					if ((modifiedDateCriterion != null) &&
						(statusDateCriterion != null)) {

						Disjunction disjunction =
							RestrictionsFactoryUtil.disjunction();

						disjunction.add(modifiedDateCriterion);
						disjunction.add(statusDateCriterion);

						fileVersionDynamicQuery.add(disjunction);
					}

					Property fileEntryIdProperty = PropertyFactoryUtil.forName(
						"fileEntryId");

					dynamicQuery.add(
						fileEntryIdProperty.in(fileVersionDynamicQuery));

					Property repositoryIdProperty = PropertyFactoryUtil.forName(
						"repositoryId");

					dynamicQuery.add(
						repositoryIdProperty.eq(
							portletDataContext.getScopeGroupId()));
				}

			});
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFileEntry dlFileEntry = (DLFileEntry)object;

					FileEntry fileEntry = DLAppLocalServiceUtil.getFileEntry(
						dlFileEntry.getFileEntryId());

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, fileEntry);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFileEntryConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getFolderActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			DLFolderLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Property property = PropertyFactoryUtil.forName(
						"repositoryId");

					dynamicQuery.add(
						property.eq(portletDataContext.getScopeGroupId()));
				}

			});
		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					DLFolder dlFolder = (DLFolder)object;

					if (dlFolder.isInTrash()) {
						return;
					}

					Folder folder = DLAppLocalServiceUtil.getFolder(
						dlFolder.getFolderId());

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, folder);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(DLFolderConstants.getClassName()));

		return exportActionableDynamicQuery;
	}

	protected ActionableDynamicQuery getRepositoryActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws Exception {

		ExportActionableDynamicQuery exportActionableDynamicQuery =
			RepositoryLocalServiceUtil.getExportActionableDynamicQuery(
				portletDataContext);

		final ActionableDynamicQuery.AddCriteriaMethod addCriteriaMethod =
			exportActionableDynamicQuery.getAddCriteriaMethod();

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					addCriteriaMethod.addCriteria(dynamicQuery);

					Conjunction conjunction =
						RestrictionsFactoryUtil.conjunction();

					Property classNameIdProperty = PropertyFactoryUtil.forName(
						"classNameId");

					long liferayRepositoryClassNameId =
						PortalUtil.getClassNameId(
							LiferayRepositoryDefiner.CLASS_NAME);

					conjunction.add(
						classNameIdProperty.ne(liferayRepositoryClassNameId));

					long tempFileRepositoryClassNameId =
						PortalUtil.getClassNameId(
							TemporaryFileEntryRepositoryDefiner.CLASS_NAME);

					conjunction.add(
						classNameIdProperty.ne(tempFileRepositoryClassNameId));

					dynamicQuery.add(conjunction);

					Disjunction disjunction =
						RestrictionsFactoryUtil.disjunction();

					Property portletIdProperty = PropertyFactoryUtil.forName(
						"portletId");

					disjunction.add(portletIdProperty.isNull());
					disjunction.add(portletIdProperty.eq(StringPool.BLANK));

					dynamicQuery.add(disjunction);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(Repository.class.getName()),
				StagedModelType.REFERRER_CLASS_NAME_ID_ALL));

		return exportActionableDynamicQuery;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLPortletDataHandler.class);

}