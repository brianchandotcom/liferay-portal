/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
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
 * This class is a wrapper for {@link KaleoNodeSetting}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNodeSetting
 * @generated
 */
public class KaleoNodeSettingWrapper
	extends BaseModelWrapper<KaleoNodeSetting>
	implements KaleoNodeSetting, ModelWrapper<KaleoNodeSetting> {

	public KaleoNodeSettingWrapper(KaleoNodeSetting kaleoNodeSetting) {
		super(kaleoNodeSetting);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoNodeSettingId", getKaleoNodeSettingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("name", getName());
		attributes.put("value", getValue());

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

		Long kaleoNodeSettingId = (Long)attributes.get("kaleoNodeSettingId");

		if (kaleoNodeSettingId != null) {
			setKaleoNodeSettingId(kaleoNodeSettingId);
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

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public KaleoNodeSetting cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this kaleo node setting.
	 *
	 * @return the company ID of this kaleo node setting
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo node setting.
	 *
	 * @return the create date of this kaleo node setting
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo node setting.
	 *
	 * @return the ct collection ID of this kaleo node setting
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the kaleo node ID of this kaleo node setting.
	 *
	 * @return the kaleo node ID of this kaleo node setting
	 */
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	/**
	 * Returns the kaleo node setting ID of this kaleo node setting.
	 *
	 * @return the kaleo node setting ID of this kaleo node setting
	 */
	@Override
	public long getKaleoNodeSettingId() {
		return model.getKaleoNodeSettingId();
	}

	/**
	 * Returns the modified date of this kaleo node setting.
	 *
	 * @return the modified date of this kaleo node setting
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo node setting.
	 *
	 * @return the mvcc version of this kaleo node setting
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo node setting.
	 *
	 * @return the name of this kaleo node setting
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this kaleo node setting.
	 *
	 * @return the primary key of this kaleo node setting
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this kaleo node setting.
	 *
	 * @return the user ID of this kaleo node setting
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo node setting.
	 *
	 * @return the user name of this kaleo node setting
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo node setting.
	 *
	 * @return the user uuid of this kaleo node setting
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this kaleo node setting.
	 *
	 * @return the value of this kaleo node setting
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this kaleo node setting.
	 *
	 * @param companyId the company ID of this kaleo node setting
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo node setting.
	 *
	 * @param createDate the create date of this kaleo node setting
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo node setting.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo node setting
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the kaleo node ID of this kaleo node setting.
	 *
	 * @param kaleoNodeId the kaleo node ID of this kaleo node setting
	 */
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Sets the kaleo node setting ID of this kaleo node setting.
	 *
	 * @param kaleoNodeSettingId the kaleo node setting ID of this kaleo node setting
	 */
	@Override
	public void setKaleoNodeSettingId(long kaleoNodeSettingId) {
		model.setKaleoNodeSettingId(kaleoNodeSettingId);
	}

	/**
	 * Sets the modified date of this kaleo node setting.
	 *
	 * @param modifiedDate the modified date of this kaleo node setting
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo node setting.
	 *
	 * @param mvccVersion the mvcc version of this kaleo node setting
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo node setting.
	 *
	 * @param name the name of this kaleo node setting
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this kaleo node setting.
	 *
	 * @param primaryKey the primary key of this kaleo node setting
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this kaleo node setting.
	 *
	 * @param userId the user ID of this kaleo node setting
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo node setting.
	 *
	 * @param userName the user name of this kaleo node setting
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo node setting.
	 *
	 * @param userUuid the user uuid of this kaleo node setting
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this kaleo node setting.
	 *
	 * @param value the value of this kaleo node setting
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<KaleoNodeSetting, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoNodeSetting, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoNodeSettingWrapper wrap(KaleoNodeSetting kaleoNodeSetting) {
		return new KaleoNodeSettingWrapper(kaleoNodeSetting);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-423932693