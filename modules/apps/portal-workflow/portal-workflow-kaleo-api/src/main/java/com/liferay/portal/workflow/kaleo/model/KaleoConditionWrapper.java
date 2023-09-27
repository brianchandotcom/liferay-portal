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
 * This class is a wrapper for {@link KaleoCondition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoCondition
 * @generated
 */
public class KaleoConditionWrapper
	extends BaseModelWrapper<KaleoCondition>
	implements KaleoCondition, ModelWrapper<KaleoCondition> {

	public KaleoConditionWrapper(KaleoCondition kaleoCondition) {
		super(kaleoCondition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoConditionId", getKaleoConditionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("script", getScript());
		attributes.put("scriptLanguage", getScriptLanguage());
		attributes.put("scriptRequiredContexts", getScriptRequiredContexts());

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

		Long kaleoConditionId = (Long)attributes.get("kaleoConditionId");

		if (kaleoConditionId != null) {
			setKaleoConditionId(kaleoConditionId);
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

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
		}

		Long kaleoDefinitionVersionId = (Long)attributes.get(
			"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
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
	}

	@Override
	public KaleoCondition cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this kaleo condition.
	 *
	 * @return the company ID of this kaleo condition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo condition.
	 *
	 * @return the create date of this kaleo condition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo condition.
	 *
	 * @return the ct collection ID of this kaleo condition
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this kaleo condition.
	 *
	 * @return the group ID of this kaleo condition
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo condition ID of this kaleo condition.
	 *
	 * @return the kaleo condition ID of this kaleo condition
	 */
	@Override
	public long getKaleoConditionId() {
		return model.getKaleoConditionId();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo condition.
	 *
	 * @return the kaleo definition ID of this kaleo condition
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo condition.
	 *
	 * @return the kaleo definition version ID of this kaleo condition
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo node ID of this kaleo condition.
	 *
	 * @return the kaleo node ID of this kaleo condition
	 */
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	/**
	 * Returns the modified date of this kaleo condition.
	 *
	 * @return the modified date of this kaleo condition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo condition.
	 *
	 * @return the mvcc version of this kaleo condition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this kaleo condition.
	 *
	 * @return the primary key of this kaleo condition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the script of this kaleo condition.
	 *
	 * @return the script of this kaleo condition
	 */
	@Override
	public String getScript() {
		return model.getScript();
	}

	/**
	 * Returns the script language of this kaleo condition.
	 *
	 * @return the script language of this kaleo condition
	 */
	@Override
	public String getScriptLanguage() {
		return model.getScriptLanguage();
	}

	/**
	 * Returns the script required contexts of this kaleo condition.
	 *
	 * @return the script required contexts of this kaleo condition
	 */
	@Override
	public String getScriptRequiredContexts() {
		return model.getScriptRequiredContexts();
	}

	/**
	 * Returns the user ID of this kaleo condition.
	 *
	 * @return the user ID of this kaleo condition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo condition.
	 *
	 * @return the user name of this kaleo condition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo condition.
	 *
	 * @return the user uuid of this kaleo condition
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
	 * Sets the company ID of this kaleo condition.
	 *
	 * @param companyId the company ID of this kaleo condition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo condition.
	 *
	 * @param createDate the create date of this kaleo condition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo condition.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo condition
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this kaleo condition.
	 *
	 * @param groupId the group ID of this kaleo condition
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo condition ID of this kaleo condition.
	 *
	 * @param kaleoConditionId the kaleo condition ID of this kaleo condition
	 */
	@Override
	public void setKaleoConditionId(long kaleoConditionId) {
		model.setKaleoConditionId(kaleoConditionId);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo condition.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo condition
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo condition.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo condition
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo node ID of this kaleo condition.
	 *
	 * @param kaleoNodeId the kaleo node ID of this kaleo condition
	 */
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Sets the modified date of this kaleo condition.
	 *
	 * @param modifiedDate the modified date of this kaleo condition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo condition.
	 *
	 * @param mvccVersion the mvcc version of this kaleo condition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this kaleo condition.
	 *
	 * @param primaryKey the primary key of this kaleo condition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the script of this kaleo condition.
	 *
	 * @param script the script of this kaleo condition
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
	}

	/**
	 * Sets the script language of this kaleo condition.
	 *
	 * @param scriptLanguage the script language of this kaleo condition
	 */
	@Override
	public void setScriptLanguage(String scriptLanguage) {
		model.setScriptLanguage(scriptLanguage);
	}

	/**
	 * Sets the script required contexts of this kaleo condition.
	 *
	 * @param scriptRequiredContexts the script required contexts of this kaleo condition
	 */
	@Override
	public void setScriptRequiredContexts(String scriptRequiredContexts) {
		model.setScriptRequiredContexts(scriptRequiredContexts);
	}

	/**
	 * Sets the user ID of this kaleo condition.
	 *
	 * @param userId the user ID of this kaleo condition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo condition.
	 *
	 * @param userName the user name of this kaleo condition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo condition.
	 *
	 * @param userUuid the user uuid of this kaleo condition
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
	public Map<String, Function<KaleoCondition, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoCondition, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoConditionWrapper wrap(KaleoCondition kaleoCondition) {
		return new KaleoConditionWrapper(kaleoCondition);
	}

}