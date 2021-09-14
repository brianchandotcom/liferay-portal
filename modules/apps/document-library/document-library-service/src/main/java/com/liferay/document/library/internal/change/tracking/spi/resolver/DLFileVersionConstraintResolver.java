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

package com.liferay.document.library.internal.change.tracking.spi.resolver;

import com.liferay.change.tracking.spi.resolver.ConstraintResolver;
import com.liferay.change.tracking.spi.resolver.context.ConstraintResolverContext;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.store.Store;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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

			versionMap.put(fileVersion.getVersion(), newFileVersion);

			fileVersion.setVersion(newFileVersion);

			_dlFileVersionLocalService.updateDLFileVersion(fileVersion);

			previousFileVersion = fileVersion;
		}

		if (newFileVersion == null) {
			return;
		}

		DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

		dlFileEntry.setVersion(newFileVersion);

		_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		for (Map.Entry<String, String> entry : versionMap.entrySet()) {
			String oldVersion = entry.getKey();
			String newVersion = entry.getValue();

			try (InputStream inputStream = _store.getFileAsStream(
					dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
					dlFileEntry.getName(), oldVersion)) {

				_store.addFile(
					dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
					dlFileEntry.getName(), newVersion, inputStream);
			}
			catch (IOException ioException) {
				throw new UncheckedIOException(ioException);
			}

			_store.deleteFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getRepositoryId(),
				dlFileEntry.getName(), oldVersion);
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	private volatile Store _store;

}