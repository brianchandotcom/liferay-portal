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

package com.liferay.portal.tools.service.builder.test.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class LocalizedEntryLocalizationSoap implements Serializable {

	public static LocalizedEntryLocalizationSoap toSoapModel(
		LocalizedEntryLocalization model) {

		LocalizedEntryLocalizationSoap soapModel =
			new LocalizedEntryLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setLocalizedEntryLocalizationId(
			model.getLocalizedEntryLocalizationId());
		soapModel.setLocalizedEntryId(model.getLocalizedEntryId());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTitle(model.getTitle());
		soapModel.setContent(model.getContent());

		return soapModel;
	}

	public static LocalizedEntryLocalizationSoap[] toSoapModels(
		LocalizedEntryLocalization[] models) {

		LocalizedEntryLocalizationSoap[] soapModels =
			new LocalizedEntryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LocalizedEntryLocalizationSoap[][] toSoapModels(
		LocalizedEntryLocalization[][] models) {

		LocalizedEntryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LocalizedEntryLocalizationSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new LocalizedEntryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LocalizedEntryLocalizationSoap[] toSoapModels(
		List<LocalizedEntryLocalization> models) {

		List<LocalizedEntryLocalizationSoap> soapModels =
			new ArrayList<LocalizedEntryLocalizationSoap>(models.size());

		for (LocalizedEntryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new LocalizedEntryLocalizationSoap[soapModels.size()]);
	}

	public LocalizedEntryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _localizedEntryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setLocalizedEntryLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getLocalizedEntryLocalizationId() {
		return _localizedEntryLocalizationId;
	}

	public void setLocalizedEntryLocalizationId(
		long localizedEntryLocalizationId) {

		_localizedEntryLocalizationId = localizedEntryLocalizationId;
	}

	public long getLocalizedEntryId() {
		return _localizedEntryId;
	}

	public void setLocalizedEntryId(long localizedEntryId) {
		_localizedEntryId = localizedEntryId;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getContent() {
		return _content;
	}

	public void setContent(String content) {
		_content = content;
	}

	private long _mvccVersion;
	private long _localizedEntryLocalizationId;
	private long _localizedEntryId;
	private String _languageId;
	private String _title;
	private String _content;

}