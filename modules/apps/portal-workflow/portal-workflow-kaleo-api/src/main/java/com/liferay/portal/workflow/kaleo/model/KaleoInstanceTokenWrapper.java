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
 * This class is a wrapper for {@link KaleoInstanceToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoInstanceToken
 * @generated
 */
public class KaleoInstanceTokenWrapper
	extends BaseModelWrapper<KaleoInstanceToken>
	implements KaleoInstanceToken, ModelWrapper<KaleoInstanceToken> {

	public KaleoInstanceTokenWrapper(KaleoInstanceToken kaleoInstanceToken) {
		super(kaleoInstanceToken);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoInstanceTokenId", getKaleoInstanceTokenId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoInstanceId", getKaleoInstanceId());
		attributes.put(
			"parentKaleoInstanceTokenId", getParentKaleoInstanceTokenId());
		attributes.put("currentKaleoNodeId", getCurrentKaleoNodeId());
		attributes.put("currentKaleoNodeName", getCurrentKaleoNodeName());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());
		attributes.put("completed", isCompleted());
		attributes.put("completionDate", getCompletionDate());

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

		Long kaleoInstanceTokenId = (Long)attributes.get(
			"kaleoInstanceTokenId");

		if (kaleoInstanceTokenId != null) {
			setKaleoInstanceTokenId(kaleoInstanceTokenId);
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

		Long kaleoInstanceId = (Long)attributes.get("kaleoInstanceId");

		if (kaleoInstanceId != null) {
			setKaleoInstanceId(kaleoInstanceId);
		}

		Long parentKaleoInstanceTokenId = (Long)attributes.get(
			"parentKaleoInstanceTokenId");

		if (parentKaleoInstanceTokenId != null) {
			setParentKaleoInstanceTokenId(parentKaleoInstanceTokenId);
		}

		Long currentKaleoNodeId = (Long)attributes.get("currentKaleoNodeId");

		if (currentKaleoNodeId != null) {
			setCurrentKaleoNodeId(currentKaleoNodeId);
		}

		String currentKaleoNodeName = (String)attributes.get(
			"currentKaleoNodeName");

		if (currentKaleoNodeName != null) {
			setCurrentKaleoNodeName(currentKaleoNodeName);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Boolean completed = (Boolean)attributes.get("completed");

		if (completed != null) {
			setCompleted(completed);
		}

		Date completionDate = (Date)attributes.get("completionDate");

		if (completionDate != null) {
			setCompletionDate(completionDate);
		}
	}

	@Override
	public KaleoInstanceToken cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public java.util.List<KaleoInstanceToken> getChildrenKaleoInstanceTokens() {
		return model.getChildrenKaleoInstanceTokens();
	}

	/**
	 * Returns the class name of this kaleo instance token.
	 *
	 * @return the class name of this kaleo instance token
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class pk of this kaleo instance token.
	 *
	 * @return the class pk of this kaleo instance token
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this kaleo instance token.
	 *
	 * @return the company ID of this kaleo instance token
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the completed of this kaleo instance token.
	 *
	 * @return the completed of this kaleo instance token
	 */
	@Override
	public boolean getCompleted() {
		return model.getCompleted();
	}

	/**
	 * Returns the completion date of this kaleo instance token.
	 *
	 * @return the completion date of this kaleo instance token
	 */
	@Override
	public Date getCompletionDate() {
		return model.getCompletionDate();
	}

	/**
	 * Returns the create date of this kaleo instance token.
	 *
	 * @return the create date of this kaleo instance token
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo instance token.
	 *
	 * @return the ct collection ID of this kaleo instance token
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public KaleoNode getCurrentKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCurrentKaleoNode();
	}

	/**
	 * Returns the current kaleo node ID of this kaleo instance token.
	 *
	 * @return the current kaleo node ID of this kaleo instance token
	 */
	@Override
	public long getCurrentKaleoNodeId() {
		return model.getCurrentKaleoNodeId();
	}

	/**
	 * Returns the current kaleo node name of this kaleo instance token.
	 *
	 * @return the current kaleo node name of this kaleo instance token
	 */
	@Override
	public String getCurrentKaleoNodeName() {
		return model.getCurrentKaleoNodeName();
	}

	/**
	 * Returns the group ID of this kaleo instance token.
	 *
	 * @return the group ID of this kaleo instance token
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public java.util.List<KaleoInstanceToken>
		getIncompleteChildrenKaleoInstanceTokens() {

		return model.getIncompleteChildrenKaleoInstanceTokens();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo instance token.
	 *
	 * @return the kaleo definition ID of this kaleo instance token
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo instance token.
	 *
	 * @return the kaleo definition version ID of this kaleo instance token
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	@Override
	public KaleoInstance getKaleoInstance()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getKaleoInstance();
	}

	/**
	 * Returns the kaleo instance ID of this kaleo instance token.
	 *
	 * @return the kaleo instance ID of this kaleo instance token
	 */
	@Override
	public long getKaleoInstanceId() {
		return model.getKaleoInstanceId();
	}

	/**
	 * Returns the kaleo instance token ID of this kaleo instance token.
	 *
	 * @return the kaleo instance token ID of this kaleo instance token
	 */
	@Override
	public long getKaleoInstanceTokenId() {
		return model.getKaleoInstanceTokenId();
	}

	/**
	 * Returns the modified date of this kaleo instance token.
	 *
	 * @return the modified date of this kaleo instance token
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo instance token.
	 *
	 * @return the mvcc version of this kaleo instance token
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	@Override
	public KaleoInstanceToken getParentKaleoInstanceToken()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getParentKaleoInstanceToken();
	}

	/**
	 * Returns the parent kaleo instance token ID of this kaleo instance token.
	 *
	 * @return the parent kaleo instance token ID of this kaleo instance token
	 */
	@Override
	public long getParentKaleoInstanceTokenId() {
		return model.getParentKaleoInstanceTokenId();
	}

	/**
	 * Returns the primary key of this kaleo instance token.
	 *
	 * @return the primary key of this kaleo instance token
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this kaleo instance token.
	 *
	 * @return the user ID of this kaleo instance token
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo instance token.
	 *
	 * @return the user name of this kaleo instance token
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo instance token.
	 *
	 * @return the user uuid of this kaleo instance token
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean hasIncompleteChildrenKaleoInstanceToken() {
		return model.hasIncompleteChildrenKaleoInstanceToken();
	}

	/**
	 * Returns <code>true</code> if this kaleo instance token is completed.
	 *
	 * @return <code>true</code> if this kaleo instance token is completed; <code>false</code> otherwise
	 */
	@Override
	public boolean isCompleted() {
		return model.isCompleted();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the class name of this kaleo instance token.
	 *
	 * @param className the class name of this kaleo instance token
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class pk of this kaleo instance token.
	 *
	 * @param classPK the class pk of this kaleo instance token
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this kaleo instance token.
	 *
	 * @param companyId the company ID of this kaleo instance token
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets whether this kaleo instance token is completed.
	 *
	 * @param completed the completed of this kaleo instance token
	 */
	@Override
	public void setCompleted(boolean completed) {
		model.setCompleted(completed);
	}

	/**
	 * Sets the completion date of this kaleo instance token.
	 *
	 * @param completionDate the completion date of this kaleo instance token
	 */
	@Override
	public void setCompletionDate(Date completionDate) {
		model.setCompletionDate(completionDate);
	}

	/**
	 * Sets the create date of this kaleo instance token.
	 *
	 * @param createDate the create date of this kaleo instance token
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo instance token.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo instance token
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	@Override
	public void setCurrentKaleoNode(KaleoNode kaleoNode)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.setCurrentKaleoNode(kaleoNode);
	}

	/**
	 * Sets the current kaleo node ID of this kaleo instance token.
	 *
	 * @param currentKaleoNodeId the current kaleo node ID of this kaleo instance token
	 */
	@Override
	public void setCurrentKaleoNodeId(long currentKaleoNodeId) {
		model.setCurrentKaleoNodeId(currentKaleoNodeId);
	}

	/**
	 * Sets the current kaleo node name of this kaleo instance token.
	 *
	 * @param currentKaleoNodeName the current kaleo node name of this kaleo instance token
	 */
	@Override
	public void setCurrentKaleoNodeName(String currentKaleoNodeName) {
		model.setCurrentKaleoNodeName(currentKaleoNodeName);
	}

	/**
	 * Sets the group ID of this kaleo instance token.
	 *
	 * @param groupId the group ID of this kaleo instance token
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo instance token.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo instance token
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo instance token.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo instance token
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo instance ID of this kaleo instance token.
	 *
	 * @param kaleoInstanceId the kaleo instance ID of this kaleo instance token
	 */
	@Override
	public void setKaleoInstanceId(long kaleoInstanceId) {
		model.setKaleoInstanceId(kaleoInstanceId);
	}

	/**
	 * Sets the kaleo instance token ID of this kaleo instance token.
	 *
	 * @param kaleoInstanceTokenId the kaleo instance token ID of this kaleo instance token
	 */
	@Override
	public void setKaleoInstanceTokenId(long kaleoInstanceTokenId) {
		model.setKaleoInstanceTokenId(kaleoInstanceTokenId);
	}

	/**
	 * Sets the modified date of this kaleo instance token.
	 *
	 * @param modifiedDate the modified date of this kaleo instance token
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo instance token.
	 *
	 * @param mvccVersion the mvcc version of this kaleo instance token
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the parent kaleo instance token ID of this kaleo instance token.
	 *
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID of this kaleo instance token
	 */
	@Override
	public void setParentKaleoInstanceTokenId(long parentKaleoInstanceTokenId) {
		model.setParentKaleoInstanceTokenId(parentKaleoInstanceTokenId);
	}

	/**
	 * Sets the primary key of this kaleo instance token.
	 *
	 * @param primaryKey the primary key of this kaleo instance token
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this kaleo instance token.
	 *
	 * @param userId the user ID of this kaleo instance token
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo instance token.
	 *
	 * @param userName the user name of this kaleo instance token
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo instance token.
	 *
	 * @param userUuid the user uuid of this kaleo instance token
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
	public Map<String, Function<KaleoInstanceToken, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoInstanceToken, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoInstanceTokenWrapper wrap(
		KaleoInstanceToken kaleoInstanceToken) {

		return new KaleoInstanceTokenWrapper(kaleoInstanceToken);
	}

}