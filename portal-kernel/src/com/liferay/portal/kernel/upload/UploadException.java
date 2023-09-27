/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Igor Spasic
 */
public class UploadException extends PortalException {

	public UploadException() {
	}

	public UploadException(String msg) {
		super(msg);
	}

	public UploadException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UploadException(Throwable throwable) {
		super(throwable);
	}

	public boolean isExceededFileSizeLimit() {
		return _exceededFileSizeLimit;
	}

	public boolean isExceededLiferayFileItemSizeLimit() {
		return _exceededLiferayFileItemSizeLimit;
	}

	public boolean isExceededUploadRequestSizeLimit() {
		return _exceededUploadRequestSizeLimit;
	}

	public void setExceededFileSizeLimit(boolean exceededFileSizeLimit) {
		_exceededFileSizeLimit = exceededFileSizeLimit;
	}

	public void setExceededLiferayFileItemSizeLimit(
		boolean exceededLiferayFileItemSizeLimit) {

		_exceededLiferayFileItemSizeLimit = exceededLiferayFileItemSizeLimit;
	}

	public void setExceededUploadRequestSizeLimit(
		boolean exceededUploadRequestSizeLimit) {

		_exceededUploadRequestSizeLimit = exceededUploadRequestSizeLimit;
	}

	private boolean _exceededFileSizeLimit;
	private boolean _exceededLiferayFileItemSizeLimit;
	private boolean _exceededUploadRequestSizeLimit;

}