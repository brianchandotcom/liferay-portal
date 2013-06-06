/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class JournalArticleImageSoap implements Serializable {
	public static JournalArticleImageSoap toSoapModel(JournalArticleImage model) {
		JournalArticleImageSoap soapModel = new JournalArticleImageSoap();

		soapModel.setArticleImageId(model.getArticleImageId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setArticleId(model.getArticleId());
		soapModel.setVersion(model.getVersion());
		soapModel.setElInstanceId(model.getElInstanceId());
		soapModel.setElName(model.getElName());
		soapModel.setLanguageId(model.getLanguageId());
		soapModel.setTempImage(model.getTempImage());

		return soapModel;
	}

	public static JournalArticleImageSoap[] toSoapModels(
		JournalArticleImage[] models) {
		JournalArticleImageSoap[] soapModels = new JournalArticleImageSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static JournalArticleImageSoap[][] toSoapModels(
		JournalArticleImage[][] models) {
		JournalArticleImageSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new JournalArticleImageSoap[models.length][models[0].length];
		}
		else {
			soapModels = new JournalArticleImageSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static JournalArticleImageSoap[] toSoapModels(
		List<JournalArticleImage> models) {
		List<JournalArticleImageSoap> soapModels = new ArrayList<JournalArticleImageSoap>(models.size());

		for (JournalArticleImage model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new JournalArticleImageSoap[soapModels.size()]);
	}

	public JournalArticleImageSoap() {
	}

	public long getPrimaryKey() {
		return _articleImageId;
	}

	public void setPrimaryKey(long pk) {
		setArticleImageId(pk);
	}

	public long getArticleImageId() {
		return _articleImageId;
	}

	public void setArticleImageId(long articleImageId) {
		_articleImageId = articleImageId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getArticleId() {
		return _articleId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	public double getVersion() {
		return _version;
	}

	public void setVersion(double version) {
		_version = version;
	}

	public String getElInstanceId() {
		return _elInstanceId;
	}

	public void setElInstanceId(String elInstanceId) {
		_elInstanceId = elInstanceId;
	}

	public String getElName() {
		return _elName;
	}

	public void setElName(String elName) {
		_elName = elName;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public boolean getTempImage() {
		return _tempImage;
	}

	public boolean isTempImage() {
		return _tempImage;
	}

	public void setTempImage(boolean tempImage) {
		_tempImage = tempImage;
	}

	private long _articleImageId;
	private long _groupId;
	private String _articleId;
	private double _version;
	private String _elInstanceId;
	private String _elName;
	private String _languageId;
	private boolean _tempImage;
}