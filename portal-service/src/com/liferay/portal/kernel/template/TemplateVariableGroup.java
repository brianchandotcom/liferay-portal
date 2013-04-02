/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.UniqueList;

import java.util.Collection;

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableGroup {

	public TemplateVariableGroup(String label) {
		_label = label;
	}

	public TemplateVariableDefinition addCollectionVariable(
		String collectionLabel, Class<?> collectionClazz, String collectionName,
		String itemLabel, Class<?> itemClazz, String itemName) {

		TemplateVariableDefinition itemTemplateVariableDefinition =
			new TemplateVariableDefinition(itemLabel, itemClazz, itemName);

		TemplateVariableDefinition collectionTemplateVariableDefinition =
			new TemplateVariableDefinition(
				collectionLabel, collectionClazz, collectionName,
				itemTemplateVariableDefinition);

		_templateVariableDefinitions.add(collectionTemplateVariableDefinition);

		return collectionTemplateVariableDefinition;
	}

	public TemplateVariableDefinition addFieldVariable(
		String label, Class<?> clazz, String variableName, String help,
		String dataType, boolean repeatable) {

		TemplateVariableDefinition templateVariableDefinition =
			new TemplateVariableDefinition(
				label, clazz, dataType, variableName, help, repeatable);

		_templateVariableDefinitions.add(templateVariableDefinition);

		return templateVariableDefinition;
	}

	public TemplateVariableDefinition addVariable(
		String label, Class<?> clazz, String variableName) {

		TemplateVariableDefinition templateVariableDefinition =
			new TemplateVariableDefinition(label, clazz, variableName);

		_templateVariableDefinitions.add(templateVariableDefinition);

		return templateVariableDefinition;
	}

	public void empty() {
		_templateVariableDefinitions.clear();
	}

	public String getLabel() {
		return _label;
	}

	public Collection<TemplateVariableDefinition>
		getTemplateVariableDefinitions() {

		return _templateVariableDefinitions;
	}

	private String _label;
	private Collection<TemplateVariableDefinition>
		_templateVariableDefinitions =
			new UniqueList<TemplateVariableDefinition>();

}