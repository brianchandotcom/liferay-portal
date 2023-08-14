/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.importer;

import java.io.File;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Jorge Ferrer
 */
@ProviderType
public interface FragmentsImporter {

	public List<FragmentsImporterResultEntry> importFragmentEntries(
			long userId, long groupId, long fragmentCollectionId, File file,
			FragmentsImportStrategy fragmentsImportStrategy)
		throws Exception;

	public boolean validateFragmentEntries(
			long userId, long groupId, long fragmentCollectionId, File file)
		throws Exception;

}