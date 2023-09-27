/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link WorkflowInstanceLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowInstanceLink
 * @generated
 */
public class WorkflowInstanceLinkWrapper
	extends BaseModelWrapper<WorkflowInstanceLink>
	implements ModelWrapper<WorkflowInstanceLink>, WorkflowInstanceLink {

	public WorkflowInstanceLinkWrapper(
		WorkflowInstanceLink workflowInstanceLink) {

		super(workflowInstanceLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("workflowInstanceLinkId", getWorkflowInstanceLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("workflowInstanceId", getWorkflowInstanceId());

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

		Long workflowInstanceLinkId = (Long)attributes.get(
			"workflowInstanceLinkId");

		if (workflowInstanceLinkId != null) {
			setWorkflowInstanceLinkId(workflowInstanceLinkId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long workflowInstanceId = (Long)attributes.get("workflowInstanceId");

		if (workflowInstanceId != null) {
			setWorkflowInstanceId(workflowInstanceId);
		}
	}

	@Override
	public WorkflowInstanceLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this workflow instance link.
	 *
	 * @return the fully qualified class name of this workflow instance link
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this workflow instance link.
	 *
	 * @return the class name ID of this workflow instance link
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this workflow instance link.
	 *
	 * @return the class pk of this workflow instance link
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this workflow instance link.
	 *
	 * @return the company ID of this workflow instance link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this workflow instance link.
	 *
	 * @return the create date of this workflow instance link
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this workflow instance link.
	 *
	 * @return the ct collection ID of this workflow instance link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this workflow instance link.
	 *
	 * @return the group ID of this workflow instance link
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this workflow instance link.
	 *
	 * @return the modified date of this workflow instance link
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this workflow instance link.
	 *
	 * @return the mvcc version of this workflow instance link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this workflow instance link.
	 *
	 * @return the primary key of this workflow instance link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this workflow instance link.
	 *
	 * @return the user ID of this workflow instance link
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this workflow instance link.
	 *
	 * @return the user name of this workflow instance link
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this workflow instance link.
	 *
	 * @return the user uuid of this workflow instance link
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the workflow instance ID of this workflow instance link.
	 *
	 * @return the workflow instance ID of this workflow instance link
	 */
	@Override
	public long getWorkflowInstanceId() {
		return model.getWorkflowInstanceId();
	}

	/**
	 * Returns the workflow instance link ID of this workflow instance link.
	 *
	 * @return the workflow instance link ID of this workflow instance link
	 */
	@Override
	public long getWorkflowInstanceLinkId() {
		return model.getWorkflowInstanceLinkId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this workflow instance link.
	 *
	 * @param classNameId the class name ID of this workflow instance link
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this workflow instance link.
	 *
	 * @param classPK the class pk of this workflow instance link
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this workflow instance link.
	 *
	 * @param companyId the company ID of this workflow instance link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this workflow instance link.
	 *
	 * @param createDate the create date of this workflow instance link
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this workflow instance link.
	 *
	 * @param ctCollectionId the ct collection ID of this workflow instance link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this workflow instance link.
	 *
	 * @param groupId the group ID of this workflow instance link
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this workflow instance link.
	 *
	 * @param modifiedDate the modified date of this workflow instance link
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this workflow instance link.
	 *
	 * @param mvccVersion the mvcc version of this workflow instance link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this workflow instance link.
	 *
	 * @param primaryKey the primary key of this workflow instance link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this workflow instance link.
	 *
	 * @param userId the user ID of this workflow instance link
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this workflow instance link.
	 *
	 * @param userName the user name of this workflow instance link
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this workflow instance link.
	 *
	 * @param userUuid the user uuid of this workflow instance link
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the workflow instance ID of this workflow instance link.
	 *
	 * @param workflowInstanceId the workflow instance ID of this workflow instance link
	 */
	@Override
	public void setWorkflowInstanceId(long workflowInstanceId) {
		model.setWorkflowInstanceId(workflowInstanceId);
	}

	/**
	 * Sets the workflow instance link ID of this workflow instance link.
	 *
	 * @param workflowInstanceLinkId the workflow instance link ID of this workflow instance link
	 */
	@Override
	public void setWorkflowInstanceLinkId(long workflowInstanceLinkId) {
		model.setWorkflowInstanceLinkId(workflowInstanceLinkId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<WorkflowInstanceLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<WorkflowInstanceLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected WorkflowInstanceLinkWrapper wrap(
		WorkflowInstanceLink workflowInstanceLink) {

		return new WorkflowInstanceLinkWrapper(workflowInstanceLink);
	}

}