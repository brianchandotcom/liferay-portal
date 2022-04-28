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

import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.upload.UploadException;
import com.liferay.portal.upload.ServletFileUpload;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
			HttpServletRequest httpServletRequest, long sizeMax,
			long fileSizeMax, String location, int fileSizeThreshold)
		throws UploadException {

		org.apache.commons.fileupload.servlet.ServletFileUpload
			servletFileUpload =
				new org.apache.commons.fileupload.servlet.ServletFileUpload(
					new LiferayFileItemFactory(
						new File(location), fileSizeThreshold,
						httpServletRequest.getCharacterEncoding()));

		servletFileUpload.setSizeMax(sizeMax);

		servletFileUpload.setFileSizeMax(fileSizeMax);

		List<FileItem> fileItemList = new ArrayList<>();

		try {
			for (org.apache.commons.fileupload.FileItem fileItem :
					servletFileUpload.parseRequest(httpServletRequest)) {

				fileItemList.add((FileItem)fileItem);
			}

			return fileItemList;
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