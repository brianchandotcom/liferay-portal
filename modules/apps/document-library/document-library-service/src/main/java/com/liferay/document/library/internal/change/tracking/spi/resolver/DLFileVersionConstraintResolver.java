/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.comparator.DLFileVersionVersionComparator;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.change.tracking.sql.CTSQLModeThreadLocal;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.persistence.change.tracking.CTPersistence;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(service = ConstraintResolver.class)
public class DLFileVersionConstraintResolver
	implements ConstraintResolver<DLFileVersion> {

	@Override
	public String getConflictDescriptionKey() {
		return "duplicate-document-version";
	}

	@Override
	public Class<DLFileVersion> getModelClass() {
		return DLFileVersion.class;
	}

	@Override
	public String getResolutionDescriptionKey() {
		return "the-document-version-was-updated-to-latest";
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return LanguageResources.getResourceBundle(locale);
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"fileEntryId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverContext<DLFileVersion> constraintResolverContext)
		throws PortalException {

		DLFileVersion dlFileVersion =
			constraintResolverContext.getSourceCTModel();

		DLFileVersion latestFileVersion = constraintResolverContext.getInTarget(
			() -> _dlFileVersionLocalService.getLatestFileVersion(
				dlFileVersion.getFileEntryId(), true));

		if (Validator.isNull(latestFileVersion.getVersion())) {
			return;
		}

		List<DLFileVersion> dlFileVersions;

		try (SafeCloseable safeCloseable1 =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					dlFileVersion.getCtCollectionId());
			SafeCloseable safeCloseable2 =
				CTSQLModeThreadLocal.setCTSQLModeWithSafeCloseable(
					CTSQLModeThreadLocal.CTSQLMode.CT_ONLY)) {

			dlFileVersions = _dlFileVersionLocalService.getFileVersions(
				dlFileVersion.getFileEntryId(), WorkflowConstants.STATUS_ANY);
		}

		dlFileVersions.sort(DLFileVersionVersionComparator.getInstance(true));

		String newFileVersion = null;
		String previousFileVersion = null;

		List<DLFileVersion> newDLFileVersions = new ArrayList<>();
		Map<String, String> versionMap = new HashMap<>();

		for (DLFileVersion currentDLFileVersion : dlFileVersions) {
			if (!constraintResolverContext.isSourceCTModel(
					currentDLFileVersion)) {

				continue;
			}

			int[] ctVersionParts = StringUtil.split(
				currentDLFileVersion.getVersion(), StringPool.PERIOD, 0);
			int[] latestVersionParts = StringUtil.split(
				latestFileVersion.getVersion(), StringPool.PERIOD, 0);

			if (ctVersionParts.length != latestVersionParts.length) {
				return;
			}

			StringBundler sb = new StringBundler(2 * latestVersionParts.length);

			if (previousFileVersion == null) {
				sb.append(latestVersionParts[0]);
				sb.append(StringPool.PERIOD);
				sb.append(latestVersionParts[1] + 1);
			}
			else {
				int[] previousVersionParts = StringUtil.split(
					previousFileVersion, StringPool.PERIOD, 0);

				int versionIncrease = Math.abs(
					GetterUtil.getInteger(ctVersionParts[0]) -
						GetterUtil.getInteger(previousVersionParts[0]));

				if (versionIncrease > 0) {
					sb.append(latestVersionParts[0] + 1);
					sb.append(".0");
				}
				else {
					sb.append(latestVersionParts[0]);
					sb.append(StringPool.PERIOD);
					sb.append(latestVersionParts[1] + 1);
				}
			}

			newFileVersion = sb.toString();

			previousFileVersion = currentDLFileVersion.getVersion();

			String oldStoreFileName = currentDLFileVersion.getStoreFileName();

			currentDLFileVersion.setVersion(newFileVersion);
			currentDLFileVersion.setStoreUUID(
				String.valueOf(UUID.randomUUID()));

			newDLFileVersions.add(currentDLFileVersion);

			versionMap.put(
				currentDLFileVersion.getStoreFileName(), oldStoreFileName);

			latestFileVersion = currentDLFileVersion;
		}

		if (newFileVersion == null) {
			return;
		}

		CTPersistence ctPersistence =
			_dlFileVersionLocalService.getCTPersistence();

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			dlFileVersion.getFileEntryId());

		for (int i = newDLFileVersions.size() - 1; i >= 0; i--) {
			DLFileVersion currentDLFileVersion = newDLFileVersions.get(i);

			DLFileVersion updatedDLFileVersion =
				_dlFileVersionLocalService.getFileVersion(
					currentDLFileVersion.getFileVersionId());

			updatedDLFileVersion.setVersion(currentDLFileVersion.getVersion());
			updatedDLFileVersion.setStoreUUID(
				currentDLFileVersion.getStoreUUID());

			String newStoreFileName = updatedDLFileVersion.getStoreFileName();

			String oldStoreFileName = versionMap.get(newStoreFileName);

			try (InputStream inputStream = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					oldStoreFileName)) {

				_dlFileVersionLocalService.updateDLFileVersion(
					updatedDLFileVersion);

				DLStoreUtil.addFile(
					DLStoreRequest.builder(
						dlFileEntry.getCompanyId(),
						dlFileEntry.getRepositoryId(), dlFileEntry.getName()
					).versionLabel(
						newStoreFileName
					).build(),
					inputStream);
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}

			DLStoreUtil.deleteFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
				dlFileEntry.getName(), oldStoreFileName);

			Session session = ctPersistence.getCurrentSession();

			session.flush();

			session.clear();
		}

		dlFileEntry.setVersion(newFileVersion);

		_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}