/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.resource.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.ItemExternalReference;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Lourdes Fernández Besada
 */
public class FileEntryUtil {

	public static long getPreviewFileEntryId(
			long groupId, ItemExternalReference itemExternalReference)
		throws Exception {

		if ((itemExternalReference == null) ||
			Validator.isNull(
				itemExternalReference.getExternalReferenceCode())) {

			return 0;
		}

		FileEntry fileEntry =
			PortletFileRepositoryUtil.
				getPortletFileEntryByExternalReferenceCode(
					itemExternalReference.getExternalReferenceCode(), groupId);

		return fileEntry.getFileEntryId();
	}

}