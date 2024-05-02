/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommercePaymentEntry}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see CommercePaymentEntry
 * @generated
 */
public class CommercePaymentEntryWrapper
	extends BaseModelWrapper<CommercePaymentEntry>
	implements CommercePaymentEntry, ModelWrapper<CommercePaymentEntry> {

	public CommercePaymentEntryWrapper(
		CommercePaymentEntry commercePaymentEntry) {

		super(commercePaymentEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commercePaymentEntryId", getCommercePaymentEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceChannelId", getCommerceChannelId());
		attributes.put("amount", getAmount());
		attributes.put("callbackURL", getCallbackURL());
		attributes.put("cancelURL", getCancelURL());
		attributes.put("currencyCode", getCurrencyCode());
		attributes.put("errorMessages", getErrorMessages());
		attributes.put("languageId", getLanguageId());
		attributes.put("note", getNote());
		attributes.put("payload", getPayload());
		attributes.put("paymentIntegrationKey", getPaymentIntegrationKey());
		attributes.put("paymentIntegrationType", getPaymentIntegrationType());
		attributes.put("paymentStatus", getPaymentStatus());
		attributes.put("reasonKey", getReasonKey());
		attributes.put("reasonName", getReasonName());
		attributes.put("redirectURL", getRedirectURL());
		attributes.put("transactionCode", getTransactionCode());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commercePaymentEntryId = (Long)attributes.get(
			"commercePaymentEntryId");

		if (commercePaymentEntryId != null) {
			setCommercePaymentEntryId(commercePaymentEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
		}

		BigDecimal amount = (BigDecimal)attributes.get("amount");

		if (amount != null) {
			setAmount(amount);
		}

		String callbackURL = (String)attributes.get("callbackURL");

		if (callbackURL != null) {
			setCallbackURL(callbackURL);
		}

		String cancelURL = (String)attributes.get("cancelURL");

		if (cancelURL != null) {
			setCancelURL(cancelURL);
		}

		String currencyCode = (String)attributes.get("currencyCode");

		if (currencyCode != null) {
			setCurrencyCode(currencyCode);
		}

		String errorMessages = (String)attributes.get("errorMessages");

		if (errorMessages != null) {
			setErrorMessages(errorMessages);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String note = (String)attributes.get("note");

		if (note != null) {
			setNote(note);
		}

		String payload = (String)attributes.get("payload");

		if (payload != null) {
			setPayload(payload);
		}

		String paymentIntegrationKey = (String)attributes.get(
			"paymentIntegrationKey");

		if (paymentIntegrationKey != null) {
			setPaymentIntegrationKey(paymentIntegrationKey);
		}

		Integer paymentIntegrationType = (Integer)attributes.get(
			"paymentIntegrationType");

		if (paymentIntegrationType != null) {
			setPaymentIntegrationType(paymentIntegrationType);
		}

		Integer paymentStatus = (Integer)attributes.get("paymentStatus");

		if (paymentStatus != null) {
			setPaymentStatus(paymentStatus);
		}

		String reasonKey = (String)attributes.get("reasonKey");

		if (reasonKey != null) {
			setReasonKey(reasonKey);
		}

		String reasonName = (String)attributes.get("reasonName");

		if (reasonName != null) {
			setReasonName(reasonName);
		}

		String redirectURL = (String)attributes.get("redirectURL");

		if (redirectURL != null) {
			setRedirectURL(redirectURL);
		}

		String transactionCode = (String)attributes.get("transactionCode");

		if (transactionCode != null) {
			setTransactionCode(transactionCode);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public CommercePaymentEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the amount of this commerce payment entry.
	 *
	 * @return the amount of this commerce payment entry
	 */
	@Override
	public BigDecimal getAmount() {
		return model.getAmount();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the callback url of this commerce payment entry.
	 *
	 * @return the callback url of this commerce payment entry
	 */
	@Override
	public String getCallbackURL() {
		return model.getCallbackURL();
	}

	/**
	 * Returns the cancel url of this commerce payment entry.
	 *
	 * @return the cancel url of this commerce payment entry
	 */
	@Override
	public String getCancelURL() {
		return model.getCancelURL();
	}

	/**
	 * Returns the fully qualified class name of this commerce payment entry.
	 *
	 * @return the fully qualified class name of this commerce payment entry
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce payment entry.
	 *
	 * @return the class name ID of this commerce payment entry
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce payment entry.
	 *
	 * @return the class pk of this commerce payment entry
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the commerce channel ID of this commerce payment entry.
	 *
	 * @return the commerce channel ID of this commerce payment entry
	 */
	@Override
	public long getCommerceChannelId() {
		return model.getCommerceChannelId();
	}

	/**
	 * Returns the commerce payment entry ID of this commerce payment entry.
	 *
	 * @return the commerce payment entry ID of this commerce payment entry
	 */
	@Override
	public long getCommercePaymentEntryId() {
		return model.getCommercePaymentEntryId();
	}

	/**
	 * Returns the company ID of this commerce payment entry.
	 *
	 * @return the company ID of this commerce payment entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce payment entry.
	 *
	 * @return the create date of this commerce payment entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the currency code of this commerce payment entry.
	 *
	 * @return the currency code of this commerce payment entry
	 */
	@Override
	public String getCurrencyCode() {
		return model.getCurrencyCode();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the error messages of this commerce payment entry.
	 *
	 * @return the error messages of this commerce payment entry
	 */
	@Override
	public String getErrorMessages() {
		return model.getErrorMessages();
	}

	/**
	 * Returns the external reference code of this commerce payment entry.
	 *
	 * @return the external reference code of this commerce payment entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the language ID of this commerce payment entry.
	 *
	 * @return the language ID of this commerce payment entry
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the modified date of this commerce payment entry.
	 *
	 * @return the modified date of this commerce payment entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce payment entry.
	 *
	 * @return the mvcc version of this commerce payment entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the note of this commerce payment entry.
	 *
	 * @return the note of this commerce payment entry
	 */
	@Override
	public String getNote() {
		return model.getNote();
	}

	/**
	 * Returns the payload of this commerce payment entry.
	 *
	 * @return the payload of this commerce payment entry
	 */
	@Override
	public String getPayload() {
		return model.getPayload();
	}

	/**
	 * Returns the payment integration key of this commerce payment entry.
	 *
	 * @return the payment integration key of this commerce payment entry
	 */
	@Override
	public String getPaymentIntegrationKey() {
		return model.getPaymentIntegrationKey();
	}

	/**
	 * Returns the payment integration type of this commerce payment entry.
	 *
	 * @return the payment integration type of this commerce payment entry
	 */
	@Override
	public int getPaymentIntegrationType() {
		return model.getPaymentIntegrationType();
	}

	/**
	 * Returns the payment status of this commerce payment entry.
	 *
	 * @return the payment status of this commerce payment entry
	 */
	@Override
	public int getPaymentStatus() {
		return model.getPaymentStatus();
	}

	/**
	 * Returns the primary key of this commerce payment entry.
	 *
	 * @return the primary key of this commerce payment entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the reason key of this commerce payment entry.
	 *
	 * @return the reason key of this commerce payment entry
	 */
	@Override
	public String getReasonKey() {
		return model.getReasonKey();
	}

	/**
	 * Returns the reason name of this commerce payment entry.
	 *
	 * @return the reason name of this commerce payment entry
	 */
	@Override
	public String getReasonName() {
		return model.getReasonName();
	}

	/**
	 * Returns the localized reason name of this commerce payment entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized reason name of this commerce payment entry
	 */
	@Override
	public String getReasonName(java.util.Locale locale) {
		return model.getReasonName(locale);
	}

	/**
	 * Returns the localized reason name of this commerce payment entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized reason name of this commerce payment entry. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getReasonName(java.util.Locale locale, boolean useDefault) {
		return model.getReasonName(locale, useDefault);
	}

	/**
	 * Returns the localized reason name of this commerce payment entry in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized reason name of this commerce payment entry
	 */
	@Override
	public String getReasonName(String languageId) {
		return model.getReasonName(languageId);
	}

	/**
	 * Returns the localized reason name of this commerce payment entry in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized reason name of this commerce payment entry
	 */
	@Override
	public String getReasonName(String languageId, boolean useDefault) {
		return model.getReasonName(languageId, useDefault);
	}

	@Override
	public String getReasonNameCurrentLanguageId() {
		return model.getReasonNameCurrentLanguageId();
	}

	@Override
	public String getReasonNameCurrentValue() {
		return model.getReasonNameCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized reason names of this commerce payment entry.
	 *
	 * @return the locales and localized reason names of this commerce payment entry
	 */
	@Override
	public Map<java.util.Locale, String> getReasonNameMap() {
		return model.getReasonNameMap();
	}

	/**
	 * Returns the redirect url of this commerce payment entry.
	 *
	 * @return the redirect url of this commerce payment entry
	 */
	@Override
	public String getRedirectURL() {
		return model.getRedirectURL();
	}

	/**
	 * Returns the transaction code of this commerce payment entry.
	 *
	 * @return the transaction code of this commerce payment entry
	 */
	@Override
	public String getTransactionCode() {
		return model.getTransactionCode();
	}

	/**
	 * Returns the type of this commerce payment entry.
	 *
	 * @return the type of this commerce payment entry
	 */
	@Override
	public int getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this commerce payment entry.
	 *
	 * @return the user ID of this commerce payment entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce payment entry.
	 *
	 * @return the user name of this commerce payment entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce payment entry.
	 *
	 * @return the user uuid of this commerce payment entry
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void prepareLocalizedFieldsForImport()
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport();
	}

	@Override
	public void prepareLocalizedFieldsForImport(
			java.util.Locale defaultImportLocale)
		throws com.liferay.portal.kernel.exception.LocaleException {

		model.prepareLocalizedFieldsForImport(defaultImportLocale);
	}

	/**
	 * Sets the amount of this commerce payment entry.
	 *
	 * @param amount the amount of this commerce payment entry
	 */
	@Override
	public void setAmount(BigDecimal amount) {
		model.setAmount(amount);
	}

	/**
	 * Sets the callback url of this commerce payment entry.
	 *
	 * @param callbackURL the callback url of this commerce payment entry
	 */
	@Override
	public void setCallbackURL(String callbackURL) {
		model.setCallbackURL(callbackURL);
	}

	/**
	 * Sets the cancel url of this commerce payment entry.
	 *
	 * @param cancelURL the cancel url of this commerce payment entry
	 */
	@Override
	public void setCancelURL(String cancelURL) {
		model.setCancelURL(cancelURL);
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce payment entry.
	 *
	 * @param classNameId the class name ID of this commerce payment entry
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce payment entry.
	 *
	 * @param classPK the class pk of this commerce payment entry
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce channel ID of this commerce payment entry.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce payment entry
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		model.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the commerce payment entry ID of this commerce payment entry.
	 *
	 * @param commercePaymentEntryId the commerce payment entry ID of this commerce payment entry
	 */
	@Override
	public void setCommercePaymentEntryId(long commercePaymentEntryId) {
		model.setCommercePaymentEntryId(commercePaymentEntryId);
	}

	/**
	 * Sets the company ID of this commerce payment entry.
	 *
	 * @param companyId the company ID of this commerce payment entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce payment entry.
	 *
	 * @param createDate the create date of this commerce payment entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the currency code of this commerce payment entry.
	 *
	 * @param currencyCode the currency code of this commerce payment entry
	 */
	@Override
	public void setCurrencyCode(String currencyCode) {
		model.setCurrencyCode(currencyCode);
	}

	/**
	 * Sets the error messages of this commerce payment entry.
	 *
	 * @param errorMessages the error messages of this commerce payment entry
	 */
	@Override
	public void setErrorMessages(String errorMessages) {
		model.setErrorMessages(errorMessages);
	}

	/**
	 * Sets the external reference code of this commerce payment entry.
	 *
	 * @param externalReferenceCode the external reference code of this commerce payment entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the language ID of this commerce payment entry.
	 *
	 * @param languageId the language ID of this commerce payment entry
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the modified date of this commerce payment entry.
	 *
	 * @param modifiedDate the modified date of this commerce payment entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce payment entry.
	 *
	 * @param mvccVersion the mvcc version of this commerce payment entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the note of this commerce payment entry.
	 *
	 * @param note the note of this commerce payment entry
	 */
	@Override
	public void setNote(String note) {
		model.setNote(note);
	}

	/**
	 * Sets the payload of this commerce payment entry.
	 *
	 * @param payload the payload of this commerce payment entry
	 */
	@Override
	public void setPayload(String payload) {
		model.setPayload(payload);
	}

	/**
	 * Sets the payment integration key of this commerce payment entry.
	 *
	 * @param paymentIntegrationKey the payment integration key of this commerce payment entry
	 */
	@Override
	public void setPaymentIntegrationKey(String paymentIntegrationKey) {
		model.setPaymentIntegrationKey(paymentIntegrationKey);
	}

	/**
	 * Sets the payment integration type of this commerce payment entry.
	 *
	 * @param paymentIntegrationType the payment integration type of this commerce payment entry
	 */
	@Override
	public void setPaymentIntegrationType(int paymentIntegrationType) {
		model.setPaymentIntegrationType(paymentIntegrationType);
	}

	/**
	 * Sets the payment status of this commerce payment entry.
	 *
	 * @param paymentStatus the payment status of this commerce payment entry
	 */
	@Override
	public void setPaymentStatus(int paymentStatus) {
		model.setPaymentStatus(paymentStatus);
	}

	/**
	 * Sets the primary key of this commerce payment entry.
	 *
	 * @param primaryKey the primary key of this commerce payment entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the reason key of this commerce payment entry.
	 *
	 * @param reasonKey the reason key of this commerce payment entry
	 */
	@Override
	public void setReasonKey(String reasonKey) {
		model.setReasonKey(reasonKey);
	}

	/**
	 * Sets the reason name of this commerce payment entry.
	 *
	 * @param reasonName the reason name of this commerce payment entry
	 */
	@Override
	public void setReasonName(String reasonName) {
		model.setReasonName(reasonName);
	}

	/**
	 * Sets the localized reason name of this commerce payment entry in the language.
	 *
	 * @param reasonName the localized reason name of this commerce payment entry
	 * @param locale the locale of the language
	 */
	@Override
	public void setReasonName(String reasonName, java.util.Locale locale) {
		model.setReasonName(reasonName, locale);
	}

	/**
	 * Sets the localized reason name of this commerce payment entry in the language, and sets the default locale.
	 *
	 * @param reasonName the localized reason name of this commerce payment entry
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setReasonName(
		String reasonName, java.util.Locale locale,
		java.util.Locale defaultLocale) {

		model.setReasonName(reasonName, locale, defaultLocale);
	}

	@Override
	public void setReasonNameCurrentLanguageId(String languageId) {
		model.setReasonNameCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized reason names of this commerce payment entry from the map of locales and localized reason names.
	 *
	 * @param reasonNameMap the locales and localized reason names of this commerce payment entry
	 */
	@Override
	public void setReasonNameMap(Map<java.util.Locale, String> reasonNameMap) {
		model.setReasonNameMap(reasonNameMap);
	}

	/**
	 * Sets the localized reason names of this commerce payment entry from the map of locales and localized reason names, and sets the default locale.
	 *
	 * @param reasonNameMap the locales and localized reason names of this commerce payment entry
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setReasonNameMap(
		Map<java.util.Locale, String> reasonNameMap,
		java.util.Locale defaultLocale) {

		model.setReasonNameMap(reasonNameMap, defaultLocale);
	}

	/**
	 * Sets the redirect url of this commerce payment entry.
	 *
	 * @param redirectURL the redirect url of this commerce payment entry
	 */
	@Override
	public void setRedirectURL(String redirectURL) {
		model.setRedirectURL(redirectURL);
	}

	/**
	 * Sets the transaction code of this commerce payment entry.
	 *
	 * @param transactionCode the transaction code of this commerce payment entry
	 */
	@Override
	public void setTransactionCode(String transactionCode) {
		model.setTransactionCode(transactionCode);
	}

	/**
	 * Sets the type of this commerce payment entry.
	 *
	 * @param type the type of this commerce payment entry
	 */
	@Override
	public void setType(int type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this commerce payment entry.
	 *
	 * @param userId the user ID of this commerce payment entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce payment entry.
	 *
	 * @param userName the user name of this commerce payment entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce payment entry.
	 *
	 * @param userUuid the user uuid of this commerce payment entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CommercePaymentEntryWrapper wrap(
		CommercePaymentEntry commercePaymentEntry) {

		return new CommercePaymentEntryWrapper(commercePaymentEntry);
	}

}