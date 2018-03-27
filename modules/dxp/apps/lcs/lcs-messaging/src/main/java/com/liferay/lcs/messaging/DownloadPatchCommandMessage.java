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
public class DownloadPatchCommandMessage extends CommandMessage {

	public String getMd5Sum() {
		return _md5Sum;
	}

	public String getPatchFileName() {
		return _patchFileName;
	}

	public String getPatchURL() {
		return _patchURL;
	}

	@Override
	public String getSignatureString() {
		String signatureString = super.getSignatureString();

		StringBuilder sb = new StringBuilder(signatureString);

		sb.append(_md5Sum);
		sb.append(_patchFileName);
		sb.append(_patchURL);

		return sb.toString();
	}

	public void setMd5Sum(String md5Sum) {
		_md5Sum = md5Sum;
	}

	public void setPatchFileName(String patchFileName) {
		_patchFileName = patchFileName;
	}

	public void setPatchURL(String patchURL) {
		_patchURL = patchURL;
	}

	private String _md5Sum;
	private String _patchFileName;
	private String _patchURL;

}