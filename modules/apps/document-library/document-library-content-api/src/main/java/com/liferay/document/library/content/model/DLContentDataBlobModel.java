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

package com.liferay.document.library.content.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the data column in DLContent.
 *
 * @author Brian Wing Shun Chan
 * @see DLContent
 * @generated
 */
public class DLContentDataBlobModel {

	public DLContentDataBlobModel() {
	}

	public DLContentDataBlobModel(long contentId) {
		_contentId = contentId;
	}

	public DLContentDataBlobModel(long contentId, Blob dataBlob) {
		_contentId = contentId;
		_dataBlob = dataBlob;
	}

	public long getContentId() {
		return _contentId;
	}

	public void setContentId(long contentId) {
		_contentId = contentId;
	}

	public Blob getDataBlob() {
		return _dataBlob;
	}

	public void setDataBlob(Blob dataBlob) {
		_dataBlob = dataBlob;
	}

	private long _contentId;
	private Blob _dataBlob;

}