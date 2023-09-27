/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.yaml.openapi;

import java.util.Map;

/**
 * @author Peter Shin
 */
public class Items {

	public Schema getAdditionalPropertySchema() {
		return _additionalPropertySchema;
	}

	public String getFormat() {
		return _format;
	}

	public Map<String, Schema> getPropertySchemas() {
		return _propertySchemas;
	}

	public String getReference() {
		return _reference;
	}

	public String getType() {
		return _type;
	}

	public void setAdditionalPropertySchema(Schema additionalPropertySchema) {
		_additionalPropertySchema = additionalPropertySchema;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public void setPropertySchemas(Map<String, Schema> propertySchemas) {
		_propertySchemas = propertySchemas;
	}

	public void setReference(String reference) {
		_reference = reference;
	}

	public void setType(String type) {
		_type = type;
	}

	private Schema _additionalPropertySchema;
	private String _format;
	private Map<String, Schema> _propertySchemas;
	private String _reference;
	private String _type;

}