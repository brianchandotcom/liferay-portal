package com.liferay.data.engine.model;

import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataDefinition {

	public Map<Locale, String> getName() {
		return _name;
	}

	public void setName(Map<Locale, String> name) {
		_name = name;
	}

	private Map<Locale, String> _name;
}
