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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalConverterUtil;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class JournalStructureAdapter implements JournalStructure {

	public JournalStructureAdapter(DDMStructure ddmStructure)
		throws SystemException {

		_ddmStructure = ddmStructure;

		_setXsd(ddmStructure.getXsd());
	}

	@Override
	public Object clone() {
		JournalStructureAdapter journalStructureAdapter =
			new JournalStructureAdapter();

		journalStructureAdapter.setUuid(getUuid());
		journalStructureAdapter.setId(getId());
		journalStructureAdapter.setGroupId(getGroupId());
		journalStructureAdapter.setCompanyId(getCompanyId());
		journalStructureAdapter.setUserId(getUserId());
		journalStructureAdapter.setUserName(getUserName());
		journalStructureAdapter.setCreateDate(getCreateDate());
		journalStructureAdapter.setModifiedDate(getModifiedDate());
		journalStructureAdapter.setStructureId(getStructureId());
		journalStructureAdapter.setParentStructureId(getParentStructureId());
		journalStructureAdapter.setName(getName());
		journalStructureAdapter.setDescription(getDescription());
		journalStructureAdapter.setXsd(getXsd());

		return journalStructureAdapter;
	}

	@Override
	public int compareTo(JournalStructure journalStructure) {
		int value = 0;

		value = getStructureId().compareTo(journalStructure.getStructureId());

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

		if (!(obj instanceof JournalStructure)) {
			return false;
		}

		JournalStructure journalStructure = (JournalStructure)obj;

		long primaryKey = journalStructure.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public long getCompanyId() {
		return _ddmStructure.getCompanyId();
	}

	@Override
	public Date getCreateDate() {
		return _ddmStructure.getCreateDate();
	}

	@Override
	public String getDescription() {
		return _ddmStructure.getDescription();
	}

	@Override
	public String getDescription(Locale locale) {
		return _ddmStructure.getDescription(locale);
	}

	@Override
	public String getDescription(Locale locale, boolean useDefault) {
		return _ddmStructure.getDescription(locale, useDefault);
	}

	@Override
	public String getDescription(String languageId) {
		return _ddmStructure.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _ddmStructure.getDescription(languageId, useDefault);
	}

	@Override
	public Map<Locale, String> getDescriptionMap() {
		return _ddmStructure.getDescriptionMap();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ddmStructure.getExpandoBridge();
	}

	@Override
	public long getGroupId() {
		return _ddmStructure.getGroupId();
	}

	@Override
	public long getId() {
		return _ddmStructure.getStructureId();
	}

	@Override
	public String getMergedXsd() {
		String parentStructureId = getParentStructureId();

		String xsd = getXsd();

		if (Validator.isNull(parentStructureId)) {
			return xsd;
		}

		try {
			JournalStructure parentStructure =
				JournalStructureLocalServiceUtil.getStructure(
					getGroupId(), parentStructureId, true);

			Document doc = SAXReaderUtil.read(getXsd());

			Element root = doc.getRootElement();

			Document parentDoc = SAXReaderUtil.read(
				parentStructure.getMergedXsd());

			Element parentRoot = parentDoc.getRootElement();

			addParentStructureId(parentRoot, parentStructureId);

			root.content().addAll(0, parentRoot.content());

			xsd = root.asXML();
		}
		catch (Exception e) {
		}

		return xsd;
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
		attributes.put("structureId", getStructureId());
		attributes.put("parentStructureId", getParentStructureId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("xsd", getXsd());

		return attributes;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalStructure.class;
	}

	@Override
	public String getModelClassName() {
		return JournalStructure.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		return _ddmStructure.getModifiedDate();
	}

	@Override
	public String getName() {
		return _ddmStructure.getName();
	}

	@Override
	public String getName(Locale locale) {
		return _ddmStructure.getName(locale);
	}

	@Override
	public String getName(Locale locale, boolean useDefault) {
		return _ddmStructure.getName(locale, useDefault);
	}

	@Override
	public String getName(String languageId) {
		return _ddmStructure.getName(languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return _ddmStructure.getName(languageId, useDefault);
	}

	@Override
	public Map<Locale, String> getNameMap() {
		return _ddmStructure.getNameMap();
	}

	@Override
	public String getParentStructureId() {
		if (_ddmStructure.getParentStructureId() == 0) {
			return null;
		}

		try {
			DDMStructure parentDDMStructure =
				DDMStructureLocalServiceUtil.getStructure(
					_ddmStructure.getParentStructureId());

			return parentDDMStructure.getStructureKey();
		}
		catch (Exception e) {
			_log.error(e);
		}

		return null;
	}

	@Override
	public long getPrimaryKey() {
		return _ddmStructure.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ddmStructure.getPrimaryKeyObj();
	}

	@Override
	public String getStructureId() {
		return _ddmStructure.getStructureKey();
	}

	@Override
	public long getUserId() {
		return _ddmStructure.getUserId();
	}

	@Override
	public String getUserName() {
		return _ddmStructure.getUserName();
	}

	@Override
	public String getUserUuid() throws SystemException {
		return _ddmStructure.getUserUuid();
	}

	@Override
	public String getUuid() {
		return _ddmStructure.getUuid();
	}

	@Override
	public String getXsd() {
		return _xsd;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isNew() {
		return _ddmStructure.isNew();
	}

	@Override
	public void setCompanyId(long companyId) {
		_ddmStructure.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructure.setCreateDate(createDate);
	}

	@Override
	public void setDescription(String description) {
		_ddmStructure.setDescription(description);
	}

	@Override
	public void setDescription(String description, Locale locale) {
		_ddmStructure.setDescription(description, locale);
	}

	@Override
	public void setDescription(
		String description, Locale locale, Locale defaultLocale) {

		_ddmStructure.setDescription(description, locale, defaultLocale);
	}

	@Override
	public void setDescriptionMap(Map<Locale, String> descriptionMap) {
		_ddmStructure.setDescriptionMap(descriptionMap);
	}

	@Override
	public void setDescriptionMap(
		Map<Locale, String> descriptionMap, Locale defaultLocale) {

		_ddmStructure.setDescriptionMap(descriptionMap, defaultLocale);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ddmStructure.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(JournalStructure journalStructure) {
		ExpandoBridge thisExpandoBridge = getExpandoBridge();

		ExpandoBridge baseModelExpandoBridge =
			journalStructure.getExpandoBridge();

		thisExpandoBridge.setAttributes(baseModelExpandoBridge.getAttributes());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ddmStructure.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setGroupId(long groupId) {
		_ddmStructure.setGroupId(groupId);
	}

	@Override
	public void setId(long id) {
		_ddmStructure.setStructureId(id);
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

		String structureId = (String)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}

		String parentStructureId = (String)attributes.get("parentStructureId");

		if (parentStructureId != null) {
			setParentStructureId(parentStructureId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String xsd = (String)attributes.get("xsd");

		if (xsd != null) {
			setXsd(xsd);
		}
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructure.setModifiedDate(modifiedDate);
	}

	@Override
	public void setName(String name) {
		_ddmStructure.setName(name);
	}

	@Override
	public void setName(String name, Locale locale) {
		_ddmStructure.setName(name, locale);
	}

	@Override
	public void setName(String name, Locale locale, Locale defaultLocale) {
		_ddmStructure.setName(name, locale, defaultLocale);
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap) {
		_ddmStructure.setNameMap(nameMap);
	}

	@Override
	public void setNameMap(Map<Locale, String> nameMap, Locale defaultLocale) {
		_ddmStructure.setNameMap(nameMap, defaultLocale);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructure.setNew(n);
	}

	@Override
	public void setParentStructureId(String parentStructureId) {
		if (parentStructureId == null) {
			_ddmStructure.setParentStructureId(0);
		}

		try {
			DDMStructure parentDDMStructure =
				DDMStructureLocalServiceUtil.getStructure(
					getGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class.getName()),
					parentStructureId);

			_ddmStructure.setParentStructureId(
				parentDDMStructure.getStructureId());
		}
		catch (Exception e) {
			_log.error(e);
		}
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructure.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ddmStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setStructureId(String structureId) {
		_ddmStructure.setStructureKey(structureId);
	}

	@Override
	public void setUserId(long userId) {
		_ddmStructure.setUserId(userId);
	}

	@Override
	public void setUserName(String userName) {
		_ddmStructure.setUserName(userName);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_ddmStructure.setUserUuid(userUuid);
	}

	@Override
	public void setUuid(String uuid) {
		_ddmStructure.setUuid(uuid);
	}

	@Override
	public void setXsd(String xsd) {
		_xsd = xsd;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

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
		sb.append(", structureId=");
		sb.append(getStructureId());
		sb.append(", parentStructureId=");
		sb.append(getParentStructureId());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", xsd=");
		sb.append(getXsd());
		sb.append("}");

		return sb.toString();
	}

	protected void addParentStructureId(
		Element parentEl, String parentStructureId) {

		List<Element> dynamicElements = parentEl.elements("dynamic-element");

		for (Element dynamicElement : dynamicElements) {
			dynamicElement.addAttribute(
				"parent-structure-id", parentStructureId);

			addParentStructureId(dynamicElement, parentStructureId);
		}
	}

	private JournalStructureAdapter() {
	}

	private void _setXsd(String ddmXsd) throws SystemException {
		if (Validator.isNull(ddmXsd)) {
			return;
		}

		try {
			_xsd = JournalConverterUtil.getJournalXSD(ddmXsd);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JournalStructureAdapter.class);

	private DDMStructure _ddmStructure;
	private String _xsd;

}