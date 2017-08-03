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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class DownloadPatchesCommandMessage extends CommandMessage {

	public String getMd5Sum() {
		return _md5Sum;
	}

	public Map<String, String> getPatches() {
		return _patches;
	}

	@Override
	public String getSignatureString() {
		String signatureString = super.getSignatureString();

		signatureString = signatureString.concat(
			_patches.toString()).concat(_md5Sum);

		return signatureString;
	}

	public void setMd5Sum(String md5Sum) {
		_md5Sum = md5Sum;
	}

	public void setPatches(Map<String, String> patches) {
		_patches = patches;
	}

	private String _md5Sum;
	private Map<String, String> _patches = new HashMap<String, String>();

}