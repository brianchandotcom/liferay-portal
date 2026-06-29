/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.model;

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
 * This class is a wrapper for {@link LayoutPageTemplateStructureRelElementVariationAudienceEntryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
 * @generated
 */
public class
	LayoutPageTemplateStructureRelElementVariationAudienceEntryRelWrapper
		extends BaseModelWrapper
			<LayoutPageTemplateStructureRelElementVariationAudienceEntryRel>
		implements LayoutPageTemplateStructureRelElementVariationAudienceEntryRel,
				   ModelWrapper
					   <LayoutPageTemplateStructureRelElementVariationAudienceEntryRel> {

	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRelWrapper(
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
			layoutPageTemplateStructureRelElementVariationAudienceEntryRel) {

		super(layoutPageTemplateStructureRelElementVariationAudienceEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put(
			"layoutPageTemplateStructureRelElementVariationAudienceEntryRelId",
			getLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("audienceEntryERC", getAudienceEntryERC());
		attributes.put(
			"layoutPageTemplateStructureRelElementVariationERC",
			getLayoutPageTemplateStructureRelElementVariationERC());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long layoutPageTemplateStructureRelElementVariationAudienceEntryRelId =
			(Long)attributes.get(
				"layoutPageTemplateStructureRelElementVariationAudienceEntryRelId");

		if (layoutPageTemplateStructureRelElementVariationAudienceEntryRelId !=
				null) {

			setLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId(
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);
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

		String layoutPageTemplateStructureRelElementVariationERC =
			(String)attributes.get(
				"layoutPageTemplateStructureRelElementVariationERC");

		if (layoutPageTemplateStructureRelElementVariationERC != null) {
			setLayoutPageTemplateStructureRelElementVariationERC(
				layoutPageTemplateStructureRelElementVariationERC);
		}
	}

	@Override
	public LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
		cloneWithOriginalValues() {

		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the audience entry erc of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the audience entry erc of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public String getAudienceEntryERC() {
		return model.getAudienceEntryERC();
	}

	/**
	 * Returns the company ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the company ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the create date of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the ct collection ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the external reference code of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the external reference code of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the group ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the layout page template structure rel element variation audience entry rel ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the layout page template structure rel element variation audience entry rel ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long
		getLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId() {

		return model.
			getLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId();
	}

	/**
	 * Returns the layout page template structure rel element variation erc of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the layout page template structure rel element variation erc of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public String getLayoutPageTemplateStructureRelElementVariationERC() {
		return model.getLayoutPageTemplateStructureRelElementVariationERC();
	}

	/**
	 * Returns the modified date of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the modified date of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the mvcc version of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the primary key of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the user ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the user name of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the user uuid of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this layout page template structure rel element variation audience entry rel.
	 *
	 * @return the uuid of this layout page template structure rel element variation audience entry rel
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
	 * Sets the audience entry erc of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param audienceEntryERC the audience entry erc of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setAudienceEntryERC(String audienceEntryERC) {
		model.setAudienceEntryERC(audienceEntryERC);
	}

	/**
	 * Sets the company ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param companyId the company ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param createDate the create date of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param ctCollectionId the ct collection ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the external reference code of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param externalReferenceCode the external reference code of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param groupId the group ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the layout page template structure rel element variation audience entry rel ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param layoutPageTemplateStructureRelElementVariationAudienceEntryRelId the layout page template structure rel element variation audience entry rel ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void
		setLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId(
			long
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelId) {

		model.
			setLayoutPageTemplateStructureRelElementVariationAudienceEntryRelId(
				layoutPageTemplateStructureRelElementVariationAudienceEntryRelId);
	}

	/**
	 * Sets the layout page template structure rel element variation erc of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param layoutPageTemplateStructureRelElementVariationERC the layout page template structure rel element variation erc of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setLayoutPageTemplateStructureRelElementVariationERC(
		String layoutPageTemplateStructureRelElementVariationERC) {

		model.setLayoutPageTemplateStructureRelElementVariationERC(
			layoutPageTemplateStructureRelElementVariationERC);
	}

	/**
	 * Sets the modified date of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param modifiedDate the modified date of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param mvccVersion the mvcc version of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param primaryKey the primary key of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param userId the user ID of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param userName the user name of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param userUuid the user uuid of this layout page template structure rel element variation audience entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this layout page template structure rel element variation audience entry rel.
	 *
	 * @param uuid the uuid of this layout page template structure rel element variation audience entry rel
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
	public Map
		<String,
		 Function
			 <LayoutPageTemplateStructureRelElementVariationAudienceEntryRel,
			  Object>> getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map
		<String,
		 BiConsumer
			 <LayoutPageTemplateStructureRelElementVariationAudienceEntryRel,
			  Object>> getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected
		LayoutPageTemplateStructureRelElementVariationAudienceEntryRelWrapper
			wrap(
				LayoutPageTemplateStructureRelElementVariationAudienceEntryRel
					layoutPageTemplateStructureRelElementVariationAudienceEntryRel) {

		return new LayoutPageTemplateStructureRelElementVariationAudienceEntryRelWrapper(
			layoutPageTemplateStructureRelElementVariationAudienceEntryRel);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-70231635