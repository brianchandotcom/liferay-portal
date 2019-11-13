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

package com.liferay.portal.tools.service.builder.test.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the blob2 column in LazyBlobEntity.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntity
 * @generated
 */
public class LazyBlobEntityBlob2BlobModel {

	public LazyBlobEntityBlob2BlobModel() {
	}

	public LazyBlobEntityBlob2BlobModel(long lazyBlobEntityId) {
		_lazyBlobEntityId = lazyBlobEntityId;
	}

	public LazyBlobEntityBlob2BlobModel(long lazyBlobEntityId, Blob blob2Blob) {
		_lazyBlobEntityId = lazyBlobEntityId;
		_blob2Blob = blob2Blob;
	}

	public long getLazyBlobEntityId() {
		return _lazyBlobEntityId;
	}

	public void setLazyBlobEntityId(long lazyBlobEntityId) {
		_lazyBlobEntityId = lazyBlobEntityId;
	}

	public Blob getBlob2Blob() {
		return _blob2Blob;
	}

	public void setBlob2Blob(Blob blob2Blob) {
		_blob2Blob = blob2Blob;
	}

	private long _lazyBlobEntityId;
	private Blob _blob2Blob;

}