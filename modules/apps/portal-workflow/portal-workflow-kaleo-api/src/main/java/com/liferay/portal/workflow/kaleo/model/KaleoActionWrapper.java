/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link KaleoAction}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoAction
 * @generated
 */
public class KaleoActionWrapper
	extends BaseModelWrapper<KaleoAction>
	implements KaleoAction, ModelWrapper<KaleoAction> {

	public KaleoActionWrapper(KaleoAction kaleoAction) {
		super(kaleoAction);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoActionId", getKaleoActionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoClassName", getKaleoClassName());
		attributes.put("kaleoClassPK", getKaleoClassPK());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNodeName", getKaleoNodeName());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("executionType", getExecutionType());
		attributes.put("script", getScript());
		attributes.put("scriptLanguage", getScriptLanguage());
		attributes.put("scriptRequiredContexts", getScriptRequiredContexts());
		attributes.put("priority", getPriority());
		attributes.put("type", getType());
		attributes.put("status", getStatus());

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

		Long kaleoActionId = (Long)attributes.get("kaleoActionId");

		if (kaleoActionId != null) {
			setKaleoActionId(kaleoActionId);
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

		String kaleoClassName = (String)attributes.get("kaleoClassName");

		if (kaleoClassName != null) {
			setKaleoClassName(kaleoClassName);
		}

		Long kaleoClassPK = (Long)attributes.get("kaleoClassPK");

		if (kaleoClassPK != null) {
			setKaleoClassPK(kaleoClassPK);
		}

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
		}

		Long kaleoDefinitionVersionId = (Long)attributes.get(
			"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		String kaleoNodeName = (String)attributes.get("kaleoNodeName");

		if (kaleoNodeName != null) {
			setKaleoNodeName(kaleoNodeName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String executionType = (String)attributes.get("executionType");

		if (executionType != null) {
			setExecutionType(executionType);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}

		String scriptLanguage = (String)attributes.get("scriptLanguage");

		if (scriptLanguage != null) {
			setScriptLanguage(scriptLanguage);
		}

		String scriptRequiredContexts = (String)attributes.get(
			"scriptRequiredContexts");

		if (scriptRequiredContexts != null) {
			setScriptRequiredContexts(scriptRequiredContexts);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public KaleoAction cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this kaleo action.
	 *
	 * @return the company ID of this kaleo action
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo action.
	 *
	 * @return the create date of this kaleo action
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo action.
	 *
	 * @return the ct collection ID of this kaleo action
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this kaleo action.
	 *
	 * @return the description of this kaleo action
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the execution type of this kaleo action.
	 *
	 * @return the execution type of this kaleo action
	 */
	@Override
	public String getExecutionType() {
		return model.getExecutionType();
	}

	/**
	 * Returns the group ID of this kaleo action.
	 *
	 * @return the group ID of this kaleo action
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo action ID of this kaleo action.
	 *
	 * @return the kaleo action ID of this kaleo action
	 */
	@Override
	public long getKaleoActionId() {
		return model.getKaleoActionId();
	}

	/**
	 * Returns the kaleo class name of this kaleo action.
	 *
	 * @return the kaleo class name of this kaleo action
	 */
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	 * Returns the kaleo class pk of this kaleo action.
	 *
	 * @return the kaleo class pk of this kaleo action
	 */
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo action.
	 *
	 * @return the kaleo definition ID of this kaleo action
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo action.
	 *
	 * @return the kaleo definition version ID of this kaleo action
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo node name of this kaleo action.
	 *
	 * @return the kaleo node name of this kaleo action
	 */
	@Override
	public String getKaleoNodeName() {
		return model.getKaleoNodeName();
	}

	/**
	 * Returns the modified date of this kaleo action.
	 *
	 * @return the modified date of this kaleo action
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo action.
	 *
	 * @return the mvcc version of this kaleo action
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo action.
	 *
	 * @return the name of this kaleo action
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this kaleo action.
	 *
	 * @return the primary key of this kaleo action
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this kaleo action.
	 *
	 * @return the priority of this kaleo action
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the script of this kaleo action.
	 *
	 * @return the script of this kaleo action
	 */
	@Override
	public String getScript() {
		return model.getScript();
	}

	/**
	 * Returns the script language of this kaleo action.
	 *
	 * @return the script language of this kaleo action
	 */
	@Override
	public String getScriptLanguage() {
		return model.getScriptLanguage();
	}

	/**
	 * Returns the script required contexts of this kaleo action.
	 *
	 * @return the script required contexts of this kaleo action
	 */
	@Override
	public String getScriptRequiredContexts() {
		return model.getScriptRequiredContexts();
	}

	/**
	 * Returns the status of this kaleo action.
	 *
	 * @return the status of this kaleo action
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the type of this kaleo action.
	 *
	 * @return the type of this kaleo action
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this kaleo action.
	 *
	 * @return the user ID of this kaleo action
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo action.
	 *
	 * @return the user name of this kaleo action
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo action.
	 *
	 * @return the user uuid of this kaleo action
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
	 * Sets the company ID of this kaleo action.
	 *
	 * @param companyId the company ID of this kaleo action
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo action.
	 *
	 * @param createDate the create date of this kaleo action
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo action.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo action
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this kaleo action.
	 *
	 * @param description the description of this kaleo action
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the execution type of this kaleo action.
	 *
	 * @param executionType the execution type of this kaleo action
	 */
	@Override
	public void setExecutionType(String executionType) {
		model.setExecutionType(executionType);
	}

	/**
	 * Sets the group ID of this kaleo action.
	 *
	 * @param groupId the group ID of this kaleo action
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo action ID of this kaleo action.
	 *
	 * @param kaleoActionId the kaleo action ID of this kaleo action
	 */
	@Override
	public void setKaleoActionId(long kaleoActionId) {
		model.setKaleoActionId(kaleoActionId);
	}

	/**
	 * Sets the kaleo class name of this kaleo action.
	 *
	 * @param kaleoClassName the kaleo class name of this kaleo action
	 */
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	 * Sets the kaleo class pk of this kaleo action.
	 *
	 * @param kaleoClassPK the kaleo class pk of this kaleo action
	 */
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo action.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo action
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo action.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo action
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo node name of this kaleo action.
	 *
	 * @param kaleoNodeName the kaleo node name of this kaleo action
	 */
	@Override
	public void setKaleoNodeName(String kaleoNodeName) {
		model.setKaleoNodeName(kaleoNodeName);
	}

	/**
	 * Sets the modified date of this kaleo action.
	 *
	 * @param modifiedDate the modified date of this kaleo action
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo action.
	 *
	 * @param mvccVersion the mvcc version of this kaleo action
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo action.
	 *
	 * @param name the name of this kaleo action
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this kaleo action.
	 *
	 * @param primaryKey the primary key of this kaleo action
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this kaleo action.
	 *
	 * @param priority the priority of this kaleo action
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the script of this kaleo action.
	 *
	 * @param script the script of this kaleo action
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
	}

	/**
	 * Sets the script language of this kaleo action.
	 *
	 * @param scriptLanguage the script language of this kaleo action
	 */
	@Override
	public void setScriptLanguage(String scriptLanguage) {
		model.setScriptLanguage(scriptLanguage);
	}

	/**
	 * Sets the script required contexts of this kaleo action.
	 *
	 * @param scriptRequiredContexts the script required contexts of this kaleo action
	 */
	@Override
	public void setScriptRequiredContexts(String scriptRequiredContexts) {
		model.setScriptRequiredContexts(scriptRequiredContexts);
	}

	/**
	 * Sets the status of this kaleo action.
	 *
	 * @param status the status of this kaleo action
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the type of this kaleo action.
	 *
	 * @param type the type of this kaleo action
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this kaleo action.
	 *
	 * @param userId the user ID of this kaleo action
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo action.
	 *
	 * @param userName the user name of this kaleo action
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo action.
	 *
	 * @param userUuid the user uuid of this kaleo action
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
	public Map<String, Function<KaleoAction, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoAction, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoActionWrapper wrap(KaleoAction kaleoAction) {
		return new KaleoActionWrapper(kaleoAction);
	}

}