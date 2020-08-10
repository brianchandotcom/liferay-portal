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

package com.liferay.commerce.bom.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CommerceBOMEntry}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommerceBOMEntry
 * @generated
 */
public class CommerceBOMEntryWrapper
	implements CommerceBOMEntry, ModelWrapper<CommerceBOMEntry> {

	public CommerceBOMEntryWrapper(CommerceBOMEntry commerceBOMEntry) {
		_commerceBOMEntry = commerceBOMEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CommerceBOMEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CommerceBOMEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("commerceBOMEntryId", getCommerceBOMEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("number", getNumber());
		attributes.put("CPInstanceUuid", getCPInstanceUuid());
		attributes.put("CProductId", getCProductId());
		attributes.put("commerceBOMDefinitionId", getCommerceBOMDefinitionId());
		attributes.put("positionX", getPositionX());
		attributes.put("positionY", getPositionY());
		attributes.put("radius", getRadius());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long commerceBOMEntryId = (Long)attributes.get("commerceBOMEntryId");

		if (commerceBOMEntryId != null) {
			setCommerceBOMEntryId(commerceBOMEntryId);
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

		Integer number = (Integer)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		String CPInstanceUuid = (String)attributes.get("CPInstanceUuid");

		if (CPInstanceUuid != null) {
			setCPInstanceUuid(CPInstanceUuid);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Long commerceBOMDefinitionId = (Long)attributes.get(
			"commerceBOMDefinitionId");

		if (commerceBOMDefinitionId != null) {
			setCommerceBOMDefinitionId(commerceBOMDefinitionId);
		}

		Double positionX = (Double)attributes.get("positionX");

		if (positionX != null) {
			setPositionX(positionX);
		}

		Double positionY = (Double)attributes.get("positionY");

		if (positionY != null) {
			setPositionY(positionY);
		}

		Double radius = (Double)attributes.get("radius");

		if (radius != null) {
			setRadius(radius);
		}
	}

	@Override
	public Object clone() {
		return new CommerceBOMEntryWrapper(
			(CommerceBOMEntry)_commerceBOMEntry.clone());
	}

	@Override
	public int compareTo(CommerceBOMEntry commerceBOMEntry) {
		return _commerceBOMEntry.compareTo(commerceBOMEntry);
	}

	/**
	 * Returns the commerce bom definition ID of this commerce bom entry.
	 *
	 * @return the commerce bom definition ID of this commerce bom entry
	 */
	@Override
	public long getCommerceBOMDefinitionId() {
		return _commerceBOMEntry.getCommerceBOMDefinitionId();
	}

	/**
	 * Returns the commerce bom entry ID of this commerce bom entry.
	 *
	 * @return the commerce bom entry ID of this commerce bom entry
	 */
	@Override
	public long getCommerceBOMEntryId() {
		return _commerceBOMEntry.getCommerceBOMEntryId();
	}

	/**
	 * Returns the company ID of this commerce bom entry.
	 *
	 * @return the company ID of this commerce bom entry
	 */
	@Override
	public long getCompanyId() {
		return _commerceBOMEntry.getCompanyId();
	}

	/**
	 * Returns the cp instance uuid of this commerce bom entry.
	 *
	 * @return the cp instance uuid of this commerce bom entry
	 */
	@Override
	public String getCPInstanceUuid() {
		return _commerceBOMEntry.getCPInstanceUuid();
	}

	/**
	 * Returns the c product ID of this commerce bom entry.
	 *
	 * @return the c product ID of this commerce bom entry
	 */
	@Override
	public long getCProductId() {
		return _commerceBOMEntry.getCProductId();
	}

	/**
	 * Returns the create date of this commerce bom entry.
	 *
	 * @return the create date of this commerce bom entry
	 */
	@Override
	public Date getCreateDate() {
		return _commerceBOMEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _commerceBOMEntry.getExpandoBridge();
	}

	/**
	 * Returns the modified date of this commerce bom entry.
	 *
	 * @return the modified date of this commerce bom entry
	 */
	@Override
	public Date getModifiedDate() {
		return _commerceBOMEntry.getModifiedDate();
	}

	/**
	 * Returns the number of this commerce bom entry.
	 *
	 * @return the number of this commerce bom entry
	 */
	@Override
	public int getNumber() {
		return _commerceBOMEntry.getNumber();
	}

	/**
	 * Returns the position x of this commerce bom entry.
	 *
	 * @return the position x of this commerce bom entry
	 */
	@Override
	public double getPositionX() {
		return _commerceBOMEntry.getPositionX();
	}

	/**
	 * Returns the position y of this commerce bom entry.
	 *
	 * @return the position y of this commerce bom entry
	 */
	@Override
	public double getPositionY() {
		return _commerceBOMEntry.getPositionY();
	}

	/**
	 * Returns the primary key of this commerce bom entry.
	 *
	 * @return the primary key of this commerce bom entry
	 */
	@Override
	public long getPrimaryKey() {
		return _commerceBOMEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _commerceBOMEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the radius of this commerce bom entry.
	 *
	 * @return the radius of this commerce bom entry
	 */
	@Override
	public double getRadius() {
		return _commerceBOMEntry.getRadius();
	}

	/**
	 * Returns the user ID of this commerce bom entry.
	 *
	 * @return the user ID of this commerce bom entry
	 */
	@Override
	public long getUserId() {
		return _commerceBOMEntry.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom entry.
	 *
	 * @return the user name of this commerce bom entry
	 */
	@Override
	public String getUserName() {
		return _commerceBOMEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom entry.
	 *
	 * @return the user uuid of this commerce bom entry
	 */
	@Override
	public String getUserUuid() {
		return _commerceBOMEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _commerceBOMEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _commerceBOMEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _commerceBOMEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _commerceBOMEntry.isNew();
	}

	@Override
	public void persist() {
		_commerceBOMEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_commerceBOMEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the commerce bom definition ID of this commerce bom entry.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID of this commerce bom entry
	 */
	@Override
	public void setCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		_commerceBOMEntry.setCommerceBOMDefinitionId(commerceBOMDefinitionId);
	}

	/**
	 * Sets the commerce bom entry ID of this commerce bom entry.
	 *
	 * @param commerceBOMEntryId the commerce bom entry ID of this commerce bom entry
	 */
	@Override
	public void setCommerceBOMEntryId(long commerceBOMEntryId) {
		_commerceBOMEntry.setCommerceBOMEntryId(commerceBOMEntryId);
	}

	/**
	 * Sets the company ID of this commerce bom entry.
	 *
	 * @param companyId the company ID of this commerce bom entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_commerceBOMEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance uuid of this commerce bom entry.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this commerce bom entry
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		_commerceBOMEntry.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this commerce bom entry.
	 *
	 * @param CProductId the c product ID of this commerce bom entry
	 */
	@Override
	public void setCProductId(long CProductId) {
		_commerceBOMEntry.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce bom entry.
	 *
	 * @param createDate the create date of this commerce bom entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		_commerceBOMEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_commerceBOMEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_commerceBOMEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_commerceBOMEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the modified date of this commerce bom entry.
	 *
	 * @param modifiedDate the modified date of this commerce bom entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_commerceBOMEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_commerceBOMEntry.setNew(n);
	}

	/**
	 * Sets the number of this commerce bom entry.
	 *
	 * @param number the number of this commerce bom entry
	 */
	@Override
	public void setNumber(int number) {
		_commerceBOMEntry.setNumber(number);
	}

	/**
	 * Sets the position x of this commerce bom entry.
	 *
	 * @param positionX the position x of this commerce bom entry
	 */
	@Override
	public void setPositionX(double positionX) {
		_commerceBOMEntry.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this commerce bom entry.
	 *
	 * @param positionY the position y of this commerce bom entry
	 */
	@Override
	public void setPositionY(double positionY) {
		_commerceBOMEntry.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this commerce bom entry.
	 *
	 * @param primaryKey the primary key of this commerce bom entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_commerceBOMEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_commerceBOMEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the radius of this commerce bom entry.
	 *
	 * @param radius the radius of this commerce bom entry
	 */
	@Override
	public void setRadius(double radius) {
		_commerceBOMEntry.setRadius(radius);
	}

	/**
	 * Sets the user ID of this commerce bom entry.
	 *
	 * @param userId the user ID of this commerce bom entry
	 */
	@Override
	public void setUserId(long userId) {
		_commerceBOMEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom entry.
	 *
	 * @param userName the user name of this commerce bom entry
	 */
	@Override
	public void setUserName(String userName) {
		_commerceBOMEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom entry.
	 *
	 * @param userUuid the user uuid of this commerce bom entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_commerceBOMEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CommerceBOMEntry>
		toCacheModel() {

		return _commerceBOMEntry.toCacheModel();
	}

	@Override
	public CommerceBOMEntry toEscapedModel() {
		return new CommerceBOMEntryWrapper(_commerceBOMEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _commerceBOMEntry.toString();
	}

	@Override
	public CommerceBOMEntry toUnescapedModel() {
		return new CommerceBOMEntryWrapper(
			_commerceBOMEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _commerceBOMEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceBOMEntryWrapper)) {
			return false;
		}

		CommerceBOMEntryWrapper commerceBOMEntryWrapper =
			(CommerceBOMEntryWrapper)object;

		if (Objects.equals(
				_commerceBOMEntry, commerceBOMEntryWrapper._commerceBOMEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public CommerceBOMEntry getWrappedModel() {
		return _commerceBOMEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _commerceBOMEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _commerceBOMEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_commerceBOMEntry.resetOriginalValues();
	}

	private final CommerceBOMEntry _commerceBOMEntry;

}