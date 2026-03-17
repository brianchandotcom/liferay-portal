/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link DepotEntryPin}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryPin
 * @generated
 */
public class DepotEntryPinWrapper
	extends BaseModelWrapper<DepotEntryPin>
	implements DepotEntryPin, ModelWrapper<DepotEntryPin> {

	public DepotEntryPinWrapper(DepotEntryPin depotEntryPin) {
		super(depotEntryPin);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("depotEntryPinId", getDepotEntryPinId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("depotEntryId", getDepotEntryId());

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

		Long depotEntryPinId = (Long)attributes.get("depotEntryPinId");

		if (depotEntryPinId != null) {
			setDepotEntryPinId(depotEntryPinId);
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

		Long depotEntryId = (Long)attributes.get("depotEntryId");

		if (depotEntryId != null) {
			setDepotEntryId(depotEntryId);
		}
	}

	@Override
	public DepotEntryPin cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this depot entry pin.
	 *
	 * @return the company ID of this depot entry pin
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this depot entry pin.
	 *
	 * @return the ct collection ID of this depot entry pin
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the depot entry ID of this depot entry pin.
	 *
	 * @return the depot entry ID of this depot entry pin
	 */
	@Override
	public long getDepotEntryId() {
		return model.getDepotEntryId();
	}

	/**
	 * Returns the depot entry pin ID of this depot entry pin.
	 *
	 * @return the depot entry pin ID of this depot entry pin
	 */
	@Override
	public long getDepotEntryPinId() {
		return model.getDepotEntryPinId();
	}

	/**
	 * Returns the group ID of this depot entry pin.
	 *
	 * @return the group ID of this depot entry pin
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this depot entry pin.
	 *
	 * @return the mvcc version of this depot entry pin
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this depot entry pin.
	 *
	 * @return the primary key of this depot entry pin
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this depot entry pin.
	 *
	 * @return the user ID of this depot entry pin
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this depot entry pin.
	 *
	 * @return the user uuid of this depot entry pin
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this depot entry pin.
	 *
	 * @return the uuid of this depot entry pin
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
	 * Sets the company ID of this depot entry pin.
	 *
	 * @param companyId the company ID of this depot entry pin
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this depot entry pin.
	 *
	 * @param ctCollectionId the ct collection ID of this depot entry pin
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the depot entry ID of this depot entry pin.
	 *
	 * @param depotEntryId the depot entry ID of this depot entry pin
	 */
	@Override
	public void setDepotEntryId(long depotEntryId) {
		model.setDepotEntryId(depotEntryId);
	}

	/**
	 * Sets the depot entry pin ID of this depot entry pin.
	 *
	 * @param depotEntryPinId the depot entry pin ID of this depot entry pin
	 */
	@Override
	public void setDepotEntryPinId(long depotEntryPinId) {
		model.setDepotEntryPinId(depotEntryPinId);
	}

	/**
	 * Sets the group ID of this depot entry pin.
	 *
	 * @param groupId the group ID of this depot entry pin
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this depot entry pin.
	 *
	 * @param mvccVersion the mvcc version of this depot entry pin
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this depot entry pin.
	 *
	 * @param primaryKey the primary key of this depot entry pin
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this depot entry pin.
	 *
	 * @param userId the user ID of this depot entry pin
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this depot entry pin.
	 *
	 * @param userUuid the user uuid of this depot entry pin
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this depot entry pin.
	 *
	 * @param uuid the uuid of this depot entry pin
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
	public Map<String, Function<DepotEntryPin, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<DepotEntryPin, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected DepotEntryPinWrapper wrap(DepotEntryPin depotEntryPin) {
		return new DepotEntryPinWrapper(depotEntryPin);
	}

}
// SB-Hash:-76151580