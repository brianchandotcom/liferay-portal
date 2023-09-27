/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTCollectionTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTCollectionTemplate
 * @generated
 */
public class CTCollectionTemplateWrapper
	extends BaseModelWrapper<CTCollectionTemplate>
	implements CTCollectionTemplate, ModelWrapper<CTCollectionTemplate> {

	public CTCollectionTemplateWrapper(
		CTCollectionTemplate ctCollectionTemplate) {

		super(ctCollectionTemplate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionTemplateId", getCtCollectionTemplateId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionTemplateId = (Long)attributes.get(
			"ctCollectionTemplateId");

		if (ctCollectionTemplateId != null) {
			setCtCollectionTemplateId(ctCollectionTemplateId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public CTCollectionTemplate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct collection template.
	 *
	 * @return the company ID of this ct collection template
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ct collection template.
	 *
	 * @return the create date of this ct collection template
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection template ID of this ct collection template.
	 *
	 * @return the ct collection template ID of this ct collection template
	 */
	@Override
	public long getCtCollectionTemplateId() {
		return model.getCtCollectionTemplateId();
	}

	/**
	 * Returns the description of this ct collection template.
	 *
	 * @return the description of this ct collection template
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONObject getJSONObject() {
		return model.getJSONObject();
	}

	/**
	 * Returns the modified date of this ct collection template.
	 *
	 * @return the modified date of this ct collection template
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ct collection template.
	 *
	 * @return the mvcc version of this ct collection template
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ct collection template.
	 *
	 * @return the name of this ct collection template
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getParsedPublicationDescription() {
		return model.getParsedPublicationDescription();
	}

	@Override
	public String getParsedPublicationName() {
		return model.getParsedPublicationName();
	}

	/**
	 * Returns the primary key of this ct collection template.
	 *
	 * @return the primary key of this ct collection template
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getPublicationDescription() {
		return model.getPublicationDescription();
	}

	@Override
	public String getPublicationName() {
		return model.getPublicationName();
	}

	/**
	 * Returns the user ID of this ct collection template.
	 *
	 * @return the user ID of this ct collection template
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ct collection template.
	 *
	 * @return the user uuid of this ct collection template
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct collection template.
	 *
	 * @param companyId the company ID of this ct collection template
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ct collection template.
	 *
	 * @param createDate the create date of this ct collection template
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection template ID of this ct collection template.
	 *
	 * @param ctCollectionTemplateId the ct collection template ID of this ct collection template
	 */
	@Override
	public void setCtCollectionTemplateId(long ctCollectionTemplateId) {
		model.setCtCollectionTemplateId(ctCollectionTemplateId);
	}

	/**
	 * Sets the description of this ct collection template.
	 *
	 * @param description the description of this ct collection template
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the modified date of this ct collection template.
	 *
	 * @param modifiedDate the modified date of this ct collection template
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ct collection template.
	 *
	 * @param mvccVersion the mvcc version of this ct collection template
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ct collection template.
	 *
	 * @param name the name of this ct collection template
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this ct collection template.
	 *
	 * @param primaryKey the primary key of this ct collection template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this ct collection template.
	 *
	 * @param userId the user ID of this ct collection template
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this ct collection template.
	 *
	 * @param userUuid the user uuid of this ct collection template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTCollectionTemplateWrapper wrap(
		CTCollectionTemplate ctCollectionTemplate) {

		return new CTCollectionTemplateWrapper(ctCollectionTemplate);
	}

}