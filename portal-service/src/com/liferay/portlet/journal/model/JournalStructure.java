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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
public interface JournalStructure {

	public int compareTo(JournalStructure journalStructure);

	public long getCompanyId();

	public Date getCreateDate();

	public String getDescription();

	public String getDescription(Locale locale);

	public String getDescription(Locale locale, boolean useDefault);

	public String getDescription(String languageId);

	public String getDescription(String languageId, boolean useDefault);

	public Map<Locale, String> getDescriptionMap();

	public ExpandoBridge getExpandoBridge();

	public long getGroupId();

	public long getId();

	public String getMergedXsd();

	public Map<String, Object> getModelAttributes();

	public Class<?> getModelClass();

	public String getModelClassName();

	public Date getModifiedDate();

	public String getName();

	public String getName(Locale locale);

	public String getName(Locale locale, boolean useDefault);

	public String getName(String languageId);

	public String getName(String languageId, boolean useDefault);

	public Map<Locale, String> getNameMap();

	public String getParentStructureId();

	public long getPrimaryKey();

	public Serializable getPrimaryKeyObj();

	public String getStructureId();

	public long getUserId();

	public String getUserName();

	public String getUserUuid() throws SystemException;

	public String getUuid();

	public String getXsd();

	public int hashCode();
	public boolean isNew();

	public void setCompanyId(long companyId);

	public void setCreateDate(Date createDate);

	public void setDescription(String description);

	public void setDescription(String description, Locale locale);

	public void setDescription(
		String description, Locale locale, Locale defaultLocale);

	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale);

	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	public void setExpandoBridgeAttributes(JournalStructure journalStructure);

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public void setGroupId(long groupId);

	public void setId(long id);

	public void setModelAttributes(Map<String, Object> attributes);

	public void setModifiedDate(Date modifiedDate);

	public void setName(String name);

	public void setName(String name, Locale locale);

	public void setName(String name, Locale locale, Locale defaultLocale);

	public void setNameMap(Map<Locale, String> nameMap);

	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale);

	public void setNew(boolean n);

	public void setParentStructureId(String parentStructureId);

	public void setPrimaryKey(long primaryKey);

	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	public void setStructureId(String structureId);

	public void setUserId(long userId);

	public void setUserName(String userName);

	public void setUserUuid(String userUuid);

	public void setUuid(String uuid);

	public void setXsd(String xsd);

}