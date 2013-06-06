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

import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link JournalContentSearch}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalContentSearch
 * @generated
 */
public class JournalContentSearchWrapper implements JournalContentSearch,
	ModelWrapper<JournalContentSearch> {
	public JournalContentSearchWrapper(
		JournalContentSearch journalContentSearch) {
		_journalContentSearch = journalContentSearch;
	}

	@Override
	public Class<?> getModelClass() {
		return JournalContentSearch.class;
	}

	@Override
	public String getModelClassName() {
		return JournalContentSearch.class.getName();
	}

	@Override
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

	@Override
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
	@Override
	public long getPrimaryKey() {
		return _journalContentSearch.getPrimaryKey();
	}

	/**
	* Sets the primary key of this journal content search.
	*
	* @param primaryKey the primary key of this journal content search
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_journalContentSearch.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the content search ID of this journal content search.
	*
	* @return the content search ID of this journal content search
	*/
	@Override
	public long getContentSearchId() {
		return _journalContentSearch.getContentSearchId();
	}

	/**
	* Sets the content search ID of this journal content search.
	*
	* @param contentSearchId the content search ID of this journal content search
	*/
	@Override
	public void setContentSearchId(long contentSearchId) {
		_journalContentSearch.setContentSearchId(contentSearchId);
	}

	/**
	* Returns the group ID of this journal content search.
	*
	* @return the group ID of this journal content search
	*/
	@Override
	public long getGroupId() {
		return _journalContentSearch.getGroupId();
	}

	/**
	* Sets the group ID of this journal content search.
	*
	* @param groupId the group ID of this journal content search
	*/
	@Override
	public void setGroupId(long groupId) {
		_journalContentSearch.setGroupId(groupId);
	}

	/**
	* Returns the company ID of this journal content search.
	*
	* @return the company ID of this journal content search
	*/
	@Override
	public long getCompanyId() {
		return _journalContentSearch.getCompanyId();
	}

	/**
	* Sets the company ID of this journal content search.
	*
	* @param companyId the company ID of this journal content search
	*/
	@Override
	public void setCompanyId(long companyId) {
		_journalContentSearch.setCompanyId(companyId);
	}

	/**
	* Returns the private layout of this journal content search.
	*
	* @return the private layout of this journal content search
	*/
	@Override
	public boolean getPrivateLayout() {
		return _journalContentSearch.getPrivateLayout();
	}

	/**
	* Returns <code>true</code> if this journal content search is private layout.
	*
	* @return <code>true</code> if this journal content search is private layout; <code>false</code> otherwise
	*/
	@Override
	public boolean isPrivateLayout() {
		return _journalContentSearch.isPrivateLayout();
	}

	/**
	* Sets whether this journal content search is private layout.
	*
	* @param privateLayout the private layout of this journal content search
	*/
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		_journalContentSearch.setPrivateLayout(privateLayout);
	}

	/**
	* Returns the layout ID of this journal content search.
	*
	* @return the layout ID of this journal content search
	*/
	@Override
	public long getLayoutId() {
		return _journalContentSearch.getLayoutId();
	}

	/**
	* Sets the layout ID of this journal content search.
	*
	* @param layoutId the layout ID of this journal content search
	*/
	@Override
	public void setLayoutId(long layoutId) {
		_journalContentSearch.setLayoutId(layoutId);
	}

	/**
	* Returns the portlet ID of this journal content search.
	*
	* @return the portlet ID of this journal content search
	*/
	@Override
	public java.lang.String getPortletId() {
		return _journalContentSearch.getPortletId();
	}

	/**
	* Sets the portlet ID of this journal content search.
	*
	* @param portletId the portlet ID of this journal content search
	*/
	@Override
	public void setPortletId(java.lang.String portletId) {
		_journalContentSearch.setPortletId(portletId);
	}

	/**
	* Returns the article ID of this journal content search.
	*
	* @return the article ID of this journal content search
	*/
	@Override
	public java.lang.String getArticleId() {
		return _journalContentSearch.getArticleId();
	}

	/**
	* Sets the article ID of this journal content search.
	*
	* @param articleId the article ID of this journal content search
	*/
	@Override
	public void setArticleId(java.lang.String articleId) {
		_journalContentSearch.setArticleId(articleId);
	}

	@Override
	public boolean isNew() {
		return _journalContentSearch.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_journalContentSearch.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _journalContentSearch.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_journalContentSearch.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _journalContentSearch.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _journalContentSearch.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_journalContentSearch.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _journalContentSearch.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_journalContentSearch.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_journalContentSearch.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_journalContentSearch.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new JournalContentSearchWrapper((JournalContentSearch)_journalContentSearch.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.journal.model.JournalContentSearch journalContentSearch) {
		return _journalContentSearch.compareTo(journalContentSearch);
	}

	@Override
	public int hashCode() {
		return _journalContentSearch.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.journal.model.JournalContentSearch> toCacheModel() {
		return _journalContentSearch.toCacheModel();
	}

	@Override
	public com.liferay.portlet.journal.model.JournalContentSearch toEscapedModel() {
		return new JournalContentSearchWrapper(_journalContentSearch.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.journal.model.JournalContentSearch toUnescapedModel() {
		return new JournalContentSearchWrapper(_journalContentSearch.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _journalContentSearch.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _journalContentSearch.toXmlString();
	}

	@Override
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

	@Override
	public JournalContentSearch getWrappedModel() {
		return _journalContentSearch;
	}

	@Override
	public void resetOriginalValues() {
		_journalContentSearch.resetOriginalValues();
	}

	private JournalContentSearch _journalContentSearch;
}