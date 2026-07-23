/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.patcher.util;

import com.liferay.osb.patcher.configuration.PatcherConfiguration;
import com.liferay.osb.patcher.constants.JiraConstants;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pedro Malta
 */
public class JiraTicketResolverUtil {

	public static List<String> getRelatedLPDKeys(JSONObject issueJSONObject) {
		List<String> lpdKeys = new ArrayList<>();

		JSONObject fieldsJSONObject = issueJSONObject.getJSONObject(
			JiraConstants.FIELD_FIELDS);

		if (fieldsJSONObject == null) {
			return lpdKeys;
		}

		JSONArray issueLinksJSONArray = fieldsJSONObject.getJSONArray(
			JiraConstants.FIELD_ISSUE_LINKS);

		if (issueLinksJSONArray == null) {
			return lpdKeys;
		}

		for (int i = 0; i < issueLinksJSONArray.length(); i++) {
			JSONObject issueLinkJSONObject = issueLinksJSONArray.getJSONObject(
				i);

			if (issueLinkJSONObject == null) {
				continue;
			}

			JSONObject typeJSONObject = issueLinkJSONObject.getJSONObject(
				JiraConstants.FIELD_TYPE);

			if ((typeJSONObject == null) ||
				!JiraConstants.LINK_TYPE_RELATIONSHIP.equals(
					typeJSONObject.getString(JiraConstants.FIELD_NAME))) {

				continue;
			}

			_addLinkedKeyIfLPD(
				issueLinkJSONObject.getJSONObject(
					JiraConstants.FIELD_INWARD_ISSUE),
				lpdKeys);
			_addLinkedKeyIfLPD(
				issueLinkJSONObject.getJSONObject(
					JiraConstants.FIELD_OUTWARD_ISSUE),
				lpdKeys);
		}

		return lpdKeys;
	}

	public static List<String> resolveTickets(
			PatcherConfiguration patcherConfiguration, String tickets)
		throws Exception {

		List<String> resolvedTickets = new ArrayList<>();

		for (String ticket : StringUtil.split(tickets)) {
			ticket = ticket.trim();

			if (ticket.isEmpty()) {
				continue;
			}

			if (!ticket.startsWith(JiraConstants.LPE_KEY_PREFIX)) {
				if (!resolvedTickets.contains(ticket)) {
					resolvedTickets.add(ticket);
				}

				continue;
			}

			JSONObject issueJSONObject = JiraUtil.getIssue(
				patcherConfiguration, ticket);

			List<String> lpdKeys = getRelatedLPDKeys(issueJSONObject);

			if (lpdKeys.isEmpty()) {
				throw new PortalException(
					LanguageUtil.format(
						LocaleUtil.getMostRelevantLocale(),
						"no-related-lpd-ticket-found-for-x", ticket));
			}

			if (lpdKeys.size() > 1) {
				throw new PortalException(
					LanguageUtil.format(
						LocaleUtil.getMostRelevantLocale(),
						"multiple-related-lpd-tickets-found-for-x", ticket));
			}

			String lpdKey = lpdKeys.get(0);

			if (!resolvedTickets.contains(lpdKey)) {
				resolvedTickets.add(lpdKey);
			}
		}

		return resolvedTickets;
	}

	private static void _addLinkedKeyIfLPD(
		JSONObject linkedIssueJSONObject, List<String> lpdKeys) {

		if (linkedIssueJSONObject == null) {
			return;
		}

		String key = linkedIssueJSONObject.getString(JiraConstants.FIELD_KEY);

		if (key.startsWith(JiraConstants.LPD_KEY_PREFIX) &&
			!lpdKeys.contains(key)) {

			lpdKeys.add(key);
		}
	}

}