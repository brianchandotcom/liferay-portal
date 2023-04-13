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

package com.liferay.portal.upload.internal;

import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.upload.ServletFileUpload;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.BadRequestException;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = ServletFileUpload.class)
public class ServletFileUploadImpl implements ServletFileUpload {

	@Override
	public List<FileItem> parseRequest(
			HttpServletRequest httpServletRequest, String location,
			int fileSizeThreshold)
		throws UploadException {

		List<FileItem> fileItems = new ArrayList<>();

		org.apache.commons.fileupload.servlet.ServletFileUpload
			servletFileUpload =
				new org.apache.commons.fileupload.servlet.ServletFileUpload(
					new LiferayFileItemFactory(
						new File(location), fileSizeThreshold,
						httpServletRequest.getCharacterEncoding()));

		long fileMaxSize = DLValidatorUtil.getMaxAllowableSize(
			GroupConstants.DEFAULT_PARENT_GROUP_ID, null);

		servletFileUpload.setFileSizeMax(fileMaxSize);
		servletFileUpload.setSizeMax(fileMaxSize);

		try {
			for (org.apache.commons.fileupload.FileItem fileItem :
					servletFileUpload.parseRequest(httpServletRequest)) {

				fileItems.add((FileItem)fileItem);
			}

			return fileItems;
		}
		catch (FileUploadBase.SizeLimitExceededException
					sizeLimitExceededException) {

			throw new BadRequestException(
				"Please enter a file with a valid file size no larger than " +
					fileMaxSize,
				sizeLimitExceededException);
		}
		catch (FileUploadException fileUploadException) {
			UploadException uploadException = new UploadException(
				fileUploadException);

			if (fileUploadException instanceof
					FileUploadBase.FileSizeLimitExceededException) {

				uploadException.setExceededFileSizeLimit(true);
			}
			else if (fileUploadException instanceof
						FileUploadBase.SizeLimitExceededException) {

				uploadException.setExceededUploadRequestSizeLimit(true);
			}

			throw uploadException;
		}
	}

}