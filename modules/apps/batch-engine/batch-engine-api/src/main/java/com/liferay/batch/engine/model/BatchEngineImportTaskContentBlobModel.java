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