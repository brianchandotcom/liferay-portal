/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link AssetTagGroupRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRel
 * @generated
 */
public class AssetTagGroupRelWrapper
	extends BaseModelWrapper<AssetTagGroupRel>
	implements AssetTagGroupRel, ModelWrapper<AssetTagGroupRel> {

	public AssetTagGroupRelWrapper(AssetTagGroupRel assetTagGroupRel) {
		super(assetTagGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put("assetTagGroupRelId", getAssetTagGroupRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("tagId", getTagId());

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

		Long assetTagGroupRelId = (Long)attributes.get("assetTagGroupRelId");

		if (assetTagGroupRelId != null) {
			setAssetTagGroupRelId(assetTagGroupRelId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long tagId = (Long)attributes.get("tagId");

		if (tagId != null) {
			setTagId(tagId);
		}
	}

	@Override
	public AssetTagGroupRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the asset tag group rel ID of this asset tag group rel.
	 *
	 * @return the asset tag group rel ID of this asset tag group rel
	 */
	@Override
	public long getAssetTagGroupRelId() {
		return model.getAssetTagGroupRelId();
	}

	/**
	 * Returns the company ID of this asset tag group rel.
	 *
	 * @return the company ID of this asset tag group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this asset tag group rel.
	 *
	 * @return the ct collection ID of this asset tag group rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this asset tag group rel.
	 *
	 * @return the group ID of this asset tag group rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this asset tag group rel.
	 *
	 * @return the mvcc version of this asset tag group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset tag group rel.
	 *
	 * @return the primary key of this asset tag group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the tag ID of this asset tag group rel.
	 *
	 * @return the tag ID of this asset tag group rel
	 */
	@Override
	public long getTagId() {
		return model.getTagId();
	}

	/**
	 * Returns the uuid of this asset tag group rel.
	 *
	 * @return the uuid of this asset tag group rel
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
	 * Sets the asset tag group rel ID of this asset tag group rel.
	 *
	 * @param assetTagGroupRelId the asset tag group rel ID of this asset tag group rel
	 */
	@Override
	public void setAssetTagGroupRelId(long assetTagGroupRelId) {
		model.setAssetTagGroupRelId(assetTagGroupRelId);
	}

	/**
	 * Sets the company ID of this asset tag group rel.
	 *
	 * @param companyId the company ID of this asset tag group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this asset tag group rel.
	 *
	 * @param ctCollectionId the ct collection ID of this asset tag group rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this asset tag group rel.
	 *
	 * @param groupId the group ID of this asset tag group rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this asset tag group rel.
	 *
	 * @param mvccVersion the mvcc version of this asset tag group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset tag group rel.
	 *
	 * @param primaryKey the primary key of this asset tag group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the tag ID of this asset tag group rel.
	 *
	 * @param tagId the tag ID of this asset tag group rel
	 */
	@Override
	public void setTagId(long tagId) {
		model.setTagId(tagId);
	}

	/**
	 * Sets the uuid of this asset tag group rel.
	 *
	 * @param uuid the uuid of this asset tag group rel
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
	public Map<String, Function<AssetTagGroupRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetTagGroupRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AssetTagGroupRelWrapper wrap(AssetTagGroupRel assetTagGroupRel) {
		return new AssetTagGroupRelWrapper(assetTagGroupRel);
	}

}