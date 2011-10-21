/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing DDMTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplate
 * @generated
 */
public class DDMTemplateCacheModel implements CacheModel<DDMTemplate>,
	Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(31);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", mode=");
		sb.append(mode);
		sb.append(", language=");
		sb.append(language);
		sb.append(", script=");
		sb.append(script);
		sb.append("}");

		return sb.toString();
	}

	public DDMTemplate toEntityModel() {
		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl();

		if (uuid == null) {
			ddmTemplateImpl.setUuid(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setUuid(uuid);
		}

		ddmTemplateImpl.setTemplateId(templateId);
		ddmTemplateImpl.setGroupId(groupId);
		ddmTemplateImpl.setCompanyId(companyId);
		ddmTemplateImpl.setUserId(userId);

		if (userName == null) {
			ddmTemplateImpl.setUserName(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmTemplateImpl.setCreateDate(null);
		}
		else {
			ddmTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmTemplateImpl.setModifiedDate(null);
		}
		else {
			ddmTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmTemplateImpl.setStructureId(structureId);

		if (name == null) {
			ddmTemplateImpl.setName(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setName(name);
		}

		if (description == null) {
			ddmTemplateImpl.setDescription(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setDescription(description);
		}

		if (type == null) {
			ddmTemplateImpl.setType(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setType(type);
		}

		if (mode == null) {
			ddmTemplateImpl.setMode(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setMode(mode);
		}

		if (language == null) {
			ddmTemplateImpl.setLanguage(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setLanguage(language);
		}

		if (script == null) {
			ddmTemplateImpl.setScript(StringPool.BLANK);
		}
		else {
			ddmTemplateImpl.setScript(script);
		}

		ddmTemplateImpl.resetOriginalValues();

		return ddmTemplateImpl;
	}

	public String uuid;
	public long templateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long structureId;
	public String name;
	public String description;
	public String type;
	public String mode;
	public String language;
	public String script;
}