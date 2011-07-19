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

package com.liferay.portlet.journal.model;

/**
 * <p>
 * This class is a wrapper for {@link JournalStructure}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalStructure
 * @generated
 */
public class JournalStructureWrapper implements JournalStructure {
	public JournalStructureWrapper(JournalStructure journalStructure) {
		_journalStructure = journalStructure;
	}

	public Class<?> getModelClass() {
		return JournalStructure.class;
	}

	public String getModelClassName() {
		return JournalStructure.class.getName();
	}

	/**
	* Returns the primary key of this journal structure.
	*
	* @return the primary key of this journal structure
	*/
	public long getPrimaryKey() {
		return _journalStructure.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal structure.
	*
	* @param primaryKey the primary key of this journal structure
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalStructure.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal structure.
	*
	* @return the uuid of this journal structure
	*/
	public java.lang.String getUuid() {
		return _journalStructure.getUuid();
	}

	/**
	* Sets the uuid of this journal structure.
	*
	* @param uuid the uuid of this journal structure
	*/
	public void setUuid(java.lang.String uuid) {
		_journalStructure.setUuid(uuid);
	}

	/**
	* Returns the ID of this journal structure.
	*
	* @return the ID of this journal structure
	*/
	public long getId() {
		return _journalStructure.getId();
	}

	/**
	* Sets the ID of this journal structure.
	*
	* @param id the ID of this journal structure
	*/
	public void setId(long id) {
		_journalStructure.setId(id);
	}

	/**
	* Returns the group ID of this journal structure.
	*
	* @return the group ID of this journal structure
	*/
	public long getGroupId() {
		return _journalStructure.getGroupId();
	}

	/**
	* Sets the group ID of this journal structure.
	*
	* @param groupId the group ID of this journal structure
	*/
	public void setGroupId(long groupId) {
		_journalStructure.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal structure.
	*
	* @return the company ID of this journal structure
	*/
	public long getCompanyId() {
		return _journalStructure.getCompanyId();
	}

	/**
	* Sets the company ID of this journal structure.
	*
	* @param companyId the company ID of this journal structure
	*/
	public void setCompanyId(long companyId) {
		_journalStructure.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this journal structure.
	*
	* @return the user ID of this journal structure
	*/
	public long getUserId() {
		return _journalStructure.getUserId();
	}

	/**
	* Sets the user ID of this journal structure.
	*
	* @param userId the user ID of this journal structure
	*/
	public void setUserId(long userId) {
		_journalStructure.setUserId(userId);
	}

	/**
	* Returns the user uuid of this journal structure.
	*
	* @return the user uuid of this journal structure
	* @throws SystemException if a system exception occurred
	*/
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _journalStructure.getUserUuid();
	}

	/**
	* Sets the user uuid of this journal structure.
	*
	* @param userUuid the user uuid of this journal structure
	*/
	public void setUserUuid(java.lang.String userUuid) {
		_journalStructure.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this journal structure.
	*
	* @return the user name of this journal structure
	*/
	public java.lang.String getUserName() {
		return _journalStructure.getUserName();
	}

	/**
	* Sets the user name of this journal structure.
	*
	* @param userName the user name of this journal structure
	*/
	public void setUserName(java.lang.String userName) {
		_journalStructure.setUserName(userName);
	}

	/**
	* Returns the create date of this journal structure.
	*
	* @return the create date of this journal structure
	*/
	public java.util.Date getCreateDate() {
		return _journalStructure.getCreateDate();
	}

	/**
	* Sets the create date of this journal structure.
	*
	* @param createDate the create date of this journal structure
	*/
	public void setCreateDate(java.util.Date createDate) {
		_journalStructure.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this journal structure.
	*
	* @return the modified date of this journal structure
	*/
	public java.util.Date getModifiedDate() {
		return _journalStructure.getModifiedDate();
	}

	/**
	* Sets the modified date of this journal structure.
	*
	* @param modifiedDate the modified date of this journal structure
	*/
	public void setModifiedDate(java.util.Date modifiedDate) {
		_journalStructure.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the structure ID of this journal structure.
	*
	* @return the structure ID of this journal structure
	*/
	public java.lang.String getStructureId() {
		return _journalStructure.getStructureId();
	}

	/**
	* Sets the structure ID of this journal structure.
	*
	* @param structureId the structure ID of this journal structure
	*/
	public void setStructureId(java.lang.String structureId) {
		_journalStructure.setStructureId(structureId);
	}

	/**
	* Returns the parent structure ID of this journal structure.
	*
	* @return the parent structure ID of this journal structure
	*/
	public java.lang.String getParentStructureId() {
		return _journalStructure.getParentStructureId();
	}

	/**
	* Sets the parent structure ID of this journal structure.
	*
	* @param parentStructureId the parent structure ID of this journal structure
	*/
	public void setParentStructureId(java.lang.String parentStructureId) {
		_journalStructure.setParentStructureId(parentStructureId);
	}

	/**
	* Returns the name of this journal structure.
	*
	* @return the name of this journal structure
	*/
	public java.lang.String getName() {
		return _journalStructure.getName();
	}

	/**
	* Sets the name of this journal structure.
	*
	* @param name the name of this journal structure
	*/
	public void setName(java.lang.String name) {
		_journalStructure.setName(name);
	}

	/**
	* Returns the description of this journal structure.
	*
	* @return the description of this journal structure
	*/
	public java.lang.String getDescription() {
		return _journalStructure.getDescription();
	}

	/**
	* Sets the description of this journal structure.
	*
	* @param description the description of this journal structure
	*/
	public void setDescription(java.lang.String description) {
		_journalStructure.setDescription(description);
	}

	/**
	* Returns the xsd of this journal structure.
	*
	* @return the xsd of this journal structure
	*/
	public java.lang.String getXsd() {
		return _journalStructure.getXsd();
	}

	/**
	* Sets the xsd of this journal structure.
	*
	* @param xsd the xsd of this journal structure
	*/
	public void setXsd(java.lang.String xsd) {
		_journalStructure.setXsd(xsd);
	}

	public boolean isNew() {
		return _journalStructure.isNew();
	}

	public void setNew(boolean n) {
		_journalStructure.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalStructure.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalStructure.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalStructure.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_journalStructure.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalStructure.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalStructure.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalStructure.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalStructureWrapper((JournalStructure)_journalStructure.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalStructure journalStructure) {
		return _journalStructure.compareTo(journalStructure);
	}

	@Override
	public int hashCode() {
		return _journalStructure.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalStructure> toCacheModel() {
		return _journalStructure.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalStructure toEscapedModel() {
		return new JournalStructureWrapper(_journalStructure.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalStructure.toString();
	}

	public java.lang.String toXmlString() {
		return _journalStructure.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalStructure.persist();
	}

	public java.lang.String getMergedXsd() {
		return _journalStructure.getMergedXsd();
	}

	public JournalStructure getWrappedJournalStructure() {
		return _journalStructure;
	}

	public void resetOriginalValues() {
		_journalStructure.resetOriginalValues();
	}

	private JournalStructure _journalStructure;
}