/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CPSpecificationOptionListTypeDefinitionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPSpecificationOptionListTypeDefinitionRel
 * @generated
 */
public class CPSpecificationOptionListTypeDefinitionRelWrapper
	extends BaseModelWrapper<CPSpecificationOptionListTypeDefinitionRel>
	implements CPSpecificationOptionListTypeDefinitionRel,
			   ModelWrapper<CPSpecificationOptionListTypeDefinitionRel> {

	public CPSpecificationOptionListTypeDefinitionRelWrapper(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		super(cpSpecificationOptionListTypeDefinitionRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"CPSpecificationOptionListTypeDefinitionRelId",
			getCPSpecificationOptionListTypeDefinitionRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("CPSpecificationOptionId", getCPSpecificationOptionId());
		attributes.put("listTypeDefinitionId", getListTypeDefinitionId());

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

		Long CPSpecificationOptionListTypeDefinitionRelId =
			(Long)attributes.get(
				"CPSpecificationOptionListTypeDefinitionRelId");

		if (CPSpecificationOptionListTypeDefinitionRelId != null) {
			setCPSpecificationOptionListTypeDefinitionRelId(
				CPSpecificationOptionListTypeDefinitionRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long CPSpecificationOptionId = (Long)attributes.get(
			"CPSpecificationOptionId");

		if (CPSpecificationOptionId != null) {
			setCPSpecificationOptionId(CPSpecificationOptionId);
		}

		Long listTypeDefinitionId = (Long)attributes.get(
			"listTypeDefinitionId");

		if (listTypeDefinitionId != null) {
			setListTypeDefinitionId(listTypeDefinitionId);
		}
	}

	@Override
	public CPSpecificationOptionListTypeDefinitionRel
		cloneWithOriginalValues() {

		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cp specification option list type definition rel.
	 *
	 * @return the company ID of this cp specification option list type definition rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp specification option ID of this cp specification option list type definition rel.
	 *
	 * @return the cp specification option ID of this cp specification option list type definition rel
	 */
	@Override
	public long getCPSpecificationOptionId() {
		return model.getCPSpecificationOptionId();
	}

	/**
	 * Returns the cp specification option list type definition rel ID of this cp specification option list type definition rel.
	 *
	 * @return the cp specification option list type definition rel ID of this cp specification option list type definition rel
	 */
	@Override
	public long getCPSpecificationOptionListTypeDefinitionRelId() {
		return model.getCPSpecificationOptionListTypeDefinitionRelId();
	}

	/**
	 * Returns the ct collection ID of this cp specification option list type definition rel.
	 *
	 * @return the ct collection ID of this cp specification option list type definition rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the list type definition ID of this cp specification option list type definition rel.
	 *
	 * @return the list type definition ID of this cp specification option list type definition rel
	 */
	@Override
	public long getListTypeDefinitionId() {
		return model.getListTypeDefinitionId();
	}

	/**
	 * Returns the mvcc version of this cp specification option list type definition rel.
	 *
	 * @return the mvcc version of this cp specification option list type definition rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cp specification option list type definition rel.
	 *
	 * @return the primary key of this cp specification option list type definition rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cp specification option list type definition rel.
	 *
	 * @param companyId the company ID of this cp specification option list type definition rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp specification option ID of this cp specification option list type definition rel.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID of this cp specification option list type definition rel
	 */
	@Override
	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		model.setCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	 * Sets the cp specification option list type definition rel ID of this cp specification option list type definition rel.
	 *
	 * @param CPSpecificationOptionListTypeDefinitionRelId the cp specification option list type definition rel ID of this cp specification option list type definition rel
	 */
	@Override
	public void setCPSpecificationOptionListTypeDefinitionRelId(
		long CPSpecificationOptionListTypeDefinitionRelId) {

		model.setCPSpecificationOptionListTypeDefinitionRelId(
			CPSpecificationOptionListTypeDefinitionRelId);
	}

	/**
	 * Sets the ct collection ID of this cp specification option list type definition rel.
	 *
	 * @param ctCollectionId the ct collection ID of this cp specification option list type definition rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the list type definition ID of this cp specification option list type definition rel.
	 *
	 * @param listTypeDefinitionId the list type definition ID of this cp specification option list type definition rel
	 */
	@Override
	public void setListTypeDefinitionId(long listTypeDefinitionId) {
		model.setListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Sets the mvcc version of this cp specification option list type definition rel.
	 *
	 * @param mvccVersion the mvcc version of this cp specification option list type definition rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cp specification option list type definition rel.
	 *
	 * @param primaryKey the primary key of this cp specification option list type definition rel
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
	public Map
		<String, Function<CPSpecificationOptionListTypeDefinitionRel, Object>>
			getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map
		<String, BiConsumer<CPSpecificationOptionListTypeDefinitionRel, Object>>
			getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CPSpecificationOptionListTypeDefinitionRelWrapper wrap(
		CPSpecificationOptionListTypeDefinitionRel
			cpSpecificationOptionListTypeDefinitionRel) {

		return new CPSpecificationOptionListTypeDefinitionRelWrapper(
			cpSpecificationOptionListTypeDefinitionRel);
	}

}