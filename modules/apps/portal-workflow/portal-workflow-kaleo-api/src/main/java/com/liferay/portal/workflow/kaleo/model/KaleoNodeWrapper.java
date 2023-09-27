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
 * This class is a wrapper for {@link KaleoNode}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoNode
 * @generated
 */
public class KaleoNodeWrapper
	extends BaseModelWrapper<KaleoNode>
	implements KaleoNode, ModelWrapper<KaleoNode> {

	public KaleoNodeWrapper(KaleoNode kaleoNode) {
		super(kaleoNode);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("kaleoNodeId", getKaleoNodeId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("kaleoDefinitionId", getKaleoDefinitionId());
		attributes.put(
			"kaleoDefinitionVersionId", getKaleoDefinitionVersionId());
		attributes.put("name", getName());
		attributes.put("label", getLabel());
		attributes.put("metadata", getMetadata());
		attributes.put("description", getDescription());
		attributes.put("type", getType());
		attributes.put("initial", isInitial());
		attributes.put("terminal", isTerminal());

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

		Long kaleoNodeId = (Long)attributes.get("kaleoNodeId");

		if (kaleoNodeId != null) {
			setKaleoNodeId(kaleoNodeId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String label = (String)attributes.get("label");

		if (label != null) {
			setLabel(label);
		}

		String metadata = (String)attributes.get("metadata");

		if (metadata != null) {
			setMetadata(metadata);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Boolean initial = (Boolean)attributes.get("initial");

		if (initial != null) {
			setInitial(initial);
		}

		Boolean terminal = (Boolean)attributes.get("terminal");

		if (terminal != null) {
			setTerminal(terminal);
		}
	}

	@Override
	public KaleoNode cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return model.getAvailableLanguageIds();
	}

	/**
	 * Returns the company ID of this kaleo node.
	 *
	 * @return the company ID of this kaleo node
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this kaleo node.
	 *
	 * @return the create date of this kaleo node
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this kaleo node.
	 *
	 * @return the ct collection ID of this kaleo node
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public KaleoTransition getDefaultKaleoTransition()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getDefaultKaleoTransition();
	}

	@Override
	public String getDefaultLanguageId() {
		return model.getDefaultLanguageId();
	}

	/**
	 * Returns the description of this kaleo node.
	 *
	 * @return the description of this kaleo node
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this kaleo node.
	 *
	 * @return the group ID of this kaleo node
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the initial of this kaleo node.
	 *
	 * @return the initial of this kaleo node
	 */
	@Override
	public boolean getInitial() {
		return model.getInitial();
	}

	/**
	 * Returns the kaleo definition ID of this kaleo node.
	 *
	 * @return the kaleo definition ID of this kaleo node
	 */
	@Override
	public long getKaleoDefinitionId() {
		return model.getKaleoDefinitionId();
	}

	/**
	 * Returns the kaleo definition version ID of this kaleo node.
	 *
	 * @return the kaleo definition version ID of this kaleo node
	 */
	@Override
	public long getKaleoDefinitionVersionId() {
		return model.getKaleoDefinitionVersionId();
	}

	/**
	 * Returns the kaleo node ID of this kaleo node.
	 *
	 * @return the kaleo node ID of this kaleo node
	 */
	@Override
	public long getKaleoNodeId() {
		return model.getKaleoNodeId();
	}

	@Override
	public KaleoTransition getKaleoTransition(String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getKaleoTransition(name);
	}

	@Override
	public java.util.List<KaleoTransition> getKaleoTransitions() {
		return model.getKaleoTransitions();
	}

	/**
	 * Returns the label of this kaleo node.
	 *
	 * @return the label of this kaleo node
	 */
	@Override
	public String getLabel() {
		return model.getLabel();
	}

	/**
	 * Returns the localized label of this kaleo node in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param locale the locale of the language
	 * @return the localized label of this kaleo node
	 */
	@Override
	public String getLabel(java.util.Locale locale) {
		return model.getLabel(locale);
	}

	/**
	 * Returns the localized label of this kaleo node in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param locale the local of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this kaleo node. If <code>useDefault</code> is <code>false</code> and no localization exists for the requested language, an empty string will be returned.
	 */
	@Override
	public String getLabel(java.util.Locale locale, boolean useDefault) {
		return model.getLabel(locale, useDefault);
	}

	/**
	 * Returns the localized label of this kaleo node in the language. Uses the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @return the localized label of this kaleo node
	 */
	@Override
	public String getLabel(String languageId) {
		return model.getLabel(languageId);
	}

	/**
	 * Returns the localized label of this kaleo node in the language, optionally using the default language if no localization exists for the requested language.
	 *
	 * @param languageId the ID of the language
	 * @param useDefault whether to use the default language if no localization exists for the requested language
	 * @return the localized label of this kaleo node
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
	 * Returns a map of the locales and localized labels of this kaleo node.
	 *
	 * @return the locales and localized labels of this kaleo node
	 */
	@Override
	public Map<java.util.Locale, String> getLabelMap() {
		return model.getLabelMap();
	}

	/**
	 * Returns the metadata of this kaleo node.
	 *
	 * @return the metadata of this kaleo node
	 */
	@Override
	public String getMetadata() {
		return model.getMetadata();
	}

	/**
	 * Returns the modified date of this kaleo node.
	 *
	 * @return the modified date of this kaleo node
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this kaleo node.
	 *
	 * @return the mvcc version of this kaleo node
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this kaleo node.
	 *
	 * @return the name of this kaleo node
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this kaleo node.
	 *
	 * @return the primary key of this kaleo node
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the terminal of this kaleo node.
	 *
	 * @return the terminal of this kaleo node
	 */
	@Override
	public boolean getTerminal() {
		return model.getTerminal();
	}

	/**
	 * Returns the type of this kaleo node.
	 *
	 * @return the type of this kaleo node
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this kaleo node.
	 *
	 * @return the user ID of this kaleo node
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this kaleo node.
	 *
	 * @return the user name of this kaleo node
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this kaleo node.
	 *
	 * @return the user uuid of this kaleo node
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean hasKaleoTransition() {
		return model.hasKaleoTransition();
	}

	/**
	 * Returns <code>true</code> if this kaleo node is initial.
	 *
	 * @return <code>true</code> if this kaleo node is initial; <code>false</code> otherwise
	 */
	@Override
	public boolean isInitial() {
		return model.isInitial();
	}

	/**
	 * Returns <code>true</code> if this kaleo node is terminal.
	 *
	 * @return <code>true</code> if this kaleo node is terminal; <code>false</code> otherwise
	 */
	@Override
	public boolean isTerminal() {
		return model.isTerminal();
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
	 * Sets the company ID of this kaleo node.
	 *
	 * @param companyId the company ID of this kaleo node
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this kaleo node.
	 *
	 * @param createDate the create date of this kaleo node
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this kaleo node.
	 *
	 * @param ctCollectionId the ct collection ID of this kaleo node
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the description of this kaleo node.
	 *
	 * @param description the description of this kaleo node
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this kaleo node.
	 *
	 * @param groupId the group ID of this kaleo node
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets whether this kaleo node is initial.
	 *
	 * @param initial the initial of this kaleo node
	 */
	@Override
	public void setInitial(boolean initial) {
		model.setInitial(initial);
	}

	/**
	 * Sets the kaleo definition ID of this kaleo node.
	 *
	 * @param kaleoDefinitionId the kaleo definition ID of this kaleo node
	 */
	@Override
	public void setKaleoDefinitionId(long kaleoDefinitionId) {
		model.setKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	 * Sets the kaleo definition version ID of this kaleo node.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID of this kaleo node
	 */
	@Override
	public void setKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		model.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
	}

	/**
	 * Sets the kaleo node ID of this kaleo node.
	 *
	 * @param kaleoNodeId the kaleo node ID of this kaleo node
	 */
	@Override
	public void setKaleoNodeId(long kaleoNodeId) {
		model.setKaleoNodeId(kaleoNodeId);
	}

	/**
	 * Sets the label of this kaleo node.
	 *
	 * @param label the label of this kaleo node
	 */
	@Override
	public void setLabel(String label) {
		model.setLabel(label);
	}

	/**
	 * Sets the localized label of this kaleo node in the language.
	 *
	 * @param label the localized label of this kaleo node
	 * @param locale the locale of the language
	 */
	@Override
	public void setLabel(String label, java.util.Locale locale) {
		model.setLabel(label, locale);
	}

	/**
	 * Sets the localized label of this kaleo node in the language, and sets the default locale.
	 *
	 * @param label the localized label of this kaleo node
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
	 * Sets the localized labels of this kaleo node from the map of locales and localized labels.
	 *
	 * @param labelMap the locales and localized labels of this kaleo node
	 */
	@Override
	public void setLabelMap(Map<java.util.Locale, String> labelMap) {
		model.setLabelMap(labelMap);
	}

	/**
	 * Sets the localized labels of this kaleo node from the map of locales and localized labels, and sets the default locale.
	 *
	 * @param labelMap the locales and localized labels of this kaleo node
	 * @param defaultLocale the default locale
	 */
	@Override
	public void setLabelMap(
		Map<java.util.Locale, String> labelMap,
		java.util.Locale defaultLocale) {

		model.setLabelMap(labelMap, defaultLocale);
	}

	/**
	 * Sets the metadata of this kaleo node.
	 *
	 * @param metadata the metadata of this kaleo node
	 */
	@Override
	public void setMetadata(String metadata) {
		model.setMetadata(metadata);
	}

	/**
	 * Sets the modified date of this kaleo node.
	 *
	 * @param modifiedDate the modified date of this kaleo node
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this kaleo node.
	 *
	 * @param mvccVersion the mvcc version of this kaleo node
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this kaleo node.
	 *
	 * @param name the name of this kaleo node
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this kaleo node.
	 *
	 * @param primaryKey the primary key of this kaleo node
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets whether this kaleo node is terminal.
	 *
	 * @param terminal the terminal of this kaleo node
	 */
	@Override
	public void setTerminal(boolean terminal) {
		model.setTerminal(terminal);
	}

	/**
	 * Sets the type of this kaleo node.
	 *
	 * @param type the type of this kaleo node
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this kaleo node.
	 *
	 * @param userId the user ID of this kaleo node
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this kaleo node.
	 *
	 * @param userName the user name of this kaleo node
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this kaleo node.
	 *
	 * @param userUuid the user uuid of this kaleo node
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
	public Map<String, Function<KaleoNode, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<KaleoNode, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected KaleoNodeWrapper wrap(KaleoNode kaleoNode) {
		return new KaleoNodeWrapper(kaleoNode);
	}

}