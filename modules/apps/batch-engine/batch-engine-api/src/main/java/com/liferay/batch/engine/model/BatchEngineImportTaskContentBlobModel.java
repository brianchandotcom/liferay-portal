/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the content column in BatchEngineImportTask.
 *
 * @author Shuyang Zhou
 * @see BatchEngineImportTask
 * @generated
 */
public class BatchEngineImportTaskContentBlobModel {

	public BatchEngineImportTaskContentBlobModel() {
	}

	public BatchEngineImportTaskContentBlobModel(long batchEngineImportTaskId) {
		_batchEngineImportTaskId = batchEngineImportTaskId;
	}

	public BatchEngineImportTaskContentBlobModel(
		long batchEngineImportTaskId, Blob contentBlob) {

		_batchEngineImportTaskId = batchEngineImportTaskId;
		_contentBlob = contentBlob;
	}

	public long getBatchEngineImportTaskId() {
		return _batchEngineImportTaskId;
	}

	public void setBatchEngineImportTaskId(long batchEngineImportTaskId) {
		_batchEngineImportTaskId = batchEngineImportTaskId;
	}

	public Blob getContentBlob() {
		return _contentBlob;
	}

	public void setContentBlob(Blob contentBlob) {
		_contentBlob = contentBlob;
	}

	private long _batchEngineImportTaskId;
	private Blob _contentBlob;

}