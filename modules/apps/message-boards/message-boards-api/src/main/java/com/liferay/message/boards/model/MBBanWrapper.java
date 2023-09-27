/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link MBBan}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MBBan
 * @generated
 */
public class MBBanWrapper
	extends BaseModelWrapper<MBBan> implements MBBan, ModelWrapper<MBBan> {

	public MBBanWrapper(MBBan mbBan) {
		super(mbBan);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("banId", getBanId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("banUserId", getBanUserId());
		attributes.put("lastPublishDate", getLastPublishDate());

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

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long banId = (Long)attributes.get("banId");

		if (banId != null) {
			setBanId(banId);
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

		Long banUserId = (Long)attributes.get("banUserId");

		if (banUserId != null) {
			setBanUserId(banUserId);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public MBBan cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the ban ID of this message boards ban.
	 *
	 * @return the ban ID of this message boards ban
	 */
	@Override
	public long getBanId() {
		return model.getBanId();
	}

	/**
	 * Returns the ban user ID of this message boards ban.
	 *
	 * @return the ban user ID of this message boards ban
	 */
	@Override
	public long getBanUserId() {
		return model.getBanUserId();
	}

	/**
	 * Returns the ban user uuid of this message boards ban.
	 *
	 * @return the ban user uuid of this message boards ban
	 */
	@Override
	public String getBanUserUuid() {
		return model.getBanUserUuid();
	}

	/**
	 * Returns the company ID of this message boards ban.
	 *
	 * @return the company ID of this message boards ban
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this message boards ban.
	 *
	 * @return the create date of this message boards ban
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this message boards ban.
	 *
	 * @return the ct collection ID of this message boards ban
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this message boards ban.
	 *
	 * @return the group ID of this message boards ban
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the last publish date of this message boards ban.
	 *
	 * @return the last publish date of this message boards ban
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this message boards ban.
	 *
	 * @return the modified date of this message boards ban
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this message boards ban.
	 *
	 * @return the mvcc version of this message boards ban
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this message boards ban.
	 *
	 * @return the primary key of this message boards ban
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this message boards ban.
	 *
	 * @return the user ID of this message boards ban
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this message boards ban.
	 *
	 * @return the user name of this message boards ban
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this message boards ban.
	 *
	 * @return the user uuid of this message boards ban
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this message boards ban.
	 *
	 * @return the uuid of this message boards ban
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
	 * Sets the ban ID of this message boards ban.
	 *
	 * @param banId the ban ID of this message boards ban
	 */
	@Override
	public void setBanId(long banId) {
		model.setBanId(banId);
	}

	/**
	 * Sets the ban user ID of this message boards ban.
	 *
	 * @param banUserId the ban user ID of this message boards ban
	 */
	@Override
	public void setBanUserId(long banUserId) {
		model.setBanUserId(banUserId);
	}

	/**
	 * Sets the ban user uuid of this message boards ban.
	 *
	 * @param banUserUuid the ban user uuid of this message boards ban
	 */
	@Override
	public void setBanUserUuid(String banUserUuid) {
		model.setBanUserUuid(banUserUuid);
	}

	/**
	 * Sets the company ID of this message boards ban.
	 *
	 * @param companyId the company ID of this message boards ban
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this message boards ban.
	 *
	 * @param createDate the create date of this message boards ban
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this message boards ban.
	 *
	 * @param ctCollectionId the ct collection ID of this message boards ban
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this message boards ban.
	 *
	 * @param groupId the group ID of this message boards ban
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the last publish date of this message boards ban.
	 *
	 * @param lastPublishDate the last publish date of this message boards ban
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this message boards ban.
	 *
	 * @param modifiedDate the modified date of this message boards ban
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this message boards ban.
	 *
	 * @param mvccVersion the mvcc version of this message boards ban
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this message boards ban.
	 *
	 * @param primaryKey the primary key of this message boards ban
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this message boards ban.
	 *
	 * @param userId the user ID of this message boards ban
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this message boards ban.
	 *
	 * @param userName the user name of this message boards ban
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this message boards ban.
	 *
	 * @param userUuid the user uuid of this message boards ban
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this message boards ban.
	 *
	 * @param uuid the uuid of this message boards ban
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
	public Map<String, Function<MBBan, Object>> getAttributeGetterFunctions() {
		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<MBBan, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected MBBanWrapper wrap(MBBan mbBan) {
		return new MBBanWrapper(mbBan);
	}

}