/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.entry.contributor;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Sam Ziemer
 */
public interface ObjectEntryReviewNotificationContributor {

	public void contribute(
		ObjectEntry objectEntry, JSONObject payloadJSONObject);

	public boolean isApplicable(ObjectEntry objectEntry);

}