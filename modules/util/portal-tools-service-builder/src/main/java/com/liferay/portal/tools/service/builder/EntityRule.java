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

package com.liferay.portal.tools.service.builder;

/**
 * @author Michael Bowerman
 */
public class EntityRule {

	public EntityRule(String name, String definition) {
		_name = name;
		_definition = definition;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EntityRule)) {
			return false;
		}

		EntityRule rule = (EntityRule)obj;

		String name = rule.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getDefinition() {
		return _definition;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	private final String _definition;
	private final String _name;

}