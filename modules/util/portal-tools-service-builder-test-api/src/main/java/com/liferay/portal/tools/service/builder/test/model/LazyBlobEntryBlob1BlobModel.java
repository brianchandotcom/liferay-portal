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
 * The Blob model class for lazy loading the blob1 column in LazyBlobEntry.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntry
 * @generated
 */
public class LazyBlobEntryBlob1BlobModel {

	public LazyBlobEntryBlob1BlobModel() {
	}

	public LazyBlobEntryBlob1BlobModel(long lazyBlobEntryId) {
		_lazyBlobEntryId = lazyBlobEntryId;
	}

	public LazyBlobEntryBlob1BlobModel(long lazyBlobEntryId, Blob blob1Blob) {
		_lazyBlobEntryId = lazyBlobEntryId;
		_blob1Blob = blob1Blob;
	}

	public long getLazyBlobEntryId() {
		return _lazyBlobEntryId;
	}

	public void setLazyBlobEntryId(long lazyBlobEntryId) {
		_lazyBlobEntryId = lazyBlobEntryId;
	}

	public Blob getBlob1Blob() {
		return _blob1Blob;
	}

	public void setBlob1Blob(Blob blob1Blob) {
		_blob1Blob = blob1Blob;
	}

	private long _lazyBlobEntryId;
	private Blob _blob1Blob;

}