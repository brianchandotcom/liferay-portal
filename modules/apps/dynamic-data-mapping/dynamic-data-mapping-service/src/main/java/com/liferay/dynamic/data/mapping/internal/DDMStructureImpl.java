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

package com.liferay.dynamic.data.mapping.internal;

import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.DDMFormField;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslatorUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.exception.LocaleException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Leonardo Barros
 * @author Rafael Praxedes
 */
public class DDMStructureImpl implements DDMStructure {

	public DDMStructureImpl(
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure) {

		_ddmStructure = ddmStructure;
	}

	@Override
	public Object clone() {
		return new DDMStructureImpl(
			(com.liferay.dynamic.data.mapping.model.DDMStructure)
				_ddmStructure.clone());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _ddmStructure.getAvailableLanguageIds();
	}

	@Override
	public List<String> getChildrenFieldNames(String fieldName)
		throws PortalException {

		return _ddmStructure.getChildrenFieldNames(fieldName);
	}

	@Override
	public String getClassName() {
		return _ddmStructure.getClassName();
	}

	@Override
	public long getClassNameId() {
		return _ddmStructure.getClassNameId();
	}

	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	@Override
	public DDMForm getDDMForm() {
		return DDMBeanTranslatorUtil.translate(_ddmStructure.getDDMForm());
	}

	@Override
	public DDMFormField getDDMFormField(String fieldName)
		throws PortalException {

		return DDMBeanTranslatorUtil.translate(
			_ddmStructure.getDDMFormField(fieldName));
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMFormField ddmFormField :
				_ddmStructure.getDDMFormFields(includeTransientFields)) {

			ddmFormFields.add(DDMBeanTranslatorUtil.translate(ddmFormField));
		}

		return ddmFormFields;
	}

	@Override
	public String getDefaultLanguageId() {
		return _ddmStructure.getDefaultLanguageId();
	}

	@Override
	public String getDefinition() {
		return _ddmStructure.getDefinition();
	}

	@Override
	public String getDescription() {
		return _ddmStructure.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		return _ddmStructure.getDescription(locale, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return _ddmStructure.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _ddmStructure.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _ddmStructure.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _ddmStructure.getDescriptionCurrentValue();
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public String getFieldDataType(String fieldName) throws PortalException {
		return _ddmStructure.getFieldDataType(fieldName);
	}

	@Override
	public String getFieldLabel(String fieldName, Locale locale)
		throws PortalException {

		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public String getFieldLabel(String fieldName, String locale)
		throws PortalException {

		return _ddmStructure.getFieldLabel(fieldName, locale);
	}

	@Override
	public Set<String> getFieldNames() {
		return _ddmStructure.getFieldNames();
	}

	@Override
	public String getFieldProperty(String fieldName, String property)
		throws PortalException {

		return _ddmStructure.getFieldProperty(fieldName, property);
	}

	@Override
	public boolean getFieldRepeatable(String fieldName) throws PortalException {
		return _ddmStructure.getFieldRepeatable(fieldName);
	}

	@Override
	public boolean getFieldRequired(String fieldName) throws PortalException {
		return _ddmStructure.getFieldRequired(fieldName);
	}

	@Override
	public String getFieldTip(String fieldName, Locale locale)
		throws PortalException {

		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public String getFieldTip(String fieldName, String locale)
		throws PortalException {

		return _ddmStructure.getFieldTip(fieldName, locale);
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		return _ddmStructure.getFieldType(fieldName);
	}

	@Override
	public DDMForm getFullHierarchyDDMForm() {
		return DDMBeanTranslatorUtil.translate(
			_ddmStructure.getFullHierarchyDDMForm());
	}

	@Override
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	@Override
	public Date getLastPublishDate() {
		return _ddmStructure.getLastPublishDate();
	}

	@Override
	public Class<?> getModelClass() {
		return _ddmStructure.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _ddmStructure.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	@Override
	public String getName() {
		return _ddmStructure.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _ddmStructure.getName(locale);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		return _ddmStructure.getName(locale, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return _ddmStructure.getName(languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return _ddmStructure.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _ddmStructure.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _ddmStructure.getNameCurrentValue();
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _ddmStructure.getNameMap();
	}

	@Override
	public long getParentStructureId() {
		return _ddmStructure.getParentStructureId();
	}

	@Override
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public List<String> getRootFieldNames() {
		return _ddmStructure.getRootFieldNames();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmStructure.getStagedModelType();
	}

	@Override
	public String getStorageType() {
		return _ddmStructure.getStorageType();
	}

	@Override
	public long getStructureId() {
		return _ddmStructure.getStructureId();
	}

	@Override
	public String getStructureKey() {
		return _ddmStructure.getStructureKey();
	}

	@Override
	public List<DDMTemplate> getTemplates() throws PortalException {
		List<com.liferay.dynamic.data.mapping.model.DDMTemplate> ddmTemplates =
			_ddmStructure.getTemplates();

		List<DDMTemplate> templates = new ArrayList<>();

		for (com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate :
				ddmTemplates) {

			templates.add(new DDMTemplateImpl(ddmTemplate));
		}

		return templates;
	}

	@Override
	public int getType() {
		return _ddmStructure.getType();
	}

	@Override
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmStructure.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _ddmStructure.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _ddmStructure.getUuid();
	}

	@Override
	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken) {
		return _ddmStructure.getWebDavURL(themeDisplay, webDAVToken);
	}

	@Override
	public boolean hasField(String fieldName) {
		return _ddmStructure.hasField(fieldName);
	}

	@Override
	public boolean isFieldRepeatable(String fieldName) throws PortalException {
		return _ddmStructure.isFieldRepeatable(fieldName);
	}

	@Override
	public boolean isFieldTransient(String fieldName) throws PortalException {
		return _ddmStructure.isFieldTransient(fieldName);
	}

	@Override
	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	@Override
	public void prepareLocalizedFieldsForImport() throws LocaleException {
		_ddmStructure.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		_ddmStructure.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	@Override
	public void setDefinition(String definition) {
		_ddmStructure.setDefinition(definition);
	}

	@Override
	public void setGroupId(long groupId) {
		_ddmStructure.setGroupId(groupId);
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmStructure.setLastPublishDate(lastPublishDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_ddmStructure.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_ddmStructure.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_ddmStructure.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return _ddmStructure.toXmlString();
	}

	private final com.liferay.dynamic.data.mapping.model.DDMStructure
		_ddmStructure;

}