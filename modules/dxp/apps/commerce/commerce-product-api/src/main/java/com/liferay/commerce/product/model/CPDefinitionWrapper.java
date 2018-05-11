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

package com.liferay.commerce.product.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link CPDefinition}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDefinition
 * @generated
 */
@ProviderType
public class CPDefinitionWrapper implements CPDefinition,
	ModelWrapper<CPDefinition> {
	public CPDefinitionWrapper(CPDefinition cpDefinition) {
		_cpDefinition = cpDefinition;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return CPDefinition.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDefinitionId", getCPDefinitionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("productTypeName", getProductTypeName());
		attributes.put("availableIndividually", isAvailableIndividually());
		attributes.put("ignoreSKUCombinations", isIgnoreSKUCombinations());
		attributes.put("shippable", isShippable());
		attributes.put("freeShipping", isFreeShipping());
		attributes.put("shipSeparately", isShipSeparately());
		attributes.put("shippingExtraPrice", getShippingExtraPrice());
		attributes.put("width", getWidth());
		attributes.put("height", getHeight());
		attributes.put("depth", getDepth());
		attributes.put("weight", getWeight());
		attributes.put("CPTaxCategoryId", getCPTaxCategoryId());
		attributes.put("taxExempt", isTaxExempt());
		attributes.put("telcoOrElectronics", isTelcoOrElectronics());
		attributes.put("DDMStructureKey", getDDMStructureKey());
		attributes.put("published", isPublished());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("displayDate", getDisplayDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("lastPublishDate", getLastPublishDate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("defaultLanguageId", getDefaultLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
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

		String productTypeName = (String)attributes.get("productTypeName");

		if (productTypeName != null) {
			setProductTypeName(productTypeName);
		}

		Boolean availableIndividually = (Boolean)attributes.get(
				"availableIndividually");

		if (availableIndividually != null) {
			setAvailableIndividually(availableIndividually);
		}

		Boolean ignoreSKUCombinations = (Boolean)attributes.get(
				"ignoreSKUCombinations");

		if (ignoreSKUCombinations != null) {
			setIgnoreSKUCombinations(ignoreSKUCombinations);
		}

		Boolean shippable = (Boolean)attributes.get("shippable");

		if (shippable != null) {
			setShippable(shippable);
		}

		Boolean freeShipping = (Boolean)attributes.get("freeShipping");

		if (freeShipping != null) {
			setFreeShipping(freeShipping);
		}

		Boolean shipSeparately = (Boolean)attributes.get("shipSeparately");

		if (shipSeparately != null) {
			setShipSeparately(shipSeparately);
		}

		Double shippingExtraPrice = (Double)attributes.get("shippingExtraPrice");

		if (shippingExtraPrice != null) {
			setShippingExtraPrice(shippingExtraPrice);
		}

		Double width = (Double)attributes.get("width");

		if (width != null) {
			setWidth(width);
		}

		Double height = (Double)attributes.get("height");

		if (height != null) {
			setHeight(height);
		}

		Double depth = (Double)attributes.get("depth");

		if (depth != null) {
			setDepth(depth);
		}

		Double weight = (Double)attributes.get("weight");

		if (weight != null) {
			setWeight(weight);
		}

		Long CPTaxCategoryId = (Long)attributes.get("CPTaxCategoryId");

		if (CPTaxCategoryId != null) {
			setCPTaxCategoryId(CPTaxCategoryId);
		}

		Boolean taxExempt = (Boolean)attributes.get("taxExempt");

		if (taxExempt != null) {
			setTaxExempt(taxExempt);
		}

		Boolean telcoOrElectronics = (Boolean)attributes.get(
				"telcoOrElectronics");

		if (telcoOrElectronics != null) {
			setTelcoOrElectronics(telcoOrElectronics);
		}

		String DDMStructureKey = (String)attributes.get("DDMStructureKey");

		if (DDMStructureKey != null) {
			setDDMStructureKey(DDMStructureKey);
		}

		Boolean published = (Boolean)attributes.get("published");

		if (published != null) {
			setPublished(published);
		}

		String externalReferenceCode = (String)attributes.get(
				"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Date displayDate = (Date)attributes.get("displayDate");

		if (displayDate != null) {
			setDisplayDate(displayDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}
	}

	@Override
	public Object clone() {
		return new CPDefinitionWrapper((CPDefinition)_cpDefinition.clone());
	}

	@Override
	public int compareTo(CPDefinition cpDefinition) {
		return _cpDefinition.compareTo(cpDefinition);
	}

	/**
	* Returns the available individually of this cp definition.
	*
	* @return the available individually of this cp definition
	*/
	@Override
	public boolean getAvailableIndividually() {
		return _cpDefinition.getAvailableIndividually();
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _cpDefinition.getAvailableLanguageIds();
	}

	/**
	* Returns the company ID of this cp definition.
	*
	* @return the company ID of this cp definition
	*/
	@Override
	public long getCompanyId() {
		return _cpDefinition.getCompanyId();
	}

	/**
	* Returns the cp definition ID of this cp definition.
	*
	* @return the cp definition ID of this cp definition
	*/
	@Override
	public long getCPDefinitionId() {
		return _cpDefinition.getCPDefinitionId();
	}

	@Override
	public java.util.List<CPDefinitionOptionRel> getCPDefinitionOptionRels() {
		return _cpDefinition.getCPDefinitionOptionRels();
	}

	@Override
	public java.util.List<CPDefinitionSpecificationOptionValue> getCPDefinitionSpecificationOptionValues() {
		return _cpDefinition.getCPDefinitionSpecificationOptionValues();
	}

	@Override
	public java.util.List<CPInstance> getCPInstances() {
		return _cpDefinition.getCPInstances();
	}

	/**
	* Returns the cp tax category ID of this cp definition.
	*
	* @return the cp tax category ID of this cp definition
	*/
	@Override
	public long getCPTaxCategoryId() {
		return _cpDefinition.getCPTaxCategoryId();
	}

	/**
	* Returns the create date of this cp definition.
	*
	* @return the create date of this cp definition
	*/
	@Override
	public Date getCreateDate() {
		return _cpDefinition.getCreateDate();
	}

	/**
	* Returns the ddm structure key of this cp definition.
	*
	* @return the ddm structure key of this cp definition
	*/
	@Override
	public String getDDMStructureKey() {
		return _cpDefinition.getDDMStructureKey();
	}

	@Override
	public String getDefaultImageThumbnailSrc(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay)
		throws Exception {
		return _cpDefinition.getDefaultImageThumbnailSrc(themeDisplay);
	}

	/**
	* Returns the default language ID of this cp definition.
	*
	* @return the default language ID of this cp definition
	*/
	@Override
	public String getDefaultLanguageId() {
		return _cpDefinition.getDefaultLanguageId();
	}

	/**
	* Returns the depth of this cp definition.
	*
	* @return the depth of this cp definition
	*/
	@Override
	public double getDepth() {
		return _cpDefinition.getDepth();
	}

	@Override
	public String getDescription() {
		return _cpDefinition.getDescription();
	}

	@Override
	public String getDescription(String languageId) {
		return _cpDefinition.getDescription(languageId);
	}

	@Override
	public String getDescription(String languageId, boolean useDefault) {
		return _cpDefinition.getDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getDescriptionMap() {
		return _cpDefinition.getDescriptionMap();
	}

	@Override
	public String getDescriptionMapAsXML() {
		return _cpDefinition.getDescriptionMapAsXML();
	}

	/**
	* Returns the display date of this cp definition.
	*
	* @return the display date of this cp definition
	*/
	@Override
	public Date getDisplayDate() {
		return _cpDefinition.getDisplayDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDefinition.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this cp definition.
	*
	* @return the expiration date of this cp definition
	*/
	@Override
	public Date getExpirationDate() {
		return _cpDefinition.getExpirationDate();
	}

	/**
	* Returns the external reference code of this cp definition.
	*
	* @return the external reference code of this cp definition
	*/
	@Override
	public String getExternalReferenceCode() {
		return _cpDefinition.getExternalReferenceCode();
	}

	/**
	* Returns the free shipping of this cp definition.
	*
	* @return the free shipping of this cp definition
	*/
	@Override
	public boolean getFreeShipping() {
		return _cpDefinition.getFreeShipping();
	}

	/**
	* Returns the group ID of this cp definition.
	*
	* @return the group ID of this cp definition
	*/
	@Override
	public long getGroupId() {
		return _cpDefinition.getGroupId();
	}

	/**
	* Returns the height of this cp definition.
	*
	* @return the height of this cp definition
	*/
	@Override
	public double getHeight() {
		return _cpDefinition.getHeight();
	}

	/**
	* Returns the ignore sku combinations of this cp definition.
	*
	* @return the ignore sku combinations of this cp definition
	*/
	@Override
	public boolean getIgnoreSKUCombinations() {
		return _cpDefinition.getIgnoreSKUCombinations();
	}

	@Override
	public Map<String, String> getLanguageIdToDescriptionMap() {
		return _cpDefinition.getLanguageIdToDescriptionMap();
	}

	@Override
	public Map<String, String> getLanguageIdToMetaDescriptionMap() {
		return _cpDefinition.getLanguageIdToMetaDescriptionMap();
	}

	@Override
	public Map<String, String> getLanguageIdToMetaKeywordsMap() {
		return _cpDefinition.getLanguageIdToMetaKeywordsMap();
	}

	@Override
	public Map<String, String> getLanguageIdToMetaTitleMap() {
		return _cpDefinition.getLanguageIdToMetaTitleMap();
	}

	@Override
	public Map<String, String> getLanguageIdToNameMap() {
		return _cpDefinition.getLanguageIdToNameMap();
	}

	@Override
	public Map<String, String> getLanguageIdToShortDescriptionMap() {
		return _cpDefinition.getLanguageIdToShortDescriptionMap();
	}

	/**
	* Returns the last publish date of this cp definition.
	*
	* @return the last publish date of this cp definition
	*/
	@Override
	public Date getLastPublishDate() {
		return _cpDefinition.getLastPublishDate();
	}

	@Override
	public String getLayoutUuid() {
		return _cpDefinition.getLayoutUuid();
	}

	@Override
	public String getMetaDescription() {
		return _cpDefinition.getMetaDescription();
	}

	@Override
	public String getMetaDescription(String languageId) {
		return _cpDefinition.getMetaDescription(languageId);
	}

	@Override
	public String getMetaDescription(String languageId, boolean useDefault) {
		return _cpDefinition.getMetaDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getMetaDescriptionMap() {
		return _cpDefinition.getMetaDescriptionMap();
	}

	@Override
	public String getMetaDescriptionMapAsXML() {
		return _cpDefinition.getMetaDescriptionMapAsXML();
	}

	@Override
	public String getMetaKeywords() {
		return _cpDefinition.getMetaKeywords();
	}

	@Override
	public String getMetaKeywords(String languageId) {
		return _cpDefinition.getMetaKeywords(languageId);
	}

	@Override
	public String getMetaKeywords(String languageId, boolean useDefault) {
		return _cpDefinition.getMetaKeywords(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getMetaKeywordsMap() {
		return _cpDefinition.getMetaKeywordsMap();
	}

	@Override
	public String getMetaKeywordsMapAsXML() {
		return _cpDefinition.getMetaKeywordsMapAsXML();
	}

	@Override
	public String getMetaTitle() {
		return _cpDefinition.getMetaTitle();
	}

	@Override
	public String getMetaTitle(String languageId) {
		return _cpDefinition.getMetaTitle(languageId);
	}

	@Override
	public String getMetaTitle(String languageId, boolean useDefault) {
		return _cpDefinition.getMetaTitle(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getMetaTitleMap() {
		return _cpDefinition.getMetaTitleMap();
	}

	@Override
	public String getMetaTitleMapAsXML() {
		return _cpDefinition.getMetaTitleMapAsXML();
	}

	/**
	* Returns the modified date of this cp definition.
	*
	* @return the modified date of this cp definition
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDefinition.getModifiedDate();
	}

	@Override
	public String getName() {
		return _cpDefinition.getName();
	}

	@Override
	public String getName(String languageId) {
		return _cpDefinition.getName(languageId);
	}

	@Override
	public String getName(String languageId, boolean useDefault) {
		return _cpDefinition.getName(languageId, useDefault);
	}

	@Override
	public String getNameCurrentValue() {
		return _cpDefinition.getNameCurrentValue();
	}

	@Override
	public Map<java.util.Locale, String> getNameMap() {
		return _cpDefinition.getNameMap();
	}

	@Override
	public String getNameMapAsXML() {
		return _cpDefinition.getNameMapAsXML();
	}

	/**
	* Returns the primary key of this cp definition.
	*
	* @return the primary key of this cp definition
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDefinition.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDefinition.getPrimaryKeyObj();
	}

	/**
	* Returns the product type name of this cp definition.
	*
	* @return the product type name of this cp definition
	*/
	@Override
	public String getProductTypeName() {
		return _cpDefinition.getProductTypeName();
	}

	/**
	* Returns the published of this cp definition.
	*
	* @return the published of this cp definition
	*/
	@Override
	public boolean getPublished() {
		return _cpDefinition.getPublished();
	}

	/**
	* Returns the shippable of this cp definition.
	*
	* @return the shippable of this cp definition
	*/
	@Override
	public boolean getShippable() {
		return _cpDefinition.getShippable();
	}

	/**
	* Returns the shipping extra price of this cp definition.
	*
	* @return the shipping extra price of this cp definition
	*/
	@Override
	public double getShippingExtraPrice() {
		return _cpDefinition.getShippingExtraPrice();
	}

	/**
	* Returns the ship separately of this cp definition.
	*
	* @return the ship separately of this cp definition
	*/
	@Override
	public boolean getShipSeparately() {
		return _cpDefinition.getShipSeparately();
	}

	@Override
	public String getShortDescription() {
		return _cpDefinition.getShortDescription();
	}

	@Override
	public String getShortDescription(String languageId) {
		return _cpDefinition.getShortDescription(languageId);
	}

	@Override
	public String getShortDescription(String languageId, boolean useDefault) {
		return _cpDefinition.getShortDescription(languageId, useDefault);
	}

	@Override
	public Map<java.util.Locale, String> getShortDescriptionMap() {
		return _cpDefinition.getShortDescriptionMap();
	}

	@Override
	public String getShortDescriptionMapAsXML() {
		return _cpDefinition.getShortDescriptionMapAsXML();
	}

	/**
	* Returns the status of this cp definition.
	*
	* @return the status of this cp definition
	*/
	@Override
	public int getStatus() {
		return _cpDefinition.getStatus();
	}

	/**
	* Returns the status by user ID of this cp definition.
	*
	* @return the status by user ID of this cp definition
	*/
	@Override
	public long getStatusByUserId() {
		return _cpDefinition.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this cp definition.
	*
	* @return the status by user name of this cp definition
	*/
	@Override
	public String getStatusByUserName() {
		return _cpDefinition.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this cp definition.
	*
	* @return the status by user uuid of this cp definition
	*/
	@Override
	public String getStatusByUserUuid() {
		return _cpDefinition.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this cp definition.
	*
	* @return the status date of this cp definition
	*/
	@Override
	public Date getStatusDate() {
		return _cpDefinition.getStatusDate();
	}

	/**
	* Returns the tax exempt of this cp definition.
	*
	* @return the tax exempt of this cp definition
	*/
	@Override
	public boolean getTaxExempt() {
		return _cpDefinition.getTaxExempt();
	}

	/**
	* Returns the telco or electronics of this cp definition.
	*
	* @return the telco or electronics of this cp definition
	*/
	@Override
	public boolean getTelcoOrElectronics() {
		return _cpDefinition.getTelcoOrElectronics();
	}

	/**
	* Returns the trash entry created when this cp definition was moved to the Recycle Bin. The trash entry may belong to one of the ancestors of this cp definition.
	*
	* @return the trash entry created when this cp definition was moved to the Recycle Bin
	*/
	@Override
	public com.liferay.trash.kernel.model.TrashEntry getTrashEntry()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cpDefinition.getTrashEntry();
	}

	/**
	* Returns the class primary key of the trash entry for this cp definition.
	*
	* @return the class primary key of the trash entry for this cp definition
	*/
	@Override
	public long getTrashEntryClassPK() {
		return _cpDefinition.getTrashEntryClassPK();
	}

	/**
	* Returns the trash handler for this cp definition.
	*
	* @return the trash handler for this cp definition
	* @deprecated As of 7.0.0, with no direct replacement
	*/
	@Deprecated
	@Override
	public com.liferay.portal.kernel.trash.TrashHandler getTrashHandler() {
		return _cpDefinition.getTrashHandler();
	}

	@Override
	public Map<java.util.Locale, String> getUrlTitleMap() {
		return _cpDefinition.getUrlTitleMap();
	}

	/**
	* Returns the user ID of this cp definition.
	*
	* @return the user ID of this cp definition
	*/
	@Override
	public long getUserId() {
		return _cpDefinition.getUserId();
	}

	/**
	* Returns the user name of this cp definition.
	*
	* @return the user name of this cp definition
	*/
	@Override
	public String getUserName() {
		return _cpDefinition.getUserName();
	}

	/**
	* Returns the user uuid of this cp definition.
	*
	* @return the user uuid of this cp definition
	*/
	@Override
	public String getUserUuid() {
		return _cpDefinition.getUserUuid();
	}

	/**
	* Returns the uuid of this cp definition.
	*
	* @return the uuid of this cp definition
	*/
	@Override
	public String getUuid() {
		return _cpDefinition.getUuid();
	}

	/**
	* Returns the weight of this cp definition.
	*
	* @return the weight of this cp definition
	*/
	@Override
	public double getWeight() {
		return _cpDefinition.getWeight();
	}

	/**
	* Returns the width of this cp definition.
	*
	* @return the width of this cp definition
	*/
	@Override
	public double getWidth() {
		return _cpDefinition.getWidth();
	}

	@Override
	public int hashCode() {
		return _cpDefinition.hashCode();
	}

	/**
	* Returns <code>true</code> if this cp definition is approved.
	*
	* @return <code>true</code> if this cp definition is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _cpDefinition.isApproved();
	}

	/**
	* Returns <code>true</code> if this cp definition is available individually.
	*
	* @return <code>true</code> if this cp definition is available individually; <code>false</code> otherwise
	*/
	@Override
	public boolean isAvailableIndividually() {
		return _cpDefinition.isAvailableIndividually();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDefinition.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition is denied.
	*
	* @return <code>true</code> if this cp definition is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _cpDefinition.isDenied();
	}

	/**
	* Returns <code>true</code> if this cp definition is a draft.
	*
	* @return <code>true</code> if this cp definition is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _cpDefinition.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDefinition.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp definition is expired.
	*
	* @return <code>true</code> if this cp definition is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _cpDefinition.isExpired();
	}

	/**
	* Returns <code>true</code> if this cp definition is free shipping.
	*
	* @return <code>true</code> if this cp definition is free shipping; <code>false</code> otherwise
	*/
	@Override
	public boolean isFreeShipping() {
		return _cpDefinition.isFreeShipping();
	}

	/**
	* Returns <code>true</code> if this cp definition is ignore sku combinations.
	*
	* @return <code>true</code> if this cp definition is ignore sku combinations; <code>false</code> otherwise
	*/
	@Override
	public boolean isIgnoreSKUCombinations() {
		return _cpDefinition.isIgnoreSKUCombinations();
	}

	/**
	* Returns <code>true</code> if this cp definition is inactive.
	*
	* @return <code>true</code> if this cp definition is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _cpDefinition.isInactive();
	}

	/**
	* Returns <code>true</code> if this cp definition is incomplete.
	*
	* @return <code>true</code> if this cp definition is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _cpDefinition.isIncomplete();
	}

	/**
	* Returns <code>true</code> if this cp definition is in the Recycle Bin.
	*
	* @return <code>true</code> if this cp definition is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrash() {
		return _cpDefinition.isInTrash();
	}

	/**
	* Returns <code>true</code> if the parent of this cp definition is in the Recycle Bin.
	*
	* @return <code>true</code> if the parent of this cp definition is in the Recycle Bin; <code>false</code> otherwise
	*/
	@Override
	public boolean isInTrashContainer() {
		return _cpDefinition.isInTrashContainer();
	}

	@Override
	public boolean isInTrashExplicitly() {
		return _cpDefinition.isInTrashExplicitly();
	}

	@Override
	public boolean isInTrashImplicitly() {
		return _cpDefinition.isInTrashImplicitly();
	}

	@Override
	public boolean isNew() {
		return _cpDefinition.isNew();
	}

	/**
	* Returns <code>true</code> if this cp definition is pending.
	*
	* @return <code>true</code> if this cp definition is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _cpDefinition.isPending();
	}

	/**
	* Returns <code>true</code> if this cp definition is published.
	*
	* @return <code>true</code> if this cp definition is published; <code>false</code> otherwise
	*/
	@Override
	public boolean isPublished() {
		return _cpDefinition.isPublished();
	}

	/**
	* Returns <code>true</code> if this cp definition is scheduled.
	*
	* @return <code>true</code> if this cp definition is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _cpDefinition.isScheduled();
	}

	/**
	* Returns <code>true</code> if this cp definition is shippable.
	*
	* @return <code>true</code> if this cp definition is shippable; <code>false</code> otherwise
	*/
	@Override
	public boolean isShippable() {
		return _cpDefinition.isShippable();
	}

	/**
	* Returns <code>true</code> if this cp definition is ship separately.
	*
	* @return <code>true</code> if this cp definition is ship separately; <code>false</code> otherwise
	*/
	@Override
	public boolean isShipSeparately() {
		return _cpDefinition.isShipSeparately();
	}

	/**
	* Returns <code>true</code> if this cp definition is tax exempt.
	*
	* @return <code>true</code> if this cp definition is tax exempt; <code>false</code> otherwise
	*/
	@Override
	public boolean isTaxExempt() {
		return _cpDefinition.isTaxExempt();
	}

	/**
	* Returns <code>true</code> if this cp definition is telco or electronics.
	*
	* @return <code>true</code> if this cp definition is telco or electronics; <code>false</code> otherwise
	*/
	@Override
	public boolean isTelcoOrElectronics() {
		return _cpDefinition.isTelcoOrElectronics();
	}

	@Override
	public void persist() {
		_cpDefinition.persist();
	}

	/**
	* Sets whether this cp definition is available individually.
	*
	* @param availableIndividually the available individually of this cp definition
	*/
	@Override
	public void setAvailableIndividually(boolean availableIndividually) {
		_cpDefinition.setAvailableIndividually(availableIndividually);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDefinition.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this cp definition.
	*
	* @param companyId the company ID of this cp definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDefinition.setCompanyId(companyId);
	}

	/**
	* Sets the cp definition ID of this cp definition.
	*
	* @param CPDefinitionId the cp definition ID of this cp definition
	*/
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		_cpDefinition.setCPDefinitionId(CPDefinitionId);
	}

	/**
	* Sets the cp tax category ID of this cp definition.
	*
	* @param CPTaxCategoryId the cp tax category ID of this cp definition
	*/
	@Override
	public void setCPTaxCategoryId(long CPTaxCategoryId) {
		_cpDefinition.setCPTaxCategoryId(CPTaxCategoryId);
	}

	/**
	* Sets the create date of this cp definition.
	*
	* @param createDate the create date of this cp definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDefinition.setCreateDate(createDate);
	}

	/**
	* Sets the ddm structure key of this cp definition.
	*
	* @param DDMStructureKey the ddm structure key of this cp definition
	*/
	@Override
	public void setDDMStructureKey(String DDMStructureKey) {
		_cpDefinition.setDDMStructureKey(DDMStructureKey);
	}

	/**
	* Sets the default language ID of this cp definition.
	*
	* @param defaultLanguageId the default language ID of this cp definition
	*/
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_cpDefinition.setDefaultLanguageId(defaultLanguageId);
	}

	/**
	* Sets the depth of this cp definition.
	*
	* @param depth the depth of this cp definition
	*/
	@Override
	public void setDepth(double depth) {
		_cpDefinition.setDepth(depth);
	}

	@Override
	public void setDescriptionMap(Map<java.util.Locale, String> descriptionMap) {
		_cpDefinition.setDescriptionMap(descriptionMap);
	}

	/**
	* Sets the display date of this cp definition.
	*
	* @param displayDate the display date of this cp definition
	*/
	@Override
	public void setDisplayDate(Date displayDate) {
		_cpDefinition.setDisplayDate(displayDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDefinition.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDefinition.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDefinition.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this cp definition.
	*
	* @param expirationDate the expiration date of this cp definition
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_cpDefinition.setExpirationDate(expirationDate);
	}

	/**
	* Sets the external reference code of this cp definition.
	*
	* @param externalReferenceCode the external reference code of this cp definition
	*/
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_cpDefinition.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	* Sets whether this cp definition is free shipping.
	*
	* @param freeShipping the free shipping of this cp definition
	*/
	@Override
	public void setFreeShipping(boolean freeShipping) {
		_cpDefinition.setFreeShipping(freeShipping);
	}

	/**
	* Sets the group ID of this cp definition.
	*
	* @param groupId the group ID of this cp definition
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDefinition.setGroupId(groupId);
	}

	/**
	* Sets the height of this cp definition.
	*
	* @param height the height of this cp definition
	*/
	@Override
	public void setHeight(double height) {
		_cpDefinition.setHeight(height);
	}

	/**
	* Sets whether this cp definition is ignore sku combinations.
	*
	* @param ignoreSKUCombinations the ignore sku combinations of this cp definition
	*/
	@Override
	public void setIgnoreSKUCombinations(boolean ignoreSKUCombinations) {
		_cpDefinition.setIgnoreSKUCombinations(ignoreSKUCombinations);
	}

	/**
	* Sets the last publish date of this cp definition.
	*
	* @param lastPublishDate the last publish date of this cp definition
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		_cpDefinition.setLastPublishDate(lastPublishDate);
	}

	@Override
	public void setLayoutUuid(String layoutUuid) {
		_cpDefinition.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets the modified date of this cp definition.
	*
	* @param modifiedDate the modified date of this cp definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDefinition.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNameMap(Map<java.util.Locale, String> nameMap) {
		_cpDefinition.setNameMap(nameMap);
	}

	@Override
	public void setNew(boolean n) {
		_cpDefinition.setNew(n);
	}

	/**
	* Sets the primary key of this cp definition.
	*
	* @param primaryKey the primary key of this cp definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDefinition.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDefinition.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the product type name of this cp definition.
	*
	* @param productTypeName the product type name of this cp definition
	*/
	@Override
	public void setProductTypeName(String productTypeName) {
		_cpDefinition.setProductTypeName(productTypeName);
	}

	/**
	* Sets whether this cp definition is published.
	*
	* @param published the published of this cp definition
	*/
	@Override
	public void setPublished(boolean published) {
		_cpDefinition.setPublished(published);
	}

	/**
	* Sets whether this cp definition is shippable.
	*
	* @param shippable the shippable of this cp definition
	*/
	@Override
	public void setShippable(boolean shippable) {
		_cpDefinition.setShippable(shippable);
	}

	/**
	* Sets the shipping extra price of this cp definition.
	*
	* @param shippingExtraPrice the shipping extra price of this cp definition
	*/
	@Override
	public void setShippingExtraPrice(double shippingExtraPrice) {
		_cpDefinition.setShippingExtraPrice(shippingExtraPrice);
	}

	/**
	* Sets whether this cp definition is ship separately.
	*
	* @param shipSeparately the ship separately of this cp definition
	*/
	@Override
	public void setShipSeparately(boolean shipSeparately) {
		_cpDefinition.setShipSeparately(shipSeparately);
	}

	@Override
	public void setShortDescriptionMap(
		Map<java.util.Locale, String> shortDescriptionMap) {
		_cpDefinition.setShortDescriptionMap(shortDescriptionMap);
	}

	/**
	* Sets the status of this cp definition.
	*
	* @param status the status of this cp definition
	*/
	@Override
	public void setStatus(int status) {
		_cpDefinition.setStatus(status);
	}

	/**
	* Sets the status by user ID of this cp definition.
	*
	* @param statusByUserId the status by user ID of this cp definition
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_cpDefinition.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this cp definition.
	*
	* @param statusByUserName the status by user name of this cp definition
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_cpDefinition.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this cp definition.
	*
	* @param statusByUserUuid the status by user uuid of this cp definition
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_cpDefinition.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this cp definition.
	*
	* @param statusDate the status date of this cp definition
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_cpDefinition.setStatusDate(statusDate);
	}

	/**
	* Sets whether this cp definition is tax exempt.
	*
	* @param taxExempt the tax exempt of this cp definition
	*/
	@Override
	public void setTaxExempt(boolean taxExempt) {
		_cpDefinition.setTaxExempt(taxExempt);
	}

	/**
	* Sets whether this cp definition is telco or electronics.
	*
	* @param telcoOrElectronics the telco or electronics of this cp definition
	*/
	@Override
	public void setTelcoOrElectronics(boolean telcoOrElectronics) {
		_cpDefinition.setTelcoOrElectronics(telcoOrElectronics);
	}

	@Override
	public void setUrlTitleMap(Map<java.util.Locale, String> urlTitleMap) {
		_cpDefinition.setUrlTitleMap(urlTitleMap);
	}

	/**
	* Sets the user ID of this cp definition.
	*
	* @param userId the user ID of this cp definition
	*/
	@Override
	public void setUserId(long userId) {
		_cpDefinition.setUserId(userId);
	}

	/**
	* Sets the user name of this cp definition.
	*
	* @param userName the user name of this cp definition
	*/
	@Override
	public void setUserName(String userName) {
		_cpDefinition.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp definition.
	*
	* @param userUuid the user uuid of this cp definition
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpDefinition.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp definition.
	*
	* @param uuid the uuid of this cp definition
	*/
	@Override
	public void setUuid(String uuid) {
		_cpDefinition.setUuid(uuid);
	}

	/**
	* Sets the weight of this cp definition.
	*
	* @param weight the weight of this cp definition
	*/
	@Override
	public void setWeight(double weight) {
		_cpDefinition.setWeight(weight);
	}

	/**
	* Sets the width of this cp definition.
	*
	* @param width the width of this cp definition
	*/
	@Override
	public void setWidth(double width) {
		_cpDefinition.setWidth(width);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDefinition> toCacheModel() {
		return _cpDefinition.toCacheModel();
	}

	@Override
	public CPDefinition toEscapedModel() {
		return new CPDefinitionWrapper(_cpDefinition.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpDefinition.toString();
	}

	@Override
	public CPDefinition toUnescapedModel() {
		return new CPDefinitionWrapper(_cpDefinition.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpDefinition.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDefinitionWrapper)) {
			return false;
		}

		CPDefinitionWrapper cpDefinitionWrapper = (CPDefinitionWrapper)obj;

		if (Objects.equals(_cpDefinition, cpDefinitionWrapper._cpDefinition)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDefinition.getStagedModelType();
	}

	@Override
	public CPDefinition getWrappedModel() {
		return _cpDefinition;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDefinition.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDefinition.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDefinition.resetOriginalValues();
	}

	private final CPDefinition _cpDefinition;
}