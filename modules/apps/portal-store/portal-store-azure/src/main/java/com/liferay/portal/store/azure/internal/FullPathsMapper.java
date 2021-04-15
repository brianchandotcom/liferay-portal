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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

/**
 * @author Josef Sustacek
 */
public class FullPathsMapper {

	public String toAzureBlobName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		return toFullAzurePath(companyId, repositoryId, fileName, versionLabel);
	}

	public String toAzureBlobPrefix(
		long companyId, long repositoryId, String dirName) {

		String dirPath = toFullAzurePath(
			companyId, repositoryId, dirName, null);

		return dirPath + StringPool.SLASH;
	}

	public String toFullAzurePath(
		long companyId, long repositoryId, String liferayPath,
		String versionLabel) {

		if (StringUtil.startsWith(liferayPath, StringPool.SLASH)) {
			liferayPath = liferayPath.substring(1);
		}

		if (StringUtil.endsWith(liferayPath, StringPool.SLASH)) {
			liferayPath = liferayPath.substring(0, liferayPath.length() - 1);
		}

		StringBundler sb = new StringBundler(7);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);

		if (!liferayPath.isEmpty()) {
			sb.append(StringPool.SLASH);
			sb.append(liferayPath);
		}

		if (Validator.isNotNull(versionLabel)) {
			sb.append(StringPool.SLASH);
			sb.append(versionLabel);
		}

		return sb.toString();
	}

	public String toLiferayDirName(
		long companyId, long repositoryId, String azureBlobsPrefix) {

		if (!azureBlobsPrefix.endsWith(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				StringPool.APOSTROPHE + azureBlobsPrefix + "' must end with /");
		}

		String rootBlobPathWithDelimiter =
			toFullAzurePath(companyId, repositoryId, StringPool.BLANK, null) +
				StringPool.SLASH;

		if (!azureBlobsPrefix.startsWith(rootBlobPathWithDelimiter)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"It looks like blobs prefix '", azureBlobsPrefix,
					"' does not belong to company: ", companyId,
					" and repository: ", repositoryId,
					" (the blobs prefix does not begin with '",
					rootBlobPathWithDelimiter, "')"));
		}

		String dirName = azureBlobsPrefix.substring(
			rootBlobPathWithDelimiter.length());

		if (StringUtil.endsWith(dirName, StringPool.SLASH)) {
			return dirName.substring(0, dirName.length() - 1);
		}

		return dirName;
	}

	public String toLiferayFileName(
		long companyId, long repositoryId, String azureBlobName) {

		Objects.requireNonNull(azureBlobName);

		String rootPrefix =
			toFullAzurePath(companyId, repositoryId, StringPool.BLANK, null) +
				StringPool.SLASH;

		if (!azureBlobName.startsWith(rootPrefix)) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"It looks like blob '", azureBlobName,
					"' does not belong to company: ", companyId,
					"and repository: ", repositoryId));
		}

		String fileNamePathWithVersion = azureBlobName.substring(
			rootPrefix.length());

		if (fileNamePathWithVersion.isEmpty() ||
			!fileNamePathWithVersion.contains(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				StringBundler.concat(
					"The blob '", azureBlobName, "' does not conform to the ",
					"pattern ${companyId}/${repositoryId}/${fileName}",
					"/${versionLabel} -- missing the '/${versionLabel}' part. ",
					"Delete the blob in Azure to fix this (there should be no ",
					"blobs directly under ${companyId}/${repositoryId}, only ",
					"subfolders."));
		}

		return fileNamePathWithVersion.substring(
			0, fileNamePathWithVersion.lastIndexOf(StringPool.SLASH));
	}

}