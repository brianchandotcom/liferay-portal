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
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplate template) {

		_template = template;
	}

	@Override
	public Object clone() {
		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl(
			(com.liferay.portlet.dynamicdatamapping.model.DDMTemplate)
				_template.clone());

		return ddmTemplateImpl;
	}

	@Override
	public boolean getCacheable() {
		return _template.getCacheable();
	}

	@Override
	public long getClassNameId() {
		return _template.getClassNameId();
	}

	@Override
	public long getClassPK() {
		return _template.getClassPK();
	}

	@Override
	public long getCompanyId() {
		return _template.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _template.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _template.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _template.getDescription(locale);
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _template.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _template.getGroupId();
	}

	@Override
	public String getLanguage() {
		return _template.getLanguage();
	}

	@Override
	public String getMode() {
		return _template.getMode();
	}

	@Override
	public Class<?> getModelClass() {
		return _template.getModelClass();
	}

	@Override
	public String getModelClassName() {
		return _template.getModelClassName();
	}

	@Override
	public Date getModifiedDate() {
		return _template.getModifiedDate();
	}

	@Override
	public String getName() {
		return _template.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _template.getName(locale);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _template.getPrimaryKeyObj();
	}

	@Override
	public long getResourceClassNameId() {
		return _template.getResourceClassNameId();
	}

	@Override
	public String getScript() {
		return _template.getScript();
	}

	@Override
	public boolean getSmallImage() {
		return _template.getSmallImage();
	}

	@Override
	public long getSmallImageId() {
		return _template.getSmallImageId();
	}

	@Override
	public String getSmallImageURL() {
		return _template.getSmallImageURL();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _template.getStagedModelType();
	}

	@Override
	public long getTemplateId() {
		return _template.getTemplateId();
	}

	@Override
	public String getTemplateKey() {
		return _template.getTemplateKey();
	}

	@Override
	public String getType() {
		return _template.getType();
	}

	@Override
	public long getUserId() {
		return _template.getUserId();
	}

	@Override
	public String getUserName() {
		return _template.getUserName();
	}

	@Override
	public String getUuid() {
		return _template.getUuid();
	}

	@Override
	public String getVersion() {
		return _template.getVersion();
	}

	@Override
	public long getVersionUserId() {
		return _template.getVersionUserId();
	}

	@Override
	public String getVersionUserName() {
		return _template.getVersionUserName();
	}

	@Override
	public boolean isCacheable() {
		return _template.isCacheable();
	}

	@Override
	public boolean isSmallImage() {
		return _template.isSmallImage();
	}

	@Override
	public void setCompanyId(long companyId) {
		_template.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_template.setCreateDate(createDate);
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_template.setModifiedDate(modifiedDate);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_template.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setUuid(String uuid) {
		_template.setUuid(uuid);
	}

	private final com.liferay.portlet.dynamicdatamapping.model.DDMTemplate
		_template;

}