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

package com.liferay.document.library.internal.verify;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.verify.VerifyProcess;

import java.io.InputStream;

import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.springframework.context.ApplicationContext;

/**
 * @author Raymond Augé
 * @author Douglas Wong
 * @author Alexander Chow
 */
@Component(
	immediate = true,
	property = "verify.process.name=com.liferay.document.library.service",
	service = VerifyProcess.class
)
public class DLServiceVerifyProcess extends VerifyProcess {

	protected void checkDLFileEntryMetadata() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<DLFileEntryMetadata> mismatchedCompanyIdDLFileEntryMetadatas =
				_dlFileEntryMetadataLocalService.
					getMismatchedCompanyIdFileEntryMetadatas();

			if (_log.isDebugEnabled()) {
				int size = mismatchedCompanyIdDLFileEntryMetadatas.size();

				_log.debug(
					StringBundler.concat(
						"Deleting ", size,
						" file entry metadatas with mismatched company IDs"));
			}

			for (DLFileEntryMetadata dlFileEntryMetadata :
					mismatchedCompanyIdDLFileEntryMetadatas) {

				deleteUnusedDLFileEntryMetadata(dlFileEntryMetadata);
			}

			List<DLFileEntryMetadata> noStructuresDLFileEntryMetadatas =
				_dlFileEntryMetadataLocalService.
					getNoStructuresFileEntryMetadatas();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Deleting " + noStructuresDLFileEntryMetadatas.size() +
						" file entry metadatas with no structures");
			}

			for (DLFileEntryMetadata dlFileEntryMetadata :
					noStructuresDLFileEntryMetadatas) {

				deleteUnusedDLFileEntryMetadata(dlFileEntryMetadata);
			}
		}
	}

	protected void checkFileVersionMimeTypes(final String[] originalMimeTypes)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileVersionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion criterion = RestrictionsFactoryUtil.eq(
						"mimeType", originalMimeTypes[0]);

					for (int i = 1; i < originalMimeTypes.length; i++) {
						criterion = RestrictionsFactoryUtil.or(
							criterion,
							RestrictionsFactoryUtil.eq(
								"mimeType", originalMimeTypes[i]));
					}

					dynamicQuery.add(criterion);
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileVersion>() {

				@Override
				public void performAction(DLFileVersion dlFileVersion) {
					String title = DLUtil.getTitleWithExtension(
						dlFileVersion.getTitle(), dlFileVersion.getExtension());

					try (InputStream inputStream =
							_dlFileEntryLocalService.getFileAsStream(
								dlFileVersion.getFileEntryId(),
								dlFileVersion.getVersion(), false)) {

						String mimeType = MimeTypesUtil.getContentType(
							inputStream, title);

						if (mimeType.equals(dlFileVersion.getMimeType())) {
							return;
						}

						dlFileVersion.setMimeType(mimeType);

						_dlFileVersionLocalService.updateDLFileVersion(
							dlFileVersion);

						try {
							DLFileEntry dlFileEntry =
								dlFileVersion.getFileEntry();

							if (Objects.equals(
									dlFileEntry.getVersion(),
									dlFileVersion.getVersion())) {

								dlFileEntry.setMimeType(mimeType);

								_dlFileEntryLocalService.updateDLFileEntry(
									dlFileEntry);
							}
						}
						catch (PortalException pe) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to get file entry " +
										dlFileVersion.getFileEntryId(),
									pe);
							}
						}
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							DLFileEntry dlFileEntry =
								_dlFileEntryLocalService.fetchDLFileEntry(
									dlFileVersion.getFileEntryId());

							if (dlFileEntry == null) {
								_log.warn(
									"Unable to find file entry associated " +
										"with file version " +
											dlFileVersion.getFileVersionId(),
									e);
							}
							else {
								StringBundler sb = new StringBundler(4);

								sb.append("Unable to find file version ");
								sb.append(dlFileVersion.getVersion());
								sb.append(" for file entry ");
								sb.append(dlFileEntry.getName());

								_log.warn(sb.toString(), e);
							}
						}
					}
				}

			});

		if (_log.isDebugEnabled()) {
			long count = actionableDynamicQuery.performCount();

			_log.debug(
				StringBundler.concat(
					"Processing ", count, " file versions with mime types: ",
					StringUtil.merge(originalMimeTypes, StringPool.COMMA)));
		}

		actionableDynamicQuery.performActions();
	}

	protected void checkMimeTypes() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			String[] mimeTypes = {
				ContentTypes.APPLICATION_OCTET_STREAM,
				_MS_OFFICE_2010_TEXT_XML_UTF8
			};

			checkFileVersionMimeTypes(mimeTypes);

			if (_log.isDebugEnabled()) {
				_log.debug("Fixed file entries with invalid mime types");
			}
		}
	}

	protected void deleteUnusedDLFileEntryMetadata(
			DLFileEntryMetadata dlFileEntryMetadata)
		throws Exception {

		_dlFileEntryMetadataLocalService.deleteFileEntryMetadata(
			dlFileEntryMetadata);
	}

	@Override
	protected void doVerify() throws Exception {
		checkDLFileEntryMetadata();
		checkMimeTypes();
		updateClassNameId();
		updateFileEntryAssets();
		updateFolderAssets();
		updateStagedPortletNames();
	}

	@Reference(
		target = "(org.springframework.context.service.name=com.liferay.dynamic.data.mapping.service)",
		unbind = "-"
	)
	protected void setApplicationContext(
		ApplicationContext applicationContext) {
	}

	@Reference(unbind = "-")
	protected void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(
		target = "(&(release.bundle.symbolic.name=com.liferay.document.library.service)(release.schema.version=1.0.2))",
		unbind = "-"
	)
	protected void setRelease(Release release) {
	}

	protected void updateClassNameId() {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			runSQL(
				"update DLFileEntry set classNameId = 0 where classNameId is " +
					"null");
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fix file entries where class name ID is null",
					e);
			}
		}
	}

	protected void updateFileEntryAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<DLFileEntry> dlFileEntries =
				_dlFileEntryLocalService.getNoAssetFileEntries();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + dlFileEntries.size() +
						" file entries with no asset");
			}

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);
				FileVersion fileVersion = new LiferayFileVersion(
					dlFileEntry.getFileVersion());

				try {
					_dlAppHelperLocalService.updateAsset(
						dlFileEntry.getUserId(), fileEntry, fileVersion, null,
						null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for file entry ",
								dlFileEntry.getFileEntryId(), ": ",
								e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for file entries");
			}
		}
	}

	protected void updateFolderAssets() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<DLFolder> dlFolders =
				_dlFolderLocalService.getNoAssetFolders();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Processing " + dlFolders.size() +
						" folders with no asset");
			}

			for (DLFolder dlFolder : dlFolders) {
				Folder folder = new LiferayFolder(dlFolder);

				try {
					_dlAppHelperLocalService.updateAsset(
						dlFolder.getUserId(), folder, null, null, null);
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to update asset for folder ",
								dlFolder.getFolderId(), ": ", e.getMessage()));
					}
				}
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Assets verified for folders");
			}
		}
	}

	protected void updateStagedPortletNames() throws PortalException {
		ActionableDynamicQuery groupActionableDynamicQuery =
			_groupLocalService.getActionableDynamicQuery();

		groupActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property siteProperty = PropertyFactoryUtil.forName("site");

				dynamicQuery.add(siteProperty.eq(Boolean.TRUE));
			});

		groupActionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<Group>)group -> {
				UnicodeProperties typeSettingsProperties =
					group.getTypeSettingsProperties();

				if (typeSettingsProperties == null) {
					return;
				}

				String propertyKey = _staging.getStagedPortletId(
					DLPortletKeys.DOCUMENT_LIBRARY);

				String propertyValue = typeSettingsProperties.getProperty(
					propertyKey);

				if (Validator.isNull(propertyValue)) {
					return;
				}

				typeSettingsProperties.remove(propertyKey);

				propertyKey = _staging.getStagedPortletId(
					DLPortletKeys.DOCUMENT_LIBRARY_ADMIN);

				typeSettingsProperties.put(propertyKey, propertyValue);

				group.setTypeSettingsProperties(typeSettingsProperties);

				_groupLocalService.updateGroup(group);
			});

		groupActionableDynamicQuery.performActions();
	}

	private static final String _MS_OFFICE_2010_TEXT_XML_UTF8 =
		"text/xml; charset=\"utf-8\"";

	private static final Log _log = LogFactoryUtil.getLog(
		DLServiceVerifyProcess.class);

	private DLAppHelperLocalService _dlAppHelperLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private DLFileVersionLocalService _dlFileVersionLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private GroupLocalService _groupLocalService;

	@Reference
	private Staging _staging;

}