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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.exportimport.kernel.lar.BasePortletDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportDateUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryRegistryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.constants.DLConstants;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 * @author Sampsa Sohlman
 * @author Máté Thurzó
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
@Component(
	property = "javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
	service = PortletDataHandler.class
)
public class DLAdminPortletDataHandler extends BasePortletDataHandler {

	public static final String[] CLASS_NAMES = {
		DLFileEntryConstants.getClassName(), DLFileEntryType.class.getName(),
		DLFileShortcutConstants.getClassName(),
		DLFolderConstants.getClassName(), Repository.class.getName(),
		RepositoryEntry.class.getName()
	};

	public static final String NAMESPACE = "document_library";

	public static final String SCHEMA_VERSION = "1.0.0";

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getNamespace() {
		return NAMESPACE;
	}

	@Override
	public String getResourceName() {
		return DLConstants.RESOURCE_NAME;
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
	protected void activate() {
		setDataLocalized(true);
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
			StagedModelRepository<?> stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DLFolder.class.getName());

			ActionableDynamicQuery folderActionableDynamicQuery =
				stagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

			folderActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "documents")) {
			StagedModelRepository<?> stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DLFileEntry.class.getName());

			ActionableDynamicQuery fileEntryActionableDynamicQuery =
				stagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

			fileEntryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(
				NAMESPACE, "document-types")) {

			StagedModelRepository<?> stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DLFileEntryType.class.getName());

			ActionableDynamicQuery fileEntryTypeActionableDynamicQuery =
				stagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

			fileEntryTypeActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "repositories")) {
			StagedModelRepository<?> stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					Repository.class.getName());

			ActionableDynamicQuery repositoryActionableDynamicQuery =
				stagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

			repositoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "shortcuts")) {
			StagedModelRepository<?> stagedModelRepository =
				StagedModelRepositoryRegistryUtil.getStagedModelRepository(
					DLFileShortcut.class.getName());

			ActionableDynamicQuery fileShortcutActionableDynamicQuery =
				stagedModelRepository.getExportActionableDynamicQuery(
					portletDataContext);

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

		StagedModelRepository<?> fileShortcutStagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				DLFileShortcut.class.getName());

		ActionableDynamicQuery dlFileShortcutActionableDynamicQuery =
			fileShortcutStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		dlFileShortcutActionableDynamicQuery.performCount();

		StagedModelRepository<?> fileEntryStagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				DLFileEntry.class.getName());

		ActionableDynamicQuery fileEntryActionableDynamicQuery =
			fileEntryStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		fileEntryActionableDynamicQuery.performCount();

		StagedModelRepository<?> dlFileEntryTypeStagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				DLFileEntryType.class.getName());

		ActionableDynamicQuery fileEntryTypeActionableDynamicQuery =
			dlFileEntryTypeStagedModelRepository.
				getExportActionableDynamicQuery(portletDataContext);

		fileEntryTypeActionableDynamicQuery.performCount();

		StagedModelRepository<?> folderStagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				DLFolder.class.getName());

		ActionableDynamicQuery folderActionableDynamicQuery =
			folderStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		folderActionableDynamicQuery.performCount();

		StagedModelRepository<?> repositoryStagedModelRepository =
			StagedModelRepositoryRegistryUtil.getStagedModelRepository(
				Repository.class.getName());

		ActionableDynamicQuery repositoryActionableDynamicQuery =
			repositoryStagedModelRepository.getExportActionableDynamicQuery(
				portletDataContext);

		repositoryActionableDynamicQuery.performCount();
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private Staging _staging;

}