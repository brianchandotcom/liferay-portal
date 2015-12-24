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
@ProviderType
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
		attributes.put("companyId", getCompanyId());
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

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new JournalArticleResourceWrapper((JournalArticleResource)_journalArticleResource.clone());
	}

	@Override
	public int compareTo(
		com.liferay.journal.model.JournalArticleResource journalArticleResource) {
		return _journalArticleResource.compareTo(journalArticleResource);
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
	* Returns the company ID of this journal article resource.
	*
	* @return the company ID of this journal article resource
	*/
	@Override
	public long getCompanyId() {
		return _journalArticleResource.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalArticleResource.getExpandoBridge();
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
	* Returns the primary key of this journal article resource.
	*
	* @return the primary key of this journal article resource
	*/
	@Override
	public long getPrimaryKey() {
		return _journalArticleResource.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalArticleResource.getPrimaryKeyObj();
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
	* Returns the uuid of this journal article resource.
	*
	* @return the uuid of this journal article resource
	*/
	@Override
	public java.lang.String getUuid() {
		return _journalArticleResource.getUuid();
	}

	@Override
	public int hashCode() {
		return _journalArticleResource.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _journalArticleResource.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _journalArticleResource.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _journalArticleResource.isNew();
	}

	@Override
	public void persist() {
		_journalArticleResource.persist();
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
	public void setCachedModel(boolean cachedModel) {
		_journalArticleResource.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this journal article resource.
	*
	* @param companyId the company ID of this journal article resource
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalArticleResource.setCompanyId(companyId);
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

	/**
	* Sets the group ID of this journal article resource.
	*
	* @param groupId the group ID of this journal article resource
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalArticleResource.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_journalArticleResource.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalArticleResource.setPrimaryKeyObj(primaryKeyObj);
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
	* Sets the uuid of this journal article resource.
	*
	* @param uuid the uuid of this journal article resource
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_journalArticleResource.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.journal.model.JournalArticleResource> toCacheModel() {
		return _journalArticleResource.toCacheModel();
	}

	@Override
	public com.liferay.journal.model.JournalArticleResource toEscapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalArticleResource.toString();
	}

	@Override
	public com.liferay.journal.model.JournalArticleResource toUnescapedModel() {
		return new JournalArticleResourceWrapper(_journalArticleResource.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalArticleResource.toXmlString();
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

	@Override
	public JournalArticleResource getWrappedModel() {
		return _journalArticleResource;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _journalArticleResource.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _journalArticleResource.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_journalArticleResource.resetOriginalValues();
	}

	private final JournalArticleResource _journalArticleResource;
}