/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.frontend.data.set.model;

import java.util.Date;

/**
 * @author Eudaldo Alonso
 */
public class FDSAudienceEntry {

	public FDSAudienceEntry(
		long audienceEntryId, Date modifiedDate, String name) {

		_audienceEntryId = audienceEntryId;
		_modifiedDate = modifiedDate;
		_name = name;
	}

	public long getAudienceEntryId() {
		return _audienceEntryId;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public String getName() {
		return _name;
	}

	private final long _audienceEntryId;
	private final Date _modifiedDate;
	private final String _name;

}