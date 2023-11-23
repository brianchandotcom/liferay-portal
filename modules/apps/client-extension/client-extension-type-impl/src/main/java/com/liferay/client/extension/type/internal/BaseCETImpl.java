/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCETImpl implements CET, Cloneable {

	public BaseCETImpl(
		String baseURL, long companyId, Date createDate, String description,
		String externalReferenceCode, Date modifiedDate, String name,
		Properties properties, boolean readOnly, String sourceCodeURL,
		int status, UnicodeProperties typeSettingsUnicodeProperties) {

		_baseURL = baseURL;
		_companyId = companyId;
		_createDate = createDate;
		_description = description;
		_externalReferenceCode = externalReferenceCode;
		_modifiedDate = modifiedDate;
		_name = name;
		_properties = properties;
		_readOnly = readOnly;
		_sourceCodeURL = sourceCodeURL;
		_status = status;

		_rawTypeSettingsUnicodeProperties = _transform(
			baseURL, typeSettingsUnicodeProperties);

		_typeSettingsUnicodeProperties = _replaceVariables(
			_modifiedDate, _rawTypeSettingsUnicodeProperties);
	}

	@Override
	public String getBaseURL() {
		return _baseURL;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getName(Locale locale) {
		String languageId = LocaleUtil.toLanguageId(locale);

		return LocalizationUtil.getLocalization(_name, languageId);
	}

	@Override
	public Properties getProperties() {
		return (Properties)_properties.clone();
	}

	@Override
	public CET getRawCET() {
		try {
			BaseCETImpl baseCETImpl = (BaseCETImpl)super.clone();

			baseCETImpl._typeSettingsUnicodeProperties =
				baseCETImpl._rawTypeSettingsUnicodeProperties;

			return baseCETImpl;
		}
		catch (CloneNotSupportedException cloneNotSupportedException) {
			throw new RuntimeException(cloneNotSupportedException);
		}
	}

	@Override
	public String getSourceCodeURL() {
		return _sourceCodeURL;
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public String getTypeSettings() {
		return _typeSettingsUnicodeProperties.toString();
	}

	@Override
	public boolean isReadOnly() {
		return _readOnly;
	}

	@Override
	public String toString() {
		return getTypeSettings();
	}

	protected static Set<String> getURLCETPropertyNames(
		Class<? extends CET> cetClass) {

		Set<String> urlCETPropertyNames = new HashSet<>();

		for (Method method : cetClass.getDeclaredMethods()) {
			CETProperty cetProperty = method.getAnnotation(CETProperty.class);

			CETProperty.Type type = cetProperty.type();

			if (type.isURL()) {
				urlCETPropertyNames.add(cetProperty.name());
			}
		}

		return urlCETPropertyNames;
	}

	protected boolean getBoolean(String key) {
		return GetterUtil.getBoolean(
			_typeSettingsUnicodeProperties.getProperty(key));
	}

	protected String getString(String key) {
		return GetterUtil.getString(
			_typeSettingsUnicodeProperties.getProperty(key));
	}

	protected abstract boolean isURLCETPropertyName(String name);

	private UnicodeProperties _replaceVariables(
		Date modifiedDate, UnicodeProperties unicodeProperties) {

		UnicodeProperties transformedUnicodeProperties = new UnicodeProperties(
			true);

		String modifiedTimestamp = String.valueOf(
			(modifiedDate == null) ? 0 : modifiedDate.getTime());

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (isURLCETPropertyName(name)) {
				value = value.replaceAll(
					Pattern.quote("${modifiedTimestamp}"), modifiedTimestamp);
			}

			transformedUnicodeProperties.put(name, value);
		}

		return transformedUnicodeProperties;
	}

	private String _transform(String baseURL, String value) {
		if (value.contains(StringPool.NEW_LINE)) {
			List<String> values = new ArrayList<>();

			for (String line : StringUtil.split(value, CharPool.NEW_LINE)) {
				values.add(_transform(baseURL, line));
			}

			return StringUtil.merge(values, StringPool.NEW_LINE);
		}

		if (value.contains(StringPool.COLON)) {
			return value;
		}

		if (!value.isEmpty() && !value.startsWith(StringPool.SLASH)) {
			value = StringPool.SLASH + value;
		}

		return baseURL + value;
	}

	private UnicodeProperties _transform(
		String baseURL, UnicodeProperties unicodeProperties) {

		UnicodeProperties transformedUnicodeProperties = new UnicodeProperties(
			true);

		for (Map.Entry<String, String> entry : unicodeProperties.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();

			if (isURLCETPropertyName(name)) {
				value = HtmlUtil.escapeHREF(_transform(baseURL, value));
			}

			transformedUnicodeProperties.put(name, value);
		}

		return transformedUnicodeProperties;
	}

	private final String _baseURL;
	private final long _companyId;
	private final Date _createDate;
	private final String _description;
	private final String _externalReferenceCode;
	private final Date _modifiedDate;
	private final String _name;
	private final Properties _properties;
	private final UnicodeProperties _rawTypeSettingsUnicodeProperties;
	private final boolean _readOnly;
	private final String _sourceCodeURL;
	private final int _status;
	private UnicodeProperties _typeSettingsUnicodeProperties;

}