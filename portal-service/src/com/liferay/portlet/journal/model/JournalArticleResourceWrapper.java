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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleResource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResource
 * @generated
 */
public class JournalArticleResourceWrapper implements JournalArticleResource,
	ModelWrapper<JournalArticleResource> {
	public JournalArticleResourceWrapper(
		JournalArticleResource journalArticleResource) {
		_journalArticleResource = journalArticleResource;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalArticleResource.class;
	}

	@Override
	public String getModelClassName() {
		return JournalArticleResource.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	@Override
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
	@Override
	public long getPrimaryKey() {
		return _journalArticleResource.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal article resource.
	*
	* @param primaryKey the primary key of this journal article resource
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalArticleResource.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the uuid of this journal article resource.
	*
	* @return the uuid of this journal article resource
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalArticleResource.getUuid();
	}

	/**
	* Sets the uuid of this journal article resource.
	*
	* @param uuid the uuid of this journal article resource
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalArticleResource.setUuid(uuid);
	}

	/**
	* Returns the resource prim key of this journal article resource.
	*
	* @return the resource prim key of this journal article resource
	*/
	@Override
	public long getResourcePrimKey() {
		return _journalArticleResource.getResourcePrimKey();
	}

	/**
	* Sets the resource prim key of this journal article resource.
	*
	* @param resourcePrimKey the resource prim key of this journal article resource
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_journalArticleResource.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the group ID of this journal article resource.
	*
	* @return the group ID of this journal article resource
	*/
	@Override
	public long getGroupId() {
		return _journalArticleResource.getGroupId();
	}

	/**
	* Sets the group ID of this journal article resource.
	*
	* @param groupId the group ID of this journal article resource
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalArticleResource.setGroupId(groupId);
	}

	/**
	* Returns the article ID of this journal article resource.
	*
	* @return the article ID of this journal article resource
	*/
	@Override
	public java.lang.String getArticleId() {
		return _journalArticleResource.getArticleId();
	}

	/**
	* Sets the article ID of this journal article resource.
	*
	* @param articleId the article ID of this journal article resource
	*/
	@Override
	public void setArticleId(java.lang.String articleId) {
		_journalArticleResource.setArticleId(articleId);
	}

	@Override
	public boolean isNew() {
		return _journalArticleResource.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_journalArticleResource.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _journalArticleResource.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalArticleResource.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _journalArticleResource.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticleResource.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalArticleResource.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticleResource.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalArticleResource.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalArticleResource.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalArticleResource.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleResourceWrapper((JournalArticleResource)_journalArticleResource.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.journal.model.JournalArticleResource journalArticleResource) {
		return _journalArticleResource.compareTo(journalArticleResource);
	}

	@Override
	public int hashCode() {
		return _journalArticleResource.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalArticleResource> toCacheModel() {
		return _journalArticleResource.toCacheModel();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalArticleResource toEscapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.journal.model.JournalArticleResource toUnescapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalArticleResource.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalArticleResource.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalArticleResource.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof JournalArticleResourceWrapper)) {
			return false;
		}

		JournalArticleResourceWrapper journalArticleResourceWrapper = (JournalArticleResourceWrapper)obj;

		if (Validator.equals(_journalArticleResource,
					journalArticleResourceWrapper._journalArticleResource)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public JournalArticleResource getWrappedJournalArticleResource() {
		return _journalArticleResource;
	}

	@Override
	public JournalArticleResource getWrappedModel() {
		return _journalArticleResource;
	}

	@Override
	public void resetOriginalValues() {
		_journalArticleResource.resetOriginalValues();
	}

	private JournalArticleResource _journalArticleResource;
}