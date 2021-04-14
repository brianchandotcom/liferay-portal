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
	String toAzureBlobName(
		long companyId, long repositoryId, String fileName,
		String versionLabel);

	/**
	 * Translates a Liferay dirName into a Blobs' prefix in Azure Storage.
	 * @param companyId
	 * @param repositoryId
	 * @param dirName
	 * @return
	 */
	String toAzureBlobsPrefix(
		long companyId, long repositoryId, String dirName);

	/**
	 * Translates the Azure Blob path into a Liferay fileName.
	 * @param companyId
	 * @param repositoryId
	 * @param azureBlobName
	 * @return
	 */
	String toLiferayFileName(
		long companyId, long repositoryId, String azureBlobName);

	/**
	 * Translates the Azure Blobs' prefix into a Liferay dirName.
	 * @param companyId
	 * @param repositoryId
	 * @param azureBlobsPrefix
	 * @return
	 */
	String toLiferayDirName(
		long companyId, long repositoryId, String azureBlobsPrefix);

}