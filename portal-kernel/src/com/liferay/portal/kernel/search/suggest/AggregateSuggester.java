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

package com.liferay.portal.kernel.search.suggest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author     Michael C. Han
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class AggregateSuggester implements Suggester {

	public AggregateSuggester(String name, String value) {
		_name = name;
		_value = value;
	}

	@Override
	public <T> T accept(SuggesterVisitor<T> suggesterVisitor) {
		return suggesterVisitor.visit(this);
	}

	public void addSuggester(Suggester suggester) {
		_suggesters.put(suggester.getName(), suggester);
	}

	@Override
	public String getName() {
		return _name;
	}

	public Map<String, Suggester> getSuggesters() {
		return Collections.unmodifiableMap(_suggesters);
	}

	public String getValue() {
		return _value;
	}

	private final String _name;
	private final Map<String, Suggester> _suggesters = new HashMap<>();
	private final String _value;

}