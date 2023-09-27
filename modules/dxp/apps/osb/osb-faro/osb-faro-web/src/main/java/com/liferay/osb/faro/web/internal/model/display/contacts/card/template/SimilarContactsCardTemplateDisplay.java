/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

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

		FaroResultsDisplay faroResultsDisplay = null;

		if (faroEntityDisplay.getType() == FaroConstants.TYPE_INDIVIDUAL) {
			Results<Individual> results =
				contactsEngineClient.getSimilarIndividuals(
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

	private String _fieldMappingFieldName;

}