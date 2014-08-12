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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Brian Myunghun Kim
 * @author Brian Wing Shun Chan
 */
public class LiferayFileUpload extends ServletFileUpload {

	public static final String FILE_NAME =
		LiferayFileUpload.class.getName() + "_FILE_NAME";

	public static final String PERCENT = ProgressTracker.PERCENT;

	public LiferayFileUpload(
		FileItemFactory fileItemFactory, HttpServletRequest request) {

		super(fileItemFactory);

		_session = request.getSession();
	}

	@Override
	public List<FileItem> parseRequest(HttpServletRequest request)
		throws FileUploadException {

		_session.removeAttribute(LiferayFileUpload.FILE_NAME);
		_session.removeAttribute(LiferayFileUpload.PERCENT);

		return super.parseRequest(request);
	}

	/**
	 * @deprecated As of 6.1.0
	 */
	@Deprecated
	@Override
	@SuppressWarnings("rawtypes")
	protected FileItem createItem(Map headers, boolean formField)
		throws FileUploadException {

		LiferayFileItem item = (LiferayFileItem)super.createItem(
			headers, formField);

		String fileName = item.getFileName();

		if (Validator.isNotNull(fileName)) {
			_session.setAttribute(LiferayFileUpload.FILE_NAME, fileName);
		}

		return item;
	}

	private final HttpSession _session;

}