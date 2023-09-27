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
import com.liferay.document.library.kernel.util.comparator.VersionNumberComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.language.LanguageResources;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
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

		List<String> latestVersionParts = constraintResolverContext.getInTarget(
			() -> {
				DLFileVersion latestFileVersion =
					_dlFileVersionLocalService.getLatestFileVersion(
						dlFileVersion.getFileEntryId(), false);

				return StringUtil.split(
					latestFileVersion.getVersion(), CharPool.PERIOD);
			});

		if (latestVersionParts.isEmpty()) {
			return;
		}

		List<DLFileVersion> fileVersions =
			_dlFileVersionLocalService.getFileVersions(
				dlFileVersion.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		fileVersions.sort(new DLFileVersionVersionComparator(true));

		String newFileVersion = null;
		DLFileVersion previousFileVersion = null;

		Map<String, String> versionMap = new TreeMap<>(
			new VersionNumberComparator());

		for (DLFileVersion fileVersion : fileVersions) {
			if (!constraintResolverContext.isSourceCTModel(fileVersion)) {
				previousFileVersion = fileVersion;

				continue;
			}

			if (previousFileVersion == null) {
				return;
			}

			List<String> ctVersionParts = StringUtil.split(
				fileVersion.getVersion(), CharPool.PERIOD);
			List<String> previousVersionParts = StringUtil.split(
				previousFileVersion.getVersion(), CharPool.PERIOD);

			if ((latestVersionParts.size() != ctVersionParts.size()) ||
				(latestVersionParts.size() != previousVersionParts.size())) {

				return;
			}

			StringBundler sb = new StringBundler(2 * latestVersionParts.size());

			for (int i = 0; i < latestVersionParts.size(); i++) {
				int versionIncrease = Math.abs(
					GetterUtil.getInteger(ctVersionParts.get(i)) -
						GetterUtil.getInteger(previousVersionParts.get(i)));

				if (versionIncrease > 0) {
					int latestVersionPart = GetterUtil.getInteger(
						latestVersionParts.get(i));

					sb.append(latestVersionPart + versionIncrease);

					for (int j = i + 1; j < ctVersionParts.size(); j++) {
						sb.append(".0");
					}

					break;
				}

				sb.append(latestVersionParts.get(i));
				sb.append(StringPool.PERIOD);
			}

			newFileVersion = sb.toString();

			String oldStoreFileName = fileVersion.getStoreFileName();

			fileVersion.setVersion(newFileVersion);
			fileVersion.setStoreUUID(String.valueOf(UUID.randomUUID()));

			fileVersion = _dlFileVersionLocalService.updateDLFileVersion(
				fileVersion);

			versionMap.put(oldStoreFileName, fileVersion.getStoreFileName());

			previousFileVersion = fileVersion;
		}

		if (newFileVersion == null) {
			return;
		}

		DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

		dlFileEntry.setVersion(newFileVersion);

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		for (Map.Entry<String, String> entry : versionMap.entrySet()) {
			String oldStoreFileName = entry.getKey();
			String newStoreFileName = entry.getValue();

			try (InputStream inputStream = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
					dlFileEntry.getName(), oldStoreFileName)) {

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
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}