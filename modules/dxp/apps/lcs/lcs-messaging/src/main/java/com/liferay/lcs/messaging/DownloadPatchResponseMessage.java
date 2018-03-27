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

package com.liferay.lcs.messaging;

/**
 * @author Ivica Cardic
 */
public class DownloadPatchResponseMessage extends ResponseMessage {

	public String getPatchFileName() {
		return _patchFileName;
	}

	public int getStatus() {
		return _status;
	}

	public void setPatchFileName(String patchFileName) {
		_patchFileName = patchFileName;
	}

	public void setStatus(int status) {
		_status = status;
	}

	private String _patchFileName;
	private int _status;

}