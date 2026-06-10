/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model;

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
 * This class is a wrapper for {@link SegmentsExperienceAudienceEntryRel}.
 * </p>
 *
 * @author Eduardo Garcia
 * @see SegmentsExperienceAudienceEntryRel
 * @generated
 */
public class SegmentsExperienceAudienceEntryRelWrapper
	extends BaseModelWrapper<SegmentsExperienceAudienceEntryRel>
	implements ModelWrapper<SegmentsExperienceAudienceEntryRel>,
			   SegmentsExperienceAudienceEntryRel {

	public SegmentsExperienceAudienceEntryRelWrapper(
		SegmentsExperienceAudienceEntryRel segmentsExperienceAudienceEntryRel) {

		super(segmentsExperienceAudienceEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"segmentsExperienceAudienceEntryRelId",
			getSegmentsExperienceAudienceEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("audienceEntryERC", getAudienceEntryERC());
		attributes.put("priority", getPriority());
		attributes.put("segmentsExperienceERC", getSegmentsExperienceERC());

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

		Long segmentsExperienceAudienceEntryRelId = (Long)attributes.get(
			"segmentsExperienceAudienceEntryRelId");

		if (segmentsExperienceAudienceEntryRelId != null) {
			setSegmentsExperienceAudienceEntryRelId(
				segmentsExperienceAudienceEntryRelId);
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

		String audienceEntryERC = (String)attributes.get("audienceEntryERC");

		if (audienceEntryERC != null) {
			setAudienceEntryERC(audienceEntryERC);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		String segmentsExperienceERC = (String)attributes.get(
			"segmentsExperienceERC");

		if (segmentsExperienceERC != null) {
			setSegmentsExperienceERC(segmentsExperienceERC);
		}
	}

	@Override
	public SegmentsExperienceAudienceEntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the audience entry erc of this segments experience audience entry rel.
	 *
	 * @return the audience entry erc of this segments experience audience entry rel
	 */
	@Override
	public String getAudienceEntryERC() {
		return model.getAudienceEntryERC();
	}

	/**
	 * Returns the company ID of this segments experience audience entry rel.
	 *
	 * @return the company ID of this segments experience audience entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this segments experience audience entry rel.
	 *
	 * @return the create date of this segments experience audience entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this segments experience audience entry rel.
	 *
	 * @return the ct collection ID of this segments experience audience entry rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this segments experience audience entry rel.
	 *
	 * @return the group ID of this segments experience audience entry rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this segments experience audience entry rel.
	 *
	 * @return the modified date of this segments experience audience entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this segments experience audience entry rel.
	 *
	 * @return the mvcc version of this segments experience audience entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this segments experience audience entry rel.
	 *
	 * @return the primary key of this segments experience audience entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this segments experience audience entry rel.
	 *
	 * @return the priority of this segments experience audience entry rel
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the segments experience audience entry rel ID of this segments experience audience entry rel.
	 *
	 * @return the segments experience audience entry rel ID of this segments experience audience entry rel
	 */
	@Override
	public long getSegmentsExperienceAudienceEntryRelId() {
		return model.getSegmentsExperienceAudienceEntryRelId();
	}

	/**
	 * Returns the segments experience erc of this segments experience audience entry rel.
	 *
	 * @return the segments experience erc of this segments experience audience entry rel
	 */
	@Override
	public String getSegmentsExperienceERC() {
		return model.getSegmentsExperienceERC();
	}

	/**
	 * Returns the user ID of this segments experience audience entry rel.
	 *
	 * @return the user ID of this segments experience audience entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this segments experience audience entry rel.
	 *
	 * @return the user name of this segments experience audience entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this segments experience audience entry rel.
	 *
	 * @return the user uuid of this segments experience audience entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this segments experience audience entry rel.
	 *
	 * @return the uuid of this segments experience audience entry rel
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
	 * Sets the audience entry erc of this segments experience audience entry rel.
	 *
	 * @param audienceEntryERC the audience entry erc of this segments experience audience entry rel
	 */
	@Override
	public void setAudienceEntryERC(String audienceEntryERC) {
		model.setAudienceEntryERC(audienceEntryERC);
	}

	/**
	 * Sets the company ID of this segments experience audience entry rel.
	 *
	 * @param companyId the company ID of this segments experience audience entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this segments experience audience entry rel.
	 *
	 * @param createDate the create date of this segments experience audience entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this segments experience audience entry rel.
	 *
	 * @param ctCollectionId the ct collection ID of this segments experience audience entry rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this segments experience audience entry rel.
	 *
	 * @param groupId the group ID of this segments experience audience entry rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this segments experience audience entry rel.
	 *
	 * @param modifiedDate the modified date of this segments experience audience entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this segments experience audience entry rel.
	 *
	 * @param mvccVersion the mvcc version of this segments experience audience entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this segments experience audience entry rel.
	 *
	 * @param primaryKey the primary key of this segments experience audience entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this segments experience audience entry rel.
	 *
	 * @param priority the priority of this segments experience audience entry rel
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the segments experience audience entry rel ID of this segments experience audience entry rel.
	 *
	 * @param segmentsExperienceAudienceEntryRelId the segments experience audience entry rel ID of this segments experience audience entry rel
	 */
	@Override
	public void setSegmentsExperienceAudienceEntryRelId(
		long segmentsExperienceAudienceEntryRelId) {

		model.setSegmentsExperienceAudienceEntryRelId(
			segmentsExperienceAudienceEntryRelId);
	}

	/**
	 * Sets the segments experience erc of this segments experience audience entry rel.
	 *
	 * @param segmentsExperienceERC the segments experience erc of this segments experience audience entry rel
	 */
	@Override
	public void setSegmentsExperienceERC(String segmentsExperienceERC) {
		model.setSegmentsExperienceERC(segmentsExperienceERC);
	}

	/**
	 * Sets the user ID of this segments experience audience entry rel.
	 *
	 * @param userId the user ID of this segments experience audience entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this segments experience audience entry rel.
	 *
	 * @param userName the user name of this segments experience audience entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this segments experience audience entry rel.
	 *
	 * @param userUuid the user uuid of this segments experience audience entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this segments experience audience entry rel.
	 *
	 * @param uuid the uuid of this segments experience audience entry rel
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
	public Map<String, Function<SegmentsExperienceAudienceEntryRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<SegmentsExperienceAudienceEntryRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SegmentsExperienceAudienceEntryRelWrapper wrap(
		SegmentsExperienceAudienceEntryRel segmentsExperienceAudienceEntryRel) {

		return new SegmentsExperienceAudienceEntryRelWrapper(
			segmentsExperienceAudienceEntryRel);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:661403208