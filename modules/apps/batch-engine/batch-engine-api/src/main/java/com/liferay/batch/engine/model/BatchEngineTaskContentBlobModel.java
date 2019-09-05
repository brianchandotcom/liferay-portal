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

import org.osgi.annotation.versioning.ProviderType;

/**
 * The Blob model class for lazy loading the content column in BatchEngineTask.
 *
 * @author Shuyang Zhou
 * @see BatchEngineTask
 * @generated
 */
@ProviderType
public class BatchEngineTaskContentBlobModel {

	public BatchEngineTaskContentBlobModel() {
	}

	public BatchEngineTaskContentBlobModel(long batchEngineTaskId) {
		_batchEngineTaskId = batchEngineTaskId;
	}

	public BatchEngineTaskContentBlobModel(
		long batchEngineTaskId, Blob contentBlob) {

		_batchEngineTaskId = batchEngineTaskId;
		_contentBlob = contentBlob;
	}

	public long getBatchEngineTaskId() {
		return _batchEngineTaskId;
	}

	public void setBatchEngineTaskId(long batchEngineTaskId) {
		_batchEngineTaskId = batchEngineTaskId;
	}

	public Blob getContentBlob() {
		return _contentBlob;
	}

	public void setContentBlob(Blob contentBlob) {
		_contentBlob = contentBlob;
	}

	private long _batchEngineTaskId;
	private Blob _contentBlob;

}