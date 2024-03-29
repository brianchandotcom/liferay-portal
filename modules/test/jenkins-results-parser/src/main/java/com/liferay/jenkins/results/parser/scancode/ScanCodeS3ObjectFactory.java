/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.scancode;

import com.google.cloud.storage.Blob;

import com.liferay.jenkins.results.parser.JenkinsResultsParserUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brittney Nguyen
 */
public class ScanCodeS3ObjectFactory {

	public static ScanCodeS3Object newScanCodeS3Object(
		ScanCodeS3Bucket scanCodeS3Bucket, Blob blob) {

		if (blob == null) {
			return null;
		}

		String mapKey = JenkinsResultsParserUtil.combine(
			scanCodeS3Bucket.getName(), "/", blob.getName());

		if (_scanCodeS3Objects.containsKey(mapKey)) {
			return _scanCodeS3Objects.get(mapKey);
		}

		ScanCodeS3Object scanCodeS3Object = new ScanCodeS3Object(
			scanCodeS3Bucket, blob);

		_scanCodeS3Objects.put(mapKey, scanCodeS3Object);

		return scanCodeS3Object;
	}

	private static final Map<String, ScanCodeS3Object> _scanCodeS3Objects =
		new HashMap<>();

}