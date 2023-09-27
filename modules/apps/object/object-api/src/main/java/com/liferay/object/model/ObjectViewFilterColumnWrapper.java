/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ObjectViewFilterColumn}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectViewFilterColumn
 * @generated
 */
public class ObjectViewFilterColumnWrapper
	extends BaseModelWrapper<ObjectViewFilterColumn>
	implements ModelWrapper<ObjectViewFilterColumn>, ObjectViewFilterColumn {

	public ObjectViewFilterColumnWrapper(
		ObjectViewFilterColumn objectViewFilterColumn) {

		super(objectViewFilterColumn);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"objectViewFilterColumnId", getObjectViewFilterColumnId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectViewId", getObjectViewId());
		attributes.put("filterType", getFilterType());
		attributes.put("json", getJSON());
		attributes.put("objectFieldName", getObjectFieldName());

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

		Long objectViewFilterColumnId = (Long)attributes.get(
			"objectViewFilterColumnId");

		if (objectViewFilterColumnId != null) {
			setObjectViewFilterColumnId(objectViewFilterColumnId);
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

		Long objectViewId = (Long)attributes.get("objectViewId");

		if (objectViewId != null) {
			setObjectViewId(objectViewId);
		}

		String filterType = (String)attributes.get("filterType");

		if (filterType != null) {
			setFilterType(filterType);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJSON(json);
		}

		String objectFieldName = (String)attributes.get("objectFieldName");

		if (objectFieldName != null) {
			setObjectFieldName(objectFieldName);
		}
	}

	@Override
	public ObjectViewFilterColumn cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object view filter column.
	 *
	 * @return the company ID of this object view filter column
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object view filter column.
	 *
	 * @return the create date of this object view filter column
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the filter type of this object view filter column.
	 *
	 * @return the filter type of this object view filter column
	 */
	@Override
	public String getFilterType() {
		return model.getFilterType();
	}

	/**
	 * Returns the json of this object view filter column.
	 *
	 * @return the json of this object view filter column
	 */
	@Override
	public String getJSON() {
		return model.getJSON();
	}

	@Override
	public com.liferay.portal.kernel.json.JSONArray getJSONArray()
		throws com.liferay.portal.kernel.json.JSONException {

		return model.getJSONArray();
	}

	/**
	 * Returns the modified date of this object view filter column.
	 *
	 * @return the modified date of this object view filter column
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object view filter column.
	 *
	 * @return the mvcc version of this object view filter column
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field name of this object view filter column.
	 *
	 * @return the object field name of this object view filter column
	 */
	@Override
	public String getObjectFieldName() {
		return model.getObjectFieldName();
	}

	/**
	 * Returns the object view filter column ID of this object view filter column.
	 *
	 * @return the object view filter column ID of this object view filter column
	 */
	@Override
	public long getObjectViewFilterColumnId() {
		return model.getObjectViewFilterColumnId();
	}

	/**
	 * Returns the object view ID of this object view filter column.
	 *
	 * @return the object view ID of this object view filter column
	 */
	@Override
	public long getObjectViewId() {
		return model.getObjectViewId();
	}

	/**
	 * Returns the primary key of this object view filter column.
	 *
	 * @return the primary key of this object view filter column
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object view filter column.
	 *
	 * @return the user ID of this object view filter column
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object view filter column.
	 *
	 * @return the user name of this object view filter column
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object view filter column.
	 *
	 * @return the user uuid of this object view filter column
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object view filter column.
	 *
	 * @return the uuid of this object view filter column
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
	 * Sets the company ID of this object view filter column.
	 *
	 * @param companyId the company ID of this object view filter column
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object view filter column.
	 *
	 * @param createDate the create date of this object view filter column
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the filter type of this object view filter column.
	 *
	 * @param filterType the filter type of this object view filter column
	 */
	@Override
	public void setFilterType(String filterType) {
		model.setFilterType(filterType);
	}

	/**
	 * Sets the json of this object view filter column.
	 *
	 * @param json the json of this object view filter column
	 */
	@Override
	public void setJSON(String json) {
		model.setJSON(json);
	}

	/**
	 * Sets the modified date of this object view filter column.
	 *
	 * @param modifiedDate the modified date of this object view filter column
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object view filter column.
	 *
	 * @param mvccVersion the mvcc version of this object view filter column
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field name of this object view filter column.
	 *
	 * @param objectFieldName the object field name of this object view filter column
	 */
	@Override
	public void setObjectFieldName(String objectFieldName) {
		model.setObjectFieldName(objectFieldName);
	}

	/**
	 * Sets the object view filter column ID of this object view filter column.
	 *
	 * @param objectViewFilterColumnId the object view filter column ID of this object view filter column
	 */
	@Override
	public void setObjectViewFilterColumnId(long objectViewFilterColumnId) {
		model.setObjectViewFilterColumnId(objectViewFilterColumnId);
	}

	/**
	 * Sets the object view ID of this object view filter column.
	 *
	 * @param objectViewId the object view ID of this object view filter column
	 */
	@Override
	public void setObjectViewId(long objectViewId) {
		model.setObjectViewId(objectViewId);
	}

	/**
	 * Sets the primary key of this object view filter column.
	 *
	 * @param primaryKey the primary key of this object view filter column
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object view filter column.
	 *
	 * @param userId the user ID of this object view filter column
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object view filter column.
	 *
	 * @param userName the user name of this object view filter column
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object view filter column.
	 *
	 * @param userUuid the user uuid of this object view filter column
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object view filter column.
	 *
	 * @param uuid the uuid of this object view filter column
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
	protected ObjectViewFilterColumnWrapper wrap(
		ObjectViewFilterColumn objectViewFilterColumn) {

		return new ObjectViewFilterColumnWrapper(objectViewFilterColumn);
	}

}