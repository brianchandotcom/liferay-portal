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

package com.liferay.commerce.shop.by.diagram.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CSDiagramPin}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CSDiagramPin
 * @generated
 */
public class CSDiagramPinWrapper
	extends BaseModelWrapper<CSDiagramPin>
	implements CSDiagramPin, ModelWrapper<CSDiagramPin> {

	public CSDiagramPinWrapper(CSDiagramPin csDiagramPin) {
		super(csDiagramPin);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("CSDiagramPinId", getCSDiagramPinId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("positionX", getPositionX());
		attributes.put("positionY", getPositionY());
		attributes.put("sequence", getSequence());

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

		Long CSDiagramPinId = (Long)attributes.get("CSDiagramPinId");

		if (CSDiagramPinId != null) {
			setCSDiagramPinId(CSDiagramPinId);
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

		Double positionX = (Double)attributes.get("positionX");

		if (positionX != null) {
			setPositionX(positionX);
		}

		Double positionY = (Double)attributes.get("positionY");

		if (positionY != null) {
			setPositionY(positionY);
		}

		String sequence = (String)attributes.get("sequence");

		if (sequence != null) {
			setSequence(sequence);
		}
	}

	@Override
	public CSDiagramPin cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cs diagram pin.
	 *
	 * @return the company ID of this cs diagram pin
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
	 * Returns the cp definition ID of this cs diagram pin.
	 *
	 * @return the cp definition ID of this cs diagram pin
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this cs diagram pin.
	 *
	 * @return the create date of this cs diagram pin
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the cs diagram pin ID of this cs diagram pin.
	 *
	 * @return the cs diagram pin ID of this cs diagram pin
	 */
	@Override
	public long getCSDiagramPinId() {
		return model.getCSDiagramPinId();
	}

	/**
	 * Returns the ct collection ID of this cs diagram pin.
	 *
	 * @return the ct collection ID of this cs diagram pin
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this cs diagram pin.
	 *
	 * @return the modified date of this cs diagram pin
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this cs diagram pin.
	 *
	 * @return the mvcc version of this cs diagram pin
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the position x of this cs diagram pin.
	 *
	 * @return the position x of this cs diagram pin
	 */
	@Override
	public double getPositionX() {
		return model.getPositionX();
	}

	/**
	 * Returns the position y of this cs diagram pin.
	 *
	 * @return the position y of this cs diagram pin
	 */
	@Override
	public double getPositionY() {
		return model.getPositionY();
	}

	/**
	 * Returns the primary key of this cs diagram pin.
	 *
	 * @return the primary key of this cs diagram pin
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the sequence of this cs diagram pin.
	 *
	 * @return the sequence of this cs diagram pin
	 */
	@Override
	public String getSequence() {
		return model.getSequence();
	}

	/**
	 * Returns the user ID of this cs diagram pin.
	 *
	 * @return the user ID of this cs diagram pin
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cs diagram pin.
	 *
	 * @return the user name of this cs diagram pin
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cs diagram pin.
	 *
	 * @return the user uuid of this cs diagram pin
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
	 * Sets the company ID of this cs diagram pin.
	 *
	 * @param companyId the company ID of this cs diagram pin
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this cs diagram pin.
	 *
	 * @param CPDefinitionId the cp definition ID of this cs diagram pin
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this cs diagram pin.
	 *
	 * @param createDate the create date of this cs diagram pin
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the cs diagram pin ID of this cs diagram pin.
	 *
	 * @param CSDiagramPinId the cs diagram pin ID of this cs diagram pin
	 */
	@Override
	public void setCSDiagramPinId(long CSDiagramPinId) {
		model.setCSDiagramPinId(CSDiagramPinId);
	}

	/**
	 * Sets the ct collection ID of this cs diagram pin.
	 *
	 * @param ctCollectionId the ct collection ID of this cs diagram pin
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this cs diagram pin.
	 *
	 * @param modifiedDate the modified date of this cs diagram pin
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this cs diagram pin.
	 *
	 * @param mvccVersion the mvcc version of this cs diagram pin
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the position x of this cs diagram pin.
	 *
	 * @param positionX the position x of this cs diagram pin
	 */
	@Override
	public void setPositionX(double positionX) {
		model.setPositionX(positionX);
	}

	/**
	 * Sets the position y of this cs diagram pin.
	 *
	 * @param positionY the position y of this cs diagram pin
	 */
	@Override
	public void setPositionY(double positionY) {
		model.setPositionY(positionY);
	}

	/**
	 * Sets the primary key of this cs diagram pin.
	 *
	 * @param primaryKey the primary key of this cs diagram pin
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the sequence of this cs diagram pin.
	 *
	 * @param sequence the sequence of this cs diagram pin
	 */
	@Override
	public void setSequence(String sequence) {
		model.setSequence(sequence);
	}

	/**
	 * Sets the user ID of this cs diagram pin.
	 *
	 * @param userId the user ID of this cs diagram pin
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cs diagram pin.
	 *
	 * @param userName the user name of this cs diagram pin
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cs diagram pin.
	 *
	 * @param userUuid the user uuid of this cs diagram pin
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public Map<String, Function<CSDiagramPin, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CSDiagramPin, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CSDiagramPinWrapper wrap(CSDiagramPin csDiagramPin) {
		return new CSDiagramPinWrapper(csDiagramPin);
	}

}