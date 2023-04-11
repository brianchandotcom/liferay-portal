/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.FieldMapping;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;
import com.liferay.osb.faro.web.internal.util.SchemaOrgUtil;

import java.util.Map;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class FieldMappingDisplay extends EntityDisplay {

	public FieldMappingDisplay() {
	}

	public FieldMappingDisplay(FieldMapping fieldMapping) {
		setId(fieldMapping.getFieldName());

		_context = fieldMapping.getContext();
		_dataSourceFieldNames = fieldMapping.getDataSourceFieldNames();
		_displayName = fieldMapping.getDisplayName();
		_displayType = fieldMapping.getDisplayType();
		_name = fieldMapping.getDisplayName();
		_ownerType = fieldMapping.getOwnerType();
		_rawType = SchemaOrgUtil.getRawType(
			fieldMapping.getDisplayType(), fieldMapping.getFieldType());
		_type = fieldMapping.getFieldType();
	}

	public String getName() {
		return _name;
	}

	public String getType() {
		return _type;
	}

	private String _context;
	private Map<String, String> _dataSourceFieldNames;
	private String _displayName;
	private String _displayType;
	private String _name;
	private String _ownerType;
	private String _rawType;
	private String _type;

}