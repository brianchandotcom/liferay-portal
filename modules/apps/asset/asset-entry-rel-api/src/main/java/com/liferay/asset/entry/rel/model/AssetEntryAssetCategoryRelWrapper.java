/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.asset.entry.rel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntryAssetCategoryRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryAssetCategoryRel
 * @generated
 */
public class AssetEntryAssetCategoryRelWrapper
	extends BaseModelWrapper<AssetEntryAssetCategoryRel>
	implements AssetEntryAssetCategoryRel,
			   ModelWrapper<AssetEntryAssetCategoryRel> {

	public AssetEntryAssetCategoryRelWrapper(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

		super(assetEntryAssetCategoryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"assetEntryAssetCategoryRelId", getAssetEntryAssetCategoryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("assetEntryId", getAssetEntryId());
		attributes.put("assetCategoryId", getAssetCategoryId());
		attributes.put("priority", getPriority());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long assetEntryAssetCategoryRelId = (Long)attributes.get(
			"assetEntryAssetCategoryRelId");

		if (assetEntryAssetCategoryRelId != null) {
			setAssetEntryAssetCategoryRelId(assetEntryAssetCategoryRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}

		Long assetCategoryId = (Long)attributes.get("assetCategoryId");

		if (assetCategoryId != null) {
			setAssetCategoryId(assetCategoryId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}
	}

	/**
	 * Returns the asset category ID of this asset entry asset category rel.
	 *
	 * @return the asset category ID of this asset entry asset category rel
	 */
	@Override
	public long getAssetCategoryId() {
		return model.getAssetCategoryId();
	}

	/**
	 * Returns the asset entry asset category rel ID of this asset entry asset category rel.
	 *
	 * @return the asset entry asset category rel ID of this asset entry asset category rel
	 */
	@Override
	public long getAssetEntryAssetCategoryRelId() {
		return model.getAssetEntryAssetCategoryRelId();
	}

	/**
	 * Returns the asset entry ID of this asset entry asset category rel.
	 *
	 * @return the asset entry ID of this asset entry asset category rel
	 */
	@Override
	public long getAssetEntryId() {
		return model.getAssetEntryId();
	}

	/**
	 * Returns the company ID of this asset entry asset category rel.
	 *
	 * @return the company ID of this asset entry asset category rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this asset entry asset category rel.
	 *
	 * @return the mvcc version of this asset entry asset category rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this asset entry asset category rel.
	 *
	 * @return the primary key of this asset entry asset category rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this asset entry asset category rel.
	 *
	 * @return the priority of this asset entry asset category rel
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a asset entry asset category rel model instance should use the <code>AssetEntryAssetCategoryRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the asset category ID of this asset entry asset category rel.
	 *
	 * @param assetCategoryId the asset category ID of this asset entry asset category rel
	 */
	@Override
	public void setAssetCategoryId(long assetCategoryId) {
		model.setAssetCategoryId(assetCategoryId);
	}

	/**
	 * Sets the asset entry asset category rel ID of this asset entry asset category rel.
	 *
	 * @param assetEntryAssetCategoryRelId the asset entry asset category rel ID of this asset entry asset category rel
	 */
	@Override
	public void setAssetEntryAssetCategoryRelId(
		long assetEntryAssetCategoryRelId) {

		model.setAssetEntryAssetCategoryRelId(assetEntryAssetCategoryRelId);
	}

	/**
	 * Sets the asset entry ID of this asset entry asset category rel.
	 *
	 * @param assetEntryId the asset entry ID of this asset entry asset category rel
	 */
	@Override
	public void setAssetEntryId(long assetEntryId) {
		model.setAssetEntryId(assetEntryId);
	}

	/**
	 * Sets the company ID of this asset entry asset category rel.
	 *
	 * @param companyId the company ID of this asset entry asset category rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this asset entry asset category rel.
	 *
	 * @param mvccVersion the mvcc version of this asset entry asset category rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this asset entry asset category rel.
	 *
	 * @param primaryKey the primary key of this asset entry asset category rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this asset entry asset category rel.
	 *
	 * @param priority the priority of this asset entry asset category rel
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	@Override
	protected AssetEntryAssetCategoryRelWrapper wrap(
		AssetEntryAssetCategoryRel assetEntryAssetCategoryRel) {

		return new AssetEntryAssetCategoryRelWrapper(
			assetEntryAssetCategoryRel);
	}

}