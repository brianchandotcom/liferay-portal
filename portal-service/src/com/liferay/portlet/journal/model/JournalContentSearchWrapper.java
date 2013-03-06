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
 * This class is a wrapper for {@link JournalContentSearch}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalContentSearch
 * @generated
 */
public class JournalContentSearchWrapper implements JournalContentSearch,
	ModelWrapper<JournalContentSearch> {
	public JournalContentSearchWrapper(
		JournalContentSearch journalContentSearch) {
		_journalContentSearch = journalContentSearch;
	}

	public Class<?> getModelClass() {
		return JournalContentSearch.class;
	}

	public String getModelClassName() {
		return JournalContentSearch.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("contentSearchId", getContentSearchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("privateLayout", getPrivateLayout());
		attributes.put("layoutId", getLayoutId());
		attributes.put("portletId", getPortletId());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long contentSearchId = (Long)attributes.get("contentSearchId");

		if (contentSearchId != null) {
			setContentSearchId(contentSearchId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Boolean privateLayout = (Boolean)attributes.get("privateLayout");

		if (privateLayout != null) {
			setPrivateLayout(privateLayout);
		}

		Long layoutId = (Long)attributes.get("layoutId");

		if (layoutId != null) {
			setLayoutId(layoutId);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}
	}

	/**
	* Returns the primary key of this journal content search.
	*
	* @return the primary key of this journal content search
	*/
	public long getPrimaryKey() {
		return _journalContentSearch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal content search.
	*
	* @param primaryKey the primary key of this journal content search
	*/
	public void setPrimaryKey(long primaryKey) {
		_journalContentSearch.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the content search ID of this journal content search.
	*
	* @return the content search ID of this journal content search
	*/
	public long getContentSearchId() {
		return _journalContentSearch.getContentSearchId();
	}

	/**
	* Sets the content search ID of this journal content search.
	*
	* @param contentSearchId the content search ID of this journal content search
	*/
	public void setContentSearchId(long contentSearchId) {
		_journalContentSearch.setContentSearchId(contentSearchId);
	}

	/**
	* Returns the group ID of this journal content search.
	*
	* @return the group ID of this journal content search
	*/
	public long getGroupId() {
		return _journalContentSearch.getGroupId();
	}

	/**
	* Sets the group ID of this journal content search.
	*
	* @param groupId the group ID of this journal content search
	*/
	public void setGroupId(long groupId) {
		_journalContentSearch.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal content search.
	*
	* @return the company ID of this journal content search
	*/
	public long getCompanyId() {
		return _journalContentSearch.getCompanyId();
	}

	/**
	* Sets the company ID of this journal content search.
	*
	* @param companyId the company ID of this journal content search
	*/
	public void setCompanyId(long companyId) {
		_journalContentSearch.setCompanyId(companyId);
	}

	/**
	* Returns the private layout of this journal content search.
	*
	* @return the private layout of this journal content search
	*/
	public boolean getPrivateLayout() {
		return _journalContentSearch.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this journal content search is private layout.
	*
	* @return <code>true</code> if this journal content search is private layout; <code>false</code> otherwise
	*/
	public boolean isPrivateLayout() {
		return _journalContentSearch.isPrivateLayout();
	}

	/**
	* Sets whether this journal content search is private layout.
	*
	* @param privateLayout the private layout of this journal content search
	*/
	public void setPrivateLayout(boolean privateLayout) {
		_journalContentSearch.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the layout ID of this journal content search.
	*
	* @return the layout ID of this journal content search
	*/
	public long getLayoutId() {
		return _journalContentSearch.getLayoutId();
	}

	/**
	* Sets the layout ID of this journal content search.
	*
	* @param layoutId the layout ID of this journal content search
	*/
	public void setLayoutId(long layoutId) {
		_journalContentSearch.setLayoutId(layoutId);
	}

	/**
	* Returns the portlet ID of this journal content search.
	*
	* @return the portlet ID of this journal content search
	*/
	public java.lang.String getPortletId() {
		return _journalContentSearch.getPortletId();
	}

	/**
	* Sets the portlet ID of this journal content search.
	*
	* @param portletId the portlet ID of this journal content search
	*/
	public void setPortletId(java.lang.String portletId) {
		_journalContentSearch.setPortletId(portletId);
	}

	/**
	* Returns the article ID of this journal content search.
	*
	* @return the article ID of this journal content search
	*/
	public java.lang.String getArticleId() {
		return _journalContentSearch.getArticleId();
	}

	/**
	* Sets the article ID of this journal content search.
	*
	* @param articleId the article ID of this journal content search
	*/
	public void setArticleId(java.lang.String articleId) {
		_journalContentSearch.setArticleId(articleId);
	}

	public boolean isNew() {
		return _journalContentSearch.isNew();
	}

	public void setNew(boolean n) {
		_journalContentSearch.setNew(n);
	}

	public boolean isCachedModel() {
		return _journalContentSearch.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_journalContentSearch.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _journalContentSearch.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _journalContentSearch.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalContentSearch.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalContentSearch.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalContentSearch.setExpandoBridgeAttributes(baseModel);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalContentSearch.setExpandoBridgeAttributes(expandoBridge);
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalContentSearch.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalContentSearchWrapper((JournalContentSearch)_journalContentSearch.clone());
	}

	public int compareTo(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return _journalContentSearch.compareTo(journalContentSearch);
	}

	@Override
	public int hashCode() {
		return _journalContentSearch.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalContentSearch> toCacheModel() {
		return _journalContentSearch.toCacheModel();
	}

	public com.liferay.portlet.journal.model.JournalContentSearch toEscapedModel() {
		return new JournalContentSearchWrapper(_journalContentSearch.toEscapedModel());
	}

	public com.liferay.portlet.journal.model.JournalContentSearch toUnescapedModel() {
		return new JournalContentSearchWrapper(_journalContentSearch.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalContentSearch.toString();
	}

	public java.lang.String toXmlString() {
		return _journalContentSearch.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_journalContentSearch.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public JournalContentSearch getWrappedJournalContentSearch() {
		return _journalContentSearch;
	}

	public JournalContentSearch getWrappedModel() {
		return _journalContentSearch;
	}

	public void resetOriginalValues() {
		_journalContentSearch.resetOriginalValues();
	}

	private JournalContentSearch _journalContentSearch;
}