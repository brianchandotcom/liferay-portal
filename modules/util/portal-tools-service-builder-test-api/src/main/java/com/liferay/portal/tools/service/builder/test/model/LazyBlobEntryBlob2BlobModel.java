/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the blob2 column in LazyBlobEntry.
 *
 * @author Brian Wing Shun Chan
 * @see LazyBlobEntry
 * @generated
 */
public class LazyBlobEntryBlob2BlobModel {

	public LazyBlobEntryBlob2BlobModel() {
	}

	public LazyBlobEntryBlob2BlobModel(long lazyBlobEntryId) {
		_lazyBlobEntryId = lazyBlobEntryId;
	}

	public LazyBlobEntryBlob2BlobModel(long lazyBlobEntryId, Blob blob2Blob) {
		_lazyBlobEntryId = lazyBlobEntryId;
		_blob2Blob = blob2Blob;
	}

	public long getLazyBlobEntryId() {
		return _lazyBlobEntryId;
	}

	public void setLazyBlobEntryId(long lazyBlobEntryId) {
		_lazyBlobEntryId = lazyBlobEntryId;
	}

	public Blob getBlob2Blob() {
		return _blob2Blob;
	}

	public void setBlob2Blob(Blob blob2Blob) {
		_blob2Blob = blob2Blob;
	}

	private long _lazyBlobEntryId;
	private Blob _blob2Blob;

}