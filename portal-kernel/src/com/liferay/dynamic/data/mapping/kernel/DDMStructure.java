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

package com.liferay.dynamic.data.mapping.kernel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Leonardo Barros
 */
@ProviderType
public interface DDMStructure extends StagedGroupedModel {

	@Override
	public Object clone();

	public String[] getAvailableLanguageIds();

	public List<String> getChildrenFieldNames(String fieldName)
		throws PortalException;

	public String getClassName();

	public long getClassNameId();

	@Override
	public Date getCreateDate();

	public DDMForm getDDMForm();

	public DDMFormField getDDMFormField(String fieldName)
		throws PortalException;

	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields);

	public String getDefaultLanguageId();

	public String getDefinition();

	public String getDescription();

	public String getDescription(Locale locale);

	public String getDescription(Locale locale, boolean useDefault);

	public String getDescription(String languageId);

	public String getDescription(String languageId, boolean useDefault);

	public String getDescriptionCurrentLanguageId();

	public String getDescriptionCurrentValue();

	public Map<Locale, String> getDescriptionMap();

	@Override
	public ExpandoBridge getExpandoBridge();

	public String getFieldDataType(String fieldName) throws PortalException;

	public String getFieldLabel(String fieldName, Locale locale)
		throws PortalException;

	public String getFieldLabel(String fieldName, String locale)
		throws PortalException;

	public Set<String> getFieldNames();

	public String getFieldProperty(String fieldName, String property)
		throws PortalException;

	public boolean getFieldRepeatable(String fieldName) throws PortalException;

	public boolean getFieldRequired(String fieldName) throws PortalException;

	public String getFieldTip(String fieldName, Locale locale)
		throws PortalException;

	public String getFieldTip(String fieldName, String locale)
		throws PortalException;

	public String getFieldType(String fieldName) throws PortalException;

	public DDMForm getFullHierarchyDDMForm();

	@Override
	public long getGroupId();

	@Override
	public Date getModifiedDate();

	public String getName();

	public String getName(Locale locale);

	public String getName(Locale locale, boolean useDefault);

	public String getName(String languageId);

	public String getName(String languageId, boolean useDefault);

	public String getNameCurrentLanguageId();

	public String getNameCurrentValue();

	public Map<Locale, String> getNameMap();

	public long getParentStructureId();

	public long getPrimaryKey();

	@Override
	public Serializable getPrimaryKeyObj();

	public List<String> getRootFieldNames();

	public String getStorageType();

	public long getStructureId();

	public String getStructureKey();

	public List<DDMTemplate> getTemplates() throws PortalException;

	public int getType();

	@Override
	public long getUserId();

	@Override
	public String getUserName();

	@Override
	public String getUserUuid();

	@Override
	public String getUuid();

	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken);

	public boolean hasField(String fieldName);

	public boolean isFieldRepeatable(String fieldName) throws PortalException;

	public boolean isFieldTransient(String fieldName) throws PortalException;

	public boolean isNew();

	public void prepareLocalizedFieldsForImport() throws LocaleException;

	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException;

	public void setDefinition(String definition);

	public String toXmlString();

}