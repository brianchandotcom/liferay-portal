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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinitionDiagramPin}.
 * </p>
 *
 * @author Andrea Sbarra
 * @see CPDefinitionDiagramPin
 * @generated
 */
public class CPDefinitionDiagramPinWrapper
	extends BaseModelWrapper<CPDefinitionDiagramPin>
	implements CPDefinitionDiagramPin, ModelWrapper<CPDefinitionDiagramPin> {

	public CPDefinitionDiagramPinWrapper(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		super(cpDefinitionDiagramPin);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"CPDefinitionDiagramPinId", getCPDefinitionDiagramPinId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("number", getNumber());
		attributes.put("positionX", getPositionX());
		attributes.put("positionY", getPositionY());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPDefinitionDiagramPinId = (Long)attributes.get(
			"CPDefinitionDiagramPinId");

		if (CPDefinitionDiagramPinId != null) {
			setCPDefinitionDiagramPinId(CPDefinitionDiagramPinId);
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

		Integer number = (Integer)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		Double positionX = (Double)attributes.get("positionX");

		if (positionX != null) {
			setPositionX(positionX);
		}

		Double positionY = (Double)attributes.get("positionY");

		if (positionY != null) {
			setPositionY(positionY);
		}
	}

	/**
	 * Returns the company ID of this cp definition diagram pin.
	 *
	 * @return the company ID of this cp definition diagram pin
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public com.liferay.commerce.product.model.CPDefinition getCPDefinition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinition();
	}

	/**
	 * Returns the cp definition diagram pin ID of this cp definition diagram pin.
	 *
	 * @return the cp definition diagram pin ID of this cp definition diagram pin
	 */
	@Override
	public long getCPDefinitionDiagramPinId() {
		return model.getCPDefinitionDiagramPinId();
	}

	/**
	 * Returns the cp definition ID of this cp definition diagram pin.
	 *
	 * @return the cp definition ID of this cp definition diagram pin
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cp definition diagram pin.
	 *
	 * @return the create date of this cp definition diagram pin
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this cp definition diagram pin.
	 *
	 * @return the modified date of this cp definition diagram pin
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the number of this cp definition diagram pin.
	 *
	 * @return the number of this cp definition diagram pin
	 */
	@Override
	public int getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the position x of this cp definition diagram pin.
	 *
	 * @return the position x of this cp definition diagram pin
	 */
	@Override
	public double getPositionX() {
		return model.getPositionX();
	}

	/**
	 * Returns the position y of this cp definition diagram pin.
	 *
	 * @return the position y of this cp definition diagram pin
	 */
	@Override
	public double getPositionY() {
		return model.getPositionY();
	}

	/**
	 * Returns the primary key of this cp definition diagram pin.
	 *
	 * @return the primary key of this cp definition diagram pin
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cp definition diagram pin.
	 *
	 * @return the user ID of this cp definition diagram pin
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition diagram pin.
	 *
	 * @return the user name of this cp definition diagram pin
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition diagram pin.
	 *
	 * @return the user uuid of this cp definition diagram pin
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
	 * Sets the company ID of this cp definition diagram pin.
	 *
	 * @param companyId the company ID of this cp definition diagram pin
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition diagram pin ID of this cp definition diagram pin.
	 *
	 * @param CPDefinitionDiagramPinId the cp definition diagram pin ID of this cp definition diagram pin
	 */
	@Override
	public void setCPDefinitionDiagramPinId(long CPDefinitionDiagramPinId) {
		model.setCPDefinitionDiagramPinId(CPDefinitionDiagramPinId);
	}

	/**
	 * Sets the cp definition ID of this cp definition diagram pin.
	 *
	 * @param CPDefinitionId the cp definition ID of this cp definition diagram pin
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cp definition diagram pin.
	 *
	 * @param createDate the create date of this cp definition diagram pin
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this cp definition diagram pin.
	 *
	 * @param modifiedDate the modified date of this cp definition diagram pin
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the number of this cp definition diagram pin.
	 *
	 * @param number the number of this cp definition diagram pin
	 */
	@Override
	public void setNumber(int number) {
		model.setNumber(number);
	}

	/**
	 * Sets the position x of this cp definition diagram pin.
	 *
	 * @param positionX the position x of this cp definition diagram pin
	 */
	@Override
	public void setPositionX(double positionX) {
		model.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this cp definition diagram pin.
	 *
	 * @param positionY the position y of this cp definition diagram pin
	 */
	@Override
	public void setPositionY(double positionY) {
		model.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this cp definition diagram pin.
	 *
	 * @param primaryKey the primary key of this cp definition diagram pin
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cp definition diagram pin.
	 *
	 * @param userId the user ID of this cp definition diagram pin
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition diagram pin.
	 *
	 * @param userName the user name of this cp definition diagram pin
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition diagram pin.
	 *
	 * @param userUuid the user uuid of this cp definition diagram pin
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CPDefinitionDiagramPinWrapper wrap(
		CPDefinitionDiagramPin cpDefinitionDiagramPin) {

		return new CPDefinitionDiagramPinWrapper(cpDefinitionDiagramPin);
	}

}