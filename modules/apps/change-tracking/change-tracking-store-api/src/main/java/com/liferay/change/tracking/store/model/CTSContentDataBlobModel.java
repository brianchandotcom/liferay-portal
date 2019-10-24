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

package com.liferay.change.tracking.store.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the data column in CTSContent.
 *
 * @author Shuyang Zhou
 * @see CTSContent
 * @generated
 */
public class CTSContentDataBlobModel {

	public CTSContentDataBlobModel() {
	}

	public CTSContentDataBlobModel(long ctsContentId) {
		_ctsContentId = ctsContentId;
	}

	public CTSContentDataBlobModel(long ctsContentId, Blob dataBlob) {
		_ctsContentId = ctsContentId;
		_dataBlob = dataBlob;
	}

	public long getCtsContentId() {
		return _ctsContentId;
	}

	public void setCtsContentId(long ctsContentId) {
		_ctsContentId = ctsContentId;
	}

	public Blob getDataBlob() {
		return _dataBlob;
	}

	public void setDataBlob(Blob dataBlob) {
		_dataBlob = dataBlob;
	}

	private long _ctsContentId;
	private Blob _dataBlob;

}