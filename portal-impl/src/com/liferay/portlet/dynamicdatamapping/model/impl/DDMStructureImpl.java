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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.LocaleException;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PredicateFilter;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheField;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.StructureFieldException;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormField;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.model.LocalizedValue;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMStructureImpl extends DDMStructureBaseImpl {

	@Override
	public DDMForm createFullHierarchyDDMForm() throws PortalException {
		DDMForm fullHierarchyDDMForm = DDMFormXSDDeserializerUtil.deserialize(
			getDefinition());

		DDMStructure parentDDMStructure = getParentDDMStructure();

		if (parentDDMStructure != null) {
			DDMForm ancestorsDDMForm =
				parentDDMStructure.createFullHierarchyDDMForm();

			List<DDMFormField> ddmFormFields =
				fullHierarchyDDMForm.getDDMFormFields();

			ddmFormFields.addAll(ancestorsDDMForm.getDDMFormFields());
		}

		return fullHierarchyDDMForm;
	}

	@Override
	public String[] getAvailableLanguageIds() {
		DDMForm ddmForm = getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		return LocaleUtil.toLanguageIds(
			availableLocales.toArray(new Locale[availableLocales.size()]));
	}

	@Override
	public List<String> getChildrenFieldNames(String fieldName)
		throws PortalException {

		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return getDDMFormFieldNames(ddmFormField.getNestedDDMFormFields());
	}

	@Override
	public DDMForm getDDMForm() {
		if (_ddmForm == null) {
			try {
				_ddmForm = DDMFormXSDDeserializerUtil.deserialize(
					getDefinition());

				addDDMFormPrivateDDMFormFields(_ddmForm);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _ddmForm;
	}

	@Override
	public DDMFormField getDDMFormField(String fieldName)
		throws PortalException {

		Map<String, DDMFormField> ddmFormFieldsMap =
			getFullHierarchyDDMFormFieldsMap(true);

		DDMFormField ddmFormField = ddmFormFieldsMap.get(fieldName);

		if (ddmFormField == null) {
			throw new StructureFieldException();
		}

		return ddmFormField;
	}

	@Override
	public List<DDMFormField> getDDMFormFields(boolean includeTransientFields) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			getFullHierarchyDDMFormFieldsMap(true);

		List<DDMFormField> ddmFormFields = new ArrayList<DDMFormField>(
			ddmFormFieldsMap.values());

		if (includeTransientFields) {
			return ddmFormFields;
		}

		return filterTransientDDMFormFields(ddmFormFields);
	}

	@Override
	public String getDefaultLanguageId() {
		DDMForm ddmForm = getDDMForm();

		return LocaleUtil.toLanguageId(ddmForm.getDefaultLocale());
	}

	@Override
	public String getFieldDataType(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return ddmFormField.getDataType();
	}

	@Override
	public String getFieldLabel(String fieldName, Locale locale)
		throws PortalException {

		DDMFormField ddmFormField = getDDMFormField(fieldName);

		LocalizedValue label = ddmFormField.getLabel();

		return label.getString(locale);
	}

	@Override
	public String getFieldLabel(String fieldName, String locale)
		throws PortalException {

		return getFieldLabel(fieldName, LocaleUtil.fromLanguageId(locale));
	}

	@Override
	public Set<String> getFieldNames() {
		List<DDMFormField> ddmFormFields = getDDMFormFields(false);

		List<String> ddmFormFieldNames = getDDMFormFieldNames(ddmFormFields);

		return SetUtil.fromList(ddmFormFieldNames);
	}

	@Override
	public String getFieldProperty(String fieldName, String property)
		throws PortalException {

		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return BeanPropertiesUtil.getString(ddmFormField, property);
	}

	@Override
	public boolean getFieldRepeatable(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return ddmFormField.isRepeatable();
	}

	@Override
	public boolean getFieldRequired(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return ddmFormField.isRequired();
	}

	@Override
	public String getFieldTip(String fieldName, Locale locale)
		throws PortalException {

		DDMFormField ddmFormField = getDDMFormField(fieldName);

		LocalizedValue tip = ddmFormField.getTip();

		return tip.getString(locale);
	}

	@Override
	public String getFieldTip(String fieldName, String locale)
		throws PortalException {

		return getFieldTip(fieldName, LocaleUtil.fromLanguageId(locale));
	}

	@Override
	public String getFieldType(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return ddmFormField.getType();
	}

	@Override
	public DDMForm getFullHierarchyDDMForm() {
		if (_fullHierarchyDDMForm == null) {
			try {
				_fullHierarchyDDMForm = createFullHierarchyDDMForm();

				addDDMFormPrivateDDMFormFields(_fullHierarchyDDMForm);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		return _fullHierarchyDDMForm;
	}

	@Override
	public Map<String, DDMFormField> getFullHierarchyDDMFormFieldsMap(
		boolean includeNestedDDMFormFields) {

		DDMForm ddmForm = getFullHierarchyDDMForm();

		return ddmForm.getDDMFormFieldsMap(includeNestedDDMFormFields);
	}

	@Override
	public List<String> getRootFieldNames() {
		DDMForm ddmForm = getFullHierarchyDDMForm();

		return getDDMFormFieldNames(ddmForm.getDDMFormFields());
	}

	@Override
	public List<DDMTemplate> getTemplates() {
		return DDMTemplateLocalServiceUtil.getTemplates(getStructureId());
	}

	@Override
	public String getUnambiguousName(
			List<DDMStructure> structures, long groupId, final Locale locale)
		throws PortalException {

		if (getGroupId() == groupId ) {
			return getName(locale);
		}

		boolean hasAmbiguousName = ListUtil.exists(
			structures,
			new PredicateFilter<DDMStructure>() {

				@Override
				public boolean filter(DDMStructure structure) {
					String name = structure.getName(locale);

					if (name.equals(getName(locale)) &&
						(structure.getStructureId() != getStructureId())) {

						return true;
					}

					return false;
				}

			});

		if (hasAmbiguousName) {
			Group group = GroupLocalServiceUtil.getGroup(getGroupId());

			return group.getUnambiguousName(getName(locale), locale);
		}

		return getName(locale);
	}

	/**
	 * Returns the WebDAV URL to access the structure.
	 *
	 * @param  themeDisplay the theme display needed to build the URL. It can
	 *         set HTTPS access, the server name, the server port, the path
	 *         context, and the scope group.
	 * @param  webDAVToken the WebDAV token for the URL
	 * @return the WebDAV URL
	 */
	@Override
	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken) {
		StringBundler sb = new StringBundler(11);

		boolean secure = false;

		if (themeDisplay.isSecure() ||
			PropsValues.WEBDAV_SERVLET_HTTPS_REQUIRED) {

			secure = true;
		}

		String portalURL = PortalUtil.getPortalURL(
			themeDisplay.getServerName(), themeDisplay.getServerPort(), secure);

		sb.append(portalURL);

		sb.append(themeDisplay.getPathContext());
		sb.append(StringPool.SLASH);
		sb.append("webdav");

		Group group = themeDisplay.getScopeGroup();

		sb.append(group.getFriendlyURL());

		sb.append(StringPool.SLASH);
		sb.append(webDAVToken);
		sb.append(StringPool.SLASH);
		sb.append("Structures");
		sb.append(StringPool.SLASH);
		sb.append(getStructureId());

		return sb.toString();
	}

	@Override
	public boolean hasField(String fieldName) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			getFullHierarchyDDMFormFieldsMap(true);

		return ddmFormFieldsMap.containsKey(fieldName);
	}

	@Override
	public boolean isFieldPrivate(String fieldName) {
		if (fieldName.startsWith(StringPool.UNDERLINE)) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isFieldRepeatable(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		return ddmFormField.isRepeatable();
	}

	@Override
	public boolean isFieldTransient(String fieldName) throws PortalException {
		DDMFormField ddmFormField = getDDMFormField(fieldName);

		if (Validator.isNull(ddmFormField.getDataType())) {
			return true;
		}

		return false;
	}

	@Override
	public void prepareLocalizedFieldsForImport(Locale defaultImportLocale)
		throws LocaleException {

		super.prepareLocalizedFieldsForImport(defaultImportLocale);

		Locale ddmStructureDefaultLocale = LocaleUtil.fromLanguageId(
			getDefaultLanguageId());

		try {
			setDefinition(
				DDMXMLUtil.updateXMLDefaultLocale(
					getDefinition(), ddmStructureDefaultLocale,
					defaultImportLocale));
		}
		catch (Exception e) {
			throw new LocaleException(LocaleException.TYPE_EXPORT_IMPORT, e);
		}
	}

	@Override
	public void setDDMForm(DDMForm ddmForm) {
		_ddmForm = ddmForm;
	}

	@Override
	public void setDefinition(String definition) {
		super.setDefinition(definition);

		_ddmForm = null;
		_fullHierarchyDDMForm = null;
	}

	@Override
	public void setFullHierarchyDDMForm(DDMForm fullHierarchyDDMForm) {
		_fullHierarchyDDMForm = fullHierarchyDDMForm;
	}

	@Override
	public void updateDDMForm(DDMForm ddmForm) {
		setDefinition(DDMFormXSDSerializerUtil.serialize(ddmForm));
	}

	protected void addDDMFormPrivateDDMFormFields(DDMForm ddmForm) {
		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		String[] privateFieldNames =
			PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_NAMES;

		for (String privateFieldName : privateFieldNames) {
			DDMFormField privateDDMFormField = createPrivateDDMFormField(
				privateFieldName);

			ddmFormFields.add(privateDDMFormField);
		}
	}

	protected DDMFormField createPrivateDDMFormField(String privateFieldName) {
		DDMFormField privateDDMFormField = new DDMFormField(
			privateFieldName, "text");

		String dataType = PropsUtil.get(
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_DATATYPE,
			new Filter(privateFieldName));

		privateDDMFormField.setDataType(dataType);

		String repeatable = PropsUtil.get(
			PropsKeys.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_REPEATABLE,
			new Filter(privateFieldName));

		privateDDMFormField.setRepeatable(Boolean.valueOf(repeatable));

		return privateDDMFormField;
	}

	protected List<DDMFormField> filterTransientDDMFormFields(
		List<DDMFormField> ddmFormFields) {

		PredicateFilter<DDMFormField> predicateFilter =
			new PredicateFilter<DDMFormField>() {

				@Override
				public boolean filter(DDMFormField ddmFormField) {
					if (Validator.isNull(ddmFormField.getDataType())) {
						return false;
					}

					return true;
				}
			};

		return ListUtil.filter(ddmFormFields, predicateFilter);
	}

	protected List<String> getDDMFormFieldNames(
		List<DDMFormField> ddmFormFields) {

		List<String> fieldNames = new ArrayList<String>();

		for (DDMFormField ddmFormField : ddmFormFields) {
			fieldNames.add(ddmFormField.getName());
		}

		return fieldNames;
	}

	protected DDMStructure getParentDDMStructure() throws PortalException {
		if (getParentStructureId() == 0) {
			return null;
		}

		DDMStructure parentStructure =
			DDMStructureLocalServiceUtil.getStructure(getParentStructureId());

		return parentStructure;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DDMStructureImpl.class);

	@CacheField(methodName = "DDMForm")
	private DDMForm _ddmForm;

	@CacheField(methodName = "FullHierarchyDDMForm")
	private DDMForm _fullHierarchyDDMForm;

}