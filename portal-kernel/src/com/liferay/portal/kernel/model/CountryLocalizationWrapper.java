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
 * This class is a wrapper for {@link CountryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CountryLocalization
 * @generated
 */
public class CountryLocalizationWrapper
	extends BaseModelWrapper<CountryLocalization>
	implements CountryLocalization, ModelWrapper<CountryLocalization> {

	public CountryLocalizationWrapper(CountryLocalization countryLocalization) {
		super(countryLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("countryLocalizationId", getCountryLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("countryId", getCountryId());
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

		Long countryLocalizationId = (Long)attributes.get(
			"countryLocalizationId");

		if (countryLocalizationId != null) {
			setCountryLocalizationId(countryLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long countryId = (Long)attributes.get("countryId");

		if (countryId != null) {
			setCountryId(countryId);
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
	 * Returns the company ID of this country localization.
	 *
	 * @return the company ID of this country localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the country ID of this country localization.
	 *
	 * @return the country ID of this country localization
	 */
	@Override
	public long getCountryId() {
		return model.getCountryId();
	}

	/**
	 * Returns the country localization ID of this country localization.
	 *
	 * @return the country localization ID of this country localization
	 */
	@Override
	public long getCountryLocalizationId() {
		return model.getCountryLocalizationId();
	}

	/**
	 * Returns the language ID of this country localization.
	 *
	 * @return the language ID of this country localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the mvcc version of this country localization.
	 *
	 * @return the mvcc version of this country localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this country localization.
	 *
	 * @return the primary key of this country localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the title of this country localization.
	 *
	 * @return the title of this country localization
	 */
	@Override
	public String getTitle() {
		return model.getTitle();
	}

	/**
	 * Sets the company ID of this country localization.
	 *
	 * @param companyId the company ID of this country localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the country ID of this country localization.
	 *
	 * @param countryId the country ID of this country localization
	 */
	@Override
	public void setCountryId(long countryId) {
		model.setCountryId(countryId);
	}

	/**
	 * Sets the country localization ID of this country localization.
	 *
	 * @param countryLocalizationId the country localization ID of this country localization
	 */
	@Override
	public void setCountryLocalizationId(long countryLocalizationId) {
		model.setCountryLocalizationId(countryLocalizationId);
	}

	/**
	 * Sets the language ID of this country localization.
	 *
	 * @param languageId the language ID of this country localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the mvcc version of this country localization.
	 *
	 * @param mvccVersion the mvcc version of this country localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this country localization.
	 *
	 * @param primaryKey the primary key of this country localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the title of this country localization.
	 *
	 * @param title the title of this country localization
	 */
	@Override
	public void setTitle(String title) {
		model.setTitle(title);
	}

	@Override
	protected CountryLocalizationWrapper wrap(
		CountryLocalization countryLocalization) {

		return new CountryLocalizationWrapper(countryLocalization);
	}

}