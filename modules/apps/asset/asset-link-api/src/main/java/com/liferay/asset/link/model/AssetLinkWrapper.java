/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AssetLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetLink
 * @generated
 */
public class AssetLinkWrapper
	extends BaseModelWrapper<AssetLink>
	implements AssetLink, ModelWrapper<AssetLink> {

	public AssetLinkWrapper(AssetLink assetLink) {
		super(assetLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("linkId", getLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("entryId1", getEntryId1());
		attributes.put("entryId2", getEntryId2());
		attributes.put("type", getType());
		attributes.put("weight", getWeight());

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

		Long linkId = (Long)attributes.get("linkId");

		if (linkId != null) {
			setLinkId(linkId);
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

		Long entryId1 = (Long)attributes.get("entryId1");

		if (entryId1 != null) {
			setEntryId1(entryId1);
		}

		Long entryId2 = (Long)attributes.get("entryId2");

		if (entryId2 != null) {
			setEntryId2(entryId2);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Integer weight = (Integer)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}
	}

	@Override
	public AssetLink cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this asset link.
	 *
	 * @return the company ID of this asset link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this asset link.
	 *
	 * @return the create date of this asset link
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this asset link.
	 *
	 * @return the ct collection ID of this asset link
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the entry id1 of this asset link.
	 *
	 * @return the entry id1 of this asset link
	 */
	@Override
	public long getEntryId1() {
		return model.getEntryId1();
	}

	/**
	 * Returns the entry id2 of this asset link.
	 *
	 * @return the entry id2 of this asset link
	 */
	@Override
	public long getEntryId2() {
		return model.getEntryId2();
	}

	/**
	 * Returns the link ID of this asset link.
	 *
	 * @return the link ID of this asset link
	 */
	@Override
	public long getLinkId() {
		return model.getLinkId();
	}

	/**
	 * Returns the mvcc version of this asset link.
	 *
	 * @return the mvcc version of this asset link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset link.
	 *
	 * @return the primary key of this asset link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the type of this asset link.
	 *
	 * @return the type of this asset link
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this asset link.
	 *
	 * @return the user ID of this asset link
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this asset link.
	 *
	 * @return the user name of this asset link
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this asset link.
	 *
	 * @return the user uuid of this asset link
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the weight of this asset link.
	 *
	 * @return the weight of this asset link
	 */
	@Override
	public int getWeight() {
		return model.getWeight();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this asset link.
	 *
	 * @param companyId the company ID of this asset link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this asset link.
	 *
	 * @param createDate the create date of this asset link
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this asset link.
	 *
	 * @param ctCollectionId the ct collection ID of this asset link
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the entry id1 of this asset link.
	 *
	 * @param entryId1 the entry id1 of this asset link
	 */
	@Override
	public void setEntryId1(long entryId1) {
		model.setEntryId1(entryId1);
	}

	/**
	 * Sets the entry id2 of this asset link.
	 *
	 * @param entryId2 the entry id2 of this asset link
	 */
	@Override
	public void setEntryId2(long entryId2) {
		model.setEntryId2(entryId2);
	}

	/**
	 * Sets the link ID of this asset link.
	 *
	 * @param linkId the link ID of this asset link
	 */
	@Override
	public void setLinkId(long linkId) {
		model.setLinkId(linkId);
	}

	/**
	 * Sets the mvcc version of this asset link.
	 *
	 * @param mvccVersion the mvcc version of this asset link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset link.
	 *
	 * @param primaryKey the primary key of this asset link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the type of this asset link.
	 *
	 * @param type the type of this asset link
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this asset link.
	 *
	 * @param userId the user ID of this asset link
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this asset link.
	 *
	 * @param userName the user name of this asset link
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this asset link.
	 *
	 * @param userUuid the user uuid of this asset link
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the weight of this asset link.
	 *
	 * @param weight the weight of this asset link
	 */
	@Override
	public void setWeight(int weight) {
		model.setWeight(weight);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AssetLink, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetLink, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AssetLinkWrapper wrap(AssetLink assetLink) {
		return new AssetLinkWrapper(assetLink);
	}

}