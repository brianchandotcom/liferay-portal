/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import java.sql.Blob;

/**
 * The Blob model class for lazy loading the lazyBlob column in ERCVersionedEntry.
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntry
 * @generated
 */
public class ERCVersionedEntryLazyBlobBlobModel {

	public ERCVersionedEntryLazyBlobBlobModel() {
	}

	public ERCVersionedEntryLazyBlobBlobModel(long ercVersionedEntryId) {
		_ercVersionedEntryId = ercVersionedEntryId;
	}

	public ERCVersionedEntryLazyBlobBlobModel(
		long ercVersionedEntryId, Blob lazyBlobBlob) {

		_ercVersionedEntryId = ercVersionedEntryId;
		_lazyBlobBlob = lazyBlobBlob;
	}

	public long getErcVersionedEntryId() {
		return _ercVersionedEntryId;
	}

	public void setErcVersionedEntryId(long ercVersionedEntryId) {
		_ercVersionedEntryId = ercVersionedEntryId;
	}

	public Blob getLazyBlobBlob() {
		return _lazyBlobBlob;
	}

	public void setLazyBlobBlob(Blob lazyBlobBlob) {
		_lazyBlobBlob = lazyBlobBlob;
	}

	private long _ercVersionedEntryId;
	private Blob _lazyBlobBlob;

}
// LIFERAY-SERVICE-BUILDER-HASH:1538420030