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

package com.liferay.object.service.test.system;

import com.liferay.object.model.ObjectField;
import com.liferay.object.system.BaseSystemObjectDefinitionMetadata;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Feliphe Marinho
 */
@Component(immediate = true, service = SystemObjectDefinitionMetadata.class)
public class TestSystemObjectDefinitionMetadata
	extends BaseSystemObjectDefinitionMetadata {

	public TestSystemObjectDefinitionMetadata(
		Class<?> modelClass, String name, String restContextPath) {

		_modelClass = modelClass;
		_name = name;
		_restContextPath = restContextPath;
	}

	@Override
	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException {

		return null;
	}

	@Override
	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return null;
	}

	@Override
	public String getExternalReferenceCode(long primaryKey)
		throws PortalException {

		return null;
	}

	@Override
	public String getJaxRsApplicationName() {
		return "";
	}

	@Override
	public Map<Locale, String> getLabelMap() {
		return null;
	}

	@Override
	public Class<?> getModelClass() {
		return _modelClass;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public List<ObjectField> getObjectFields() {
		return null;
	}

	@Override
	public Map<Locale, String> getPluralLabelMap() {
		return null;
	}

	@Override
	public Column<?, Long> getPrimaryKeyColumn() {
		return null;
	}

	@Override
	public String getRESTContextPath() {
		return _restContextPath;
	}

	@Override
	public String getScope() {
		return null;
	}

	@Override
	public Table getTable() {
		return null;
	}

	@Override
	public int getVersion() {
		return 1;
	}

	private final Class<?> _modelClass;
	private final String _name;
	private final String _restContextPath;

}