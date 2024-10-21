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
 * This class is a wrapper for {@link CPSOListTypeDefinitionRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CPSOListTypeDefinitionRel
 * @generated
 */
public class CPSOListTypeDefinitionRelWrapper
	extends BaseModelWrapper<CPSOListTypeDefinitionRel>
	implements CPSOListTypeDefinitionRel,
			   ModelWrapper<CPSOListTypeDefinitionRel> {

	public CPSOListTypeDefinitionRelWrapper(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		super(cpsoListTypeDefinitionRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"CPSOListTypeDefinitionRelId", getCPSOListTypeDefinitionRelId());
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

		Long CPSOListTypeDefinitionRelId = (Long)attributes.get(
			"CPSOListTypeDefinitionRelId");

		if (CPSOListTypeDefinitionRelId != null) {
			setCPSOListTypeDefinitionRelId(CPSOListTypeDefinitionRelId);
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
	public CPSOListTypeDefinitionRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this cpso list type definition rel.
	 *
	 * @return the company ID of this cpso list type definition rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cpso list type definition rel ID of this cpso list type definition rel.
	 *
	 * @return the cpso list type definition rel ID of this cpso list type definition rel
	 */
	@Override
	public long getCPSOListTypeDefinitionRelId() {
		return model.getCPSOListTypeDefinitionRelId();
	}

	/**
	 * Returns the cp specification option ID of this cpso list type definition rel.
	 *
	 * @return the cp specification option ID of this cpso list type definition rel
	 */
	@Override
	public long getCPSpecificationOptionId() {
		return model.getCPSpecificationOptionId();
	}

	/**
	 * Returns the ct collection ID of this cpso list type definition rel.
	 *
	 * @return the ct collection ID of this cpso list type definition rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the list type definition ID of this cpso list type definition rel.
	 *
	 * @return the list type definition ID of this cpso list type definition rel
	 */
	@Override
	public long getListTypeDefinitionId() {
		return model.getListTypeDefinitionId();
	}

	/**
	 * Returns the mvcc version of this cpso list type definition rel.
	 *
	 * @return the mvcc version of this cpso list type definition rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this cpso list type definition rel.
	 *
	 * @return the primary key of this cpso list type definition rel
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
	 * Sets the company ID of this cpso list type definition rel.
	 *
	 * @param companyId the company ID of this cpso list type definition rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cpso list type definition rel ID of this cpso list type definition rel.
	 *
	 * @param CPSOListTypeDefinitionRelId the cpso list type definition rel ID of this cpso list type definition rel
	 */
	@Override
	public void setCPSOListTypeDefinitionRelId(
		long CPSOListTypeDefinitionRelId) {

		model.setCPSOListTypeDefinitionRelId(CPSOListTypeDefinitionRelId);
	}

	/**
	 * Sets the cp specification option ID of this cpso list type definition rel.
	 *
	 * @param CPSpecificationOptionId the cp specification option ID of this cpso list type definition rel
	 */
	@Override
	public void setCPSpecificationOptionId(long CPSpecificationOptionId) {
		model.setCPSpecificationOptionId(CPSpecificationOptionId);
	}

	/**
	 * Sets the ct collection ID of this cpso list type definition rel.
	 *
	 * @param ctCollectionId the ct collection ID of this cpso list type definition rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the list type definition ID of this cpso list type definition rel.
	 *
	 * @param listTypeDefinitionId the list type definition ID of this cpso list type definition rel
	 */
	@Override
	public void setListTypeDefinitionId(long listTypeDefinitionId) {
		model.setListTypeDefinitionId(listTypeDefinitionId);
	}

	/**
	 * Sets the mvcc version of this cpso list type definition rel.
	 *
	 * @param mvccVersion the mvcc version of this cpso list type definition rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this cpso list type definition rel.
	 *
	 * @param primaryKey the primary key of this cpso list type definition rel
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
	public Map<String, Function<CPSOListTypeDefinitionRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CPSOListTypeDefinitionRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CPSOListTypeDefinitionRelWrapper wrap(
		CPSOListTypeDefinitionRel cpsoListTypeDefinitionRel) {

		return new CPSOListTypeDefinitionRelWrapper(cpsoListTypeDefinitionRel);
	}

}