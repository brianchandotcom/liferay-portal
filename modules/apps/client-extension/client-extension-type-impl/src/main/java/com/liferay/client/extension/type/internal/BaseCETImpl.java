/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.client.extension.type.annotation.CETProperty;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCETImpl implements CET {

	public BaseCETImpl(ClientExtensionEntry clientExtensionEntry) {
		_clientExtensionEntry = clientExtensionEntry;

		if (clientExtensionEntry != null) {
			_companyId = clientExtensionEntry.getCompanyId();
			_createDate = clientExtensionEntry.getCreateDate();
			_description = clientExtensionEntry.getDescription();
			_externalReferenceCode =
				clientExtensionEntry.getExternalReferenceCode();
			_modifiedDate = clientExtensionEntry.getModifiedDate();

			try {
				_properties = PropertiesUtil.load(
					clientExtensionEntry.getProperties());
			}
			catch (IOException ioException) {
				ReflectionUtil.throwException(ioException);
			}

			_sourceCodeURL = clientExtensionEntry.getSourceCodeURL();
			_status = clientExtensionEntry.getStatus();
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).load(
				clientExtensionEntry.getTypeSettings()
			).build();
		}
		else {
			_typeSettingsUnicodeProperties = UnicodePropertiesBuilder.create(
				true
			).build();
		}
	}

	public BaseCETImpl(
		String baseURL, long companyId, String description,
		String externalReferenceCode, String name, Properties properties,
		String sourceCodeURL, UnicodeProperties typeSettingsUnicodeProperties) {

		this(baseURL, typeSettingsUnicodeProperties);

		_companyId = companyId;
		_description = description;
		_externalReferenceCode = externalReferenceCode;
		_name = name;
		_properties = properties;
		_sourceCodeURL = sourceCodeURL;

		_readOnly = true;
	}

	public BaseCETImpl(
		String baseURL, UnicodeProperties typeSettingsUnicodeProperties) {

		_baseURL = baseURL;
		_typeSettingsUnicodeProperties = _transform(
			baseURL, typeSettingsUnicodeProperties);
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
		return _modifiedDate;
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
	public String getName(Locale locale) {
		if (_clientExtensionEntry != null) {
			return _clientExtensionEntry.getName(locale);
		}

		return _name;
	}

	@Override
	public Properties getProperties() {
		return (Properties)_properties.clone();
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

	private String _baseURL = StringPool.BLANK;
	private ClientExtensionEntry _clientExtensionEntry;
	private long _companyId;
	private Date _createDate;
	private String _description = StringPool.BLANK;
	private String _externalReferenceCode = StringPool.BLANK;
	private Date _modifiedDate;
	private String _name = StringPool.BLANK;
	private Properties _properties;
	private boolean _readOnly;
	private String _sourceCodeURL = StringPool.BLANK;
	private int _status = WorkflowConstants.STATUS_APPROVED;
	private final UnicodeProperties _typeSettingsUnicodeProperties;

}