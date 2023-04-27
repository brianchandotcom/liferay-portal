/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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