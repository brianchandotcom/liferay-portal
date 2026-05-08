/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.TimeZoneUtil;

import java.text.DateFormat;

import java.util.Date;

/**
 * @author David Truong
 */
public class FriendlyURLEntryReportUtil {

	public static final String CATEGORY_FRIENDLY_URL_COLLISION =
		"Friendly URL Collision";

	public static final String CATEGORY_RESERVED_PATH_CONFLICT =
		"Reserved Path Conflict";

	public static final String TYPE_PAGE = "PAGE";

	public static final String TYPE_SITE = "SITE";

	public static String renderFriendlyURLPublicMappingConflictReport(
		JSONArray conflictsJSONArray, long companyId) {

		StringBundler sb = new StringBundler();

		sb.append(_BANNER_EQUALS);
		sb.append("\n  PUBLIC FRIENDLY URL MAPPING DISABLING PRE-CHECK\n");
		sb.append(_BANNER_EQUALS);
		sb.append("\n\nGenerated:        ");
		sb.append(_formatTimestamp());
		sb.append("\nInstance ID:      ");
		sb.append(companyId);
		sb.append("\n\n");
		sb.append(_BANNER_DASHES);
		sb.append("\n  SUMMARY\n");
		sb.append(_BANNER_DASHES);
		sb.append("\n\nTotal conflicts found:        ");
		sb.append(conflictsJSONArray.length());
		sb.append("\n\n");

		if (conflictsJSONArray.length() == 0) {
			sb.append("No friendly URL conflicts were found. The \"layout.");
			sb.append("friendly.url.public.servlet.mapping.enabled\" ");
			sb.append("property can be safely disabled.\n\n");
		}
		else {
			sb.append("Recommendation: DO NOT DISABLE the Public Friendly ");
			sb.append("URL Mapping (removing the /web/) until resolving all ");
			sb.append("the existing items in this report.\n\n");
			sb.append(_BANNER_EQUALS);
			sb.append("\n  CONFLICT DETAILS\n");
			sb.append(_BANNER_EQUALS);
			sb.append("\n\n");

			for (int i = 0; i < conflictsJSONArray.length(); i++) {
				JSONObject conflictJSONObject =
					conflictsJSONArray.getJSONObject(i);

				_appendConflict(sb, conflictJSONObject, i + 1);
			}
		}

		sb.append(_BANNER_EQUALS);
		sb.append("\n  END OF REPORT\n");
		sb.append(_BANNER_EQUALS);
		sb.append("\n");

		return sb.toString();
	}

	private static void _appendConflict(
		StringBundler sb, JSONObject conflictJSONObject, int number) {

		sb.append(_BANNER_DASHES);
		sb.append("\n#");
		sb.append(number);
		sb.append(" — ");
		sb.append(conflictJSONObject.getString("category"));
		sb.append("\n");
		sb.append(_BANNER_DASHES);
		sb.append("\n\nPath in conflict:   ");
		sb.append(conflictJSONObject.getString("path"));
		sb.append("\n\n");

		JSONArray itemsJSONArray = conflictJSONObject.getJSONArray("items");

		if (itemsJSONArray.length() == 1) {
			sb.append("Conflicting item:\n");
		}
		else {
			sb.append("Conflicting items:\n");
		}

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			if (i > 0) {
				sb.append("\n");
			}

			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			if (TYPE_PAGE.equals(itemJSONObject.getString("type"))) {
				_appendPageItem(sb, itemJSONObject);
			}
			else {
				_appendSiteItem(sb, itemJSONObject);
			}
		}

		sb.append("\n\n");
	}

	private static void _appendPageItem(
		StringBundler sb, JSONObject itemJSONObject) {

		sb.append("  - Page  : \"");
		sb.append(itemJSONObject.getString("name"));
		sb.append("\"\n           Site : \"");
		sb.append(itemJSONObject.getString("siteName"));
		sb.append("\"   (friendly URL: ");
		sb.append(itemJSONObject.getString("siteFriendlyURL"));
		sb.append(")\n           Page ID  : ");
		sb.append(itemJSONObject.getLong("pageId"));
		sb.append("\n");
	}

	private static void _appendSiteItem(
		StringBundler sb, JSONObject itemJSONObject) {

		sb.append("  - Site  : \"");
		sb.append(itemJSONObject.getString("name"));
		sb.append("\"\n           Site ID  : ");
		sb.append(itemJSONObject.getLong("siteId"));
		sb.append("\n           Friendly URL: ");
		sb.append(itemJSONObject.getString("friendlyURL"));
		sb.append("\n");
	}

	private static String _formatTimestamp() {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss 'UTC'",
			TimeZoneUtil.getTimeZone(StringPool.UTC));

		return dateFormat.format(new Date());
	}

	private static final String _BANNER_DASHES = "-".repeat(80);

	private static final String _BANNER_EQUALS = "=".repeat(80);

}