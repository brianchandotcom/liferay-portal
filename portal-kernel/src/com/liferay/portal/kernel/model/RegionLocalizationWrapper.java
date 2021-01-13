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
 * This class is a wrapper for {@link RegionLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RegionLocalization
 * @generated
 */
public class RegionLocalizationWrapper
	extends BaseModelWrapper<RegionLocalization>
	implements ModelWrapper<RegionLocalization>, RegionLocalization {

	public RegionLocalizationWrapper(RegionLocalization regionLocalization) {
		super(regionLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("regionLocalizationId", getRegionLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("regionId", getRegionId());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long regionLocalizationId = (Long)attributes.get(
			"regionLocalizationId");

		if (regionLocalizationId != null) {
			setRegionLocalizationId(regionLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long regionId = (Long)attributes.get("regionId");

		if (regionId != null) {
			setRegionId(regionId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	/**
	 * Returns the company ID of this region localization.
	 *
	 * @return the company ID of this region localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the language ID of this region localization.
	 *
	 * @return the language ID of this region localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the mvcc version of this region localization.
	 *
	 * @return the mvcc version of this region localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this region localization.
	 *
	 * @return the primary key of this region localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the region ID of this region localization.
	 *
	 * @return the region ID of this region localization
	 */
	@Override
	public long getRegionId() {
		return model.getRegionId();
	}

	/**
	 * Returns the region localization ID of this region localization.
	 *
	 * @return the region localization ID of this region localization
	 */
	@Override
	public long getRegionLocalizationId() {
		return model.getRegionLocalizationId();
	}

	/**
	 * Returns the title of this region localization.
	 *
	 * @return the title of this region localization
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Sets the company ID of this region localization.
	 *
	 * @param companyId the company ID of this region localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the language ID of this region localization.
	 *
	 * @param languageId the language ID of this region localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the mvcc version of this region localization.
	 *
	 * @param mvccVersion the mvcc version of this region localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this region localization.
	 *
	 * @param primaryKey the primary key of this region localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the region ID of this region localization.
	 *
	 * @param regionId the region ID of this region localization
	 */
	@Override
	public void setRegionId(long regionId) {
		model.setRegionId(regionId);
	}

	/**
	 * Sets the region localization ID of this region localization.
	 *
	 * @param regionLocalizationId the region localization ID of this region localization
	 */
	@Override
	public void setRegionLocalizationId(long regionLocalizationId) {
		model.setRegionLocalizationId(regionLocalizationId);
	}

	/**
	 * Sets the title of this region localization.
	 *
	 * @param title the title of this region localization
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	protected RegionLocalizationWrapper wrap(
		RegionLocalization regionLocalization) {

		return new RegionLocalizationWrapper(regionLocalization);
	}

}