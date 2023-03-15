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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;

import com.liferay.osb.faro.contacts.model.ContactsLayoutTemplate;
import com.liferay.osb.faro.contacts.service.ContactsCardTemplateLocalServiceUtil;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.card.template.ContactsCardTemplateManagerHelper;
import com.liferay.osb.faro.web.internal.model.display.FaroModelDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class ContactsLayoutTemplateDisplay extends FaroModelDisplay {

	public ContactsLayoutTemplateDisplay() {
	}

	public ContactsLayoutTemplateDisplay(
			ContactsLayoutTemplate contactsLayoutTemplate,
			ContactsCardTemplateManagerHelper contactsCardTemplateManagerHelper)
		throws Exception {

		super(contactsLayoutTemplate);

		List<List<ContactsLayoutTemplateSettingDisplay>>
			contactsLayoutTemplateSettingDisplaysList = JSONUtil.readValue(
				contactsLayoutTemplate.getSettings(),
				new TypeReference
					<List<List<ContactsLayoutTemplateSettingDisplay>>>() {
				});

		for (List<ContactsLayoutTemplateSettingDisplay>
				contactsLayoutTemplateSettingDisplays :
					contactsLayoutTemplateSettingDisplaysList) {

			List<ContactsCardTemplateDisplay> contactsCardTemplateDisplays =
				new ArrayList<>();

			for (ContactsLayoutTemplateSettingDisplay
					contactsLayoutTemplateSettingDisplay :
						contactsLayoutTemplateSettingDisplays) {

				ContactsCardTemplateDisplay contactsCardTemplateDisplay =
					contactsCardTemplateManagerHelper.
						getContactsCardTemplateDisplay(
							ContactsCardTemplateLocalServiceUtil.
								getContactsCardTemplate(
									contactsLayoutTemplateSettingDisplay.
										getContactsCardTemplateId()),
							contactsLayoutTemplateSettingDisplay.getSize());

				if (contactsCardTemplateDisplay != null) {
					contactsCardTemplateDisplays.add(
						contactsCardTemplateDisplay);
				}
			}

			_contactsCardTemplateDisplaysList.add(contactsCardTemplateDisplays);
		}

		for (String headerContactsCardTemplateId :
				StringUtil.split(
					contactsLayoutTemplate.
						getHeaderContactsCardTemplateIds())) {

			_headerContactsCardTemplateDisplays.add(
				contactsCardTemplateManagerHelper.
					getContactsCardTemplateDisplay(
						ContactsCardTemplateLocalServiceUtil.
							getContactsCardTemplate(
								GetterUtil.getLong(
									headerContactsCardTemplateId))));
		}

		_name = contactsLayoutTemplate.getName();
		_type = contactsLayoutTemplate.getType();
	}

	@JsonIgnore
	public Map<String, Map<String, Object>> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		Map<String, Map<String, Object>> contactsCardData = new HashMap<>();

		for (ContactsCardTemplateDisplay headerContactsCardTemplateDisplay :
				_headerContactsCardTemplateDisplays) {

			contactsCardData.put(
				String.valueOf(headerContactsCardTemplateDisplay.getId()),
				headerContactsCardTemplateDisplay.getContactsCardData(
					faroProject, faroEntityDisplay, contactsEngineClient));
		}

		for (List<ContactsCardTemplateDisplay> contactsCardTemplateDisplays :
				_contactsCardTemplateDisplaysList) {

			for (ContactsCardTemplateDisplay contactsCardTemplateDisplay :
					contactsCardTemplateDisplays) {

				contactsCardData.put(
					String.valueOf(contactsCardTemplateDisplay.getId()),
					contactsCardTemplateDisplay.getContactsCardData(
						faroProject, faroEntityDisplay, contactsEngineClient));
			}
		}

		return contactsCardData;
	}

	@JsonIgnore
	public List<String> getFieldMappingNames() {
		List<String> fieldMappingNames = new ArrayList<>();

		for (List<ContactsCardTemplateDisplay> contactsCardTemplateDisplays :
				_contactsCardTemplateDisplaysList) {

			for (ContactsCardTemplateDisplay contactsCardTemplateDisplay :
					contactsCardTemplateDisplays) {

				fieldMappingNames.addAll(
					contactsCardTemplateDisplay.getFieldMappingNames());
			}
		}

		return fieldMappingNames;
	}

	private final List<List<ContactsCardTemplateDisplay>>
		_contactsCardTemplateDisplaysList = new ArrayList<>();
	private final List<ContactsCardTemplateDisplay>
		_headerContactsCardTemplateDisplays = new ArrayList<>();
	private String _name;
	private int _type;

}