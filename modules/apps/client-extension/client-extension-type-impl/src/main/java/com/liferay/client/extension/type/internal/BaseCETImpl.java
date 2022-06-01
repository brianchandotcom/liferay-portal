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

package com.liferay.client.extension.type.internal;

import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.client.extension.type.CET;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseCETImpl implements CET {

	public BaseCETImpl(ClientExtensionEntry clientExtensionEntry) {
		this(
			(clientExtensionEntry == null) ? null :
				clientExtensionEntry.getTypeSettings());

		_readOnly = false;

		if (clientExtensionEntry != null) {
			_companyId = clientExtensionEntry.getCompanyId();
			_description = clientExtensionEntry.getDescription();
			_name = clientExtensionEntry.getName(
				LocaleUtil.getMostRelevantLocale());
			_primaryKey = String.valueOf(
				clientExtensionEntry.getClientExtensionEntryId());
			_sourceCodeURL = clientExtensionEntry.getSourceCodeURL();
			_status = clientExtensionEntry.getStatus();
		}
	}

	public BaseCETImpl(String typeSettings) {
		_unicodeProperties = UnicodePropertiesBuilder.load(
			GetterUtil.getString(typeSettings)
		).build();
	}

	public BaseCETImpl(
		String baseURL, long companyId, String description, String name,
		String primaryKey, String sourceCodeURL, String typeSettings) {

		this(typeSettings);

		_baseURL = baseURL;
		_companyId = companyId;
		_description = description;
		_name = name;
		_primaryKey = primaryKey;
		_sourceCodeURL = sourceCodeURL;
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
	public String getDescription() {
		return _description;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public String getPrimaryKey() {
		return _primaryKey;
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
	public boolean isReadOnly() {
		return _readOnly;
	}

	@Override
	public String toString() {
		return _unicodeProperties.toString();
	}

	protected boolean getBoolean(String key) {
		return GetterUtil.getBoolean(_unicodeProperties.getProperty(key));
	}

	protected String getString(String key) {
		return GetterUtil.getString(_unicodeProperties.getProperty(key));
	}

	private String _baseURL = StringPool.BLANK;
	private long _companyId;
	private String _description = StringPool.BLANK;
	private String _name = StringPool.BLANK;
	private String _primaryKey = StringPool.BLANK;
	private boolean _readOnly = true;
	private String _sourceCodeURL = StringPool.BLANK;
	private int _status;
	private final UnicodeProperties _unicodeProperties;

}