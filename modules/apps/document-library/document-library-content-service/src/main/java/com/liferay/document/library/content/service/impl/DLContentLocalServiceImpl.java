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

package com.liferay.document.library.content.service.impl;

import com.liferay.document.library.content.exception.NoSuchContentException;
import com.liferay.document.library.content.model.DLContent;
import com.liferay.document.library.content.service.base.DLContentLocalServiceBaseImpl;
import com.liferay.document.library.content.util.comparator.DLContentVersionComparator;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.nio.channels.FileChannel;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
@Component(
	property = "model.class.name=com.liferay.document.library.content.model.DLContent",
	service = AopService.class
)
public class DLContentLocalServiceImpl extends DLContentLocalServiceBaseImpl {

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addContent(long, long, String, String, InputStream)}
	 */
	@Deprecated
	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		byte[] bytes) {

		long contentId = counterLocalService.increment();

		DLContent dlContent = dlContentPersistence.create(contentId);

		dlContent.setCompanyId(companyId);
		dlContent.setRepositoryId(repositoryId);
		dlContent.setPath(path);
		dlContent.setVersion(version);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		OutputBlob dataOutputBlob = new OutputBlob(
			unsyncByteArrayInputStream, bytes.length);

		dlContent.setData(dataOutputBlob);

		dlContent.setSize(bytes.length);

		return dlContentPersistence.update(dlContent);
	}

	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		InputStream inputStream) {

		DLContent dlContent = dlContentPersistence.fetchByC_R_P_V(
			companyId, repositoryId, path, version);

		if (dlContent == null) {
			dlContent = dlContentPersistence.create(
				counterLocalService.increment());

			dlContent.setCompanyId(companyId);
			dlContent.setRepositoryId(repositoryId);
			dlContent.setPath(path);
			dlContent.setVersion(version);
		}

		OutputBlob outputBlob = _toOutputBlob(inputStream);

		dlContent.setData(outputBlob);
		dlContent.setSize(outputBlob.length());

		return dlContentPersistence.update(dlContent);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addContent(long, long, String, String, InputStream)}
	 */
	@Deprecated
	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		InputStream inputStream, long size) {

		DLContent dlContent = null;

		try (InputStream copyInputStream = inputStream) {
			long contentId = counterLocalService.increment();

			dlContent = dlContentPersistence.create(contentId);

			dlContent.setCompanyId(companyId);
			dlContent.setRepositoryId(repositoryId);
			dlContent.setPath(path);
			dlContent.setVersion(version);

			OutputBlob dataOutputBlob = new OutputBlob(copyInputStream, size);

			dlContent.setData(dataOutputBlob);

			dlContent.setSize(size);

			dlContent = dlContentPersistence.update(dlContent);
		}
		catch (IOException ioException) {
			if (_log.isWarnEnabled()) {
				_log.warn(ioException, ioException);
			}
		}

		return dlContent;
	}

	@Override
	public void deleteContent(
		long companyId, long repositoryId, String path, String version) {

		DLContent dlContent = dlContentPersistence.fetchByC_R_P_V(
			companyId, repositoryId, path, version);

		if (dlContent != null) {
			dlContentPersistence.remove(dlContent);
		}
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void deleteContents(long companyId, long repositoryId, String path) {
		dlContentPersistence.removeByC_R_P(companyId, repositoryId, path);
	}

	@Override
	public void deleteContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		if (dirName.isEmpty()) {
			dlContentPersistence.removeByC_R(companyId, repositoryId);

			return;
		}

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		dlContentPersistence.removeByC_R_LikeP(
			companyId, repositoryId, dirName);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getContent(long, long, String, String)}
	 */
	@Deprecated
	@Override
	public DLContent getContent(long companyId, long repositoryId, String path)
		throws NoSuchContentException {

		OrderByComparator<DLContent> orderByComparator =
			new DLContentVersionComparator();

		List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
			companyId, repositoryId, path, 0, 1, orderByComparator);

		if (ListUtil.isEmpty(dlContents)) {
			throw new NoSuchContentException(path);
		}

		return dlContents.get(0);
	}

	@Override
	public DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException {

		if (version.isEmpty()) {
			OrderByComparator<DLContent> orderByComparator =
				new DLContentVersionComparator();

			List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
				companyId, repositoryId, path, 0, 1, orderByComparator);

			if (ListUtil.isEmpty(dlContents)) {
				throw new NoSuchContentException(path);
			}

			return dlContents.get(0);
		}

		return dlContentPersistence.findByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getContentsByDirectory(long, long, String)}
	 */
	@Deprecated
	@Override
	public List<DLContent> getContents(long companyId, long repositoryId) {
		return dlContentPersistence.findByC_R(companyId, repositoryId);
	}

	@Override
	public List<DLContent> getContents(
		long companyId, long repositoryId, String path) {

		return dlContentPersistence.findByC_R_P(companyId, repositoryId, path);
	}

	@Override
	public List<DLContent> getContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		if (dirName.isEmpty()) {
			return dlContentPersistence.findByC_R(companyId, repositoryId);
		}

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		return dlContentPersistence.findByC_R_LikeP(
			companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasContent(
		long companyId, long repositoryId, String path, String version) {

		int count = dlContentPersistence.countByC_R_P_V(
			companyId, repositoryId, path, version);

		if (count > 0) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public void updateDLContent(
		long companyId, long oldRepositoryId, long newRepositoryId,
		String oldPath, String newPath) {

		List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
			companyId, oldRepositoryId, oldPath);

		for (DLContent dLContent : dlContents) {
			dLContent.setRepositoryId(newRepositoryId);
			dLContent.setPath(newPath);

			dlContentPersistence.update(dLContent);
		}
	}

	private OutputBlob _toOutputBlob(InputStream inputStream) {
		if (inputStream instanceof ByteArrayInputStream) {
			ByteArrayInputStream byteArrayInputStream =
				(ByteArrayInputStream)inputStream;

			return new OutputBlob(
				inputStream, byteArrayInputStream.available());
		}

		if (inputStream instanceof UnsyncByteArrayInputStream) {
			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				(UnsyncByteArrayInputStream)inputStream;

			return new OutputBlob(
				unsyncByteArrayInputStream,
				unsyncByteArrayInputStream.available());
		}

		if (inputStream instanceof
				com.liferay.portal.kernel.io.unsync.
					UnsyncByteArrayInputStream) {

			com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream
				unsyncByteArrayInputStream =
					(com.liferay.portal.kernel.io.unsync.
						UnsyncByteArrayInputStream)inputStream;

			return new OutputBlob(
				inputStream, unsyncByteArrayInputStream.available());
		}

		if (inputStream instanceof FileInputStream) {
			FileInputStream fileInputStream = (FileInputStream)inputStream;

			FileChannel fileChannel = fileInputStream.getChannel();

			try {
				return new OutputBlob(inputStream, fileChannel.size());
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to detect file size from file channel",
						ioException);
				}
			}
		}

		try {
			byte[] bytes = StreamUtil.toByteArray(inputStream);

			UnsyncByteArrayInputStream unsyncByteArrayInputStream =
				new UnsyncByteArrayInputStream(bytes);

			return new OutputBlob(unsyncByteArrayInputStream, bytes.length);
		}
		catch (IOException ioException) {
			throw new SystemException(ioException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLContentLocalServiceImpl.class);

}