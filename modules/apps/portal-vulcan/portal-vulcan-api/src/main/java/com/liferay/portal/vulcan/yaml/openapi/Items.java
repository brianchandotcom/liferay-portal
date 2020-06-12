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

import java.util.Map;

/**
 * @author Peter Shin
 */
public class Items {

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Schema getAdditionalPropertySchema() {
		return _additionalPropertySchema;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getFormat() {
		return _format;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public Map<String, Schema> getPropertySchemas() {
		return _propertySchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getReference() {
		return _reference;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public String getType() {
		return _type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setAdditionalPropertySchema(Schema additionalPropertySchema) {
		_additionalPropertySchema = additionalPropertySchema;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setFormat(String format) {
		_format = format;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setPropertySchemas(Map<String, Schema> propertySchemas) {
		_propertySchemas = propertySchemas;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setReference(String reference) {
		_reference = reference;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public void setType(String type) {
		_type = type;
	}

	private Schema _additionalPropertySchema;
	private String _format;
	private Map<String, Schema> _propertySchemas;
	private String _reference;
	private String _type;

}