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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.exception.NoSuchContentException;
import com.liferay.document.library.kernel.model.DLContent;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.documentlibrary.service.base.DLContentLocalServiceBaseImpl;

import java.io.InputStream;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @deprecated As of 7.0.0, replaced by {@link
 *            com.liferay.document.library.content.service.impl.DLContentLocalServiceImpl}
 */
@Deprecated
public class DLContentLocalServiceImpl extends DLContentLocalServiceBaseImpl {

	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		byte[] bytes) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public DLContent addContent(
		long companyId, long repositoryId, String path, String version,
		InputStream inputStream, long size) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public void deleteContent(
			long companyId, long repositoryId, String path, String version)
		throws PortalException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public void deleteContents(long companyId, long repositoryId, String path) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public void deleteContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public DLContent getContent(long companyId, long repositoryId, String path)
		throws NoSuchContentException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public DLContent getContent(
			long companyId, long repositoryId, String path, String version)
		throws NoSuchContentException {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public List<DLContent> getContents(long companyId, long repositoryId) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public List<DLContent> getContents(
		long companyId, long repositoryId, String path) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public List<DLContent> getContentsByDirectory(
		long companyId, long repositoryId, String dirName) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public boolean hasContent(
		long companyId, long repositoryId, String path, String version) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

	@Override
	public void updateDLContent(
		long companyId, long oldRepositoryId, long newRepositoryId,
		String oldPath, String newPath) {

		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.content.service.impl." +
					"DLContentLocalServiceImpl");
	}

}