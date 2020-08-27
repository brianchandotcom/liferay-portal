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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	extends BaseModelWrapper<CommerceBOMEntry>
	implements CommerceBOMEntry, ModelWrapper<CommerceBOMEntry> {

	public CommerceBOMEntryWrapper(CommerceBOMEntry commerceBOMEntry) {
		super(commerceBOMEntry);
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

	/**
	 * Returns the commerce bom definition ID of this commerce bom entry.
	 *
	 * @return the commerce bom definition ID of this commerce bom entry
	 */
	@Override
	public long getCommerceBOMDefinitionId() {
		return model.getCommerceBOMDefinitionId();
	}

	/**
	 * Returns the commerce bom entry ID of this commerce bom entry.
	 *
	 * @return the commerce bom entry ID of this commerce bom entry
	 */
	@Override
	public long getCommerceBOMEntryId() {
		return model.getCommerceBOMEntryId();
	}

	/**
	 * Returns the company ID of this commerce bom entry.
	 *
	 * @return the company ID of this commerce bom entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp instance uuid of this commerce bom entry.
	 *
	 * @return the cp instance uuid of this commerce bom entry
	 */
	@Override
	public String getCPInstanceUuid() {
		return model.getCPInstanceUuid();
	}

	/**
	 * Returns the c product ID of this commerce bom entry.
	 *
	 * @return the c product ID of this commerce bom entry
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this commerce bom entry.
	 *
	 * @return the create date of this commerce bom entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this commerce bom entry.
	 *
	 * @return the modified date of this commerce bom entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the number of this commerce bom entry.
	 *
	 * @return the number of this commerce bom entry
	 */
	@Override
	public int getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the position x of this commerce bom entry.
	 *
	 * @return the position x of this commerce bom entry
	 */
	@Override
	public double getPositionX() {
		return model.getPositionX();
	}

	/**
	 * Returns the position y of this commerce bom entry.
	 *
	 * @return the position y of this commerce bom entry
	 */
	@Override
	public double getPositionY() {
		return model.getPositionY();
	}

	/**
	 * Returns the primary key of this commerce bom entry.
	 *
	 * @return the primary key of this commerce bom entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the radius of this commerce bom entry.
	 *
	 * @return the radius of this commerce bom entry
	 */
	@Override
	public double getRadius() {
		return model.getRadius();
	}

	/**
	 * Returns the user ID of this commerce bom entry.
	 *
	 * @return the user ID of this commerce bom entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce bom entry.
	 *
	 * @return the user name of this commerce bom entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce bom entry.
	 *
	 * @return the user uuid of this commerce bom entry
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
	 * Sets the commerce bom definition ID of this commerce bom entry.
	 *
	 * @param commerceBOMDefinitionId the commerce bom definition ID of this commerce bom entry
	 */
	@Override
	public void setCommerceBOMDefinitionId(long commerceBOMDefinitionId) {
		model.setCommerceBOMDefinitionId(commerceBOMDefinitionId);
	}

	/**
	 * Sets the commerce bom entry ID of this commerce bom entry.
	 *
	 * @param commerceBOMEntryId the commerce bom entry ID of this commerce bom entry
	 */
	@Override
	public void setCommerceBOMEntryId(long commerceBOMEntryId) {
		model.setCommerceBOMEntryId(commerceBOMEntryId);
	}

	/**
	 * Sets the company ID of this commerce bom entry.
	 *
	 * @param companyId the company ID of this commerce bom entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp instance uuid of this commerce bom entry.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this commerce bom entry
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		model.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this commerce bom entry.
	 *
	 * @param CProductId the c product ID of this commerce bom entry
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this commerce bom entry.
	 *
	 * @param createDate the create date of this commerce bom entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this commerce bom entry.
	 *
	 * @param modifiedDate the modified date of this commerce bom entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the number of this commerce bom entry.
	 *
	 * @param number the number of this commerce bom entry
	 */
	@Override
	public void setNumber(int number) {
		model.setNumber(number);
	}

	/**
	 * Sets the position x of this commerce bom entry.
	 *
	 * @param positionX the position x of this commerce bom entry
	 */
	@Override
	public void setPositionX(double positionX) {
		model.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this commerce bom entry.
	 *
	 * @param positionY the position y of this commerce bom entry
	 */
	@Override
	public void setPositionY(double positionY) {
		model.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this commerce bom entry.
	 *
	 * @param primaryKey the primary key of this commerce bom entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the radius of this commerce bom entry.
	 *
	 * @param radius the radius of this commerce bom entry
	 */
	@Override
	public void setRadius(double radius) {
		model.setRadius(radius);
	}

	/**
	 * Sets the user ID of this commerce bom entry.
	 *
	 * @param userId the user ID of this commerce bom entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce bom entry.
	 *
	 * @param userName the user name of this commerce bom entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce bom entry.
	 *
	 * @param userUuid the user uuid of this commerce bom entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CommerceBOMEntryWrapper wrap(CommerceBOMEntry commerceBOMEntry) {
		return new CommerceBOMEntryWrapper(commerceBOMEntry);
	}

}