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
public class ScancodeS3ObjectFactory {

	public static ScancodeS3Object newScancodeS3Object(
		ScancodeS3Bucket scancodeS3Bucket, Blob blob) {

		if (blob == null) {
			return null;
		}

		String mapKey = JenkinsResultsParserUtil.combine(
			scancodeS3Bucket.getName(), "/", blob.getName());

		if (_scancodeS3Objects.containsKey(mapKey)) {
			return _scancodeS3Objects.get(mapKey);
		}

		ScancodeS3Object scancodeS3Object = new ScancodeS3Object(
			scancodeS3Bucket, blob);

		_scancodeS3Objects.put(mapKey, scancodeS3Object);

		return scancodeS3Object;
	}

	private static final Map<String, ScancodeS3Object> _scancodeS3Objects =
		new HashMap<>();

}