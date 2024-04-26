/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ListType}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ListType
 * @generated
 */
public class ListTypeWrapper
	extends BaseModelWrapper<ListType>
	implements ListType, ModelWrapper<ListType> {

	public ListTypeWrapper(ListType listType) {
		super(listType);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("listTypeId", getListTypeId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long listTypeId = (Long)attributes.get("listTypeId");

		if (listTypeId != null) {
			setListTypeId(listTypeId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public ListType cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this list type.
	 *
	 * @return the company ID of this list type
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this list type.
	 *
	 * @return the create date of this list type
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the list type ID of this list type.
	 *
	 * @return the list type ID of this list type
	 */
	@Override
	public long getListTypeId() {
		return model.getListTypeId();
	}

	/**
	 * Returns the modified date of this list type.
	 *
	 * @return the modified date of this list type
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this list type.
	 *
	 * @return the mvcc version of this list type
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this list type.
	 *
	 * @return the name of this list type
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this list type.
	 *
	 * @return the primary key of this list type
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this list type.
	 *
	 * @return the type of this list type
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this list type.
	 *
	 * @return the user ID of this list type
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this list type.
	 *
	 * @return the user name of this list type
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this list type.
	 *
	 * @return the user uuid of this list type
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this list type.
	 *
	 * @return the uuid of this list type
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
	 * Sets the company ID of this list type.
	 *
	 * @param companyId the company ID of this list type
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this list type.
	 *
	 * @param createDate the create date of this list type
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the list type ID of this list type.
	 *
	 * @param listTypeId the list type ID of this list type
	 */
	@Override
	public void setListTypeId(long listTypeId) {
		model.setListTypeId(listTypeId);
	}

	/**
	 * Sets the modified date of this list type.
	 *
	 * @param modifiedDate the modified date of this list type
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this list type.
	 *
	 * @param mvccVersion the mvcc version of this list type
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this list type.
	 *
	 * @param name the name of this list type
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this list type.
	 *
	 * @param primaryKey the primary key of this list type
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this list type.
	 *
	 * @param type the type of this list type
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this list type.
	 *
	 * @param userId the user ID of this list type
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this list type.
	 *
	 * @param userName the user name of this list type
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this list type.
	 *
	 * @param userUuid the user uuid of this list type
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this list type.
	 *
	 * @param uuid the uuid of this list type
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
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected ListTypeWrapper wrap(ListType listType) {
		return new ListTypeWrapper(listType);
	}

}