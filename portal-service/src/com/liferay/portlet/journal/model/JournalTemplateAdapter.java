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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.File;
import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class JournalTemplateAdapter implements JournalTemplate {

	public JournalTemplateAdapter(DDMTemplate ddmTemplate) {
		_ddmTemplate = ddmTemplate;
	}

	@Override
	public Object clone() {
		JournalTemplateAdapter journalTemplateAdapter =
			new JournalTemplateAdapter();

		journalTemplateAdapter.setUuid(getUuid());
		journalTemplateAdapter.setId(getId());
		journalTemplateAdapter.setGroupId(getGroupId());
		journalTemplateAdapter.setCompanyId(getCompanyId());
		journalTemplateAdapter.setUserId(getUserId());
		journalTemplateAdapter.setUserName(getUserName());
		journalTemplateAdapter.setCreateDate(getCreateDate());
		journalTemplateAdapter.setModifiedDate(getModifiedDate());
		journalTemplateAdapter.setTemplateId(getTemplateId());
		journalTemplateAdapter.setStructureId(getStructureId());
		journalTemplateAdapter.setName(getName());
		journalTemplateAdapter.setDescription(getDescription());
		journalTemplateAdapter.setXsl(getXsl());
		journalTemplateAdapter.setLangType(getLangType());
		journalTemplateAdapter.setCacheable(getCacheable());
		journalTemplateAdapter.setSmallImage(getSmallImage());
		journalTemplateAdapter.setSmallImageId(getSmallImageId());
		journalTemplateAdapter.setSmallImageURL(getSmallImageURL());

		return journalTemplateAdapter;
	}

	@Override
	public int compareTo(JournalTemplate journalTemplate) {
		String templateId = getTemplateId();

		int value = templateId.compareTo(journalTemplate.getTemplateId());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalTemplate)) {
			return false;
		}

		JournalTemplate journalTemplate = (JournalTemplate)obj;

		if (Validator.equals(getId(), journalTemplate.getId())) {
			return true;
		}

		return false;
	}

	@Override
	public boolean getCacheable() {
		return _ddmTemplate.getCacheable();
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
	public long getId() {
		return _ddmTemplate.getTemplateId();
	}

	@Override
	public String getLangType() {
		return _ddmTemplate.getLanguage();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("id", getId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("templateId", getTemplateId());
		attributes.put("structureId", getStructureId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsl", getXsl());
		attributes.put("langType", getLangType());
		attributes.put("cacheable", getCacheable());
		attributes.put("smallImage", getSmallImage());
		attributes.put("smallImageId", getSmallImageId());
		attributes.put("smallImageURL", getSmallImageURL());

		return attributes;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return JournalTemplate.class.getName();
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
	public boolean getSmallImage() {
		return _ddmTemplate.getSmallImage();
	}

	@Override
	public File getSmallImageFile() throws PortalException, SystemException {
		if (_smallImageFile == null) {
			Image smallImage = fetchSmallImage();

			if (smallImage != null) {
				_smallImageFile = getSmallImageFile(smallImage.getTextObj());
			}
		}

		return _smallImageFile;
	}

	@Override
	public long getSmallImageId() {
		return _ddmTemplate.getSmallImageId();
	}

	@Override
	public String getSmallImageType() throws PortalException, SystemException {
		if (_smallImageType == null) {
			Image smallImage = fetchSmallImage();

			if (smallImage != null) {
				_smallImageType = smallImage.getType();
			}
		}

		return _smallImageType;
	}

	@Override
	public String getSmallImageURL() {
		return _ddmTemplate.getSmallImageURL();
	}

	@Override
	public String getStructureId() {
		if (_ddmTemplate.getClassPK() == 0) {
			return null;
		}

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					_ddmTemplate.getClassPK());

			return ddmStructure.getStructureKey();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	@Override
	public String getTemplateId() {
		return _ddmTemplate.getTemplateKey();
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
	public String getUserUuid() throws SystemException {
		return _ddmTemplate.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _ddmTemplate.getUuid();
	}

	@Override
	public String getXsl() {
		return _ddmTemplate.getScript();
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isCacheable() {
		return _ddmTemplate.isCacheable();
	}

	@Override
	public boolean isNew() {
		return _ddmTemplate.isNew();
	}

	@Override
	public boolean isSmallImage() {
		return _ddmTemplate.isSmallImage();
	}

	@Override
	public void setCacheable(boolean cacheable) {
		_ddmTemplate.setCacheable(cacheable);
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
	public void setDescription(String description) {
		_ddmTemplate.setDescription(description);
	}

	@Override
	public void setDescription(String description, Locale locale) {
		_ddmTemplate.setDescription(description, locale);
	}

	@Override
	public void setDescription(
		String description, Locale locale, Locale defaultLocale) {

		_ddmTemplate.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		_ddmTemplate.setDescriptionMap(descriptionMap);
	}

	@Override
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale) {

		_ddmTemplate.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(JournalTemplate journalTemplate) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		ExpandoBridge journalTemplateExpandoBridge =
			journalTemplate.getExpandoBridge();

		expandoBridge.setAttributes(
			journalTemplateExpandoBridge.getAttributes());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setGroupId(long groupId) {
		_ddmTemplate.setGroupId(groupId);
	}

	@Override
	public void setId(long id) {
		_ddmTemplate.setTemplateId(id);
	}

	@Override
	public void setLangType(String langType) {
		_ddmTemplate.setLanguage(langType);
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long id = (Long)attributes.get("id");

		if (id != null) {
			setId(id);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String templateId = (String)attributes.get("templateId");

		if (templateId != null) {
			setTemplateId(templateId);
		}

		String structureId = (String)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String xsl = (String)attributes.get("xsl");

		if (xsl != null) {
			setXsl(xsl);
		}

		String langType = (String)attributes.get("langType");

		if (langType != null) {
			setLangType(langType);
		}

		Boolean cacheable = (Boolean)attributes.get("cacheable");

		if (cacheable != null) {
			setCacheable(cacheable);
		}

		Boolean smallImage = (Boolean)attributes.get("smallImage");

		if (smallImage != null) {
			setSmallImage(smallImage);
		}

		Long smallImageId = (Long)attributes.get("smallImageId");

		if (smallImageId != null) {
			setSmallImageId(smallImageId);
		}

		String smallImageURL = (String)attributes.get("smallImageURL");

		if (smallImageURL != null) {
			setSmallImageURL(smallImageURL);
		}
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmTemplate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setName(String name) {
		_ddmTemplate.setName(name);
	}

	@Override
	public void setName(String name, Locale locale) {
		_ddmTemplate.setName(name, locale);
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
		_ddmTemplate.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
		_ddmTemplate.setNameMap(nameMap);
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		_ddmTemplate.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmTemplate.setNew(n);
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setSmallImage(boolean smallImage) {
		_ddmTemplate.setSmallImage(smallImage);
	}

	@Override
	public void setSmallImageId(long smallImageId) {
		_ddmTemplate.setSmallImageId(smallImageId);
	}

	@Override
	public void setSmallImageType(String smallImageType) {
		_smallImageType = smallImageType;
	}

	@Override
	public void setSmallImageURL(String smallImageURL) {
		_ddmTemplate.setSmallImageURL(smallImageURL);
	}

	@Override
	public void setStructureId(String structureId) {
		if (Validator.isNull(structureId)) {
			_ddmTemplate.setClassPK(0);

			return;
		}

		try {
			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.getStructure(
					getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					structureId, true);

			_ddmTemplate.setClassPK(ddmStructure.getPrimaryKey());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void setTemplateId(String templateId) {
		_ddmTemplate.setTemplateKey(templateId);
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
	public void setXsl(String xsl) {
		_ddmTemplate.setScript(xsl);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(getUuid());
		sb.append(", id=");
		sb.append(getId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", templateId=");
		sb.append(getTemplateId());
		sb.append(", structureId=");
		sb.append(getStructureId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", xsl=");
		sb.append(getXsl());
		sb.append(", langType=");
		sb.append(getLangType());
		sb.append(", cacheable=");
		sb.append(getCacheable());
		sb.append(", smallImage=");
		sb.append(getSmallImage());
		sb.append(", smallImageId=");
		sb.append(getSmallImageId());
		sb.append(", smallImageURL=");
		sb.append(getSmallImageURL());
		sb.append("}");

		return sb.toString();
	}

	protected Image fetchSmallImage() throws PortalException, SystemException {
		if (isSmallImage()) {
			return ImageLocalServiceUtil.getImage(getSmallImageId());
		}

		return null;
	}

	protected File getSmallImageFile(byte[] bytes) {
		try {
			return FileUtil.createTempFile(bytes);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return null;
	}

	private JournalTemplateAdapter() {
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalTemplateAdapter.class);

	private DDMTemplate _ddmTemplate;
	private File _smallImageFile;
	private String _smallImageType;

}