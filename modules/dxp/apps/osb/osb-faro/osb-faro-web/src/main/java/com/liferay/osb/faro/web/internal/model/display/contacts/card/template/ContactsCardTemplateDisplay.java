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

import com.liferay.osb.faro.contacts.model.ContactsCardTemplate;
import com.liferay.osb.faro.engine.client.ContactsEngineClient;
import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.web.internal.model.display.FaroModelDisplay;
import com.liferay.osb.faro.web.internal.model.display.main.FaroEntityDisplay;
import com.liferay.osb.faro.web.internal.util.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public abstract class ContactsCardTemplateDisplay extends FaroModelDisplay {

	public ContactsCardTemplateDisplay() {
	}

	public ContactsCardTemplateDisplay(
			ContactsCardTemplate contactsCardTemplate, int size)
		throws Exception {

		this(contactsCardTemplate, size, new int[0]);
	}

	public ContactsCardTemplateDisplay(
			ContactsCardTemplate contactsCardTemplate, int size,
			int[] supportedSizes)
		throws Exception {

		super(contactsCardTemplate);

		_name = contactsCardTemplate.getName();

		settings = JSONUtil.readValue(
			contactsCardTemplate.getSettings(),
			new TypeReference<Map<String, Object>>() {
			});

		_showTitle = MapUtil.getBoolean(settings, "showTitle", true);

		if ((size == 0) && (supportedSizes.length > 0)) {
			size = supportedSizes[0];
		}

		_size = size;

		_supportedSizes = supportedSizes.clone();

		_type = contactsCardTemplate.getType();
	}

	@JsonIgnore
	public Map<String, Object> getContactsCardData(
		FaroProject faroProject, FaroEntityDisplay faroEntityDisplay,
		ContactsEngineClient contactsEngineClient) {

		return Collections.emptyMap();
	}

	public List<String> getFieldMappingNames() {
		return Collections.emptyList();
	}

	public int getSize() {
		return _size;
	}

	public int[] getSupportedSizes() {
		return _supportedSizes.clone();
	}

	public void setName(String name) {
		_name = name;
	}

	@JsonIgnore
	protected Map<String, Object> settings;

	private String _name;
	private boolean _showTitle;
	private int _size;
	private int[] _supportedSizes;
	private int _type;

}