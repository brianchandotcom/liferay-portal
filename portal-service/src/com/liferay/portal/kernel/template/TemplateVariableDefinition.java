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

/**
 * @author Jorge Ferrer
 */
public class TemplateVariableDefinition {

	public TemplateVariableDefinition(
		String labelKey, Class clazz, String variableName) {

		_labelKey = labelKey;
		_name = variableName;
		_clazz = clazz;
	}

	public TemplateVariableDefinition(
		String labelKey, Class clazz, String variableName,
		TemplateVariableDefinition collectionItem) {

		_collectionItem = collectionItem;
		_labelKey = labelKey;
		_name = variableName;
		_clazz = clazz;
	}

	public Class getClazz() {
		return _clazz;
	}

	public TemplateVariableDefinition getCollectionItem() {
		return _collectionItem;
	}

	public String getLabelKey() {
		return _labelKey;
	}

	public String getName() {
		return _name;
	}

	public boolean isCollection() {
		if (_collectionItem != null) {
			return true;
		}

		return false;
	}

	private TemplateVariableDefinition _collectionItem;
	private Class _clazz; private String _labelKey;
	private String _name;

}