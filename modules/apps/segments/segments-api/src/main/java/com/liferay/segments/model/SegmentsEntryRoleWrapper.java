/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link SegmentsEntryRole}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsEntryRole
 * @generated
 */
public class SegmentsEntryRoleWrapper
	extends BaseModelWrapper<SegmentsEntryRole>
	implements ModelWrapper<SegmentsEntryRole>, SegmentsEntryRole {

	public SegmentsEntryRoleWrapper(SegmentsEntryRole segmentsEntryRole) {
		super(segmentsEntryRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("segmentsEntryRoleId", getSegmentsEntryRoleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("segmentsEntryId", getSegmentsEntryId());
		attributes.put("roleId", getRoleId());

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

		Long segmentsEntryRoleId = (Long)attributes.get("segmentsEntryRoleId");

		if (segmentsEntryRoleId != null) {
			setSegmentsEntryRoleId(segmentsEntryRoleId);
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

		Long segmentsEntryId = (Long)attributes.get("segmentsEntryId");

		if (segmentsEntryId != null) {
			setSegmentsEntryId(segmentsEntryId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}
	}

	@Override
	public SegmentsEntryRole cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this segments entry role.
	 *
	 * @return the company ID of this segments entry role
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments entry role.
	 *
	 * @return the create date of this segments entry role
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this segments entry role.
	 *
	 * @return the ct collection ID of this segments entry role
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this segments entry role.
	 *
	 * @return the modified date of this segments entry role
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments entry role.
	 *
	 * @return the mvcc version of this segments entry role
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this segments entry role.
	 *
	 * @return the primary key of this segments entry role
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the role ID of this segments entry role.
	 *
	 * @return the role ID of this segments entry role
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	/**
	 * Returns the segments entry ID of this segments entry role.
	 *
	 * @return the segments entry ID of this segments entry role
	 */
	@Override
	public long getSegmentsEntryId() {
		return model.getSegmentsEntryId();
	}

	/**
	 * Returns the segments entry role ID of this segments entry role.
	 *
	 * @return the segments entry role ID of this segments entry role
	 */
	@Override
	public long getSegmentsEntryRoleId() {
		return model.getSegmentsEntryRoleId();
	}

	/**
	 * Returns the user ID of this segments entry role.
	 *
	 * @return the user ID of this segments entry role
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments entry role.
	 *
	 * @return the user name of this segments entry role
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments entry role.
	 *
	 * @return the user uuid of this segments entry role
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
	 * Sets the company ID of this segments entry role.
	 *
	 * @param companyId the company ID of this segments entry role
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments entry role.
	 *
	 * @param createDate the create date of this segments entry role
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this segments entry role.
	 *
	 * @param ctCollectionId the ct collection ID of this segments entry role
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this segments entry role.
	 *
	 * @param modifiedDate the modified date of this segments entry role
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments entry role.
	 *
	 * @param mvccVersion the mvcc version of this segments entry role
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this segments entry role.
	 *
	 * @param primaryKey the primary key of this segments entry role
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this segments entry role.
	 *
	 * @param roleId the role ID of this segments entry role
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	 * Sets the segments entry ID of this segments entry role.
	 *
	 * @param segmentsEntryId the segments entry ID of this segments entry role
	 */
	@Override
	public void setSegmentsEntryId(long segmentsEntryId) {
		model.setSegmentsEntryId(segmentsEntryId);
	}

	/**
	 * Sets the segments entry role ID of this segments entry role.
	 *
	 * @param segmentsEntryRoleId the segments entry role ID of this segments entry role
	 */
	@Override
	public void setSegmentsEntryRoleId(long segmentsEntryRoleId) {
		model.setSegmentsEntryRoleId(segmentsEntryRoleId);
	}

	/**
	 * Sets the user ID of this segments entry role.
	 *
	 * @param userId the user ID of this segments entry role
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments entry role.
	 *
	 * @param userName the user name of this segments entry role
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments entry role.
	 *
	 * @param userUuid the user uuid of this segments entry role
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<SegmentsEntryRole, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SegmentsEntryRole, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected SegmentsEntryRoleWrapper wrap(
		SegmentsEntryRole segmentsEntryRole) {

		return new SegmentsEntryRoleWrapper(segmentsEntryRole);
	}

}