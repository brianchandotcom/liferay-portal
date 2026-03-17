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
 * This class is a wrapper for {@link AssetVocabularyGroupRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetVocabularyGroupRel
 * @generated
 */
public class AssetVocabularyGroupRelWrapper
	extends BaseModelWrapper<AssetVocabularyGroupRel>
	implements AssetVocabularyGroupRel, ModelWrapper<AssetVocabularyGroupRel> {

	public AssetVocabularyGroupRelWrapper(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		super(assetVocabularyGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"assetVocabularyGroupRelId", getAssetVocabularyGroupRelId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("vocabularyId", getVocabularyId());

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

		Long assetVocabularyGroupRelId = (Long)attributes.get(
			"assetVocabularyGroupRelId");

		if (assetVocabularyGroupRelId != null) {
			setAssetVocabularyGroupRelId(assetVocabularyGroupRelId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long vocabularyId = (Long)attributes.get("vocabularyId");

		if (vocabularyId != null) {
			setVocabularyId(vocabularyId);
		}
	}

	@Override
	public AssetVocabularyGroupRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the asset vocabulary group rel ID of this asset vocabulary group rel.
	 *
	 * @return the asset vocabulary group rel ID of this asset vocabulary group rel
	 */
	@Override
	public long getAssetVocabularyGroupRelId() {
		return model.getAssetVocabularyGroupRelId();
	}

	/**
	 * Returns the company ID of this asset vocabulary group rel.
	 *
	 * @return the company ID of this asset vocabulary group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this asset vocabulary group rel.
	 *
	 * @return the ct collection ID of this asset vocabulary group rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the group ID of this asset vocabulary group rel.
	 *
	 * @return the group ID of this asset vocabulary group rel
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this asset vocabulary group rel.
	 *
	 * @return the mvcc version of this asset vocabulary group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset vocabulary group rel.
	 *
	 * @return the primary key of this asset vocabulary group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this asset vocabulary group rel.
	 *
	 * @return the uuid of this asset vocabulary group rel
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the vocabulary ID of this asset vocabulary group rel.
	 *
	 * @return the vocabulary ID of this asset vocabulary group rel
	 */
	@Override
	public long getVocabularyId() {
		return model.getVocabularyId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset vocabulary group rel ID of this asset vocabulary group rel.
	 *
	 * @param assetVocabularyGroupRelId the asset vocabulary group rel ID of this asset vocabulary group rel
	 */
	@Override
	public void setAssetVocabularyGroupRelId(long assetVocabularyGroupRelId) {
		model.setAssetVocabularyGroupRelId(assetVocabularyGroupRelId);
	}

	/**
	 * Sets the company ID of this asset vocabulary group rel.
	 *
	 * @param companyId the company ID of this asset vocabulary group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this asset vocabulary group rel.
	 *
	 * @param ctCollectionId the ct collection ID of this asset vocabulary group rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this asset vocabulary group rel.
	 *
	 * @param groupId the group ID of this asset vocabulary group rel
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this asset vocabulary group rel.
	 *
	 * @param mvccVersion the mvcc version of this asset vocabulary group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset vocabulary group rel.
	 *
	 * @param primaryKey the primary key of this asset vocabulary group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this asset vocabulary group rel.
	 *
	 * @param uuid the uuid of this asset vocabulary group rel
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the vocabulary ID of this asset vocabulary group rel.
	 *
	 * @param vocabularyId the vocabulary ID of this asset vocabulary group rel
	 */
	@Override
	public void setVocabularyId(long vocabularyId) {
		model.setVocabularyId(vocabularyId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<AssetVocabularyGroupRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<AssetVocabularyGroupRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected AssetVocabularyGroupRelWrapper wrap(
		AssetVocabularyGroupRel assetVocabularyGroupRel) {

		return new AssetVocabularyGroupRelWrapper(assetVocabularyGroupRel);
	}

}
// SB-Hash:-1833640854