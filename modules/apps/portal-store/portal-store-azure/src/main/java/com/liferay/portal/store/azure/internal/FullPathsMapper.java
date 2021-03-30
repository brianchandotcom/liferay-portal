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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.service.component.annotations.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * This mapper uses the same mapping as the impl in the <code>S3Store</code>:
 * Liferay/'s '${fileName}', having version named '${versionLabel}' (with
 * companyId + repositoryId always being known as well) translates to Azure Blob
 * with a name like:
 * <pre>
 * 		${companyId}/${repositoryId}/{$fileName}/${versionLabel}
 * </pre>
 * <p>
 * A <code>dirName</code> translates to:
 * <pre>
 *		${companyId}/${repositoryId}/{$dirName}
 * </pre>
 * 
 * @author Josef Sustacek
 */
@Component(
	immediate = true,
	property = LiferayToAzurePathsMapper.IMPL_TYPE_OSGI_PROPERTY + "=" + FullPathsMapper.IMPL_TYPE,
	service = LiferayToAzurePathsMapper.class
)
public class FullPathsMapper implements LiferayToAzurePathsMapper {

	public static final String IMPL_TYPE = "full-path";

	@Override
	// Transform
	// 		${fileName} + ${versionLabel}
	// to
	// 		${companyId}/${repositoryId}/${fileName}/${versionLabel}
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

	@Override
	// Transform
	// 		${dirName}
	// to
	// 		${companyId}/${repositoryId}/${dirName}/
	public String toAzureBlobsPrefix(
		long companyId, long repositoryId, String dirName) {

		Objects.requireNonNull(dirName);

		String dirPath = toFullAzurePath(companyId, repositoryId, dirName, Optional.empty());

		// append the path delimiter, to make sure we don't match a file which has
		// a name being a prefix of some other file, e.g:
		//	some-dir/
		//		- file-1.pdf/1.0
		//		- file-1.pdf-other.pdf/2.0

		// without the slash at the end, "some-dir/file-1.pdf" would match both blobs

		String dirPathEndingWithDelimiter = dirPath + PATH_DELIMITER;

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted Liferay dirName to blobs prefix: %s -> %s",
					dirName, dirPathEndingWithDelimiter));
		}

		return dirPathEndingWithDelimiter;
	}

	@Override
	// Transform
	// 		${companyId}/${repositoryId}/${fileName}/${versionLabel}
	// to
	// 		${fileName}
	public String toLiferayFileName(
		long companyId, long repositoryId, String azureBlobName) {

		Objects.requireNonNull(azureBlobName);

		String rootPrefix =
			toFullAzurePath(companyId, repositoryId, StringPool.BLANK, Optional.empty())
			+ PATH_DELIMITER;

		if (!azureBlobName.startsWith(rootPrefix)) {
			throw new IllegalArgumentException(
				String.format(
					"It looks like blob '%s' does not belong to company: %s " +
						"and repository: %s (does not begin with '%s')",
					azureBlobName, companyId, repositoryId, rootPrefix));
		}

		// drop the root part "${companyId}/${repositoryId}/"
		String fileNamePathWithVersion = azureBlobName.substring(rootPrefix.length());

		// TODO there might be invalid files, directly in the "root", ignore them?
		if (fileNamePathWithVersion.isEmpty() || !fileNamePathWithVersion.contains(PATH_DELIMITER)) {
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
			0, fileNamePathWithVersion.lastIndexOf(PATH_DELIMITER));

		if (_log.isTraceEnabled()) {
			_log.trace(
				String.format(
					"Converted blob name to Liferay fileName: %s -> %s",
					azureBlobName, fileName));
		}

		return fileName;
	}

	@Override
	// Transform
	// 		${companyId}/${repositoryId}/${dirName}/
	// to
	// 		${dirName}
	public String toLiferayDirName(
		long companyId, long repositoryId, String azureBlobsPrefix) {

		Objects.requireNonNull(azureBlobsPrefix);

		if (!azureBlobsPrefix.endsWith(PATH_DELIMITER)) {
			throw new IllegalArgumentException(
				"'azureBlobsPrefix' must end with " + PATH_DELIMITER);
		}

		String rootBlobPathWithDelimiter =
			toFullAzurePath(companyId, repositoryId, StringPool.BLANK, Optional.empty())
			+ PATH_DELIMITER;

		if (!azureBlobsPrefix.startsWith(rootBlobPathWithDelimiter)) {
			throw new IllegalArgumentException(
				String.format(
					"It looks like blobs prefix '%s' does not belong to company: %s " +
						"and repository: %s (the blobs prefix does not begin with '%s')",
					azureBlobsPrefix, companyId, repositoryId, rootBlobPathWithDelimiter));
		}

		// drop the root part
		String dirName = azureBlobsPrefix.substring(rootBlobPathWithDelimiter.length());

		// drop the delimiter at the end, if any
		if (!dirName.isEmpty() && dirName.endsWith(PATH_DELIMITER)) {
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

	/**
	 * Returns the <code>liferayPath</code>, making sure that:
	 * <ul>
	 *     <li>it does not start with <code>/</code></li>
	 *     <li>it does not end with <code>/</code></li>
	 * </ul>
	 * The <code>liferayPath</code> may denote either a file or a directory in Liferay.
	 * When <code>versionLabel</code> is passed in (makes only sense for a file),
	 * it's appended to the liferayPath.
	 *
	 * @param companyId
	 * @param repositoryId
	 * @param liferayPath
	 * @param versionLabel
	 * @return
	 */
	String toFullAzurePath(
		long companyId, long repositoryId, String liferayPath, Optional<String> versionLabel) {

		Objects.requireNonNull(liferayPath);
		Objects.requireNonNull(versionLabel);

		// TODO normalize the 'liferayPath' somehow more, like escape special characters etc.?

		String pathNoDelimitersBegin =
			liferayPath.startsWith(PATH_DELIMITER) ? liferayPath.substring(1) : liferayPath;

		String pathNoDelimitersBeginOrEnd = pathNoDelimitersBegin.endsWith(PATH_DELIMITER)
			? pathNoDelimitersBegin.substring(0, pathNoDelimitersBegin.length() - 1) :
			pathNoDelimitersBegin;

		StringBundler fullPath = new StringBundler(7)
			.append(companyId)
			.append(PATH_DELIMITER)
			.append(repositoryId);

		if (!pathNoDelimitersBeginOrEnd.isEmpty()) {
			fullPath
				.append(PATH_DELIMITER)
				.append(pathNoDelimitersBeginOrEnd);
		}

		if (versionLabel.isPresent() && !versionLabel.get().isEmpty()) {
			fullPath
				.append(PATH_DELIMITER)
				.append(versionLabel.get());
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

	public static final Log _log = LogFactoryUtil.getLog(FullPathsMapper.class);
}
