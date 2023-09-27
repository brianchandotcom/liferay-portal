/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public class CPAttachmentFileEntryImpl extends CPAttachmentFileEntryBaseImpl {

	@Override
	public FileEntry fetchFileEntry() throws PortalException {
		if (isCDNEnabled()) {
			return null;
		}

		return DLAppLocalServiceUtil.getFileEntry(getFileEntryId());
	}

}