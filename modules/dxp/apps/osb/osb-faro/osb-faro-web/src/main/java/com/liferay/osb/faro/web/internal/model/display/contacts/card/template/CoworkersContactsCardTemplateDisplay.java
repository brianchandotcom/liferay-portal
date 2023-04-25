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

package com.liferay.osb.faro.web.internal.model.display.contacts.card.template;

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.engine.client.model.Individual;
import com.liferay.osb.faro.engine.client.model.Results;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.constants.FaroConstants;
import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.model.display.contacts.IndividualDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class CoworkersContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public CoworkersContactsCardTemplateDisplay() {
	}

	public CoworkersContactsCardTemplateDisplay(
			String weDeployKey, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		FaroResultsDisplay faroResultsDisplay = null;

		if (faroEntityDisplay.getType() == FaroConstants.TYPE_INDIVIDUAL) {
			Results<Individual> results =
				contactsEngineClient.getCoworkerIndividuals(
					faroProject, faroEntityDisplay.getId(), StringPool.BLANK,
					null, 1, getSize() * _ITEMS_PER_COLUMN, null);

			faroResultsDisplay = new FaroResultsDisplay(
				TransformUtil.transform(
					results.getItems(), IndividualDisplay::new),
				results.getTotal());
		}

		return HashMapBuilder.<String, Object>put(
			"contactsEntityResults", faroResultsDisplay
		).build();
	}

	private static final int _ITEMS_PER_COLUMN = 6;

	private static final int[] _SUPPORTED_SIZES = {2};

}