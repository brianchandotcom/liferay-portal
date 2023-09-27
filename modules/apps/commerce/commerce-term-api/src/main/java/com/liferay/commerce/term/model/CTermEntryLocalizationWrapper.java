/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTermEntryLocalization}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CTermEntryLocalization
 * @generated
 */
public class CTermEntryLocalizationWrapper
	extends BaseModelWrapper<CTermEntryLocalization>
	implements CTermEntryLocalization, ModelWrapper<CTermEntryLocalization> {

	public CTermEntryLocalizationWrapper(
		CTermEntryLocalization cTermEntryLocalization) {

		super(cTermEntryLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"cTermEntryLocalizationId", getCTermEntryLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("commerceTermEntryId", getCommerceTermEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("description", getDescription());
		attributes.put("label", getLabel());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long cTermEntryLocalizationId = (Long)attributes.get(
			"cTermEntryLocalizationId");

		if (cTermEntryLocalizationId != null) {
			setCTermEntryLocalizationId(cTermEntryLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long commerceTermEntryId = (Long)attributes.get("commerceTermEntryId");

		if (commerceTermEntryId != null) {
			setCommerceTermEntryId(commerceTermEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}
	}

	@Override
	public CTermEntryLocalization cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce term entry ID of this c term entry localization.
	 *
	 * @return the commerce term entry ID of this c term entry localization
	 */
	@Override
	public long getCommerceTermEntryId() {
		return model.getCommerceTermEntryId();
	}

	/**
	 * Returns the company ID of this c term entry localization.
	 *
	 * @return the company ID of this c term entry localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the c term entry localization ID of this c term entry localization.
	 *
	 * @return the c term entry localization ID of this c term entry localization
	 */
	@Override
	public long getCTermEntryLocalizationId() {
		return model.getCTermEntryLocalizationId();
	}

	/**
	 * Returns the description of this c term entry localization.
	 *
	 * @return the description of this c term entry localization
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the label of this c term entry localization.
	 *
	 * @return the label of this c term entry localization
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the language ID of this c term entry localization.
	 *
	 * @return the language ID of this c term entry localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the mvcc version of this c term entry localization.
	 *
	 * @return the mvcc version of this c term entry localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this c term entry localization.
	 *
	 * @return the primary key of this c term entry localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Sets the commerce term entry ID of this c term entry localization.
	 *
	 * @param commerceTermEntryId the commerce term entry ID of this c term entry localization
	 */
	@Override
	public void setCommerceTermEntryId(long commerceTermEntryId) {
		model.setCommerceTermEntryId(commerceTermEntryId);
	}

	/**
	 * Sets the company ID of this c term entry localization.
	 *
	 * @param companyId the company ID of this c term entry localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the c term entry localization ID of this c term entry localization.
	 *
	 * @param cTermEntryLocalizationId the c term entry localization ID of this c term entry localization
	 */
	@Override
	public void setCTermEntryLocalizationId(long cTermEntryLocalizationId) {
		model.setCTermEntryLocalizationId(cTermEntryLocalizationId);
	}

	/**
	 * Sets the description of this c term entry localization.
	 *
	 * @param description the description of this c term entry localization
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the label of this c term entry localization.
	 *
	 * @param label the label of this c term entry localization
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the language ID of this c term entry localization.
	 *
	 * @param languageId the language ID of this c term entry localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the mvcc version of this c term entry localization.
	 *
	 * @param mvccVersion the mvcc version of this c term entry localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this c term entry localization.
	 *
	 * @param primaryKey the primary key of this c term entry localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTermEntryLocalizationWrapper wrap(
		CTermEntryLocalization cTermEntryLocalization) {

		return new CTermEntryLocalizationWrapper(cTermEntryLocalization);
	}

}