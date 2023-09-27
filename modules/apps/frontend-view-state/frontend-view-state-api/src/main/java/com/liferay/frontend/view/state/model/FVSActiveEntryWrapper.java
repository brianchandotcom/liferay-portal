/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.view.state.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FVSActiveEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FVSActiveEntry
 * @generated
 */
public class FVSActiveEntryWrapper
	extends BaseModelWrapper<FVSActiveEntry>
	implements FVSActiveEntry, ModelWrapper<FVSActiveEntry> {

	public FVSActiveEntryWrapper(FVSActiveEntry fvsActiveEntry) {
		super(fvsActiveEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("fvsActiveEntryId", getFvsActiveEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fvsEntryId", getFvsEntryId());
		attributes.put("clayDataSetDisplayId", getClayDataSetDisplayId());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());

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

		Long fvsActiveEntryId = (Long)attributes.get("fvsActiveEntryId");

		if (fvsActiveEntryId != null) {
			setFvsActiveEntryId(fvsActiveEntryId);
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

		Long fvsEntryId = (Long)attributes.get("fvsEntryId");

		if (fvsEntryId != null) {
			setFvsEntryId(fvsEntryId);
		}

		String clayDataSetDisplayId = (String)attributes.get(
			"clayDataSetDisplayId");

		if (clayDataSetDisplayId != null) {
			setClayDataSetDisplayId(clayDataSetDisplayId);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	@Override
	public FVSActiveEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the clay data set display ID of this fvs active entry.
	 *
	 * @return the clay data set display ID of this fvs active entry
	 */
	@Override
	public String getClayDataSetDisplayId() {
		return model.getClayDataSetDisplayId();
	}

	/**
	 * Returns the company ID of this fvs active entry.
	 *
	 * @return the company ID of this fvs active entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the container model ID of this fvs active entry.
	 *
	 * @return the container model ID of this fvs active entry
	 */
	@Override
	public long getContainerModelId() {
		return model.getContainerModelId();
	}

	/**
	 * Returns the container name of this fvs active entry.
	 *
	 * @return the container name of this fvs active entry
	 */
	@Override
	public String getContainerModelName() {
		return model.getContainerModelName();
	}

	/**
	 * Returns the create date of this fvs active entry.
	 *
	 * @return the create date of this fvs active entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the fvs active entry ID of this fvs active entry.
	 *
	 * @return the fvs active entry ID of this fvs active entry
	 */
	@Override
	public long getFvsActiveEntryId() {
		return model.getFvsActiveEntryId();
	}

	/**
	 * Returns the fvs entry ID of this fvs active entry.
	 *
	 * @return the fvs entry ID of this fvs active entry
	 */
	@Override
	public long getFvsEntryId() {
		return model.getFvsEntryId();
	}

	/**
	 * Returns the modified date of this fvs active entry.
	 *
	 * @return the modified date of this fvs active entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this fvs active entry.
	 *
	 * @return the mvcc version of this fvs active entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the parent container model ID of this fvs active entry.
	 *
	 * @return the parent container model ID of this fvs active entry
	 */
	@Override
	public long getParentContainerModelId() {
		return model.getParentContainerModelId();
	}

	/**
	 * Returns the plid of this fvs active entry.
	 *
	 * @return the plid of this fvs active entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this fvs active entry.
	 *
	 * @return the portlet ID of this fvs active entry
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this fvs active entry.
	 *
	 * @return the primary key of this fvs active entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this fvs active entry.
	 *
	 * @return the user ID of this fvs active entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this fvs active entry.
	 *
	 * @return the user name of this fvs active entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this fvs active entry.
	 *
	 * @return the user uuid of this fvs active entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this fvs active entry.
	 *
	 * @return the uuid of this fvs active entry
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
	 * Sets the clay data set display ID of this fvs active entry.
	 *
	 * @param clayDataSetDisplayId the clay data set display ID of this fvs active entry
	 */
	@Override
	public void setClayDataSetDisplayId(String clayDataSetDisplayId) {
		model.setClayDataSetDisplayId(clayDataSetDisplayId);
	}

	/**
	 * Sets the company ID of this fvs active entry.
	 *
	 * @param companyId the company ID of this fvs active entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the container model ID of this fvs active entry.
	 *
	 * @param containerModelId the container model ID of this fvs active entry
	 */
	@Override
	public void setContainerModelId(long containerModelId) {
		model.setContainerModelId(containerModelId);
	}

	/**
	 * Sets the create date of this fvs active entry.
	 *
	 * @param createDate the create date of this fvs active entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the fvs active entry ID of this fvs active entry.
	 *
	 * @param fvsActiveEntryId the fvs active entry ID of this fvs active entry
	 */
	@Override
	public void setFvsActiveEntryId(long fvsActiveEntryId) {
		model.setFvsActiveEntryId(fvsActiveEntryId);
	}

	/**
	 * Sets the fvs entry ID of this fvs active entry.
	 *
	 * @param fvsEntryId the fvs entry ID of this fvs active entry
	 */
	@Override
	public void setFvsEntryId(long fvsEntryId) {
		model.setFvsEntryId(fvsEntryId);
	}

	/**
	 * Sets the modified date of this fvs active entry.
	 *
	 * @param modifiedDate the modified date of this fvs active entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this fvs active entry.
	 *
	 * @param mvccVersion the mvcc version of this fvs active entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent container model ID of this fvs active entry.
	 *
	 * @param parentContainerModelId the parent container model ID of this fvs active entry
	 */
	@Override
	public void setParentContainerModelId(long parentContainerModelId) {
		model.setParentContainerModelId(parentContainerModelId);
	}

	/**
	 * Sets the plid of this fvs active entry.
	 *
	 * @param plid the plid of this fvs active entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this fvs active entry.
	 *
	 * @param portletId the portlet ID of this fvs active entry
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this fvs active entry.
	 *
	 * @param primaryKey the primary key of this fvs active entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this fvs active entry.
	 *
	 * @param userId the user ID of this fvs active entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this fvs active entry.
	 *
	 * @param userName the user name of this fvs active entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this fvs active entry.
	 *
	 * @param userUuid the user uuid of this fvs active entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this fvs active entry.
	 *
	 * @param uuid the uuid of this fvs active entry
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
	protected FVSActiveEntryWrapper wrap(FVSActiveEntry fvsActiveEntry) {
		return new FVSActiveEntryWrapper(fvsActiveEntry);
	}

}