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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class SimilarContactsCardTemplateDisplay
	extends ContactsCardTemplateDisplay {

	public SimilarContactsCardTemplateDisplay() {
	}

	public SimilarContactsCardTemplateDisplay(
			FaroProject faroProject, ContactsCardTemplate contactsCardTemplate,
			int size, ContactsEngineClient contactsEngineClient)
		throws Exception {

		super(contactsCardTemplate, size, _SUPPORTED_SIZES);

		_fieldMappingFieldName = MapUtil.getString(
			settings, "fieldMappingFieldName");
	}

	@Override
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		Map<String, Object> contactsCardData = new HashMap<>();

		FaroResultsDisplay faroResultsDisplay = null;

		if (faroEntityDisplay.getType() == FaroConstants.TYPE_INDIVIDUAL) {
			Results<Individual> results =
				contactsEngineClient.getSimilarIndividuals(
					faroProject, faroEntityDisplay.getId(), StringPool.BLANK,
					null, 1, getSize() * _ITEMS_PER_COLUMN, null);

			List<Individual> individuals = results.getItems();

			Stream<Individual> stream = individuals.stream();

			faroResultsDisplay = new FaroResultsDisplay(
				stream.map(
					IndividualDisplay::new
				).collect(
					Collectors.toList()
				),
				results.getTotal());
		}

		contactsCardData.put("contactsEntityResults", faroResultsDisplay);

		return contactsCardData;
	}

	private static final int _ITEMS_PER_COLUMN = 6;

	private static final int[] _SUPPORTED_SIZES = {2};

	private String _fieldMappingFieldName;

}