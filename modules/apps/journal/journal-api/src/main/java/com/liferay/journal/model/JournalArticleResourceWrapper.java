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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

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
public class JournalArticleResourceWrapper extends BaseModelWrapper<JournalArticleResource>
	implements JournalArticleResource, ModelWrapper<JournalArticleResource> {
	public JournalArticleResourceWrapper(
		JournalArticleResource journalArticleResource) {
		super(journalArticleResource);
	}

	/**
	* Returns the article ID of this journal article resource.
	*
	* @return the article ID of this journal article resource
	*/
	@Override
	public String getArticleId() {
		return model.getArticleId();
	}

	/**
	* Returns the company ID of this journal article resource.
	*
	* @return the company ID of this journal article resource
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the group ID of this journal article resource.
	*
	* @return the group ID of this journal article resource
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public long getLatestArticlePK() {
		return model.getLatestArticlePK();
	}

	/**
	* Returns the primary key of this journal article resource.
	*
	* @return the primary key of this journal article resource
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the resource prim key of this journal article resource.
	*
	* @return the resource prim key of this journal article resource
	*/
	@Override
	public long getResourcePrimKey() {
		return model.getResourcePrimKey();
	}

	/**
	* Returns the uuid of this journal article resource.
	*
	* @return the uuid of this journal article resource
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the article ID of this journal article resource.
	*
	* @param articleId the article ID of this journal article resource
	*/
	@Override
	public void setArticleId(String articleId) {
		model.setArticleId(articleId);
	}

	/**
	* Sets the company ID of this journal article resource.
	*
	* @param companyId the company ID of this journal article resource
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the group ID of this journal article resource.
	*
	* @param groupId the group ID of this journal article resource
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the primary key of this journal article resource.
	*
	* @param primaryKey the primary key of this journal article resource
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the resource prim key of this journal article resource.
	*
	* @param resourcePrimKey the resource prim key of this journal article resource
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		model.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the uuid of this journal article resource.
	*
	* @param uuid the uuid of this journal article resource
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected JournalArticleResourceWrapper wrap(
		JournalArticleResource journalArticleResource) {
		return new JournalArticleResourceWrapper(journalArticleResource);
	}
}