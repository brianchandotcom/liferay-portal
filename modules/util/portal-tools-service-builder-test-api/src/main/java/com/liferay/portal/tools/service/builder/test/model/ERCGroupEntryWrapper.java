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
 * This class is a wrapper for {@link ERCGroupEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntry
 * @generated
 */
public class ERCGroupEntryWrapper
	extends BaseModelWrapper<ERCGroupEntry>
	implements ERCGroupEntry, ModelWrapper<ERCGroupEntry> {

	public ERCGroupEntryWrapper(ERCGroupEntry ercGroupEntry) {
		super(ercGroupEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercGroupEntryId", getErcGroupEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());

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

		Long ercGroupEntryId = (Long)attributes.get("ercGroupEntryId");

		if (ercGroupEntryId != null) {
			setErcGroupEntryId(ercGroupEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public ERCGroupEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this erc group entry.
	 *
	 * @return the company ID of this erc group entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the erc group entry ID of this erc group entry.
	 *
	 * @return the erc group entry ID of this erc group entry
	 */
	@Override
	public long getErcGroupEntryId() {
		return model.getErcGroupEntryId();
	}

	/**
	 * Returns the external reference code of this erc group entry.
	 *
	 * @return the external reference code of this erc group entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this erc group entry.
	 *
	 * @return the group ID of this erc group entry
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this erc group entry.
	 *
	 * @return the primary key of this erc group entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this erc group entry.
	 *
	 * @return the uuid of this erc group entry
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
	 * Sets the company ID of this erc group entry.
	 *
	 * @param companyId the company ID of this erc group entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the erc group entry ID of this erc group entry.
	 *
	 * @param ercGroupEntryId the erc group entry ID of this erc group entry
	 */
	@Override
	public void setErcGroupEntryId(long ercGroupEntryId) {
		model.setErcGroupEntryId(ercGroupEntryId);
	}

	/**
	 * Sets the external reference code of this erc group entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc group entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this erc group entry.
	 *
	 * @param groupId the group ID of this erc group entry
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this erc group entry.
	 *
	 * @param primaryKey the primary key of this erc group entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this erc group entry.
	 *
	 * @param uuid the uuid of this erc group entry
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
	protected ERCGroupEntryWrapper wrap(ERCGroupEntry ercGroupEntry) {
		return new ERCGroupEntryWrapper(ercGroupEntry);
	}

}