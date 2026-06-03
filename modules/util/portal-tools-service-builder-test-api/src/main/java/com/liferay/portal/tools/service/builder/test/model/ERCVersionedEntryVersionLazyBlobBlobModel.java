/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the lazyBlob column in ERCVersionedEntryVersion.
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntryVersion
 * @generated
 */
public class ERCVersionedEntryVersionLazyBlobBlobModel {

	public ERCVersionedEntryVersionLazyBlobBlobModel() {
	}

	public ERCVersionedEntryVersionLazyBlobBlobModel(
		long ercVersionedEntryVersionId) {

		_ercVersionedEntryVersionId = ercVersionedEntryVersionId;
	}

	public ERCVersionedEntryVersionLazyBlobBlobModel(
		long ercVersionedEntryVersionId, Blob lazyBlobBlob) {

		_ercVersionedEntryVersionId = ercVersionedEntryVersionId;
		_lazyBlobBlob = lazyBlobBlob;
	}

	public long getErcVersionedEntryVersionId() {
		return _ercVersionedEntryVersionId;
	}

	public void setErcVersionedEntryVersionId(long ercVersionedEntryVersionId) {
		_ercVersionedEntryVersionId = ercVersionedEntryVersionId;
	}

	public Blob getLazyBlobBlob() {
		return _lazyBlobBlob;
	}

	public void setLazyBlobBlob(Blob lazyBlobBlob) {
		_lazyBlobBlob = lazyBlobBlob;
	}

	private long _ercVersionedEntryVersionId;
	private Blob _lazyBlobBlob;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1423896568