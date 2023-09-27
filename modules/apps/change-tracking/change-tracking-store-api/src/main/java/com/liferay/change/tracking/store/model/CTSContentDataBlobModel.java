/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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