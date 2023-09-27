/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the content column in BatchEngineExportTask.
 *
 * @author Shuyang Zhou
 * @see BatchEngineExportTask
 * @generated
 */
public class BatchEngineExportTaskContentBlobModel {

	public BatchEngineExportTaskContentBlobModel() {
	}

	public BatchEngineExportTaskContentBlobModel(long batchEngineExportTaskId) {
		_batchEngineExportTaskId = batchEngineExportTaskId;
	}

	public BatchEngineExportTaskContentBlobModel(
		long batchEngineExportTaskId, Blob contentBlob) {

		_batchEngineExportTaskId = batchEngineExportTaskId;
		_contentBlob = contentBlob;
	}

	public long getBatchEngineExportTaskId() {
		return _batchEngineExportTaskId;
	}

	public void setBatchEngineExportTaskId(long batchEngineExportTaskId) {
		_batchEngineExportTaskId = batchEngineExportTaskId;
	}

	public Blob getContentBlob() {
		return _contentBlob;
	}

	public void setContentBlob(Blob contentBlob) {
		_contentBlob = contentBlob;
	}

	private long _batchEngineExportTaskId;
	private Blob _contentBlob;

}