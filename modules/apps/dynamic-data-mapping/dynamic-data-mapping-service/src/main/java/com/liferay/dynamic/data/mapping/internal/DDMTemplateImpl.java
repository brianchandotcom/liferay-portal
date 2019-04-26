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

import com.liferay.dynamic.data.mapping.kernel.DDMTemplate;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Marcellus Tavares
 * @author Rafael Praxedes
 */
public class DDMTemplateImpl implements DDMTemplate {

	public DDMTemplateImpl(
		com.liferay.dynamic.data.mapping.model.DDMTemplate ddmTemplate) {

		_ddmTemplate = ddmTemplate;
	}

	@Override
	public Object clone() {
		return new DDMTemplateImpl(
			(com.liferay.dynamic.data.mapping.model.DDMTemplate)
				_ddmTemplate.clone());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplate)) {
			return false;
		}

		DDMTemplate ddmTemplate = (DDMTemplate)obj;

		if ((getTemplateId() == ddmTemplate.getTemplateId()) &&
			Objects.equals(getScript(), ddmTemplate.getScript())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean getCacheable() {
		return _ddmTemplate.getCacheable();
	}

	@Override
	public String getClassName() {
		return _ddmTemplate.getClassName();
	}

	@Override
	public long getClassNameId() {
		return _ddmTemplate.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _ddmTemplate.getClassPK();
	}

	@Override
	public long getCompanyId() {
		return _ddmTemplate.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _ddmTemplate.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _ddmTemplate.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _ddmTemplate.getDescription(locale);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		return _ddmTemplate.getDescription(locale, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return _ddmTemplate.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _ddmTemplate.getDescription(languageId, useDefault);
	}

	@Override
	public String getDescriptionCurrentLanguageId() {
		return _ddmTemplate.getDescriptionCurrentLanguageId();
	}

	@Override
	public String getDescriptionCurrentValue() {
		return _ddmTemplate.getDescriptionCurrentValue();
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _ddmTemplate.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmTemplate.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _ddmTemplate.getGroupId();
	}

	@Override
	public String getLanguage() {
		return _ddmTemplate.getLanguage();
	}

	@Override
	public Date getLastPublishDate() {
		return _ddmTemplate.getLastPublishDate();
	}

	@Override
	public String getMode() {
		return _ddmTemplate.getMode();
	}

	@Override
	public Class<?> getModelClass() {
		return _ddmTemplate.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _ddmTemplate.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _ddmTemplate.getModifiedDate();
	}

	@Override
	public String getName() {
		return _ddmTemplate.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _ddmTemplate.getName(locale);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		return _ddmTemplate.getName(locale, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return _ddmTemplate.getName(languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return _ddmTemplate.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentLanguageId() {
		return _ddmTemplate.getNameCurrentLanguageId();
	}

	@Override
	public String getNameCurrentValue() {
		return _ddmTemplate.getNameCurrentValue();
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _ddmTemplate.getNameMap();
	}

	@Override
	public long getPrimaryKey() {
		return _ddmTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmTemplate.getPrimaryKeyObj();
	}

	@Override
	public long getResourceClassNameId() {
		return _ddmTemplate.getResourceClassNameId();
	}

	@Override
	public String getScript() {
		return _ddmTemplate.getScript();
	}

	@Override
	public boolean getSmallImage() {
		return _ddmTemplate.getSmallImage();
	}

	@Override
	public long getSmallImageId() {
		return _ddmTemplate.getSmallImageId();
	}

	@Override
	public String getSmallImageType() throws PortalException {
		return _ddmTemplate.getSmallImageType();
	}

	@Override
	public String getSmallImageURL() {
		return _ddmTemplate.getSmallImageURL();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmTemplate.getStagedModelType();
	}

	@Override
	public long getTemplateId() {
		return _ddmTemplate.getTemplateId();
	}

	@Override
	public String getTemplateKey() {
		return _ddmTemplate.getTemplateKey();
	}

	@Override
	public String getType() {
		return _ddmTemplate.getType();
	}

	@Override
	public long getUserId() {
		return _ddmTemplate.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmTemplate.getUserName();
	}

	@Override
	public String getUserUuid() {
		return _ddmTemplate.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _ddmTemplate.getUuid();
	}

	@Override
	public String getVersion() {
		return _ddmTemplate.getVersion();
	}

	@Override
	public long getVersionUserId() {
		return _ddmTemplate.getVersionUserId();
	}

	@Override
	public String getVersionUserName() {
		return _ddmTemplate.getVersionUserName();
	}

	@Override
	public String getWebDavURL(ThemeDisplay themeDisplay, String webDAVToken) {
		return _ddmTemplate.getWebDavURL(themeDisplay, webDAVToken);
	}

	@Override
	public int hashCode() {
		int hash = HashUtil.hash(0, getTemplateId());

		return HashUtil.hash(hash, getScript());
	}

	@Override
	public boolean isCacheable() {
		return _ddmTemplate.isCacheable();
	}

	@Override
	public boolean isSmallImage() {
		return _ddmTemplate.isSmallImage();
	}

	@Override
	public void setCompanyId(long companyId) {
		_ddmTemplate.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_ddmTemplate.setCreateDate(createDate);
	}

	@Override
	public void setGroupId(long groupId) {
		_ddmTemplate.setGroupId(groupId);
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_ddmTemplate.setLastPublishDate(lastPublishDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmTemplate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUserId(long userId) {
		_ddmTemplate.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_ddmTemplate.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_ddmTemplate.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_ddmTemplate.setUuid(uuid);
	}

	@Override
	public String toString() {
		return _ddmTemplate.toString();
	}

	@Override
	public String toXmlString() {
		return _ddmTemplate.toXmlString();
	}

	private final com.liferay.dynamic.data.mapping.model.DDMTemplate
		_ddmTemplate;

}