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

package com.liferay.portal.store.s3;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Edward C. Han
 */
public class S3KeyTransformer {

	public String getDirectoryKey(
		long companyId, long repositoryId, String folderName) {

		return getFileKey(companyId, repositoryId, folderName);
	}

	public String getFileKey(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(4);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(getNormalizedFileName(fileName));

		return sb.toString();
	}

	public String getFileName(String key) {

		// Convert /${companyId}/${repositoryId}/${dirName}/${fileName}
		// /${versionLabel} to ${dirName}/${fileName}

		int x = key.indexOf(CharPool.SLASH);

		x = key.indexOf(CharPool.SLASH, x + 1);

		int y = key.lastIndexOf(CharPool.SLASH);

		return key.substring(x + 1, y);
	}

	public String getFileVersionKey(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(6);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(getNormalizedFileName(fileName));
		sb.append(StringPool.SLASH);
		sb.append(versionLabel);

		return sb.toString();
	}

	public String getNormalizedFileName(String fileName) {
		String normalizedFileName = fileName;

		if (!fileName.startsWith(StringPool.SLASH)) {
			normalizedFileName = StringPool.SLASH + normalizedFileName;
		}

		if (fileName.endsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(
				0, normalizedFileName.length() - 1);
		}

		return normalizedFileName;
	}

	public String getRepositoryKey(long companyId, long repositoryId) {
		return companyId + StringPool.SLASH + repositoryId;
	}

	public String moveKey(String key, String oldPrefix, String newPrefix) {
		String name = key.substring(oldPrefix.length());
		return newPrefix.concat(name);
	}

}