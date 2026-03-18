/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CTEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntry
 * @generated
 */
public class CTEntryWrapper
	extends BaseModelWrapper<CTEntry>
	implements CTEntry, ModelWrapper<CTEntry> {

	public CTEntryWrapper(CTEntry ctEntry) {
		super(ctEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("ctEntryId", getCtEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());

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

		Long ctEntryId = (Long)attributes.get("ctEntryId");

		if (ctEntryId != null) {
			setCtEntryId(ctEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public CTEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct entry.
	 *
	 * @return the company ID of this ct entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this ct entry.
	 *
	 * @return the ct collection ID of this ct entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct entry ID of this ct entry.
	 *
	 * @return the ct entry ID of this ct entry
	 */
	@Override
	public long getCtEntryId() {
		return model.getCtEntryId();
	}

	/**
	 * Returns the mvcc version of this ct entry.
	 *
	 * @return the mvcc version of this ct entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ct entry.
	 *
	 * @return the name of this ct entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this ct entry.
	 *
	 * @return the primary key of this ct entry
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
	 * Sets the company ID of this ct entry.
	 *
	 * @param companyId the company ID of this ct entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this ct entry.
	 *
	 * @param ctCollectionId the ct collection ID of this ct entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct entry ID of this ct entry.
	 *
	 * @param ctEntryId the ct entry ID of this ct entry
	 */
	@Override
	public void setCtEntryId(long ctEntryId) {
		model.setCtEntryId(ctEntryId);
	}

	/**
	 * Sets the mvcc version of this ct entry.
	 *
	 * @param mvccVersion the mvcc version of this ct entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ct entry.
	 *
	 * @param name the name of this ct entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this ct entry.
	 *
	 * @param primaryKey the primary key of this ct entry
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
	public Map<String, Function<CTEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CTEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CTEntryWrapper wrap(CTEntry ctEntry) {
		return new CTEntryWrapper(ctEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1540765470