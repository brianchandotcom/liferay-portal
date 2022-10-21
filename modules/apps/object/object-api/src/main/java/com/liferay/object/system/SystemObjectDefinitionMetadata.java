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

package com.liferay.object.system;

import com.liferay.object.model.ObjectField;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.Table;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface SystemObjectDefinitionMetadata {

	public BaseModel<?> deleteBaseModel(BaseModel<?> baseModel)
		throws PortalException;

	public BaseModel<?> getBaseModelByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	public String getExternalReferenceCode(long primaryKey)
		throws PortalException;

	public String getJaxRsApplicationName();

	public Map<Locale, String> getLabelMap();

	public Class<?> getModelClass();

	public String getModelClassName();

	public String getName();

	public List<ObjectField> getObjectFields();

	public Map<Locale, String> getPluralLabelMap();

	public Column<?, Long> getPrimaryKeyColumn();

	public String getRESTContextPath();

	public String getRESTDTOIdPropertyName();

	public String getScope();

	public Table getTable();

	public int getVersion();

}