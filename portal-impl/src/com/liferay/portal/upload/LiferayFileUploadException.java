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

package com.liferay.portal.upload;

import com.liferay.portal.kernel.exception.PortalException;

import org.apache.commons.fileupload.FileUploadBase;

/**
 * @author Igor Spasic
 */
public class LiferayFileUploadException extends PortalException {

	public LiferayFileUploadException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LiferayFileUploadException(Throwable cause) {
		super(cause);
	}

	public boolean isSizeLimitException() {
		Throwable cause = getCause();
		if (cause instanceof FileUploadBase.SizeLimitExceededException ||
			cause instanceof
				FileUploadBase.FileSizeLimitExceededException) {

			return true;
		} else {
			return false;
		}
	}
}