/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDAvailabilityEstimate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimate
 * @generated
 */
public class CPDAvailabilityEstimateWrapper
	extends BaseModelWrapper<CPDAvailabilityEstimate>
	implements CPDAvailabilityEstimate, ModelWrapper<CPDAvailabilityEstimate> {

	public CPDAvailabilityEstimateWrapper(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		super(cpdAvailabilityEstimate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"CPDAvailabilityEstimateId", getCPDAvailabilityEstimateId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"commerceAvailabilityEstimateId",
			getCommerceAvailabilityEstimateId());
		attributes.put("CProductId", getCProductId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDAvailabilityEstimateId = (Long)attributes.get(
			"CPDAvailabilityEstimateId");

		if (CPDAvailabilityEstimateId != null) {
			setCPDAvailabilityEstimateId(CPDAvailabilityEstimateId);
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

		Long commerceAvailabilityEstimateId = (Long)attributes.get(
			"commerceAvailabilityEstimateId");

		if (commerceAvailabilityEstimateId != null) {
			setCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CPDAvailabilityEstimate cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceAvailabilityEstimate();
	}

	/**
	 * Returns the commerce availability estimate ID of this cpd availability estimate.
	 *
	 * @return the commerce availability estimate ID of this cpd availability estimate
	 */
	@Override
	public long getCommerceAvailabilityEstimateId() {
		return model.getCommerceAvailabilityEstimateId();
	}

	/**
	 * Returns the company ID of this cpd availability estimate.
	 *
	 * @return the company ID of this cpd availability estimate
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cpd availability estimate ID of this cpd availability estimate.
	 *
	 * @return the cpd availability estimate ID of this cpd availability estimate
	 */
	@Override
	public long getCPDAvailabilityEstimateId() {
		return model.getCPDAvailabilityEstimateId();
	}

	/**
	 * Returns the c product ID of this cpd availability estimate.
	 *
	 * @return the c product ID of this cpd availability estimate
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this cpd availability estimate.
	 *
	 * @return the create date of this cpd availability estimate
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the last publish date of this cpd availability estimate.
	 *
	 * @return the last publish date of this cpd availability estimate
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this cpd availability estimate.
	 *
	 * @return the modified date of this cpd availability estimate
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cpd availability estimate.
	 *
	 * @return the mvcc version of this cpd availability estimate
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cpd availability estimate.
	 *
	 * @return the primary key of this cpd availability estimate
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cpd availability estimate.
	 *
	 * @return the user ID of this cpd availability estimate
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cpd availability estimate.
	 *
	 * @return the user name of this cpd availability estimate
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cpd availability estimate.
	 *
	 * @return the user uuid of this cpd availability estimate
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this cpd availability estimate.
	 *
	 * @return the uuid of this cpd availability estimate
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
	 * Sets the commerce availability estimate ID of this cpd availability estimate.
	 *
	 * @param commerceAvailabilityEstimateId the commerce availability estimate ID of this cpd availability estimate
	 */
	@Override
	public void setCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {

		model.setCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
	}

	/**
	 * Sets the company ID of this cpd availability estimate.
	 *
	 * @param companyId the company ID of this cpd availability estimate
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cpd availability estimate ID of this cpd availability estimate.
	 *
	 * @param CPDAvailabilityEstimateId the cpd availability estimate ID of this cpd availability estimate
	 */
	@Override
	public void setCPDAvailabilityEstimateId(long CPDAvailabilityEstimateId) {
		model.setCPDAvailabilityEstimateId(CPDAvailabilityEstimateId);
	}

	/**
	 * Sets the c product ID of this cpd availability estimate.
	 *
	 * @param CProductId the c product ID of this cpd availability estimate
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this cpd availability estimate.
	 *
	 * @param createDate the create date of this cpd availability estimate
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the last publish date of this cpd availability estimate.
	 *
	 * @param lastPublishDate the last publish date of this cpd availability estimate
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this cpd availability estimate.
	 *
	 * @param modifiedDate the modified date of this cpd availability estimate
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cpd availability estimate.
	 *
	 * @param mvccVersion the mvcc version of this cpd availability estimate
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cpd availability estimate.
	 *
	 * @param primaryKey the primary key of this cpd availability estimate
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cpd availability estimate.
	 *
	 * @param userId the user ID of this cpd availability estimate
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cpd availability estimate.
	 *
	 * @param userName the user name of this cpd availability estimate
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cpd availability estimate.
	 *
	 * @param userUuid the user uuid of this cpd availability estimate
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this cpd availability estimate.
	 *
	 * @param uuid the uuid of this cpd availability estimate
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CPDAvailabilityEstimateWrapper wrap(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {

		return new CPDAvailabilityEstimateWrapper(cpdAvailabilityEstimate);
	}

}