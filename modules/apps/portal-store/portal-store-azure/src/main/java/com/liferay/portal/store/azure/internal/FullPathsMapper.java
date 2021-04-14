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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Objects;
import java.util.Optional;

/**
 * @author Josef Sustacek
 */
public class FullPathsMapper {

	public static final String IMPL_TYPE = "full-path";

	public static final Log _log = LogFactoryUtil.getLog(FullPathsMapper.class);

	public String toAzureBlobName(
		long companyId, long repositoryId, String fileName,
		String versionLabel) {

		Objects.requireNonNull(fileName);
		Objects.requireNonNull(versionLabel);

		String blobName = toFullAzurePath(
			companyId, repositoryId, fileName, Optional.of(versionLabel));

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted Liferay fileName to blob name: %s (v: %s) -> %s",
					fileName, versionLabel, blobName));
		}

		return blobName;
	}

	public String toAzureBlobsPrefix(
		long companyId, long repositoryId, String dirName) {

		Objects.requireNonNull(dirName);

		String dirPath = toFullAzurePath(
			companyId, repositoryId, dirName, Optional.empty());

		// append the path delimiter, to make sure we don't match a file which has
		// a name being a prefix of some other file, e.g:

		//	some-dir/
		//		- file-1.pdf/1.0
		//		- file-1.pdf-other.pdf/2.0

		// without the slash at the end, "some-dir/file-1.pdf" would match both blobs

		String dirPathEndingWithDelimiter = dirPath + StringPool.SLASH;

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted Liferay dirName to blobs prefix: %s -> %s",
					dirName, dirPathEndingWithDelimiter));
		}

		return dirPathEndingWithDelimiter;
	}

	public String toLiferayDirName(
		long companyId, long repositoryId, String azureBlobsPrefix) {

		Objects.requireNonNull(azureBlobsPrefix);

		if (!azureBlobsPrefix.endsWith(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				"'azureBlobsPrefix' must end with " + StringPool.SLASH);
		}

		String rootBlobPathWithDelimiter =
			toFullAzurePath(
				companyId, repositoryId, StringPool.BLANK, Optional.empty()) +
					StringPool.SLASH;

		if (!azureBlobsPrefix.startsWith(rootBlobPathWithDelimiter)) {
			throw new IllegalArgumentException(
				String.format(
					"It looks like blobs prefix '%s' does not belong to company: %s " +
						"and repository: %s (the blobs prefix does not begin with '%s')",
					azureBlobsPrefix, companyId, repositoryId,
					rootBlobPathWithDelimiter));
		}

		// drop the root part

		String dirName = azureBlobsPrefix.substring(
			rootBlobPathWithDelimiter.length());

		// drop the delimiter at the end, if any

		if (!dirName.isEmpty() && dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.substring(0, dirName.length() - 1);
		}

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted blobs prefix to Liferay dirName: %s -> %s",
					azureBlobsPrefix, dirName));
		}

		return dirName;
	}

	String toFullAzurePath(
		long companyId, long repositoryId, String liferayPath,
		Optional<String> versionLabel) {

		Objects.requireNonNull(liferayPath);
		Objects.requireNonNull(versionLabel);

		// TODO normalize the 'liferayPath' somehow more, like escape special characters etc.?

		String pathNoDelimitersBegin =
			liferayPath.startsWith(StringPool.SLASH) ? liferayPath.substring(1) :
				liferayPath;

		String pathNoDelimitersBeginOrEnd =
			pathNoDelimitersBegin.endsWith(StringPool.SLASH) ?
				pathNoDelimitersBegin.substring(
					0, pathNoDelimitersBegin.length() - 1) :
						pathNoDelimitersBegin;

		StringBundler fullPath = new StringBundler(
			7
		).append(
			companyId
		).append(
			StringPool.SLASH
		).append(
			repositoryId
		);

		if (!pathNoDelimitersBeginOrEnd.isEmpty()) {
			fullPath.append(
				StringPool.SLASH
			).append(
				pathNoDelimitersBeginOrEnd
			);
		}

		if (versionLabel.isPresent() &&
			!versionLabel.get(
			).isEmpty()) {

			fullPath.append(
				StringPool.SLASH
			).append(
				versionLabel.get()
			);
		}

		String fullPathString = fullPath.toString();

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"liferayPath to full Azure path: %s (v: %s) -> %s",
					liferayPath, versionLabel.orElse(null), fullPathString));
		}

		return fullPathString;
	}

	public String toLiferayFileName(
		long companyId, long repositoryId, String azureBlobName) {

		Objects.requireNonNull(azureBlobName);

		String rootPrefix =
			toFullAzurePath(
				companyId, repositoryId, StringPool.BLANK, Optional.empty()) +
					StringPool.SLASH;

		if (!azureBlobName.startsWith(rootPrefix)) {
			throw new IllegalArgumentException(
				String.format(
					"It looks like blob '%s' does not belong to company: %s " +
						"and repository: %s (does not begin with '%s')",
					azureBlobName, companyId, repositoryId, rootPrefix));
		}

		// drop the root part "${companyId}/${repositoryId}/"

		String fileNamePathWithVersion = azureBlobName.substring(
			rootPrefix.length());

		// TODO there might be invalid files, directly in the "root", ignore them?

		if (fileNamePathWithVersion.isEmpty() ||
			!fileNamePathWithVersion.contains(StringPool.SLASH)) {

			throw new IllegalArgumentException(
				String.format(
					"The blob '%s' does not conform to the pattern ${companyId}/${repositoryId}/${fileName}/${versionLabel} -- " +
						"missing the '/${versionLabel}' part. Delete the blob in Azure to " +
							"fix this (there should be no blobs directly under ${companyId}/${repositoryId}, " +
								"only \"sub-directories\").",
					azureBlobName));
		}

		// drop the version part ("/{versionLabel}")

		String fileName = fileNamePathWithVersion.substring(
			0, fileNamePathWithVersion.lastIndexOf(StringPool.SLASH));

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted blob name to Liferay fileName: %s -> %s",
					azureBlobName, fileName));
		}

		return fileName;
	}

}