/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.model;

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
 * This class is a wrapper for {@link StyleBookTokenSet}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSet
 * @generated
 */
public class StyleBookTokenSetWrapper
	extends BaseModelWrapper<StyleBookTokenSet>
	implements ModelWrapper<StyleBookTokenSet>, StyleBookTokenSet {

	public StyleBookTokenSetWrapper(StyleBookTokenSet styleBookTokenSet) {
		super(styleBookTokenSet);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("styleBookTokenSetId", getStyleBookTokenSetId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("styleBookEntryId", getStyleBookEntryId());
		attributes.put(
			"frontendTokenCategoryName", getFrontendTokenCategoryName());
		attributes.put(
			"frontendTokenDefinitionId", getFrontendTokenDefinitionId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

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

		Long styleBookTokenSetId = (Long)attributes.get("styleBookTokenSetId");

		if (styleBookTokenSetId != null) {
			setStyleBookTokenSetId(styleBookTokenSetId);
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

		Long styleBookEntryId = (Long)attributes.get("styleBookEntryId");

		if (styleBookEntryId != null) {
			setStyleBookEntryId(styleBookEntryId);
		}

		String frontendTokenCategoryName = (String)attributes.get(
			"frontendTokenCategoryName");

		if (frontendTokenCategoryName != null) {
			setFrontendTokenCategoryName(frontendTokenCategoryName);
		}

		String frontendTokenDefinitionId = (String)attributes.get(
			"frontendTokenDefinitionId");

		if (frontendTokenDefinitionId != null) {
			setFrontendTokenDefinitionId(frontendTokenDefinitionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public StyleBookTokenSet cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this style book token set.
	 *
	 * @return the company ID of this style book token set
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this style book token set.
	 *
	 * @return the create date of this style book token set
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this style book token set.
	 *
	 * @return the ct collection ID of this style book token set
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the description of this style book token set.
	 *
	 * @return the description of this style book token set
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the external reference code of this style book token set.
	 *
	 * @return the external reference code of this style book token set
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the frontend token category name of this style book token set.
	 *
	 * @return the frontend token category name of this style book token set
	 */
	@Override
	public String getFrontendTokenCategoryName() {
		return model.getFrontendTokenCategoryName();
	}

	/**
	 * Returns the frontend token definition ID of this style book token set.
	 *
	 * @return the frontend token definition ID of this style book token set
	 */
	@Override
	public String getFrontendTokenDefinitionId() {
		return model.getFrontendTokenDefinitionId();
	}

	/**
	 * Returns the group ID of this style book token set.
	 *
	 * @return the group ID of this style book token set
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this style book token set.
	 *
	 * @return the modified date of this style book token set
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this style book token set.
	 *
	 * @return the mvcc version of this style book token set
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this style book token set.
	 *
	 * @return the name of this style book token set
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this style book token set.
	 *
	 * @return the primary key of this style book token set
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the style book entry ID of this style book token set.
	 *
	 * @return the style book entry ID of this style book token set
	 */
	@Override
	public long getStyleBookEntryId() {
		return model.getStyleBookEntryId();
	}

	/**
	 * Returns the style book token set ID of this style book token set.
	 *
	 * @return the style book token set ID of this style book token set
	 */
	@Override
	public long getStyleBookTokenSetId() {
		return model.getStyleBookTokenSetId();
	}

	/**
	 * Returns the user ID of this style book token set.
	 *
	 * @return the user ID of this style book token set
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this style book token set.
	 *
	 * @return the user name of this style book token set
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this style book token set.
	 *
	 * @return the user uuid of this style book token set
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this style book token set.
	 *
	 * @return the uuid of this style book token set
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
	 * Sets the company ID of this style book token set.
	 *
	 * @param companyId the company ID of this style book token set
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this style book token set.
	 *
	 * @param createDate the create date of this style book token set
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this style book token set.
	 *
	 * @param ctCollectionId the ct collection ID of this style book token set
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this style book token set.
	 *
	 * @param description the description of this style book token set
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the external reference code of this style book token set.
	 *
	 * @param externalReferenceCode the external reference code of this style book token set
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the frontend token category name of this style book token set.
	 *
	 * @param frontendTokenCategoryName the frontend token category name of this style book token set
	 */
	@Override
	public void setFrontendTokenCategoryName(String frontendTokenCategoryName) {
		model.setFrontendTokenCategoryName(frontendTokenCategoryName);
	}

	/**
	 * Sets the frontend token definition ID of this style book token set.
	 *
	 * @param frontendTokenDefinitionId the frontend token definition ID of this style book token set
	 */
	@Override
	public void setFrontendTokenDefinitionId(String frontendTokenDefinitionId) {
		model.setFrontendTokenDefinitionId(frontendTokenDefinitionId);
	}

	/**
	 * Sets the group ID of this style book token set.
	 *
	 * @param groupId the group ID of this style book token set
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this style book token set.
	 *
	 * @param modifiedDate the modified date of this style book token set
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this style book token set.
	 *
	 * @param mvccVersion the mvcc version of this style book token set
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this style book token set.
	 *
	 * @param name the name of this style book token set
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this style book token set.
	 *
	 * @param primaryKey the primary key of this style book token set
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the style book entry ID of this style book token set.
	 *
	 * @param styleBookEntryId the style book entry ID of this style book token set
	 */
	@Override
	public void setStyleBookEntryId(long styleBookEntryId) {
		model.setStyleBookEntryId(styleBookEntryId);
	}

	/**
	 * Sets the style book token set ID of this style book token set.
	 *
	 * @param styleBookTokenSetId the style book token set ID of this style book token set
	 */
	@Override
	public void setStyleBookTokenSetId(long styleBookTokenSetId) {
		model.setStyleBookTokenSetId(styleBookTokenSetId);
	}

	/**
	 * Sets the user ID of this style book token set.
	 *
	 * @param userId the user ID of this style book token set
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this style book token set.
	 *
	 * @param userName the user name of this style book token set
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this style book token set.
	 *
	 * @param userUuid the user uuid of this style book token set
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this style book token set.
	 *
	 * @param uuid the uuid of this style book token set
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
	public Map<String, Function<StyleBookTokenSet, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<StyleBookTokenSet, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected StyleBookTokenSetWrapper wrap(
		StyleBookTokenSet styleBookTokenSet) {

		return new StyleBookTokenSetWrapper(styleBookTokenSet);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1388571608