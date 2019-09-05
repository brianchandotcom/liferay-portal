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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class JournalContentSearchWrapper
	extends BaseModelWrapper<JournalContentSearch>
	implements JournalContentSearch, ModelWrapper<JournalContentSearch> {

	public JournalContentSearchWrapper(
		JournalContentSearch journalContentSearch) {

		super(journalContentSearch);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("contentSearchId", getContentSearchId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("privateLayout", isPrivateLayout());
		attributes.put("layoutId", getLayoutId());
		attributes.put("portletId", getPortletId());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	 * Returns the article ID of this journal content search.
	 *
	 * @return the article ID of this journal content search
	 */
	@Override
	public String getArticleId() {
		return model.getArticleId();
	}

	/**
	 * Returns the company ID of this journal content search.
	 *
	 * @return the company ID of this journal content search
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the content search ID of this journal content search.
	 *
	 * @return the content search ID of this journal content search
	 */
	@Override
	public long getContentSearchId() {
		return model.getContentSearchId();
	}

	/**
	 * Returns the group ID of this journal content search.
	 *
	 * @return the group ID of this journal content search
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout ID of this journal content search.
	 *
	 * @return the layout ID of this journal content search
	 */
	@Override
	public long getLayoutId() {
		return model.getLayoutId();
	}

	/**
	 * Returns the mvcc version of this journal content search.
	 *
	 * @return the mvcc version of this journal content search
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the portlet ID of this journal content search.
	 *
	 * @return the portlet ID of this journal content search
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this journal content search.
	 *
	 * @return the primary key of this journal content search
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the private layout of this journal content search.
	 *
	 * @return the private layout of this journal content search
	 */
	@Override
	public boolean getPrivateLayout() {
		return model.getPrivateLayout();
	}

	/**
	 * Returns <code>true</code> if this journal content search is private layout.
	 *
	 * @return <code>true</code> if this journal content search is private layout; <code>false</code> otherwise
	 */
	@Override
	public boolean isPrivateLayout() {
		return model.isPrivateLayout();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the article ID of this journal content search.
	 *
	 * @param articleId the article ID of this journal content search
	 */
	@Override
	public void setArticleId(String articleId) {
		model.setArticleId(articleId);
	}

	/**
	 * Sets the company ID of this journal content search.
	 *
	 * @param companyId the company ID of this journal content search
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the content search ID of this journal content search.
	 *
	 * @param contentSearchId the content search ID of this journal content search
	 */
	@Override
	public void setContentSearchId(long contentSearchId) {
		model.setContentSearchId(contentSearchId);
	}

	/**
	 * Sets the group ID of this journal content search.
	 *
	 * @param groupId the group ID of this journal content search
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout ID of this journal content search.
	 *
	 * @param layoutId the layout ID of this journal content search
	 */
	@Override
	public void setLayoutId(long layoutId) {
		model.setLayoutId(layoutId);
	}

	/**
	 * Sets the mvcc version of this journal content search.
	 *
	 * @param mvccVersion the mvcc version of this journal content search
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the portlet ID of this journal content search.
	 *
	 * @param portletId the portlet ID of this journal content search
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this journal content search.
	 *
	 * @param primaryKey the primary key of this journal content search
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this journal content search is private layout.
	 *
	 * @param privateLayout the private layout of this journal content search
	 */
	@Override
	public void setPrivateLayout(boolean privateLayout) {
		model.setPrivateLayout(privateLayout);
	}

	@Override
	protected JournalContentSearchWrapper wrap(
		JournalContentSearch journalContentSearch) {

		return new JournalContentSearchWrapper(journalContentSearch);
	}

}