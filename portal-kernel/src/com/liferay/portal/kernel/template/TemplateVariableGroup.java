/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.template;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.TextFormatter;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableGroup {

	public TemplateVariableGroup(String label) {
		this(label, null);
	}

	public TemplateVariableGroup(String label, String[] restrictedVariables) {
		_label = label;
		_restrictedVariables = restrictedVariables;
	}

	public TemplateVariableDefinition addCollectionVariable(
		String collectionLabel, Class<?> collectionClazz, String collectionName,
		String itemLabel, Class<?> itemClazz, String itemName,
		String itemAccessor) {

		if (isRestrictedVariable(collectionName)) {
			return null;
		}

		TemplateVariableDefinition itemTemplateVariableDefinition =
			new TemplateVariableDefinition(
				itemLabel, itemClazz, itemName, itemAccessor);

		TemplateVariableDefinition collectionTemplateVariableDefinition =
			new TemplateVariableDefinition(
				collectionLabel, collectionClazz, collectionName,
				itemTemplateVariableDefinition);

		_templateVariableDefinitions.add(collectionTemplateVariableDefinition);

		return collectionTemplateVariableDefinition;
	}

	public TemplateVariableDefinition addFieldVariable(
		String label, Class<?> clazz, String variableName, String help,
		String dataType, boolean repeatable,
		TemplateVariableCodeHandler templateVariableCodeHandler) {

		if (isRestrictedVariable(variableName)) {
			return null;
		}

		TemplateVariableDefinition templateVariableDefinition =
			new TemplateVariableDefinition(
				label, clazz, dataType, variableName, null, help, repeatable,
				templateVariableCodeHandler);

		_templateVariableDefinitions.add(templateVariableDefinition);

		return templateVariableDefinition;
	}

	public void addServiceLocatorVariables(Class<?>... serviceClasses) {
		if (isRestrictedVariable("serviceLocator")) {
			return;
		}

		for (Class<?> serviceClass : serviceClasses) {
			String label = TextFormatter.format(
				serviceClass.getSimpleName(), TextFormatter.I);

			label = TextFormatter.format(label, TextFormatter.K);

			TemplateVariableDefinition templateVariableDefinition =
				new TemplateVariableDefinition(
					label, serviceClass, "service-locator",
					serviceClass.getCanonicalName(), null, label + "-help",
					false, null);

			_templateVariableDefinitions.add(templateVariableDefinition);
		}
	}

	public TemplateVariableDefinition addVariable(
		String label, Class<?> clazz, String name) {

		return addVariable(label, clazz, name, null);
	}

	public TemplateVariableDefinition addVariable(
		String label, Class<?> clazz, String name, String accessor) {

		if (isRestrictedVariable(name)) {
			return null;
		}

		TemplateVariableDefinition templateVariableDefinition =
			new TemplateVariableDefinition(label, clazz, name, accessor);

		_templateVariableDefinitions.add(templateVariableDefinition);

		return templateVariableDefinition;
	}

	public TemplateVariableDefinition addVariable(
		String label, Class<?> clazz, String dataType, String name,
		String accessor, String help) {

		if (isRestrictedVariable(name)) {
			return null;
		}

		TemplateVariableDefinition templateVariableDefinition =
			new TemplateVariableDefinition(
				label, clazz, dataType, name, accessor, help, false, null);

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

	public boolean isAutocompleteEnabled() {
		return _autocompleteEnabled;
	}

	public boolean isEmpty() {
		return _templateVariableDefinitions.isEmpty();
	}

	public void setAutocompleteEnabled(boolean autocompleteEnabled) {
		_autocompleteEnabled = autocompleteEnabled;
	}

	public void setLabel(String label) {
		_label = label;
	}

	protected boolean isRestrictedVariable(String variableName) {
		return ArrayUtil.contains(_restrictedVariables, variableName);
	}

	private boolean _autocompleteEnabled = true;
	private String _label;
	private final String[] _restrictedVariables;
	private final Collection<TemplateVariableDefinition>
		_templateVariableDefinitions = new LinkedHashSet<>();

}