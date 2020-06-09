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

package com.liferay.portal.file.install.internal.manifest;

/**
 * @author Matthew Tambara
 */
public class Clause {

	public Clause(String name, Directive[] directives, Attribute[] attributes) {
		_name = name;
		_directives = directives;
		_attributes = attributes;
	}

	public String getAttribute(String name) {
		for (Attribute attribute : _attributes) {
			if (name.equals(attribute.getName())) {
				return attribute.getValue();
			}
		}

		return null;
	}

	public Attribute[] getAttributes() {
		return _attributes;
	}

	public String getDirective(String name) {
		for (Directive directive : _directives) {
			if (name.equals(directive.getName())) {
				return directive.getValue();
			}
		}

		return null;
	}

	public Directive[] getDirectives() {
		return _directives;
	}

	public String getName() {
		return _name;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		sb.append(_name);

		if (_directives != null) {
			for (Directive directive : _directives) {
				sb.append(";");
				sb.append(directive.getName());
				sb.append(":=");

				String value = directive.getValue();

				if (value.contains(",")) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}
			}
		}

		if (_attributes != null) {
			for (Attribute attribute : _attributes) {
				sb.append(";");
				sb.append(attribute.getName());
				sb.append("=");

				String value = attribute.getValue();

				if (value.contains(",")) {
					sb.append("\"");
					sb.append(value);
					sb.append("\"");
				}
				else {
					sb.append(value);
				}
			}
		}

		return sb.toString();
	}

	private final Attribute[] _attributes;
	private final Directive[] _directives;
	private final String _name;

}