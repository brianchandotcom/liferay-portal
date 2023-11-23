/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.type.CET;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;
import java.util.Locale;
import java.util.Properties;

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
		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;
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

	protected boolean getBoolean(String key) {
		return GetterUtil.getBoolean(
			_typeSettingsUnicodeProperties.getProperty(key));
	}

	protected String getString(String key) {
		return GetterUtil.getString(
			_typeSettingsUnicodeProperties.getProperty(key));
	}

	private final String _baseURL;
	private final long _companyId;
	private final Date _createDate;
	private final String _description;
	private final String _externalReferenceCode;
	private final Date _modifiedDate;
	private final String _name;
	private final Properties _properties;
	private final boolean _readOnly;
	private final String _sourceCodeURL;
	private final int _status;
	private final UnicodeProperties _typeSettingsUnicodeProperties;

}