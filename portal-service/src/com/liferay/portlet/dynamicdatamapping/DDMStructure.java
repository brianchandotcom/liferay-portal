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

package com.liferay.portlet.dynamicdatamapping;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.StagedGroupedModel;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 */
public interface DDMStructure extends StagedGroupedModel {

	public List<String> getChildrenFieldNames(String fieldName)
		throws PortalException;

	public long getClassNameId();

	public DDMForm getDDMForm();

	public DDMFormField getDDMFormField(String fieldName)
		throws PortalException;

	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields);

	public String getDefinition();

	public String getDescription();

	public String getDescription(Locale locale);

	public Map<Locale, String> getDescriptionMap();

	public String getFieldDataType(String fieldName) throws PortalException;

	public Set<String> getFieldNames();

	public String getFieldProperty(String fieldName, String property)
		throws PortalException;

	public String getFieldType(String fieldName) throws PortalException;

	public DDMForm getFullHierarchyDDMForm();

	@Override
	public long getGroupId();

	public String getName();

	public String getName(Locale locale);

	public String getName(Locale locale, boolean useDefault);

	public Map<Locale, String> getNameMap();

	public long getParentStructureId();

	public long getPrimaryKey();

	public List<String> getRootFieldNames();

	public long getStructureId();

	public String getStructureKey();

	public int getType();

	@Override
	public long getUserId();

	public boolean hasField(String fieldName);

	public boolean isFieldTransient(String fieldName) throws PortalException;

	public void setDefinition(String definition);

}