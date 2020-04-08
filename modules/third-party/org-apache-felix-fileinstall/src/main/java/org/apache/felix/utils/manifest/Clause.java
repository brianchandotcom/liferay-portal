/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.felix.utils.manifest;

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
/* @generated */