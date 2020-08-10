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

package com.liferay.commerce.data.integration.talend;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.repository.model.FileEntry;

import java.io.InputStream;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface TalendProcessTypeHelper {

	public FileEntry addFileEntry(
			long companyId, long userId, long commerceDataIntegrationProcessId,
			String fileName, long size, String contentType,
			InputStream inputStream)
		throws Exception;

	public FileEntry getFileEntry(long commerceDataIntegrationProcessId)
		throws Exception;

}