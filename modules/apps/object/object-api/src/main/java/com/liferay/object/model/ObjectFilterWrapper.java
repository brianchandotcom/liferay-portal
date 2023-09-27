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
 * This class is a wrapper for {@link ObjectFilter}.
 * </p>
 *
 * @author Marco Leo
 * @see ObjectFilter
 * @generated
 */
public class ObjectFilterWrapper
	extends BaseModelWrapper<ObjectFilter>
	implements ModelWrapper<ObjectFilter>, ObjectFilter {

	public ObjectFilterWrapper(ObjectFilter objectFilter) {
		super(objectFilter);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("objectFilterId", getObjectFilterId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("objectFieldId", getObjectFieldId());
		attributes.put("filterBy", getFilterBy());
		attributes.put("filterType", getFilterType());
		attributes.put("json", getJSON());

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

		Long objectFilterId = (Long)attributes.get("objectFilterId");

		if (objectFilterId != null) {
			setObjectFilterId(objectFilterId);
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

		Long objectFieldId = (Long)attributes.get("objectFieldId");

		if (objectFieldId != null) {
			setObjectFieldId(objectFieldId);
		}

		String filterBy = (String)attributes.get("filterBy");

		if (filterBy != null) {
			setFilterBy(filterBy);
		}

		String filterType = (String)attributes.get("filterType");

		if (filterType != null) {
			setFilterType(filterType);
		}

		String json = (String)attributes.get("json");

		if (json != null) {
			setJSON(json);
		}
	}

	@Override
	public ObjectFilter cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this object filter.
	 *
	 * @return the company ID of this object filter
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this object filter.
	 *
	 * @return the create date of this object filter
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the filter by of this object filter.
	 *
	 * @return the filter by of this object filter
	 */
	@Override
	public String getFilterBy() {
		return model.getFilterBy();
	}

	/**
	 * Returns the filter type of this object filter.
	 *
	 * @return the filter type of this object filter
	 */
	@Override
	public String getFilterType() {
		return model.getFilterType();
	}

	/**
	 * Returns the json of this object filter.
	 *
	 * @return the json of this object filter
	 */
	@Override
	public String getJSON() {
		return model.getJSON();
	}

	/**
	 * Returns the modified date of this object filter.
	 *
	 * @return the modified date of this object filter
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this object filter.
	 *
	 * @return the mvcc version of this object filter
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the object field ID of this object filter.
	 *
	 * @return the object field ID of this object filter
	 */
	@Override
	public long getObjectFieldId() {
		return model.getObjectFieldId();
	}

	/**
	 * Returns the object filter ID of this object filter.
	 *
	 * @return the object filter ID of this object filter
	 */
	@Override
	public long getObjectFilterId() {
		return model.getObjectFilterId();
	}

	/**
	 * Returns the primary key of this object filter.
	 *
	 * @return the primary key of this object filter
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this object filter.
	 *
	 * @return the user ID of this object filter
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this object filter.
	 *
	 * @return the user name of this object filter
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this object filter.
	 *
	 * @return the user uuid of this object filter
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this object filter.
	 *
	 * @return the uuid of this object filter
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
	 * Sets the company ID of this object filter.
	 *
	 * @param companyId the company ID of this object filter
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this object filter.
	 *
	 * @param createDate the create date of this object filter
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the filter by of this object filter.
	 *
	 * @param filterBy the filter by of this object filter
	 */
	@Override
	public void setFilterBy(String filterBy) {
		model.setFilterBy(filterBy);
	}

	/**
	 * Sets the filter type of this object filter.
	 *
	 * @param filterType the filter type of this object filter
	 */
	@Override
	public void setFilterType(String filterType) {
		model.setFilterType(filterType);
	}

	/**
	 * Sets the json of this object filter.
	 *
	 * @param json the json of this object filter
	 */
	@Override
	public void setJSON(String json) {
		model.setJSON(json);
	}

	/**
	 * Sets the modified date of this object filter.
	 *
	 * @param modifiedDate the modified date of this object filter
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this object filter.
	 *
	 * @param mvccVersion the mvcc version of this object filter
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the object field ID of this object filter.
	 *
	 * @param objectFieldId the object field ID of this object filter
	 */
	@Override
	public void setObjectFieldId(long objectFieldId) {
		model.setObjectFieldId(objectFieldId);
	}

	/**
	 * Sets the object filter ID of this object filter.
	 *
	 * @param objectFilterId the object filter ID of this object filter
	 */
	@Override
	public void setObjectFilterId(long objectFilterId) {
		model.setObjectFilterId(objectFilterId);
	}

	/**
	 * Sets the primary key of this object filter.
	 *
	 * @param primaryKey the primary key of this object filter
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this object filter.
	 *
	 * @param userId the user ID of this object filter
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this object filter.
	 *
	 * @param userName the user name of this object filter
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this object filter.
	 *
	 * @param userUuid the user uuid of this object filter
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this object filter.
	 *
	 * @param uuid the uuid of this object filter
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
	protected ObjectFilterWrapper wrap(ObjectFilter objectFilter) {
		return new ObjectFilterWrapper(objectFilter);
	}

}