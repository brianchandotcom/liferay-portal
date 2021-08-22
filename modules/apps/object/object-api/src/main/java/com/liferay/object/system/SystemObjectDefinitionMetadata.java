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

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public interface SystemObjectDefinitionMetadata {

	public default String getDBTableName() {
		return null;
	}

	public Map<Locale, String> getLabelMap();

	public String getName();

	public String getScope();

	public List<ObjectField> getObjectFields();

	public default String getPKObjectFieldDBColumnName() {
		return null;
	}

	public default String getPKObjectFieldName() {
		return null;
	}

	public Map<Locale, String> getPluralLabelMap();

	public int getVersion();

}