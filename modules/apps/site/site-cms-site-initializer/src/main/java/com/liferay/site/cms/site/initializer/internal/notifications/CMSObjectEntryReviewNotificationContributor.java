/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.notifications;

import com.liferay.object.entry.contributor.ObjectEntryReviewNotificationContributor;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.util.Portal;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sam Ziemer
 */
@Component(service = ObjectEntryReviewNotificationContributor.class)
public class CMSObjectEntryReviewNotificationContributor
	implements ObjectEntryReviewNotificationContributor {

	@Override
	public void contribute(
		ObjectEntry objectEntry, JSONObject payloadJSONObject) {

		payloadJSONObject.put(
			"notificationLink",
			StringBundler.concat(
				_portal.getPathFriendlyURLPublic(),
				GroupConstants.CMS_FRIENDLY_URL, "/view-asset?objectEntryId=",
				objectEntry.getObjectEntryId()));
	}

	@Override
	public boolean isApplicable(ObjectEntry objectEntry) {
		ObjectDefinition objectDefinition = objectEntry.getObjectDefinition();

		if (objectDefinition == null) {
			return false;
		}

		return objectDefinition.isCMS();
	}

	@Reference
	private Portal _portal;

}