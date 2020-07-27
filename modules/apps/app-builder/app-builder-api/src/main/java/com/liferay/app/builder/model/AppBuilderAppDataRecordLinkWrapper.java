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

package com.liferay.app.builder.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderAppDataRecordLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderAppDataRecordLink
 * @generated
 */
public class AppBuilderAppDataRecordLinkWrapper
	extends BaseModelWrapper<AppBuilderAppDataRecordLink>
	implements AppBuilderAppDataRecordLink,
			   ModelWrapper<AppBuilderAppDataRecordLink> {

	public AppBuilderAppDataRecordLinkWrapper(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		super(appBuilderAppDataRecordLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"appBuilderAppDataRecordLinkId",
			getAppBuilderAppDataRecordLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("appBuilderAppVersionId", getAppBuilderAppVersionId());
		attributes.put("ddlRecordId", getDdlRecordId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long appBuilderAppDataRecordLinkId = (Long)attributes.get(
			"appBuilderAppDataRecordLinkId");

		if (appBuilderAppDataRecordLinkId != null) {
			setAppBuilderAppDataRecordLinkId(appBuilderAppDataRecordLinkId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long appBuilderAppId = (Long)attributes.get("appBuilderAppId");

		if (appBuilderAppId != null) {
			setAppBuilderAppId(appBuilderAppId);
		}

		Long appBuilderAppVersionId = (Long)attributes.get(
			"appBuilderAppVersionId");

		if (appBuilderAppVersionId != null) {
			setAppBuilderAppVersionId(appBuilderAppVersionId);
		}

		Long ddlRecordId = (Long)attributes.get("ddlRecordId");

		if (ddlRecordId != null) {
			setDdlRecordId(ddlRecordId);
		}
	}

	/**
	 * Returns the app builder app data record link ID of this app builder app data record link.
	 *
	 * @return the app builder app data record link ID of this app builder app data record link
	 */
	@Override
	public long getAppBuilderAppDataRecordLinkId() {
		return model.getAppBuilderAppDataRecordLinkId();
	}

	/**
	 * Returns the app builder app ID of this app builder app data record link.
	 *
	 * @return the app builder app ID of this app builder app data record link
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	/**
	 * Returns the app builder app version ID of this app builder app data record link.
	 *
	 * @return the app builder app version ID of this app builder app data record link
	 */
	@Override
	public long getAppBuilderAppVersionId() {
		return model.getAppBuilderAppVersionId();
	}

	/**
	 * Returns the company ID of this app builder app data record link.
	 *
	 * @return the company ID of this app builder app data record link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ddl record ID of this app builder app data record link.
	 *
	 * @return the ddl record ID of this app builder app data record link
	 */
	@Override
	public long getDdlRecordId() {
		return model.getDdlRecordId();
	}

	/**
	 * Returns the group ID of this app builder app data record link.
	 *
	 * @return the group ID of this app builder app data record link
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this app builder app data record link.
	 *
	 * @return the primary key of this app builder app data record link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app builder app data record link ID of this app builder app data record link.
	 *
	 * @param appBuilderAppDataRecordLinkId the app builder app data record link ID of this app builder app data record link
	 */
	@Override
	public void setAppBuilderAppDataRecordLinkId(
		long appBuilderAppDataRecordLinkId) {

		model.setAppBuilderAppDataRecordLinkId(appBuilderAppDataRecordLinkId);
	}

	/**
	 * Sets the app builder app ID of this app builder app data record link.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder app data record link
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the app builder app version ID of this app builder app data record link.
	 *
	 * @param appBuilderAppVersionId the app builder app version ID of this app builder app data record link
	 */
	@Override
	public void setAppBuilderAppVersionId(long appBuilderAppVersionId) {
		model.setAppBuilderAppVersionId(appBuilderAppVersionId);
	}

	/**
	 * Sets the company ID of this app builder app data record link.
	 *
	 * @param companyId the company ID of this app builder app data record link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ddl record ID of this app builder app data record link.
	 *
	 * @param ddlRecordId the ddl record ID of this app builder app data record link
	 */
	@Override
	public void setDdlRecordId(long ddlRecordId) {
		model.setDdlRecordId(ddlRecordId);
	}

	/**
	 * Sets the group ID of this app builder app data record link.
	 *
	 * @param groupId the group ID of this app builder app data record link
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this app builder app data record link.
	 *
	 * @param primaryKey the primary key of this app builder app data record link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected AppBuilderAppDataRecordLinkWrapper wrap(
		AppBuilderAppDataRecordLink appBuilderAppDataRecordLink) {

		return new AppBuilderAppDataRecordLinkWrapper(
			appBuilderAppDataRecordLink);
	}

}