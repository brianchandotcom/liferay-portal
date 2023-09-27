/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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