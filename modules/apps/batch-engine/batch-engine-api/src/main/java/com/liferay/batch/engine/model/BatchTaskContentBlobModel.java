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
 * The Blob model class for lazy loading the content column in BatchTask.
 *
 * @author Shuyang Zhou
 * @see BatchTask
 * @generated
 */
@ProviderType
public class BatchTaskContentBlobModel {

	public BatchTaskContentBlobModel() {
	}

	public BatchTaskContentBlobModel(long batchTaskId) {
		_batchTaskId = batchTaskId;
	}

	public BatchTaskContentBlobModel(long batchTaskId, Blob contentBlob) {
		_batchTaskId = batchTaskId;
		_contentBlob = contentBlob;
	}

	public long getBatchTaskId() {
		return _batchTaskId;
	}

	public void setBatchTaskId(long batchTaskId) {
		_batchTaskId = batchTaskId;
	}

	public Blob getContentBlob() {
		return _contentBlob;
	}

	public void setContentBlob(Blob contentBlob) {
		_contentBlob = contentBlob;
	}

	private long _batchTaskId;
	private Blob _contentBlob;

}