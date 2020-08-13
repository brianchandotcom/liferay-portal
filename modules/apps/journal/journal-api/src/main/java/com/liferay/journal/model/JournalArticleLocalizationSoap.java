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

package com.liferay.journal.model;

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
public class JournalArticleLocalizationSoap implements Serializable {

	public static JournalArticleLocalizationSoap toSoapModel(
		JournalArticleLocalization model) {

		JournalArticleLocalizationSoap soapModel =
			new JournalArticleLocalizationSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setArticleLocalizationId(model.getArticleLocalizationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setArticlePK(model.getArticlePK());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setLanguageId(model.getLanguageId());

		return soapModel;
	}

	public static JournalArticleLocalizationSoap[] toSoapModels(
		JournalArticleLocalization[] models) {

		JournalArticleLocalizationSoap[] soapModels =
			new JournalArticleLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static JournalArticleLocalizationSoap[][] toSoapModels(
		JournalArticleLocalization[][] models) {

		JournalArticleLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new JournalArticleLocalizationSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new JournalArticleLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static JournalArticleLocalizationSoap[] toSoapModels(
		List<JournalArticleLocalization> models) {

		List<JournalArticleLocalizationSoap> soapModels =
			new ArrayList<JournalArticleLocalizationSoap>(models.size());

		for (JournalArticleLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new JournalArticleLocalizationSoap[soapModels.size()]);
	}

	public JournalArticleLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _articleLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setArticleLocalizationId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
	}

	public long getArticleLocalizationId() {
		return _articleLocalizationId;
	}

	public void setArticleLocalizationId(long articleLocalizationId) {
		_articleLocalizationId = articleLocalizationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getArticlePK() {
		return _articlePK;
	}

	public void setArticlePK(long articlePK) {
		_articlePK = articlePK;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private long _articleLocalizationId;
	private long _companyId;
	private long _articlePK;
	private String _title;
	private String _description;
	private String _languageId;

}