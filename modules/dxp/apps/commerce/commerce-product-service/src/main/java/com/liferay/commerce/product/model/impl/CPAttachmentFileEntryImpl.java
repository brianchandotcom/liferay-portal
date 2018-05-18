/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPAttachmentFileEntryImpl extends CPAttachmentFileEntryBaseImpl {

	public CPAttachmentFileEntryImpl() {
	}

	@Override
	public FileEntry getFileEntry() throws PortalException {
		return DLAppLocalServiceUtil.getFileEntry(getFileEntryId());
	}

}