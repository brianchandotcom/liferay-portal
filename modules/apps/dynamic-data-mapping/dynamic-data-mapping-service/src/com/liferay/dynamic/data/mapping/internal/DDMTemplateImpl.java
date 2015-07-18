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

import com.liferay.portlet.dynamicdatamapping.DDMTemplate;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;

/**
 * @author Marcellus Tavares
 */
public class DDMTemplateImpl implements DDMTemplate {

	public DDMTemplateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate ddmTemplate) {

		_ddmTemplate = ddmTemplate;
	}

	@Override
	public Object clone() {
		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl(
			(com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)
				_ddmTemplate.clone());

		return ddmTemplateImpl;
	}

	@Override
	public boolean getCacheable() {
		return _ddmTemplate.getCacheable();
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
	public void setModifiedDate(Date modifiedDate) {
		_ddmTemplate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUuid(String uuid) {
		_ddmTemplate.setUuid(uuid);
	}

	private final com.liferay.portlet.dynamicdatamapping.model.DDMTemplate
		_ddmTemplate;

}