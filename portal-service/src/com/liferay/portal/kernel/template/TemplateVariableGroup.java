/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.template;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableGroup {

	public TemplateVariableGroup(String labelKey) {
		_labelKey = labelKey;
	}

	public TemplateVariableDefinition addCollectionVariable(
		String collectionLabelKey, Class collectionClazz, String collectionName,
		String itemLabelKey, Class itemClazz, String itemName) {

		TemplateVariableDefinition itemVariableDefinition =
			new TemplateVariableDefinition(itemLabelKey, itemClazz, itemName);

		TemplateVariableDefinition collectionVariableDefinition =
			new TemplateVariableDefinition(
				collectionLabelKey, collectionClazz, collectionName,
				itemVariableDefinition);

		_variableDefinitions.add(collectionVariableDefinition);

		return collectionVariableDefinition;
	}

	public TemplateVariableDefinition addVariable(
		String labelKey, Class clazz, String variableName) {

		TemplateVariableDefinition variableDefinition =
			new TemplateVariableDefinition(labelKey, clazz, variableName);

		_variableDefinitions.add(variableDefinition);

		return variableDefinition;
	}

	public void empty() {
		_variableDefinitions = new ArrayList<TemplateVariableDefinition>();
	}

	public String getLabelKey() {
		return _labelKey;
	}

	public Collection<TemplateVariableDefinition> getVariableDefinitions() {
		return _variableDefinitions;
	}

	private String _labelKey;
	private Collection<TemplateVariableDefinition> _variableDefinitions =
		new ArrayList<TemplateVariableDefinition>();

}