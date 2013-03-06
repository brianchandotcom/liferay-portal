/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleResource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalArticleResource
 * @generated
 */
public class JournalArticleResourceWrapper implements JournalArticleResource,
	ModelWrapper<JournalArticleResource> {
	public JournalArticleResourceWrapper(
		JournalArticleResource journalArticleResource) {
		_journalArticleResource = journalArticleResource;
	}

	public Class<?> getModelClass() {
		return JournalArticleResource.class;
	}

	public String getModelClassName() {
		return JournalArticleResource.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}
	}

	/**
	* Returns the primary key of this journal article resource.
	*
	* @return the primary key of this journal article resource
	*/
	public long getPrimaryKey() {
		return _journalArticleResource.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal article resource.
	*
	* @param primaryKey the primary key of this journal article resource
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalArticleResource.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal article resource.
	*
	* @return the uuid of this journal article resource
	*/
	public java.lang.String getUuid() {
		return _journalArticleResource.getUuid();
	}

	/**
	* Sets the uuid of this journal article resource.
	*
	* @param uuid the uuid of this journal article resource
	*/
	public void setUuid(java.lang.String uuid) {
		_journalArticleResource.setUuid(uuid);
	}

	/**
	* Returns the resource prim key of this journal article resource.
	*
	* @return the resource prim key of this journal article resource
	*/
	public long getResourcePrimKey() {
		return _journalArticleResource.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this journal article resource.
	*
	* @param resourcePrimKey the resource prim key of this journal article resource
	*/
	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticleResource.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the group ID of this journal article resource.
	*
	* @return the group ID of this journal article resource
	*/
	public long getGroupId() {
		return _journalArticleResource.getGroupId();
	}

	/**
	* Sets the group ID of this journal article resource.
	*
	* @param groupId the group ID of this journal article resource
	*/
	public void setGroupId(long groupId) {
		_journalArticleResource.setGroupId(groupId);
	}

	/**
	* Returns the article ID of this journal article resource.
	*
	* @return the article ID of this journal article resource
	*/
	public java.lang.String getArticleId() {
		return _journalArticleResource.getArticleId();
	}

	/**
	* Sets the article ID of this journal article resource.
	*
	* @param articleId the article ID of this journal article resource
	*/
	public void setArticleId(java.lang.String articleId) {
		_journalArticleResource.setArticleId(articleId);
	}

	public boolean isNew() {
		return _journalArticleResource.isNew();
	}

	public void setNew(boolean n) {
		_journalArticleResource.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalArticleResource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalArticleResource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalArticleResource.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticleResource.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalArticleResource.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticleResource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalArticleResource.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalArticleResource.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticleResource.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleResourceWrapper((JournalArticleResource)_journalArticleResource.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource) {
		return _journalArticleResource.compareTo(journalArticleResource);
	}

	@Override
	public int hashCode() {
		return _journalArticleResource.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalArticleResource> toCacheModel() {
		return _journalArticleResource.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalArticleResource toEscapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toEscapedModel());
	}

	public com.liferay.portlet.journal.model.JournalArticleResource toUnescapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalArticleResource.toString();
	}

	public java.lang.String toXmlString() {
		return _journalArticleResource.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResource.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public JournalArticleResource getWrappedJournalArticleResource() {
		return _journalArticleResource;
	}

	public JournalArticleResource getWrappedModel() {
		return _journalArticleResource;
	}

	public void resetOriginalValues() {
		_journalArticleResource.resetOriginalValues();
	}

	private JournalArticleResource _journalArticleResource;
}