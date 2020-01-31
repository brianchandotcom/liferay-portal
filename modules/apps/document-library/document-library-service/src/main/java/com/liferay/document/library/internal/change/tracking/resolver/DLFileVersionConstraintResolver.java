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

package com.liferay.document.library.internal.change.tracking.resolver;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.resolver.ConstraintResolver;
import com.liferay.change.tracking.resolver.helper.ConstraintResolverHelper;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.util.comparator.DLFileVersionVersionComparator;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
		return ResourceBundleUtil.getBundle(
			locale, "com.liferay.document.library.service");
	}

	@Override
	public String[] getUniqueIndexColumnNames() {
		return new String[] {"fileEntryId", "version"};
	}

	@Override
	public void resolveConflict(
			ConstraintResolverHelper<DLFileVersion> constraintResolverHelper)
		throws PortalException {

		DLFileVersion dlFileVersion =
			constraintResolverHelper.getSourceCTModel();

		List<String> latestVersionParts = constraintResolverHelper.getInTarget(
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

		for (DLFileVersion fileVersion : fileVersions) {
			if (fileVersion.getCtCollectionId() ==
					CTConstants.CT_COLLECTION_ID_PRODUCTION) {

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

			StringBundler sb = new StringBundler();

			boolean increased = false;

			for (int i = 0; i < ctVersionParts.size(); i++) {
				if (increased) {
					sb.append(0);
				}
				else {
					int versionIncrease = Math.abs(
						GetterUtil.getInteger(ctVersionParts.get(i)) -
							GetterUtil.getInteger(previousVersionParts.get(i)));

					if (versionIncrease > 0) {
						increased = true;
					}

					int latestVersionPart = GetterUtil.getInteger(
						latestVersionParts.get(i));

					sb.append(latestVersionPart + versionIncrease);
				}

				sb.append(StringPool.PERIOD);
			}

			sb.setIndex(sb.index() - 1);

			newFileVersion = sb.toString();

			fileVersion.setVersion(newFileVersion);

			_dlFileVersionLocalService.updateDLFileVersion(fileVersion);

			previousFileVersion = fileVersion;
		}

		if (newFileVersion != null) {
			DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

			dlFileEntry.setVersion(newFileVersion);

			_dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}