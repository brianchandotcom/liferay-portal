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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Region}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Region
 * @generated
 */
public class RegionWrapper
	extends BaseModelWrapper<Region> implements ModelWrapper<Region>, Region {

	public RegionWrapper(Region region) {
		super(region);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("regionId", getRegionId());
		attributes.put("countryId", getCountryId());
		attributes.put("regionCode", getRegionCode());
		attributes.put("name", getName());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
		}

		String regionCode = (String)attributes.get("regionCode");

		if (regionCode != null) {
			setRegionCode(regionCode);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	 * Returns the active of this region.
	 *
	 * @return the active of this region
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the country ID of this region.
	 *
	 * @return the country ID of this region
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the mvcc version of this region.
	 *
	 * @return the mvcc version of this region
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this region.
	 *
	 * @return the name of this region
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this region.
	 *
	 * @return the primary key of this region
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the region code of this region.
	 *
	 * @return the region code of this region
	 */
	@Override
	public String getRegionCode() {
		return model.getRegionCode();
	}

	/**
	 * Returns the region ID of this region.
	 *
	 * @return the region ID of this region
	 */
	@Override
	public long getRegionId() {
		return model.getRegionId();
	}

	/**
	 * Returns <code>true</code> if this region is active.
	 *
	 * @return <code>true</code> if this region is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Sets whether this region is active.
	 *
	 * @param active the active of this region
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the country ID of this region.
	 *
	 * @param countryId the country ID of this region
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the mvcc version of this region.
	 *
	 * @param mvccVersion the mvcc version of this region
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this region.
	 *
	 * @param name the name of this region
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this region.
	 *
	 * @param primaryKey the primary key of this region
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the region code of this region.
	 *
	 * @param regionCode the region code of this region
	 */
	@Override
	public void setRegionCode(String regionCode) {
		model.setRegionCode(regionCode);
	}

	/**
	 * Sets the region ID of this region.
	 *
	 * @param regionId the region ID of this region
	 */
	@Override
	public void setRegionId(long regionId) {
		model.setRegionId(regionId);
	}

	@Override
	protected RegionWrapper wrap(Region region) {
		return new RegionWrapper(region);
	}

}