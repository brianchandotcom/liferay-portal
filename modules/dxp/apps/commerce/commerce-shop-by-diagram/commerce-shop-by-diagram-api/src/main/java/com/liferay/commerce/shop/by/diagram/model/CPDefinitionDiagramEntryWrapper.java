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
 * This class is a wrapper for {@link CPDefinitionDiagramEntry}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionDiagramEntry
 * @generated
 */
public class CPDefinitionDiagramEntryWrapper
	extends BaseModelWrapper<CPDefinitionDiagramEntry>
	implements CPDefinitionDiagramEntry,
			   ModelWrapper<CPDefinitionDiagramEntry> {

	public CPDefinitionDiagramEntryWrapper(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		super(cpDefinitionDiagramEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"CPDefinitionDiagramEntryId", getCPDefinitionDiagramEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put(
			"CPDefinitionDiagramSettingsId",
			getCPDefinitionDiagramSettingsId());
		attributes.put("CPInstanceUuid", getCPInstanceUuid());
		attributes.put("CProductId", getCProductId());
		attributes.put("number", getNumber());
		attributes.put("quantity", getQuantity());
		attributes.put("positionX", getPositionX());
		attributes.put("positionY", getPositionY());
		attributes.put("radius", getRadius());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long CPDefinitionDiagramEntryId = (Long)attributes.get(
			"CPDefinitionDiagramEntryId");

		if (CPDefinitionDiagramEntryId != null) {
			setCPDefinitionDiagramEntryId(CPDefinitionDiagramEntryId);
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

		Long CPDefinitionDiagramSettingsId = (Long)attributes.get(
			"CPDefinitionDiagramSettingsId");

		if (CPDefinitionDiagramSettingsId != null) {
			setCPDefinitionDiagramSettingsId(CPDefinitionDiagramSettingsId);
		}

		String CPInstanceUuid = (String)attributes.get("CPInstanceUuid");

		if (CPInstanceUuid != null) {
			setCPInstanceUuid(CPInstanceUuid);
		}

		Long CProductId = (Long)attributes.get("CProductId");

		if (CProductId != null) {
			setCProductId(CProductId);
		}

		Integer number = (Integer)attributes.get("number");

		if (number != null) {
			setNumber(number);
		}

		Integer quantity = (Integer)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
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
	 * Returns the company ID of this cp definition diagram entry.
	 *
	 * @return the company ID of this cp definition diagram entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp definition diagram entry ID of this cp definition diagram entry.
	 *
	 * @return the cp definition diagram entry ID of this cp definition diagram entry
	 */
	@Override
	public long getCPDefinitionDiagramEntryId() {
		return model.getCPDefinitionDiagramEntryId();
	}

	@Override
	public CPDefinitionDiagramSettings getCPDefinitionDiagramSettings()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCPDefinitionDiagramSettings();
	}

	/**
	 * Returns the cp definition diagram settings ID of this cp definition diagram entry.
	 *
	 * @return the cp definition diagram settings ID of this cp definition diagram entry
	 */
	@Override
	public long getCPDefinitionDiagramSettingsId() {
		return model.getCPDefinitionDiagramSettingsId();
	}

	/**
	 * Returns the cp instance uuid of this cp definition diagram entry.
	 *
	 * @return the cp instance uuid of this cp definition diagram entry
	 */
	@Override
	public String getCPInstanceUuid() {
		return model.getCPInstanceUuid();
	}

	/**
	 * Returns the c product ID of this cp definition diagram entry.
	 *
	 * @return the c product ID of this cp definition diagram entry
	 */
	@Override
	public long getCProductId() {
		return model.getCProductId();
	}

	/**
	 * Returns the create date of this cp definition diagram entry.
	 *
	 * @return the create date of this cp definition diagram entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this cp definition diagram entry.
	 *
	 * @return the modified date of this cp definition diagram entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the number of this cp definition diagram entry.
	 *
	 * @return the number of this cp definition diagram entry
	 */
	@Override
	public int getNumber() {
		return model.getNumber();
	}

	/**
	 * Returns the position x of this cp definition diagram entry.
	 *
	 * @return the position x of this cp definition diagram entry
	 */
	@Override
	public double getPositionX() {
		return model.getPositionX();
	}

	/**
	 * Returns the position y of this cp definition diagram entry.
	 *
	 * @return the position y of this cp definition diagram entry
	 */
	@Override
	public double getPositionY() {
		return model.getPositionY();
	}

	/**
	 * Returns the primary key of this cp definition diagram entry.
	 *
	 * @return the primary key of this cp definition diagram entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this cp definition diagram entry.
	 *
	 * @return the quantity of this cp definition diagram entry
	 */
	@Override
	public int getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the radius of this cp definition diagram entry.
	 *
	 * @return the radius of this cp definition diagram entry
	 */
	@Override
	public double getRadius() {
		return model.getRadius();
	}

	/**
	 * Returns the user ID of this cp definition diagram entry.
	 *
	 * @return the user ID of this cp definition diagram entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cp definition diagram entry.
	 *
	 * @return the user name of this cp definition diagram entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cp definition diagram entry.
	 *
	 * @return the user uuid of this cp definition diagram entry
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
	 * Sets the company ID of this cp definition diagram entry.
	 *
	 * @param companyId the company ID of this cp definition diagram entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition diagram entry ID of this cp definition diagram entry.
	 *
	 * @param CPDefinitionDiagramEntryId the cp definition diagram entry ID of this cp definition diagram entry
	 */
	@Override
	public void setCPDefinitionDiagramEntryId(long CPDefinitionDiagramEntryId) {
		model.setCPDefinitionDiagramEntryId(CPDefinitionDiagramEntryId);
	}

	/**
	 * Sets the cp definition diagram settings ID of this cp definition diagram entry.
	 *
	 * @param CPDefinitionDiagramSettingsId the cp definition diagram settings ID of this cp definition diagram entry
	 */
	@Override
	public void setCPDefinitionDiagramSettingsId(
		long CPDefinitionDiagramSettingsId) {

		model.setCPDefinitionDiagramSettingsId(CPDefinitionDiagramSettingsId);
	}

	/**
	 * Sets the cp instance uuid of this cp definition diagram entry.
	 *
	 * @param CPInstanceUuid the cp instance uuid of this cp definition diagram entry
	 */
	@Override
	public void setCPInstanceUuid(String CPInstanceUuid) {
		model.setCPInstanceUuid(CPInstanceUuid);
	}

	/**
	 * Sets the c product ID of this cp definition diagram entry.
	 *
	 * @param CProductId the c product ID of this cp definition diagram entry
	 */
	@Override
	public void setCProductId(long CProductId) {
		model.setCProductId(CProductId);
	}

	/**
	 * Sets the create date of this cp definition diagram entry.
	 *
	 * @param createDate the create date of this cp definition diagram entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this cp definition diagram entry.
	 *
	 * @param modifiedDate the modified date of this cp definition diagram entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the number of this cp definition diagram entry.
	 *
	 * @param number the number of this cp definition diagram entry
	 */
	@Override
	public void setNumber(int number) {
		model.setNumber(number);
	}

	/**
	 * Sets the position x of this cp definition diagram entry.
	 *
	 * @param positionX the position x of this cp definition diagram entry
	 */
	@Override
	public void setPositionX(double positionX) {
		model.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this cp definition diagram entry.
	 *
	 * @param positionY the position y of this cp definition diagram entry
	 */
	@Override
	public void setPositionY(double positionY) {
		model.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this cp definition diagram entry.
	 *
	 * @param primaryKey the primary key of this cp definition diagram entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this cp definition diagram entry.
	 *
	 * @param quantity the quantity of this cp definition diagram entry
	 */
	@Override
	public void setQuantity(int quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the radius of this cp definition diagram entry.
	 *
	 * @param radius the radius of this cp definition diagram entry
	 */
	@Override
	public void setRadius(double radius) {
		model.setRadius(radius);
	}

	/**
	 * Sets the user ID of this cp definition diagram entry.
	 *
	 * @param userId the user ID of this cp definition diagram entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cp definition diagram entry.
	 *
	 * @param userName the user name of this cp definition diagram entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cp definition diagram entry.
	 *
	 * @param userUuid the user uuid of this cp definition diagram entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CPDefinitionDiagramEntryWrapper wrap(
		CPDefinitionDiagramEntry cpDefinitionDiagramEntry) {

		return new CPDefinitionDiagramEntryWrapper(cpDefinitionDiagramEntry);
	}

}