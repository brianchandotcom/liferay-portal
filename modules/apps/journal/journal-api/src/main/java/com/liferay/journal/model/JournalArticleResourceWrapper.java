/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link JournalArticleResource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see JournalArticleResource
 * @generated
 */
public class JournalArticleResourceWrapper
	extends BaseModelWrapper<JournalArticleResource>
	implements JournalArticleResource, ModelWrapper<JournalArticleResource> {

	public JournalArticleResourceWrapper(
		JournalArticleResource journalArticleResource) {

		super(journalArticleResource);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

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
	public JournalArticleResource cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this journal article resource.
	 *
	 * @return the ct collection ID of this journal article resource
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the mvcc version of this journal article resource.
	 *
	 * @return the mvcc version of this journal article resource
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this journal article resource.
	 *
	 * @param ctCollectionId the ct collection ID of this journal article resource
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the mvcc version of this journal article resource.
	 *
	 * @param mvccVersion the mvcc version of this journal article resource
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<JournalArticleResource, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<JournalArticleResource, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected JournalArticleResourceWrapper wrap(
		JournalArticleResource journalArticleResource) {

		return new JournalArticleResourceWrapper(journalArticleResource);
	}

}