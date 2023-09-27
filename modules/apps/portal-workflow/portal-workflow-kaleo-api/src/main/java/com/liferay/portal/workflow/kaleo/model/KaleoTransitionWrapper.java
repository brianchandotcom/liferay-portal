/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link KaleoTransition}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTransition
 * @generated
 */
public class KaleoTransitionWrapper
	extends BaseModelWrapper<KaleoTransition>
	implements KaleoTransition, ModelWrapper<KaleoTransition> {

	public KaleoTransitionWrapper(KaleoTransition kaleoTransition) {
		super(kaleoTransition);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoTransitionId", getKaleoTransitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("name", getName());
		attributes.put("label", getLabel());
		attributes.put("description", getDescription());
		attributes.put("sourceKaleoNodeId", getSourceKaleoNodeId());
		attributes.put("sourceKaleoNodeName", getSourceKaleoNodeName());
		attributes.put("targetKaleoNodeId", getTargetKaleoNodeId());
		attributes.put("targetKaleoNodeName", getTargetKaleoNodeName());
		attributes.put("defaultTransition", isDefaultTransition());

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

		Long kaleoTransitionId = (Long)attributes.get("kaleoTransitionId");

		if (kaleoTransitionId != null) {
			setKaleoTransitionId(kaleoTransitionId);
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

		Long kaleoDefinitionId = (Long)attributes.get("kaleoDefinitionId");

		if (kaleoDefinitionId != null) {
			setKaleoDefinitionId(kaleoDefinitionId);
		}

		Long kaleoDefinitionVersionId = (Long)attributes.get(
			"kaleoDefinitionVersionId");

		if (kaleoDefinitionVersionId != null) {
			setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		}

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long sourceKaleoNodeId = (Long)attributes.get("sourceKaleoNodeId");

		if (sourceKaleoNodeId != null) {
			setSourceKaleoNodeId(sourceKaleoNodeId);
		}

		String sourceKaleoNodeName = (String)attributes.get(
			"sourceKaleoNodeName");

		if (sourceKaleoNodeName != null) {
			setSourceKaleoNodeName(sourceKaleoNodeName);
		}

		Long targetKaleoNodeId = (Long)attributes.get("targetKaleoNodeId");

		if (targetKaleoNodeId != null) {
			setTargetKaleoNodeId(targetKaleoNodeId);
		}

		String targetKaleoNodeName = (String)attributes.get(
			"targetKaleoNodeName");

		if (targetKaleoNodeName != null) {
			setTargetKaleoNodeName(targetKaleoNodeName);
		}

		Boolean defaultTransition = (Boolean)attributes.get(
			"defaultTransition");

		if (defaultTransition != null) {
			setDefaultTransition(defaultTransition);
		}
	}

	@Override
	public KaleoTransition cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this kaleo transition.
	 *
	 * @return the company ID of this kaleo transition
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo transition.
	 *
	 * @return the create date of this kaleo transition
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo transition.
	 *
	 * @return the ct collection ID of this kaleo transition
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the default transition of this kaleo transition.
	 *
	 * @return the default transition of this kaleo transition
	 */
	@Override
	public boolean getDefaultTransition() {
		return model.getDefaultTransition();
	}

	/**
	 * Returns the description of this kaleo transition.
	 *
	 * @return the description of this kaleo transition
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this kaleo transition.
	 *
	 * @return the group ID of this kaleo transition
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo transition.
	 *
	 * @return the kaleo definition ID of this kaleo transition
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo transition.
	 *
	 * @return the kaleo definition version ID of this kaleo transition
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo node ID of this kaleo transition.
	 *
	 * @return the kaleo node ID of this kaleo transition
	 */
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	/**
	 * Returns the kaleo transition ID of this kaleo transition.
	 *
	 * @return the kaleo transition ID of this kaleo transition
	 */
	@Override
	public long getKaleoTransitionId() {
		return model.getKaleoTransitionId();
	}

	/**
	 * Returns the label of this kaleo transition.
	 *
	 * @return the label of this kaleo transition
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this kaleo transition in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this kaleo transition
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this kaleo transition in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this kaleo transition. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this kaleo transition in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this kaleo transition
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this kaleo transition in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this kaleo transition
	 */
	@Override
	public String getLabel(String languageId, boolean useDefault) {
		return model.getLabel(languageId, useDefault);
	}

	@Override
	public String getLabelCurrentLanguageId() {
		return model.getLabelCurrentLanguageId();
	}

	@Override
	public String getLabelCurrentValue() {
		return model.getLabelCurrentValue();
	}

	/**
	 * Returns a map of the locales and localized labels of this kaleo transition.
	 *
	 * @return the locales and localized labels of this kaleo transition
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the modified date of this kaleo transition.
	 *
	 * @return the modified date of this kaleo transition
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo transition.
	 *
	 * @return the mvcc version of this kaleo transition
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo transition.
	 *
	 * @return the name of this kaleo transition
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this kaleo transition.
	 *
	 * @return the primary key of this kaleo transition
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public KaleoNode getSourceKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getSourceKaleoNode();
	}

	/**
	 * Returns the source kaleo node ID of this kaleo transition.
	 *
	 * @return the source kaleo node ID of this kaleo transition
	 */
	@Override
	public long getSourceKaleoNodeId() {
		return model.getSourceKaleoNodeId();
	}

	/**
	 * Returns the source kaleo node name of this kaleo transition.
	 *
	 * @return the source kaleo node name of this kaleo transition
	 */
	@Override
	public String getSourceKaleoNodeName() {
		return model.getSourceKaleoNodeName();
	}

	@Override
	public KaleoNode getTargetKaleoNode()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getTargetKaleoNode();
	}

	/**
	 * Returns the target kaleo node ID of this kaleo transition.
	 *
	 * @return the target kaleo node ID of this kaleo transition
	 */
	@Override
	public long getTargetKaleoNodeId() {
		return model.getTargetKaleoNodeId();
	}

	/**
	 * Returns the target kaleo node name of this kaleo transition.
	 *
	 * @return the target kaleo node name of this kaleo transition
	 */
	@Override
	public String getTargetKaleoNodeName() {
		return model.getTargetKaleoNodeName();
	}

	/**
	 * Returns the user ID of this kaleo transition.
	 *
	 * @return the user ID of this kaleo transition
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo transition.
	 *
	 * @return the user name of this kaleo transition
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo transition.
	 *
	 * @return the user uuid of this kaleo transition
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this kaleo transition is default transition.
	 *
	 * @return <code>true</code> if this kaleo transition is default transition; <code>false</code> otherwise
	 */
	@Override
	public boolean isDefaultTransition() {
		return model.isDefaultTransition();
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
	 * Sets the company ID of this kaleo transition.
	 *
	 * @param companyId the company ID of this kaleo transition
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo transition.
	 *
	 * @param createDate the create date of this kaleo transition
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo transition.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo transition
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets whether this kaleo transition is default transition.
	 *
	 * @param defaultTransition the default transition of this kaleo transition
	 */
	@Override
	public void setDefaultTransition(boolean defaultTransition) {
		model.setDefaultTransition(defaultTransition);
	}

	/**
	 * Sets the description of this kaleo transition.
	 *
	 * @param description the description of this kaleo transition
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this kaleo transition.
	 *
	 * @param groupId the group ID of this kaleo transition
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo transition.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo transition
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo transition.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo transition
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo node ID of this kaleo transition.
	 *
	 * @param kaleoNodeId the kaleo node ID of this kaleo transition
	 */
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Sets the kaleo transition ID of this kaleo transition.
	 *
	 * @param kaleoTransitionId the kaleo transition ID of this kaleo transition
	 */
	@Override
	public void setKaleoTransitionId(long kaleoTransitionId) {
		model.setKaleoTransitionId(kaleoTransitionId);
	}

	/**
	 * Sets the label of this kaleo transition.
	 *
	 * @param label the label of this kaleo transition
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this kaleo transition in the language.
	 *
	 * @param label the localized label of this kaleo transition
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this kaleo transition in the language, and sets the default locale.
	 *
	 * @param label the localized label of this kaleo transition
	 * @param locale the locale of the language
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabel(
		String label, java.util.Locale locale, java.util.Locale defaultLocale) {

		model.setLabel(label, locale, defaultLocale);
	}

	@Override
	public void setLabelCurrentLanguageId(String languageId) {
		model.setLabelCurrentLanguageId(languageId);
	}

	/**
	 * Sets the localized labels of this kaleo transition from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this kaleo transition
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this kaleo transition from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this kaleo transition
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the modified date of this kaleo transition.
	 *
	 * @param modifiedDate the modified date of this kaleo transition
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo transition.
	 *
	 * @param mvccVersion the mvcc version of this kaleo transition
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo transition.
	 *
	 * @param name the name of this kaleo transition
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this kaleo transition.
	 *
	 * @param primaryKey the primary key of this kaleo transition
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the source kaleo node ID of this kaleo transition.
	 *
	 * @param sourceKaleoNodeId the source kaleo node ID of this kaleo transition
	 */
	@Override
	public void setSourceKaleoNodeId(long sourceKaleoNodeId) {
		model.setSourceKaleoNodeId(sourceKaleoNodeId);
	}

	/**
	 * Sets the source kaleo node name of this kaleo transition.
	 *
	 * @param sourceKaleoNodeName the source kaleo node name of this kaleo transition
	 */
	@Override
	public void setSourceKaleoNodeName(String sourceKaleoNodeName) {
		model.setSourceKaleoNodeName(sourceKaleoNodeName);
	}

	/**
	 * Sets the target kaleo node ID of this kaleo transition.
	 *
	 * @param targetKaleoNodeId the target kaleo node ID of this kaleo transition
	 */
	@Override
	public void setTargetKaleoNodeId(long targetKaleoNodeId) {
		model.setTargetKaleoNodeId(targetKaleoNodeId);
	}

	/**
	 * Sets the target kaleo node name of this kaleo transition.
	 *
	 * @param targetKaleoNodeName the target kaleo node name of this kaleo transition
	 */
	@Override
	public void setTargetKaleoNodeName(String targetKaleoNodeName) {
		model.setTargetKaleoNodeName(targetKaleoNodeName);
	}

	/**
	 * Sets the user ID of this kaleo transition.
	 *
	 * @param userId the user ID of this kaleo transition
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo transition.
	 *
	 * @param userName the user name of this kaleo transition
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo transition.
	 *
	 * @param userUuid the user uuid of this kaleo transition
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
	public Map<String, Function<KaleoTransition, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoTransition, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoTransitionWrapper wrap(KaleoTransition kaleoTransition) {
		return new KaleoTransitionWrapper(kaleoTransition);
	}

}