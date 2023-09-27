/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Interest;
import com.liferay.osb.faro.engine.client.model.Rels;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.engine.client.util.OrderByField;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.InterestDisplay;

import java.util.List;
import java.util.function.Function;

/**
 * @author Shinn Lok
 */
public class InterestUtil {

	@SuppressWarnings("unchecked")
	public static FaroResultsDisplay getInterests(
		FaroProject faroProject, String channelId, String contactsEntityId,
		String query, int cur, int delta, List<OrderByField> orderByFields,
		ContactsEngineClient contactsEngineClient) {

		Results<Interest> results = contactsEngineClient.getInterests(
			faroProject, channelId, contactsEntityId, null, null, query,
			Rels.Interests.PAGES_VISITED, cur, delta, orderByFields);

		Function<Interest, InterestDisplay> function = InterestDisplay::new;

		return new FaroResultsDisplay(results, function);
	}

}