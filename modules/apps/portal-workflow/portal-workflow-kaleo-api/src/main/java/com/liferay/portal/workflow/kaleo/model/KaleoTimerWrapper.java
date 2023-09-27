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
 * This class is a wrapper for {@link KaleoTimer}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTimer
 * @generated
 */
public class KaleoTimerWrapper
	extends BaseModelWrapper<KaleoTimer>
	implements KaleoTimer, ModelWrapper<KaleoTimer> {

	public KaleoTimerWrapper(KaleoTimer kaleoTimer) {
		super(kaleoTimer);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoTimerId", getKaleoTimerId());
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
		attributes.put("name", getName());
		attributes.put("blocking", isBlocking());
		attributes.put("description", getDescription());
		attributes.put("duration", getDuration());
		attributes.put("scale", getScale());
		attributes.put("recurrenceDuration", getRecurrenceDuration());
		attributes.put("recurrenceScale", getRecurrenceScale());

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

		Long kaleoTimerId = (Long)attributes.get("kaleoTimerId");

		if (kaleoTimerId != null) {
			setKaleoTimerId(kaleoTimerId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean blocking = (Boolean)attributes.get("blocking");

		if (blocking != null) {
			setBlocking(blocking);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Double duration = (Double)attributes.get("duration");

		if (duration != null) {
			setDuration(duration);
		}

		String scale = (String)attributes.get("scale");

		if (scale != null) {
			setScale(scale);
		}

		Double recurrenceDuration = (Double)attributes.get(
			"recurrenceDuration");

		if (recurrenceDuration != null) {
			setRecurrenceDuration(recurrenceDuration);
		}

		String recurrenceScale = (String)attributes.get("recurrenceScale");

		if (recurrenceScale != null) {
			setRecurrenceScale(recurrenceScale);
		}
	}

	@Override
	public KaleoTimer cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the blocking of this kaleo timer.
	 *
	 * @return the blocking of this kaleo timer
	 */
	@Override
	public boolean getBlocking() {
		return model.getBlocking();
	}

	/**
	 * Returns the company ID of this kaleo timer.
	 *
	 * @return the company ID of this kaleo timer
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo timer.
	 *
	 * @return the create date of this kaleo timer
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo timer.
	 *
	 * @return the ct collection ID of this kaleo timer
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this kaleo timer.
	 *
	 * @return the description of this kaleo timer
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the duration of this kaleo timer.
	 *
	 * @return the duration of this kaleo timer
	 */
	@Override
	public double getDuration() {
		return model.getDuration();
	}

	/**
	 * Returns the group ID of this kaleo timer.
	 *
	 * @return the group ID of this kaleo timer
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo class name of this kaleo timer.
	 *
	 * @return the kaleo class name of this kaleo timer
	 */
	@Override
	public String getKaleoClassName() {
		return model.getKaleoClassName();
	}

	/**
	 * Returns the kaleo class pk of this kaleo timer.
	 *
	 * @return the kaleo class pk of this kaleo timer
	 */
	@Override
	public long getKaleoClassPK() {
		return model.getKaleoClassPK();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo timer.
	 *
	 * @return the kaleo definition ID of this kaleo timer
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo timer.
	 *
	 * @return the kaleo definition version ID of this kaleo timer
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	@Override
	public java.util.List<KaleoTaskAssignment> getKaleoTaskReassignments() {
		return model.getKaleoTaskReassignments();
	}

	/**
	 * Returns the kaleo timer ID of this kaleo timer.
	 *
	 * @return the kaleo timer ID of this kaleo timer
	 */
	@Override
	public long getKaleoTimerId() {
		return model.getKaleoTimerId();
	}

	/**
	 * Returns the modified date of this kaleo timer.
	 *
	 * @return the modified date of this kaleo timer
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo timer.
	 *
	 * @return the mvcc version of this kaleo timer
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo timer.
	 *
	 * @return the name of this kaleo timer
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this kaleo timer.
	 *
	 * @return the primary key of this kaleo timer
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the recurrence duration of this kaleo timer.
	 *
	 * @return the recurrence duration of this kaleo timer
	 */
	@Override
	public double getRecurrenceDuration() {
		return model.getRecurrenceDuration();
	}

	/**
	 * Returns the recurrence scale of this kaleo timer.
	 *
	 * @return the recurrence scale of this kaleo timer
	 */
	@Override
	public String getRecurrenceScale() {
		return model.getRecurrenceScale();
	}

	/**
	 * Returns the scale of this kaleo timer.
	 *
	 * @return the scale of this kaleo timer
	 */
	@Override
	public String getScale() {
		return model.getScale();
	}

	/**
	 * Returns the user ID of this kaleo timer.
	 *
	 * @return the user ID of this kaleo timer
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo timer.
	 *
	 * @return the user name of this kaleo timer
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo timer.
	 *
	 * @return the user uuid of this kaleo timer
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this kaleo timer is blocking.
	 *
	 * @return <code>true</code> if this kaleo timer is blocking; <code>false</code> otherwise
	 */
	@Override
	public boolean isBlocking() {
		return model.isBlocking();
	}

	@Override
	public boolean isRecurring() {
		return model.isRecurring();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this kaleo timer is blocking.
	 *
	 * @param blocking the blocking of this kaleo timer
	 */
	@Override
	public void setBlocking(boolean blocking) {
		model.setBlocking(blocking);
	}

	/**
	 * Sets the company ID of this kaleo timer.
	 *
	 * @param companyId the company ID of this kaleo timer
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo timer.
	 *
	 * @param createDate the create date of this kaleo timer
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo timer.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo timer
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this kaleo timer.
	 *
	 * @param description the description of this kaleo timer
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the duration of this kaleo timer.
	 *
	 * @param duration the duration of this kaleo timer
	 */
	@Override
	public void setDuration(double duration) {
		model.setDuration(duration);
	}

	/**
	 * Sets the group ID of this kaleo timer.
	 *
	 * @param groupId the group ID of this kaleo timer
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo class name of this kaleo timer.
	 *
	 * @param kaleoClassName the kaleo class name of this kaleo timer
	 */
	@Override
	public void setKaleoClassName(String kaleoClassName) {
		model.setKaleoClassName(kaleoClassName);
	}

	/**
	 * Sets the kaleo class pk of this kaleo timer.
	 *
	 * @param kaleoClassPK the kaleo class pk of this kaleo timer
	 */
	@Override
	public void setKaleoClassPK(long kaleoClassPK) {
		model.setKaleoClassPK(kaleoClassPK);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo timer.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo timer
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo timer.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo timer
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo timer ID of this kaleo timer.
	 *
	 * @param kaleoTimerId the kaleo timer ID of this kaleo timer
	 */
	@Override
	public void setKaleoTimerId(long kaleoTimerId) {
		model.setKaleoTimerId(kaleoTimerId);
	}

	/**
	 * Sets the modified date of this kaleo timer.
	 *
	 * @param modifiedDate the modified date of this kaleo timer
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo timer.
	 *
	 * @param mvccVersion the mvcc version of this kaleo timer
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo timer.
	 *
	 * @param name the name of this kaleo timer
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this kaleo timer.
	 *
	 * @param primaryKey the primary key of this kaleo timer
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the recurrence duration of this kaleo timer.
	 *
	 * @param recurrenceDuration the recurrence duration of this kaleo timer
	 */
	@Override
	public void setRecurrenceDuration(double recurrenceDuration) {
		model.setRecurrenceDuration(recurrenceDuration);
	}

	/**
	 * Sets the recurrence scale of this kaleo timer.
	 *
	 * @param recurrenceScale the recurrence scale of this kaleo timer
	 */
	@Override
	public void setRecurrenceScale(String recurrenceScale) {
		model.setRecurrenceScale(recurrenceScale);
	}

	/**
	 * Sets the scale of this kaleo timer.
	 *
	 * @param scale the scale of this kaleo timer
	 */
	@Override
	public void setScale(String scale) {
		model.setScale(scale);
	}

	/**
	 * Sets the user ID of this kaleo timer.
	 *
	 * @param userId the user ID of this kaleo timer
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo timer.
	 *
	 * @param userName the user name of this kaleo timer
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo timer.
	 *
	 * @param userUuid the user uuid of this kaleo timer
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
	public Map<String, Function<KaleoTimer, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoTimer, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoTimerWrapper wrap(KaleoTimer kaleoTimer) {
		return new KaleoTimerWrapper(kaleoTimer);
	}

}