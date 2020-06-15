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

package com.liferay.portal.vulcan.yaml.openapi;

/**
 * @author Peter Shin
 */
public class Parameter {

	public String getExample() {
		return _example;
	}

	public String getIn() {
		return _in;
	}

	public String getName() {
		return _name;
	}

	public String getReference() {
		return _reference;
	}

	public Schema getSchema() {
		return _schema;
	}

	public boolean isRequired() {
		return _required;
	}

	public void setExample(String example) {
		_example = example;
	}

	public void setIn(String in) {
		_in = in;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public void setRequired(boolean required) {
		_required = required;
	}

	public void setSchema(Schema schema) {
		_schema = schema;
	}

	private String _example;
	private String _in;
	private String _name;
	private String _reference;
	private boolean _required;
	private Schema _schema;

}