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

package com.liferay.portal.store.gcs;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.nio.charset.StandardCharsets;

import org.osgi.service.component.annotations.Component;

/**
 * @author Edward C. Han
 * @author Shanon Mathai
 */
@Component(immediate = true, service = GCSKeyTransformer.class)
public class GCSKeyTransformerImpl implements GCSKeyTransformer {

	@Override
	public String getDirectoryKey(
		long companyId, long repositoryId, String folderName) {

		return getFileKey(companyId, repositoryId, folderName);
	}

	@Override
	public String getFileKey(
		long companyId, long repositoryId, String fileName) {

		StringBundler sb = new StringBundler(4);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(_getNormalizedFileName(fileName));

		return sb.toString();
	}

	@Override
	public String getFileVersionKey(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		StringBundler sb = new StringBundler(6);

		sb.append(companyId);
		sb.append(StringPool.SLASH);
		sb.append(repositoryId);
		sb.append(_getNormalizedFileName(fileName));
		sb.append(StringPool.SLASH);
		sb.append(versionLabel);

		return sb.toString();
	}

	@Override
	public String getRepositoryKey(long companyId, long repositoryId) {
		return companyId + StringPool.SLASH + repositoryId;
	}

	private String _getNormalizedFileName(String fileName) {
		String normalizedFileName = fileName;

		if (fileName.startsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(1);
		}

		if (fileName.endsWith(StringPool.SLASH)) {
			normalizedFileName = normalizedFileName.substring(
				0, normalizedFileName.length() - 1);
		}

		normalizedFileName = _obfuscate(normalizedFileName);

		return StringPool.SLASH + normalizedFileName;
	}

	private String _obfuscate(String fileName) {
		HashFunction sha1 = Hashing.sha1();

		HashCode hashCode = sha1.hashString(fileName, StandardCharsets.UTF_8);

		return hashCode.toString();
	}

}