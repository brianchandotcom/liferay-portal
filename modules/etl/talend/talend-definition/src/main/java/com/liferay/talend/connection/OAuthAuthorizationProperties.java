/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.talend.connection;

import com.liferay.talend.ui.UIKeys;

import org.talend.daikon.properties.PropertiesImpl;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Igor Beslic
 */
public class OAuthAuthorizationProperties extends PropertiesImpl {

	public OAuthAuthorizationProperties(String name) {
		super(name);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form referenceForm = new Form(this, UIKeys.FORM_OAUTH_AUTHORIZATION);

		referenceForm.addRow(oauthClientId);
		referenceForm.addRow(oauthClientSecret);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		oauthClientId.setValue("");
		oauthClientSecret.setValue("");
	}

	public Property<String> oauthClientId = PropertyFactory.newString(
		"oauthClientId");
	public Property<String> oauthClientSecret = PropertyFactory.newString(
		"oauthClientSecret");

}