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

package com.liferay.portal.workflow.kaleo.forms.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link KaleoProcess}.
 * </p>
 *
 * @author Marcellus Tavares
 * @see KaleoProcess
 * @generated
 */
@ProviderType
public class KaleoProcessWrapper extends BaseModelWrapper<KaleoProcess>
	implements KaleoProcess, ModelWrapper<KaleoProcess> {
	public KaleoProcessWrapper(KaleoProcess kaleoProcess) {
		super(kaleoProcess);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("kaleoProcessId", getKaleoProcessId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("DDLRecordSetId", getDDLRecordSetId());
		attributes.put("DDMTemplateId", getDDMTemplateId());
		attributes.put("workflowDefinitionName", getWorkflowDefinitionName());
		attributes.put("workflowDefinitionVersion",
			getWorkflowDefinitionVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long kaleoProcessId = (Long)attributes.get("kaleoProcessId");

		if (kaleoProcessId != null) {
			setKaleoProcessId(kaleoProcessId);
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

		Long DDLRecordSetId = (Long)attributes.get("DDLRecordSetId");

		if (DDLRecordSetId != null) {
			setDDLRecordSetId(DDLRecordSetId);
		}

		Long DDMTemplateId = (Long)attributes.get("DDMTemplateId");

		if (DDMTemplateId != null) {
			setDDMTemplateId(DDMTemplateId);
		}

		String workflowDefinitionName = (String)attributes.get(
				"workflowDefinitionName");

		if (workflowDefinitionName != null) {
			setWorkflowDefinitionName(workflowDefinitionName);
		}

		Integer workflowDefinitionVersion = (Integer)attributes.get(
				"workflowDefinitionVersion");

		if (workflowDefinitionVersion != null) {
			setWorkflowDefinitionVersion(workflowDefinitionVersion);
		}
	}

	/**
	* Returns the company ID of this kaleo process.
	*
	* @return the company ID of this kaleo process
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this kaleo process.
	*
	* @return the create date of this kaleo process
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.lists.model.DDLRecordSet getDDLRecordSet()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDDLRecordSet();
	}

	/**
	* Returns the ddl record set ID of this kaleo process.
	*
	* @return the ddl record set ID of this kaleo process
	*/
	@Override
	public long getDDLRecordSetId() {
		return model.getDDLRecordSetId();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMTemplate getDDMTemplate()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDDMTemplate();
	}

	/**
	* Returns the ddm template ID of this kaleo process.
	*
	* @return the ddm template ID of this kaleo process
	*/
	@Override
	public long getDDMTemplateId() {
		return model.getDDMTemplateId();
	}

	@Override
	public String getDescription()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDescription();
	}

	@Override
	public String getDescription(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getDescription(locale);
	}

	/**
	* Returns the group ID of this kaleo process.
	*
	* @return the group ID of this kaleo process
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the kaleo process ID of this kaleo process.
	*
	* @return the kaleo process ID of this kaleo process
	*/
	@Override
	public long getKaleoProcessId() {
		return model.getKaleoProcessId();
	}

	@Override
	public java.util.List<KaleoProcessLink> getKaleoProcessLinks() {
		return model.getKaleoProcessLinks();
	}

	/**
	* Returns the modified date of this kaleo process.
	*
	* @return the modified date of this kaleo process
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	@Override
	public String getName()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getName();
	}

	@Override
	public String getName(java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getName(locale);
	}

	/**
	* Returns the primary key of this kaleo process.
	*
	* @return the primary key of this kaleo process
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this kaleo process.
	*
	* @return the user ID of this kaleo process
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this kaleo process.
	*
	* @return the user name of this kaleo process
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this kaleo process.
	*
	* @return the user uuid of this kaleo process
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this kaleo process.
	*
	* @return the uuid of this kaleo process
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public String getWorkflowDefinition() {
		return model.getWorkflowDefinition();
	}

	/**
	* Returns the workflow definition name of this kaleo process.
	*
	* @return the workflow definition name of this kaleo process
	*/
	@Override
	public String getWorkflowDefinitionName() {
		return model.getWorkflowDefinitionName();
	}

	/**
	* Returns the workflow definition version of this kaleo process.
	*
	* @return the workflow definition version of this kaleo process
	*/
	@Override
	public int getWorkflowDefinitionVersion() {
		return model.getWorkflowDefinitionVersion();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this kaleo process.
	*
	* @param companyId the company ID of this kaleo process
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this kaleo process.
	*
	* @param createDate the create date of this kaleo process
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the ddl record set ID of this kaleo process.
	*
	* @param DDLRecordSetId the ddl record set ID of this kaleo process
	*/
	@Override
	public void setDDLRecordSetId(long DDLRecordSetId) {
		model.setDDLRecordSetId(DDLRecordSetId);
	}

	/**
	* Sets the ddm template ID of this kaleo process.
	*
	* @param DDMTemplateId the ddm template ID of this kaleo process
	*/
	@Override
	public void setDDMTemplateId(long DDMTemplateId) {
		model.setDDMTemplateId(DDMTemplateId);
	}

	/**
	* Sets the group ID of this kaleo process.
	*
	* @param groupId the group ID of this kaleo process
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the kaleo process ID of this kaleo process.
	*
	* @param kaleoProcessId the kaleo process ID of this kaleo process
	*/
	@Override
	public void setKaleoProcessId(long kaleoProcessId) {
		model.setKaleoProcessId(kaleoProcessId);
	}

	/**
	* Sets the modified date of this kaleo process.
	*
	* @param modifiedDate the modified date of this kaleo process
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this kaleo process.
	*
	* @param primaryKey the primary key of this kaleo process
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this kaleo process.
	*
	* @param userId the user ID of this kaleo process
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this kaleo process.
	*
	* @param userName the user name of this kaleo process
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this kaleo process.
	*
	* @param userUuid the user uuid of this kaleo process
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this kaleo process.
	*
	* @param uuid the uuid of this kaleo process
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	* Sets the workflow definition name of this kaleo process.
	*
	* @param workflowDefinitionName the workflow definition name of this kaleo process
	*/
	@Override
	public void setWorkflowDefinitionName(String workflowDefinitionName) {
		model.setWorkflowDefinitionName(workflowDefinitionName);
	}

	/**
	* Sets the workflow definition version of this kaleo process.
	*
	* @param workflowDefinitionVersion the workflow definition version of this kaleo process
	*/
	@Override
	public void setWorkflowDefinitionVersion(int workflowDefinitionVersion) {
		model.setWorkflowDefinitionVersion(workflowDefinitionVersion);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected KaleoProcessWrapper wrap(KaleoProcess kaleoProcess) {
		return new KaleoProcessWrapper(kaleoProcess);
	}
}