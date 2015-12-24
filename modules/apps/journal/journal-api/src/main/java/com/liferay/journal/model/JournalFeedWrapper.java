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

package com.liferay.journal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalFeed}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalFeed
 * @generated
 */
@ProviderType
public class JournalFeedWrapper implements JournalFeed,
	ModelWrapper<JournalFeed> {
	public JournalFeedWrapper(JournalFeed journalFeed) {
		_journalFeed = journalFeed;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalFeed.class;
	}

	@Override
	public String getModelClassName() {
		return JournalFeed.class.getName();
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
		attributes.put("feedId", getFeedId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("DDMStructureKey", getDDMStructureKey());
		attributes.put("DDMTemplateKey", getDDMTemplateKey());
		attributes.put("DDMRendererTemplateKey", getDDMRendererTemplateKey());
		attributes.put("delta", getDelta());
		attributes.put("orderByCol", getOrderByCol());
		attributes.put("orderByType", getOrderByType());
		attributes.put("targetLayoutFriendlyUrl", getTargetLayoutFriendlyUrl());
		attributes.put("targetPortletId", getTargetPortletId());
		attributes.put("contentField", getContentField());
		attributes.put("feedFormat", getFeedFormat());
		attributes.put("feedVersion", getFeedVersion());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
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

		String feedId = (String)attributes.get("feedId");

		if (feedId != null) {
			setFeedId(feedId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String DDMStructureKey = (String)attributes.get("DDMStructureKey");

		if (DDMStructureKey != null) {
			setDDMStructureKey(DDMStructureKey);
		}

		String DDMTemplateKey = (String)attributes.get("DDMTemplateKey");

		if (DDMTemplateKey != null) {
			setDDMTemplateKey(DDMTemplateKey);
		}

		String DDMRendererTemplateKey = (String)attributes.get(
				"DDMRendererTemplateKey");

		if (DDMRendererTemplateKey != null) {
			setDDMRendererTemplateKey(DDMRendererTemplateKey);
		}

		Integer delta = (Integer)attributes.get("delta");

		if (delta != null) {
			setDelta(delta);
		}

		String orderByCol = (String)attributes.get("orderByCol");

		if (orderByCol != null) {
			setOrderByCol(orderByCol);
		}

		String orderByType = (String)attributes.get("orderByType");

		if (orderByType != null) {
			setOrderByType(orderByType);
		}

		String targetLayoutFriendlyUrl = (String)attributes.get(
				"targetLayoutFriendlyUrl");

		if (targetLayoutFriendlyUrl != null) {
			setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
		}

		String targetPortletId = (String)attributes.get("targetPortletId");

		if (targetPortletId != null) {
			setTargetPortletId(targetPortletId);
		}

		String contentField = (String)attributes.get("contentField");

		if (contentField != null) {
			setContentField(contentField);
		}

		String feedFormat = (String)attributes.get("feedFormat");

		if (feedFormat != null) {
			setFeedFormat(feedFormat);
		}

		Double feedVersion = (Double)attributes.get("feedVersion");

		if (feedVersion != null) {
			setFeedVersion(feedVersion);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new JournalFeedWrapper((JournalFeed)_journalFeed.clone());
	}

	@Override
	public int compareTo(com.liferay.journal.model.JournalFeed journalFeed) {
		return _journalFeed.compareTo(journalFeed);
	}

	/**
	* Returns the company ID of this journal feed.
	*
	* @return the company ID of this journal feed
	*/
	@Override
	public long getCompanyId() {
		return _journalFeed.getCompanyId();
	}

	/**
	* Returns the content field of this journal feed.
	*
	* @return the content field of this journal feed
	*/
	@Override
	public java.lang.String getContentField() {
		return _journalFeed.getContentField();
	}

	/**
	* Returns the create date of this journal feed.
	*
	* @return the create date of this journal feed
	*/
	@Override
	public Date getCreateDate() {
		return _journalFeed.getCreateDate();
	}

	/**
	* Returns the d d m renderer template key of this journal feed.
	*
	* @return the d d m renderer template key of this journal feed
	*/
	@Override
	public java.lang.String getDDMRendererTemplateKey() {
		return _journalFeed.getDDMRendererTemplateKey();
	}

	/**
	* Returns the d d m structure key of this journal feed.
	*
	* @return the d d m structure key of this journal feed
	*/
	@Override
	public java.lang.String getDDMStructureKey() {
		return _journalFeed.getDDMStructureKey();
	}

	/**
	* Returns the d d m template key of this journal feed.
	*
	* @return the d d m template key of this journal feed
	*/
	@Override
	public java.lang.String getDDMTemplateKey() {
		return _journalFeed.getDDMTemplateKey();
	}

	/**
	* Returns the delta of this journal feed.
	*
	* @return the delta of this journal feed
	*/
	@Override
	public int getDelta() {
		return _journalFeed.getDelta();
	}

	/**
	* Returns the description of this journal feed.
	*
	* @return the description of this journal feed
	*/
	@Override
	public java.lang.String getDescription() {
		return _journalFeed.getDescription();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalFeed.getExpandoBridge();
	}

	/**
	* Returns the feed format of this journal feed.
	*
	* @return the feed format of this journal feed
	*/
	@Override
	public java.lang.String getFeedFormat() {
		return _journalFeed.getFeedFormat();
	}

	/**
	* Returns the feed ID of this journal feed.
	*
	* @return the feed ID of this journal feed
	*/
	@Override
	public java.lang.String getFeedId() {
		return _journalFeed.getFeedId();
	}

	/**
	* Returns the feed version of this journal feed.
	*
	* @return the feed version of this journal feed
	*/
	@Override
	public double getFeedVersion() {
		return _journalFeed.getFeedVersion();
	}

	/**
	* Returns the group ID of this journal feed.
	*
	* @return the group ID of this journal feed
	*/
	@Override
	public long getGroupId() {
		return _journalFeed.getGroupId();
	}

	/**
	* Returns the ID of this journal feed.
	*
	* @return the ID of this journal feed
	*/
	@Override
	public long getId() {
		return _journalFeed.getId();
	}

	/**
	* Returns the last publish date of this journal feed.
	*
	* @return the last publish date of this journal feed
	*/
	@Override
	public Date getLastPublishDate() {
		return _journalFeed.getLastPublishDate();
	}

	/**
	* Returns the modified date of this journal feed.
	*
	* @return the modified date of this journal feed
	*/
	@Override
	public Date getModifiedDate() {
		return _journalFeed.getModifiedDate();
	}

	/**
	* Returns the name of this journal feed.
	*
	* @return the name of this journal feed
	*/
	@Override
	public java.lang.String getName() {
		return _journalFeed.getName();
	}

	/**
	* Returns the order by col of this journal feed.
	*
	* @return the order by col of this journal feed
	*/
	@Override
	public java.lang.String getOrderByCol() {
		return _journalFeed.getOrderByCol();
	}

	/**
	* Returns the order by type of this journal feed.
	*
	* @return the order by type of this journal feed
	*/
	@Override
	public java.lang.String getOrderByType() {
		return _journalFeed.getOrderByType();
	}

	/**
	* Returns the primary key of this journal feed.
	*
	* @return the primary key of this journal feed
	*/
	@Override
	public long getPrimaryKey() {
		return _journalFeed.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalFeed.getPrimaryKeyObj();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMRendererTemplateKey()}
	*/
	@Deprecated
	@Override
	public java.lang.String getRendererTemplateId() {
		return _journalFeed.getRendererTemplateId();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMStructureKey()}
	*/
	@Deprecated
	@Override
	public java.lang.String getStructureId() {
		return _journalFeed.getStructureId();
	}

	/**
	* Returns the target layout friendly url of this journal feed.
	*
	* @return the target layout friendly url of this journal feed
	*/
	@Override
	public java.lang.String getTargetLayoutFriendlyUrl() {
		return _journalFeed.getTargetLayoutFriendlyUrl();
	}

	/**
	* Returns the target portlet ID of this journal feed.
	*
	* @return the target portlet ID of this journal feed
	*/
	@Override
	public java.lang.String getTargetPortletId() {
		return _journalFeed.getTargetPortletId();
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #getDDMTemplateKey()}
	*/
	@Deprecated
	@Override
	public java.lang.String getTemplateId() {
		return _journalFeed.getTemplateId();
	}

	/**
	* Returns the user ID of this journal feed.
	*
	* @return the user ID of this journal feed
	*/
	@Override
	public long getUserId() {
		return _journalFeed.getUserId();
	}

	/**
	* Returns the user name of this journal feed.
	*
	* @return the user name of this journal feed
	*/
	@Override
	public java.lang.String getUserName() {
		return _journalFeed.getUserName();
	}

	/**
	* Returns the user uuid of this journal feed.
	*
	* @return the user uuid of this journal feed
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _journalFeed.getUserUuid();
	}

	/**
	* Returns the uuid of this journal feed.
	*
	* @return the uuid of this journal feed
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalFeed.getUuid();
	}

	@Override
	public int hashCode() {
		return _journalFeed.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _journalFeed.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _journalFeed.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _journalFeed.isNew();
	}

	@Override
	public void persist() {
		_journalFeed.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalFeed.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this journal feed.
	*
	* @param companyId the company ID of this journal feed
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalFeed.setCompanyId(companyId);
	}

	/**
	* Sets the content field of this journal feed.
	*
	* @param contentField the content field of this journal feed
	*/
	@Override
	public void setContentField(java.lang.String contentField) {
		_journalFeed.setContentField(contentField);
	}

	/**
	* Sets the create date of this journal feed.
	*
	* @param createDate the create date of this journal feed
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_journalFeed.setCreateDate(createDate);
	}

	/**
	* Sets the d d m renderer template key of this journal feed.
	*
	* @param DDMRendererTemplateKey the d d m renderer template key of this journal feed
	*/
	@Override
	public void setDDMRendererTemplateKey(
		java.lang.String DDMRendererTemplateKey) {
		_journalFeed.setDDMRendererTemplateKey(DDMRendererTemplateKey);
	}

	/**
	* Sets the d d m structure key of this journal feed.
	*
	* @param DDMStructureKey the d d m structure key of this journal feed
	*/
	@Override
	public void setDDMStructureKey(java.lang.String DDMStructureKey) {
		_journalFeed.setDDMStructureKey(DDMStructureKey);
	}

	/**
	* Sets the d d m template key of this journal feed.
	*
	* @param DDMTemplateKey the d d m template key of this journal feed
	*/
	@Override
	public void setDDMTemplateKey(java.lang.String DDMTemplateKey) {
		_journalFeed.setDDMTemplateKey(DDMTemplateKey);
	}

	/**
	* Sets the delta of this journal feed.
	*
	* @param delta the delta of this journal feed
	*/
	@Override
	public void setDelta(int delta) {
		_journalFeed.setDelta(delta);
	}

	/**
	* Sets the description of this journal feed.
	*
	* @param description the description of this journal feed
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_journalFeed.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalFeed.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalFeed.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalFeed.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the feed format of this journal feed.
	*
	* @param feedFormat the feed format of this journal feed
	*/
	@Override
	public void setFeedFormat(java.lang.String feedFormat) {
		_journalFeed.setFeedFormat(feedFormat);
	}

	/**
	* Sets the feed ID of this journal feed.
	*
	* @param feedId the feed ID of this journal feed
	*/
	@Override
	public void setFeedId(java.lang.String feedId) {
		_journalFeed.setFeedId(feedId);
	}

	/**
	* Sets the feed version of this journal feed.
	*
	* @param feedVersion the feed version of this journal feed
	*/
	@Override
	public void setFeedVersion(double feedVersion) {
		_journalFeed.setFeedVersion(feedVersion);
	}

	/**
	* Sets the group ID of this journal feed.
	*
	* @param groupId the group ID of this journal feed
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalFeed.setGroupId(groupId);
	}

	/**
	* Sets the ID of this journal feed.
	*
	* @param id the ID of this journal feed
	*/
	@Override
	public void setId(long id) {
		_journalFeed.setId(id);
	}

	/**
	* Sets the last publish date of this journal feed.
	*
	* @param lastPublishDate the last publish date of this journal feed
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_journalFeed.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this journal feed.
	*
	* @param modifiedDate the modified date of this journal feed
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_journalFeed.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this journal feed.
	*
	* @param name the name of this journal feed
	*/
	@Override
	public void setName(java.lang.String name) {
		_journalFeed.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_journalFeed.setNew(n);
	}

	/**
	* Sets the order by col of this journal feed.
	*
	* @param orderByCol the order by col of this journal feed
	*/
	@Override
	public void setOrderByCol(java.lang.String orderByCol) {
		_journalFeed.setOrderByCol(orderByCol);
	}

	/**
	* Sets the order by type of this journal feed.
	*
	* @param orderByType the order by type of this journal feed
	*/
	@Override
	public void setOrderByType(java.lang.String orderByType) {
		_journalFeed.setOrderByType(orderByType);
	}

	/**
	* Sets the primary key of this journal feed.
	*
	* @param primaryKey the primary key of this journal feed
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalFeed.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalFeed.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link
	#setDDMRendererTemplateKey(String)}
	*/
	@Deprecated
	@Override
	public void setRendererTemplateId(java.lang.String rendererTemplateKey) {
		_journalFeed.setRendererTemplateId(rendererTemplateKey);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMStructureKey(String)}
	*/
	@Deprecated
	@Override
	public void setStructureId(java.lang.String structureKey) {
		_journalFeed.setStructureId(structureKey);
	}

	/**
	* Sets the target layout friendly url of this journal feed.
	*
	* @param targetLayoutFriendlyUrl the target layout friendly url of this journal feed
	*/
	@Override
	public void setTargetLayoutFriendlyUrl(
		java.lang.String targetLayoutFriendlyUrl) {
		_journalFeed.setTargetLayoutFriendlyUrl(targetLayoutFriendlyUrl);
	}

	/**
	* Sets the target portlet ID of this journal feed.
	*
	* @param targetPortletId the target portlet ID of this journal feed
	*/
	@Override
	public void setTargetPortletId(java.lang.String targetPortletId) {
		_journalFeed.setTargetPortletId(targetPortletId);
	}

	/**
	* @deprecated As of 7.0.0, replaced by {@link #setDDMTemplateKey(String)}
	*/
	@Deprecated
	@Override
	public void setTemplateId(java.lang.String templateKey) {
		_journalFeed.setTemplateId(templateKey);
	}

	/**
	* Sets the user ID of this journal feed.
	*
	* @param userId the user ID of this journal feed
	*/
	@Override
	public void setUserId(long userId) {
		_journalFeed.setUserId(userId);
	}

	/**
	* Sets the user name of this journal feed.
	*
	* @param userName the user name of this journal feed
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_journalFeed.setUserName(userName);
	}

	/**
	* Sets the user uuid of this journal feed.
	*
	* @param userUuid the user uuid of this journal feed
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_journalFeed.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this journal feed.
	*
	* @param uuid the uuid of this journal feed
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalFeed.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.journal.model.JournalFeed> toCacheModel() {
		return _journalFeed.toCacheModel();
	}

	@Override
	public com.liferay.journal.model.JournalFeed toEscapedModel() {
		return new JournalFeedWrapper(_journalFeed.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalFeed.toString();
	}

	@Override
	public com.liferay.journal.model.JournalFeed toUnescapedModel() {
		return new JournalFeedWrapper(_journalFeed.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalFeed.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalFeedWrapper)) {
			return false;
		}

		JournalFeedWrapper journalFeedWrapper = (JournalFeedWrapper)obj;

		if (Validator.equals(_journalFeed, journalFeedWrapper._journalFeed)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _journalFeed.getStagedModelType();
	}

	@Override
	public JournalFeed getWrappedModel() {
		return _journalFeed;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _journalFeed.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _journalFeed.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_journalFeed.resetOriginalValues();
	}

	private final JournalFeed _journalFeed;
}