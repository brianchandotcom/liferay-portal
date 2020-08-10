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

package com.liferay.commerce.product.type.virtual.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.product.type.virtual.service.http.CPDefinitionVirtualSettingServiceSoap}.
 *
 * @author Marco Leo
 * @generated
 */
public class CPDefinitionVirtualSettingSoap implements Serializable {

	public static CPDefinitionVirtualSettingSoap toSoapModel(
		CPDefinitionVirtualSetting model) {

		CPDefinitionVirtualSettingSoap soapModel =
			new CPDefinitionVirtualSettingSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setCPDefinitionVirtualSettingId(
			model.getCPDefinitionVirtualSettingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setFileEntryId(model.getFileEntryId());
		soapModel.setUrl(model.getUrl());
		soapModel.setActivationStatus(model.getActivationStatus());
		soapModel.setDuration(model.getDuration());
		soapModel.setMaxUsages(model.getMaxUsages());
		soapModel.setUseSample(model.isUseSample());
		soapModel.setSampleFileEntryId(model.getSampleFileEntryId());
		soapModel.setSampleUrl(model.getSampleUrl());
		soapModel.setTermsOfUseRequired(model.isTermsOfUseRequired());
		soapModel.setTermsOfUseContent(model.getTermsOfUseContent());
		soapModel.setTermsOfUseJournalArticleResourcePrimKey(
			model.getTermsOfUseJournalArticleResourcePrimKey());
		soapModel.setOverride(model.isOverride());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static CPDefinitionVirtualSettingSoap[] toSoapModels(
		CPDefinitionVirtualSetting[] models) {

		CPDefinitionVirtualSettingSoap[] soapModels =
			new CPDefinitionVirtualSettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionVirtualSettingSoap[][] toSoapModels(
		CPDefinitionVirtualSetting[][] models) {

		CPDefinitionVirtualSettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CPDefinitionVirtualSettingSoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new CPDefinitionVirtualSettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CPDefinitionVirtualSettingSoap[] toSoapModels(
		List<CPDefinitionVirtualSetting> models) {

		List<CPDefinitionVirtualSettingSoap> soapModels =
			new ArrayList<CPDefinitionVirtualSettingSoap>(models.size());

		for (CPDefinitionVirtualSetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CPDefinitionVirtualSettingSoap[soapModels.size()]);
	}

	public CPDefinitionVirtualSettingSoap() {
	}

	public long getPrimaryKey() {
		return _CPDefinitionVirtualSettingId;
	}

	public void setPrimaryKey(long pk) {
		setCPDefinitionVirtualSettingId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getCPDefinitionVirtualSettingId() {
		return _CPDefinitionVirtualSettingId;
	}

	public void setCPDefinitionVirtualSettingId(
		long CPDefinitionVirtualSettingId) {

		_CPDefinitionVirtualSettingId = CPDefinitionVirtualSettingId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public String getUrl() {
		return _url;
	}

	public void setUrl(String url) {
		_url = url;
	}

	public int getActivationStatus() {
		return _activationStatus;
	}

	public void setActivationStatus(int activationStatus) {
		_activationStatus = activationStatus;
	}

	public long getDuration() {
		return _duration;
	}

	public void setDuration(long duration) {
		_duration = duration;
	}

	public int getMaxUsages() {
		return _maxUsages;
	}

	public void setMaxUsages(int maxUsages) {
		_maxUsages = maxUsages;
	}

	public boolean getUseSample() {
		return _useSample;
	}

	public boolean isUseSample() {
		return _useSample;
	}

	public void setUseSample(boolean useSample) {
		_useSample = useSample;
	}

	public long getSampleFileEntryId() {
		return _sampleFileEntryId;
	}

	public void setSampleFileEntryId(long sampleFileEntryId) {
		_sampleFileEntryId = sampleFileEntryId;
	}

	public String getSampleUrl() {
		return _sampleUrl;
	}

	public void setSampleUrl(String sampleUrl) {
		_sampleUrl = sampleUrl;
	}

	public boolean getTermsOfUseRequired() {
		return _termsOfUseRequired;
	}

	public boolean isTermsOfUseRequired() {
		return _termsOfUseRequired;
	}

	public void setTermsOfUseRequired(boolean termsOfUseRequired) {
		_termsOfUseRequired = termsOfUseRequired;
	}

	public String getTermsOfUseContent() {
		return _termsOfUseContent;
	}

	public void setTermsOfUseContent(String termsOfUseContent) {
		_termsOfUseContent = termsOfUseContent;
	}

	public long getTermsOfUseJournalArticleResourcePrimKey() {
		return _termsOfUseJournalArticleResourcePrimKey;
	}

	public void setTermsOfUseJournalArticleResourcePrimKey(
		long termsOfUseJournalArticleResourcePrimKey) {

		_termsOfUseJournalArticleResourcePrimKey =
			termsOfUseJournalArticleResourcePrimKey;
	}

	public boolean getOverride() {
		return _override;
	}

	public boolean isOverride() {
		return _override;
	}

	public void setOverride(boolean override) {
		_override = override;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private String _uuid;
	private long _CPDefinitionVirtualSettingId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _fileEntryId;
	private String _url;
	private int _activationStatus;
	private long _duration;
	private int _maxUsages;
	private boolean _useSample;
	private long _sampleFileEntryId;
	private String _sampleUrl;
	private boolean _termsOfUseRequired;
	private String _termsOfUseContent;
	private long _termsOfUseJournalArticleResourcePrimKey;
	private boolean _override;
	private Date _lastPublishDate;

}