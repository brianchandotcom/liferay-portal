/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
 * <p>
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */
package com.liferay.portal.store.azure.internal;

import com.liferay.petra.string.StringPool;

/**
 * A service to convert between Liferay-style <code>fileName</code>s and <code>dirName</code>s
 * and Azure-style Blob names and Blob name prefixes.
 *
 * <p>
 *     <b>Note</b>: Both <code>fileName</code> and <code>dirName</code> may contain
 *  a path delimiter. It's assumed this is always a '/' (forward slash) in Liferay.
 *  This is a constant defined as <code>LiferayToAzurePathsMapper.PATH_DELIMITER</code>
 *  and the implementations of this interface should utilize this constant.
 *
 * @author Josef Sustacek
 */
public interface LiferayToAzurePathsMapper {

	String IMPL_TYPE_OSGI_PROPERTY = "impl.type";

	// Looks like all Liferay stores assume the delimiter is always a forward slash,
	// but just in case, make this a constant.
	String PATH_DELIMITER = StringPool.SLASH;

	/**
	 * Translates a Liferay fileName (with a version) into a Blob in Azure Storage.
	 * @param companyId
	 * @param repositoryId
	 * @param fileName
	 * @param versionLabel
	 * @return
	 */
	String toAzureBlobName(long companyId, long repositoryId, String fileName, String versionLabel);

	/**
	 * Translates a Liferay dirName into a Blobs' prefix in Azure Storage.
	 * @param companyId
	 * @param repositoryId
	 * @param dirName
	 * @return
	 */
	String toAzureBlobsPrefix(long companyId, long repositoryId, String dirName);

	/**
	 * Translates the Azure Blob path into a Liferay fileName.
	 * @param companyId
	 * @param repositoryId
	 * @param azureBlobName
	 * @return
	 */
	String toLiferayFileName(long companyId, long repositoryId, String azureBlobName);

	/**
	 * Translates the Azure Blobs' prefix into a Liferay dirName.
	 * @param companyId
	 * @param repositoryId
	 * @param azureBlobsPrefix
	 * @return
	 */
	String toLiferayDirName(long companyId, long repositoryId, String azureBlobsPrefix);

}
