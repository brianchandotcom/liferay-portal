/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine;

/**
 * @author Shuyang Zhou
 */
public enum BatchEngineTaskContentType {

	CSV("csv"), JSON("json"), JSONL("jsonl"), JSONT("batch-engine-data.json"),
	XLS("xls"), XLSX("xlsx");

	public String getFileExtension() {
		return _fileExtension;
	}

	private BatchEngineTaskContentType(String fileExtension) {
		_fileExtension = fileExtension;
	}

	private final String _fileExtension;

}