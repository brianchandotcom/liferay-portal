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