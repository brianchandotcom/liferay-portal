/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CPDAvailabilityEstimate}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimate
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateWrapper implements CPDAvailabilityEstimate,
	ModelWrapper<CPDAvailabilityEstimate> {
	public CPDAvailabilityEstimateWrapper(
		CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		_cpdAvailabilityEstimate = cpdAvailabilityEstimate;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDAvailabilityEstimate.class;
	}

	@Override
	public String getModelClassName() {
		return CPDAvailabilityEstimate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDAvailabilityEstimateId",
			getCPDAvailabilityEstimateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("commerceAvailabilityEstimateId",
			getCommerceAvailabilityEstimateId());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDAvailabilityEstimateId = (Long)attributes.get(
				"CPDAvailabilityEstimateId");

		if (CPDAvailabilityEstimateId != null) {
			setCPDAvailabilityEstimateId(CPDAvailabilityEstimateId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}

		Long commerceAvailabilityEstimateId = (Long)attributes.get(
				"commerceAvailabilityEstimateId");

		if (commerceAvailabilityEstimateId != null) {
			setCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public Object clone() {
		return new CPDAvailabilityEstimateWrapper((CPDAvailabilityEstimate)_cpdAvailabilityEstimate.clone());
	}

	@Override
	public int compareTo(CPDAvailabilityEstimate cpdAvailabilityEstimate) {
		return _cpdAvailabilityEstimate.compareTo(cpdAvailabilityEstimate);
	}

	@Override
	public CommerceAvailabilityEstimate getCommerceAvailabilityEstimate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpdAvailabilityEstimate.getCommerceAvailabilityEstimate();
	}

	/**
	* Returns the commerce availability estimate ID of this cpd availability estimate.
	*
	* @return the commerce availability estimate ID of this cpd availability estimate
	*/
	@Override
	public long getCommerceAvailabilityEstimateId() {
		return _cpdAvailabilityEstimate.getCommerceAvailabilityEstimateId();
	}

	/**
	* Returns the company ID of this cpd availability estimate.
	*
	* @return the company ID of this cpd availability estimate
	*/
	@Override
	public long getCompanyId() {
		return _cpdAvailabilityEstimate.getCompanyId();
	}

	/**
	* Returns the cpd availability estimate ID of this cpd availability estimate.
	*
	* @return the cpd availability estimate ID of this cpd availability estimate
	*/
	@Override
	public long getCPDAvailabilityEstimateId() {
		return _cpdAvailabilityEstimate.getCPDAvailabilityEstimateId();
	}

	/**
	* Returns the cp definition ID of this cpd availability estimate.
	*
	* @return the cp definition ID of this cpd availability estimate
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpdAvailabilityEstimate.getCPDefinitionId();
	}

	/**
	* Returns the create date of this cpd availability estimate.
	*
	* @return the create date of this cpd availability estimate
	*/
	@Override
	public Date getCreateDate() {
		return _cpdAvailabilityEstimate.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpdAvailabilityEstimate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cpd availability estimate.
	*
	* @return the group ID of this cpd availability estimate
	*/
	@Override
	public long getGroupId() {
		return _cpdAvailabilityEstimate.getGroupId();
	}

	/**
	* Returns the last publish date of this cpd availability estimate.
	*
	* @return the last publish date of this cpd availability estimate
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpdAvailabilityEstimate.getLastPublishDate();
	}

	/**
	* Returns the modified date of this cpd availability estimate.
	*
	* @return the modified date of this cpd availability estimate
	*/
	@Override
	public Date getModifiedDate() {
		return _cpdAvailabilityEstimate.getModifiedDate();
	}

	/**
	* Returns the primary key of this cpd availability estimate.
	*
	* @return the primary key of this cpd availability estimate
	*/
	@Override
	public long getPrimaryKey() {
		return _cpdAvailabilityEstimate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpdAvailabilityEstimate.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cpd availability estimate.
	*
	* @return the user ID of this cpd availability estimate
	*/
	@Override
	public long getUserId() {
		return _cpdAvailabilityEstimate.getUserId();
	}

	/**
	* Returns the user name of this cpd availability estimate.
	*
	* @return the user name of this cpd availability estimate
	*/
	@Override
	public String getUserName() {
		return _cpdAvailabilityEstimate.getUserName();
	}

	/**
	* Returns the user uuid of this cpd availability estimate.
	*
	* @return the user uuid of this cpd availability estimate
	*/
	@Override
	public String getUserUuid() {
		return _cpdAvailabilityEstimate.getUserUuid();
	}

	/**
	* Returns the uuid of this cpd availability estimate.
	*
	* @return the uuid of this cpd availability estimate
	*/
	@Override
	public String getUuid() {
		return _cpdAvailabilityEstimate.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpdAvailabilityEstimate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpdAvailabilityEstimate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpdAvailabilityEstimate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpdAvailabilityEstimate.isNew();
	}

	@Override
	public void persist() {
		_cpdAvailabilityEstimate.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpdAvailabilityEstimate.setCachedModel(cachedModel);
	}

	/**
	* Sets the commerce availability estimate ID of this cpd availability estimate.
	*
	* @param commerceAvailabilityEstimateId the commerce availability estimate ID of this cpd availability estimate
	*/
	@Override
	public void setCommerceAvailabilityEstimateId(
		long commerceAvailabilityEstimateId) {
		_cpdAvailabilityEstimate.setCommerceAvailabilityEstimateId(commerceAvailabilityEstimateId);
	}

	/**
	* Sets the company ID of this cpd availability estimate.
	*
	* @param companyId the company ID of this cpd availability estimate
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpdAvailabilityEstimate.setCompanyId(companyId);
	}

	/**
	* Sets the cpd availability estimate ID of this cpd availability estimate.
	*
	* @param CPDAvailabilityEstimateId the cpd availability estimate ID of this cpd availability estimate
	*/
	@Override
	public void setCPDAvailabilityEstimateId(long CPDAvailabilityEstimateId) {
		_cpdAvailabilityEstimate.setCPDAvailabilityEstimateId(CPDAvailabilityEstimateId);
	}

	/**
	* Sets the cp definition ID of this cpd availability estimate.
	*
	* @param CPDefinitionId the cp definition ID of this cpd availability estimate
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpdAvailabilityEstimate.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the create date of this cpd availability estimate.
	*
	* @param createDate the create date of this cpd availability estimate
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpdAvailabilityEstimate.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpdAvailabilityEstimate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpdAvailabilityEstimate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpdAvailabilityEstimate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cpd availability estimate.
	*
	* @param groupId the group ID of this cpd availability estimate
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpdAvailabilityEstimate.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this cpd availability estimate.
	*
	* @param lastPublishDate the last publish date of this cpd availability estimate
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpdAvailabilityEstimate.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this cpd availability estimate.
	*
	* @param modifiedDate the modified date of this cpd availability estimate
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpdAvailabilityEstimate.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpdAvailabilityEstimate.setNew(n);
	}

	/**
	* Sets the primary key of this cpd availability estimate.
	*
	* @param primaryKey the primary key of this cpd availability estimate
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpdAvailabilityEstimate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpdAvailabilityEstimate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cpd availability estimate.
	*
	* @param userId the user ID of this cpd availability estimate
	*/
	@Override
	public void setUserId(long userId) {
		_cpdAvailabilityEstimate.setUserId(userId);
	}

	/**
	* Sets the user name of this cpd availability estimate.
	*
	* @param userName the user name of this cpd availability estimate
	*/
	@Override
	public void setUserName(String userName) {
		_cpdAvailabilityEstimate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cpd availability estimate.
	*
	* @param userUuid the user uuid of this cpd availability estimate
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpdAvailabilityEstimate.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cpd availability estimate.
	*
	* @param uuid the uuid of this cpd availability estimate
	*/
	@Override
	public void setUuid(String uuid) {
		_cpdAvailabilityEstimate.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDAvailabilityEstimate> toCacheModel() {
		return _cpdAvailabilityEstimate.toCacheModel();
	}

	@Override
	public CPDAvailabilityEstimate toEscapedModel() {
		return new CPDAvailabilityEstimateWrapper(_cpdAvailabilityEstimate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpdAvailabilityEstimate.toString();
	}

	@Override
	public CPDAvailabilityEstimate toUnescapedModel() {
		return new CPDAvailabilityEstimateWrapper(_cpdAvailabilityEstimate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpdAvailabilityEstimate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDAvailabilityEstimateWrapper)) {
			return false;
		}

		CPDAvailabilityEstimateWrapper cpdAvailabilityEstimateWrapper = (CPDAvailabilityEstimateWrapper)obj;

		if (Objects.equals(_cpdAvailabilityEstimate,
					cpdAvailabilityEstimateWrapper._cpdAvailabilityEstimate)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpdAvailabilityEstimate.getStagedModelType();
	}

	@Override
	public CPDAvailabilityEstimate getWrappedModel() {
		return _cpdAvailabilityEstimate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpdAvailabilityEstimate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpdAvailabilityEstimate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpdAvailabilityEstimate.resetOriginalValues();
	}

	private final CPDAvailabilityEstimate _cpdAvailabilityEstimate;
}