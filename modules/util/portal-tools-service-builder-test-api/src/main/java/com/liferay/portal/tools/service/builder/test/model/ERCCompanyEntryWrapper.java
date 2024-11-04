/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ERCCompanyEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntry
 * @generated
 */
public class ERCCompanyEntryWrapper
	extends BaseModelWrapper<ERCCompanyEntry>
	implements ERCCompanyEntry, ModelWrapper<ERCCompanyEntry> {

	public ERCCompanyEntryWrapper(ERCCompanyEntry ercCompanyEntry) {
		super(ercCompanyEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercCompanyEntryId", getErcCompanyEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("column1", getColumn1());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long ercCompanyEntryId = (Long)attributes.get("ercCompanyEntryId");

		if (ercCompanyEntryId != null) {
			setErcCompanyEntryId(ercCompanyEntryId);
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

		Integer column1 = (Integer)attributes.get("column1");

		if (column1 != null) {
			setColumn1(column1);
		}
	}

	@Override
	public ERCCompanyEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the column1 of this erc company entry.
	 *
	 * @return the column1 of this erc company entry
	 */
	@Override
	public int getColumn1() {
		return model.getColumn1();
	}

	/**
	 * Returns the company ID of this erc company entry.
	 *
	 * @return the company ID of this erc company entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the erc company entry ID of this erc company entry.
	 *
	 * @return the erc company entry ID of this erc company entry
	 */
	@Override
	public long getErcCompanyEntryId() {
		return model.getErcCompanyEntryId();
	}

	/**
	 * Returns the external reference code of this erc company entry.
	 *
	 * @return the external reference code of this erc company entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the primary key of this erc company entry.
	 *
	 * @return the primary key of this erc company entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this erc company entry.
	 *
	 * @return the user ID of this erc company entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this erc company entry.
	 *
	 * @return the user name of this erc company entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this erc company entry.
	 *
	 * @return the user uuid of this erc company entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this erc company entry.
	 *
	 * @return the uuid of this erc company entry
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
	 * Sets the column1 of this erc company entry.
	 *
	 * @param column1 the column1 of this erc company entry
	 */
	@Override
	public void setColumn1(int column1) {
		model.setColumn1(column1);
	}

	/**
	 * Sets the company ID of this erc company entry.
	 *
	 * @param companyId the company ID of this erc company entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the erc company entry ID of this erc company entry.
	 *
	 * @param ercCompanyEntryId the erc company entry ID of this erc company entry
	 */
	@Override
	public void setErcCompanyEntryId(long ercCompanyEntryId) {
		model.setErcCompanyEntryId(ercCompanyEntryId);
	}

	/**
	 * Sets the external reference code of this erc company entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc company entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the primary key of this erc company entry.
	 *
	 * @param primaryKey the primary key of this erc company entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this erc company entry.
	 *
	 * @param userId the user ID of this erc company entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this erc company entry.
	 *
	 * @param userName the user name of this erc company entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this erc company entry.
	 *
	 * @param userUuid the user uuid of this erc company entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this erc company entry.
	 *
	 * @param uuid the uuid of this erc company entry
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
	protected ERCCompanyEntryWrapper wrap(ERCCompanyEntry ercCompanyEntry) {
		return new ERCCompanyEntryWrapper(ercCompanyEntry);
	}

}