/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface DLFileEntryMetadataFinder {

	public java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			findByMismatchedCompanyId();

	public java.util.List
		<com.liferay.document.library.kernel.model.DLFileEntryMetadata>
			findByNoStructures();

}