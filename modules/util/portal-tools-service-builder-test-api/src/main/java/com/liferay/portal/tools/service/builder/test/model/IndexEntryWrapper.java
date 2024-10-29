/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link IndexEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see IndexEntry
 * @generated
 */
public class IndexEntryWrapper
	extends BaseModelWrapper<IndexEntry>
	implements IndexEntry, ModelWrapper<IndexEntry> {

	public IndexEntryWrapper(IndexEntry indexEntry) {
		super(indexEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("indexEntryId", getIndexEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());

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

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long indexEntryId = (Long)attributes.get("indexEntryId");

		if (indexEntryId != null) {
			setIndexEntryId(indexEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	@Override
	public IndexEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this index entry.
	 *
	 * @return the company ID of this index entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this index entry.
	 *
	 * @return the ct collection ID of this index entry
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the external reference code of this index entry.
	 *
	 * @return the external reference code of this index entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the index entry ID of this index entry.
	 *
	 * @return the index entry ID of this index entry
	 */
	@Override
	public long getIndexEntryId() {
		return model.getIndexEntryId();
	}

	/**
	 * Returns the mvcc version of this index entry.
	 *
	 * @return the mvcc version of this index entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the owner ID of this index entry.
	 *
	 * @return the owner ID of this index entry
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the owner type of this index entry.
	 *
	 * @return the owner type of this index entry
	 */
	@Override
	public int getOwnerType() {
		return model.getOwnerType();
	}

	/**
	 * Returns the plid of this index entry.
	 *
	 * @return the plid of this index entry
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this index entry.
	 *
	 * @return the portlet ID of this index entry
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this index entry.
	 *
	 * @return the primary key of this index entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Sets the company ID of this index entry.
	 *
	 * @param companyId the company ID of this index entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this index entry.
	 *
	 * @param ctCollectionId the ct collection ID of this index entry
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the external reference code of this index entry.
	 *
	 * @param externalReferenceCode the external reference code of this index entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the index entry ID of this index entry.
	 *
	 * @param indexEntryId the index entry ID of this index entry
	 */
	@Override
	public void setIndexEntryId(long indexEntryId) {
		model.setIndexEntryId(indexEntryId);
	}

	/**
	 * Sets the mvcc version of this index entry.
	 *
	 * @param mvccVersion the mvcc version of this index entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the owner ID of this index entry.
	 *
	 * @param ownerId the owner ID of this index entry
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the owner type of this index entry.
	 *
	 * @param ownerType the owner type of this index entry
	 */
	@Override
	public void setOwnerType(int ownerType) {
		model.setOwnerType(ownerType);
	}

	/**
	 * Sets the plid of this index entry.
	 *
	 * @param plid the plid of this index entry
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this index entry.
	 *
	 * @param portletId the portlet ID of this index entry
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this index entry.
	 *
	 * @param primaryKey the primary key of this index entry
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
	public Map<String, Function<IndexEntry, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<IndexEntry, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected IndexEntryWrapper wrap(IndexEntry indexEntry) {
		return new IndexEntryWrapper(indexEntry);
	}

}