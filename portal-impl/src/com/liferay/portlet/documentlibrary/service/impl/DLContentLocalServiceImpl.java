/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.service.base.DLContentLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.comparator.DLContentVersionComparator;

import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class DLContentLocalServiceImpl extends DLContentLocalServiceBaseImpl {

	@Override
	public DLContent addContent(
			long companyId, long repositoryId, String path, String version,
			byte[] bytes)
		throws SystemException {

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

		dlContentPersistence.update(dlContent);

		return dlContent;
	}

	@Override
	public DLContent addContent(
			long companyId, long repositoryId, String path, String version,
			InputStream inputStream, long size)
		throws SystemException {

		try {
			long contentId = counterLocalService.increment();

			DLContent dlContent = dlContentPersistence.create(contentId);

			dlContent.setCompanyId(companyId);
			dlContent.setRepositoryId(repositoryId);
			dlContent.setPath(path);
			dlContent.setVersion(version);

			OutputBlob dataOutputBlob = new OutputBlob(inputStream, size);

			dlContent.setData(dataOutputBlob);

			dlContent.setSize(size);

			dlContentPersistence.update(dlContent);

			return dlContent;
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	public void deleteContent(
			long companyId, long repositoryId, String path, String version)
		throws PortalException, SystemException {

		dlContentPersistence.removeByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	@Override
	public void deleteContents(long companyId, long repositoryId, String path)
		throws SystemException {

		dlContentPersistence.removeByC_R_P(companyId, repositoryId, path);
	}

	@Override
	public void deleteContentsByDirectory(
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		dlContentPersistence.removeByC_R_LikeP(
			companyId, repositoryId, dirName);
	}

	@Override
	public DLContent getContent(long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException {

		OrderByComparator orderByComparator = new DLContentVersionComparator();

		List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
			companyId, repositoryId, path, 0, 1, orderByComparator);

		if ((dlContents == null) || dlContents.isEmpty()) {
			throw new NoSuchContentException(path);
		}

		return dlContents.get(0);
	}

	@Override
	public DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {

		return dlContentPersistence.findByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	@Override
	public List<DLContent> getContents(long companyId, long repositoryId)
		throws SystemException {

		return dlContentPersistence.findByC_R(companyId, repositoryId);
	}

	@Override
	public List<DLContent> getContents(
			long companyId, long repositoryId, String path)
		throws SystemException {

		return dlContentPersistence.findByC_R_P(companyId, repositoryId, path);
	}

	@Override
	public List<DLContent> getContentsByDirectory(
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName = dirName.concat(StringPool.PERCENT);

		return dlContentPersistence.findByC_R_LikeP(
			companyId, repositoryId, dirName);
	}

	@Override
	public boolean hasContent(
			long companyId, long repositoryId, String path, String version)
		throws SystemException {

		int count = dlContentPersistence.countByC_R_P_V(
			companyId, repositoryId, path, version);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void updateDLContent(
			long companyId, long oldRepositoryId, long newRepositoryId,
			String oldPath, String newPath)
		throws SystemException {

		List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
			companyId, oldRepositoryId, oldPath);

		for (DLContent dLContent : dlContents) {
			dLContent.setRepositoryId(newRepositoryId);
			dLContent.setPath(newPath);

			dlContentPersistence.update(dLContent);
		}
	}

}