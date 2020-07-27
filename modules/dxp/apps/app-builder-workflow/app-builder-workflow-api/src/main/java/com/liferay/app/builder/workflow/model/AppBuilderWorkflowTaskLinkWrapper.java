/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.app.builder.workflow.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AppBuilderWorkflowTaskLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AppBuilderWorkflowTaskLink
 * @generated
 */
public class AppBuilderWorkflowTaskLinkWrapper
	extends BaseModelWrapper<AppBuilderWorkflowTaskLink>
	implements AppBuilderWorkflowTaskLink,
			   ModelWrapper<AppBuilderWorkflowTaskLink> {

	public AppBuilderWorkflowTaskLinkWrapper(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		super(appBuilderWorkflowTaskLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"appBuilderWorkflowTaskLinkId", getAppBuilderWorkflowTaskLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("appBuilderAppId", getAppBuilderAppId());
		attributes.put("appBuilderAppVersionId", getAppBuilderAppVersionId());
		attributes.put("ddmStructureLayoutId", getDdmStructureLayoutId());
		attributes.put("readOnly", isReadOnly());
		attributes.put("workflowTaskName", getWorkflowTaskName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long appBuilderWorkflowTaskLinkId = (Long)attributes.get(
			"appBuilderWorkflowTaskLinkId");

		if (appBuilderWorkflowTaskLinkId != null) {
			setAppBuilderWorkflowTaskLinkId(appBuilderWorkflowTaskLinkId);
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

		Long ddmStructureLayoutId = (Long)attributes.get(
			"ddmStructureLayoutId");

		if (ddmStructureLayoutId != null) {
			setDdmStructureLayoutId(ddmStructureLayoutId);
		}

		Boolean readOnly = (Boolean)attributes.get("readOnly");

		if (readOnly != null) {
			setReadOnly(readOnly);
		}

		String workflowTaskName = (String)attributes.get("workflowTaskName");

		if (workflowTaskName != null) {
			setWorkflowTaskName(workflowTaskName);
		}
	}

	/**
	 * Returns the app builder app ID of this app builder workflow task link.
	 *
	 * @return the app builder app ID of this app builder workflow task link
	 */
	@Override
	public long getAppBuilderAppId() {
		return model.getAppBuilderAppId();
	}

	/**
	 * Returns the app builder app version ID of this app builder workflow task link.
	 *
	 * @return the app builder app version ID of this app builder workflow task link
	 */
	@Override
	public long getAppBuilderAppVersionId() {
		return model.getAppBuilderAppVersionId();
	}

	/**
	 * Returns the app builder workflow task link ID of this app builder workflow task link.
	 *
	 * @return the app builder workflow task link ID of this app builder workflow task link
	 */
	@Override
	public long getAppBuilderWorkflowTaskLinkId() {
		return model.getAppBuilderWorkflowTaskLinkId();
	}

	/**
	 * Returns the company ID of this app builder workflow task link.
	 *
	 * @return the company ID of this app builder workflow task link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ddm structure layout ID of this app builder workflow task link.
	 *
	 * @return the ddm structure layout ID of this app builder workflow task link
	 */
	@Override
	public long getDdmStructureLayoutId() {
		return model.getDdmStructureLayoutId();
	}

	/**
	 * Returns the mvcc version of this app builder workflow task link.
	 *
	 * @return the mvcc version of this app builder workflow task link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this app builder workflow task link.
	 *
	 * @return the primary key of this app builder workflow task link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the read only of this app builder workflow task link.
	 *
	 * @return the read only of this app builder workflow task link
	 */
	@Override
	public boolean getReadOnly() {
		return model.getReadOnly();
	}

	/**
	 * Returns the workflow task name of this app builder workflow task link.
	 *
	 * @return the workflow task name of this app builder workflow task link
	 */
	@Override
	public String getWorkflowTaskName() {
		return model.getWorkflowTaskName();
	}

	/**
	 * Returns <code>true</code> if this app builder workflow task link is read only.
	 *
	 * @return <code>true</code> if this app builder workflow task link is read only; <code>false</code> otherwise
	 */
	@Override
	public boolean isReadOnly() {
		return model.isReadOnly();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app builder app ID of this app builder workflow task link.
	 *
	 * @param appBuilderAppId the app builder app ID of this app builder workflow task link
	 */
	@Override
	public void setAppBuilderAppId(long appBuilderAppId) {
		model.setAppBuilderAppId(appBuilderAppId);
	}

	/**
	 * Sets the app builder app version ID of this app builder workflow task link.
	 *
	 * @param appBuilderAppVersionId the app builder app version ID of this app builder workflow task link
	 */
	@Override
	public void setAppBuilderAppVersionId(long appBuilderAppVersionId) {
		model.setAppBuilderAppVersionId(appBuilderAppVersionId);
	}

	/**
	 * Sets the app builder workflow task link ID of this app builder workflow task link.
	 *
	 * @param appBuilderWorkflowTaskLinkId the app builder workflow task link ID of this app builder workflow task link
	 */
	@Override
	public void setAppBuilderWorkflowTaskLinkId(
		long appBuilderWorkflowTaskLinkId) {

		model.setAppBuilderWorkflowTaskLinkId(appBuilderWorkflowTaskLinkId);
	}

	/**
	 * Sets the company ID of this app builder workflow task link.
	 *
	 * @param companyId the company ID of this app builder workflow task link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ddm structure layout ID of this app builder workflow task link.
	 *
	 * @param ddmStructureLayoutId the ddm structure layout ID of this app builder workflow task link
	 */
	@Override
	public void setDdmStructureLayoutId(long ddmStructureLayoutId) {
		model.setDdmStructureLayoutId(ddmStructureLayoutId);
	}

	/**
	 * Sets the mvcc version of this app builder workflow task link.
	 *
	 * @param mvccVersion the mvcc version of this app builder workflow task link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this app builder workflow task link.
	 *
	 * @param primaryKey the primary key of this app builder workflow task link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this app builder workflow task link is read only.
	 *
	 * @param readOnly the read only of this app builder workflow task link
	 */
	@Override
	public void setReadOnly(boolean readOnly) {
		model.setReadOnly(readOnly);
	}

	/**
	 * Sets the workflow task name of this app builder workflow task link.
	 *
	 * @param workflowTaskName the workflow task name of this app builder workflow task link
	 */
	@Override
	public void setWorkflowTaskName(String workflowTaskName) {
		model.setWorkflowTaskName(workflowTaskName);
	}

	@Override
	protected AppBuilderWorkflowTaskLinkWrapper wrap(
		AppBuilderWorkflowTaskLink appBuilderWorkflowTaskLink) {

		return new AppBuilderWorkflowTaskLinkWrapper(
			appBuilderWorkflowTaskLink);
	}

}