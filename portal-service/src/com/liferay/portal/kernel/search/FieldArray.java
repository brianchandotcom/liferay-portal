/**
 *
 */
package com.liferay.portal.kernel.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author Miguel Angelo Caldas Gallindo
 *
 */
public class FieldArray extends Field {

	public FieldArray(String name, Map<Locale, String> localizedValues) {
		super(name, localizedValues);
	}

	public FieldArray(String name, String value) {
		super(name, value);
	}

	public FieldArray(String name, String[] values) {
		super(name, values);
	}

	public FieldArray(String name) {
		super(name);
	}

	@Override
	public void addField(Field field) {
		_arrayElementsFields.add(field);
	}

	@Override
	public List<Field> getFields() {
		return _arrayElementsFields;
	}

	public boolean isArray() {
		return true;
	}

	private List<Field> _arrayElementsFields = new ArrayList<Field>();

}
