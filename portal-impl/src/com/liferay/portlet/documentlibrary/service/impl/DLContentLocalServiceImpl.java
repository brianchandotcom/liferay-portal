/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.service.base.DLContentLocalServiceBaseImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class DLContentLocalServiceImpl extends DLContentLocalServiceBaseImpl {

	public DLContent addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, byte[] bytes)
		throws SystemException {

		long contentId = counterLocalService.increment();

		DLContent dlContent = dlContentPersistence.create(contentId);

		dlContent.setGroupId(groupId);
		dlContent.setCompanyId(companyId);
		dlContent.setPortletId(portletId);
		dlContent.setRepositoryId(repositoryId);
		dlContent.setPath(path);
		dlContent.setVersion(version);

		UnsyncByteArrayInputStream unsyncByteArrayInputStream =
			new UnsyncByteArrayInputStream(bytes);

		OutputBlob dataOutputBlob = new OutputBlob(
			unsyncByteArrayInputStream, bytes.length);

		dlContent.setData(dataOutputBlob);

		dlContent.setSize(bytes.length);

		dlContentPersistence.update(dlContent, false);

		unsyncByteArrayInputStream.reset();

		dlContent.setData(dataOutputBlob);

		return dlContent;
	}

	public DLContent addContent(
			long companyId, String portletId, long groupId, long repositoryId,
			String path, String version, InputStream inputStream, long size)
		throws SystemException {

		long contentId = counterLocalService.increment();

		DLContent dlContent = dlContentPersistence.create(contentId);

		dlContent.setGroupId(groupId);
		dlContent.setCompanyId(companyId);
		dlContent.setPortletId(portletId);
		dlContent.setRepositoryId(repositoryId);
		dlContent.setPath(path);
		dlContent.setVersion(version);

		OutputBlob dataOutputBlob = new OutputBlob(inputStream, size);

		dlContent.setData(dataOutputBlob);

		dlContent.setSize(size);

		dlContentPersistence.update(dlContent, false);

		return dlContent;
	}

	public void deleteContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws PortalException, SystemException {

		dlContentPersistence.removeByC_P_R_P_V(
			companyId, portletId, repositoryId, path, version);
	}

	public void deleteContents(
			long companyId, String portletId, long repositoryId, String path)
		throws SystemException {

		dlContentPersistence.removeByC_P_R_P(
			companyId, portletId, repositoryId, path);
	}

	public void deleteContentsByDirectory(
			long companyId, String portletId, long repositoryId, String dirName)
		throws PortalException, SystemException {

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName.concat(StringPool.PERCENT);

		dlContentPersistence.removeByC_P_R_LikeP(
			companyId, portletId, repositoryId, dirName);
	}

	public DLContent getContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws NoSuchContentException, SystemException {

		return dlContentPersistence.findByC_P_R_P_V(
			companyId, portletId, repositoryId, path, version);
	}

	public DLContent getContent(
			long companyId, long repositoryId, String path)
		throws NoSuchContentException, SystemException {

		List<DLContent> dlContents = dlContentPersistence.findByC_R_P(
			companyId, repositoryId, path);

		if (dlContents == null || dlContents.isEmpty()) {
			throw new NoSuchContentException(path);
		}

		return dlContents.get(0);
	}

	public DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException, SystemException {

		return dlContentPersistence.findByC_R_P_V(
			companyId, repositoryId, path, version);
	}

	public List<DLContent> getContents(
			long companyId, String portletId, long repositoryId, String path)
		throws SystemException {

		return dlContentPersistence.findByC_P_R_P(
			companyId, portletId, repositoryId, path);
	}

	public List<DLContent> getContents(long companyId, long repositoryId)
		throws SystemException {

		return dlContentPersistence.findByC_R(companyId, repositoryId);
	}

	public List<DLContent> getContents(
			long companyId, long repositoryId, String path)
		throws SystemException {

		return dlContentPersistence.findByC_R_P(companyId, repositoryId, path);
	}

	public List<DLContent> getContentsByDirectory(
			long companyId, long repositoryId, String dirName)
		throws SystemException {

		if (!dirName.endsWith(StringPool.SLASH)) {
			dirName = dirName.concat(StringPool.SLASH);
		}

		dirName.concat(StringPool.PERCENT);

		return dlContentPersistence.findByC_R_LikeP(
			companyId, repositoryId, dirName);
	}

	public boolean hasContent(
			long companyId, String portletId, long repositoryId, String path,
			String version)
		throws SystemException {

		int count = dlContentPersistence.countByC_P_R_P_V(
			companyId, portletId, repositoryId, path, version);

		if (count > 0) {
			return true;
		}
		else {
			return false;
		}
	}

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

	public void updateDLContent(
			long companyId, long oldRepositoryId, long newRepositoryId,
			String oldPath, String newPath)
		throws SystemException {

		String sql = CustomSQLUtil.get(_UPDATE_DL_CONTENT);

		sql = StringUtil.replace(
			sql,
			new String[] {
				"[$COMPANY_ID$]",
				"[$NEW_PATH$]",
				"[$NEW_REPOSITORY_ID$]",
				"[$OLD_PATH$]",
				"[$OLD_REPOSITORY_ID$]"
			},
			new String[] {
				String.valueOf(companyId),
				newPath,
				String.valueOf(newRepositoryId),
				oldPath,
				String.valueOf(oldRepositoryId)
			});

		runSQL(sql);
	}

	private static final String _UPDATE_DL_CONTENT =
		DLContentLocalServiceImpl.class.getName() + ".updateDLContent";

}