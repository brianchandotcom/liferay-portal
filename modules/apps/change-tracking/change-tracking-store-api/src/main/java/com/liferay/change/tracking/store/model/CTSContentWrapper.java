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

package com.liferay.change.tracking.store.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.sql.Blob;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CTSContent}.
 * </p>
 *
 * @author Shuyang Zhou
 * @see CTSContent
 * @generated
 */
public class CTSContentWrapper
	extends BaseModelWrapper<CTSContent>
	implements CTSContent, ModelWrapper<CTSContent> {

	public CTSContentWrapper(CTSContent ctsContent) {
		super(ctsContent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("ctsContentId", getCtsContentId());
		attributes.put("companyId", getCompanyId());
		attributes.put("repositoryId", getRepositoryId());
		attributes.put("path", getPath());
		attributes.put("version", getVersion());
		attributes.put("data", getData());
		attributes.put("size", getSize());
		attributes.put("storeType", getStoreType());

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

		Long ctsContentId = (Long)attributes.get("ctsContentId");

		if (ctsContentId != null) {
			setCtsContentId(ctsContentId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long repositoryId = (Long)attributes.get("repositoryId");

		if (repositoryId != null) {
			setRepositoryId(repositoryId);
		}

		String path = (String)attributes.get("path");

		if (path != null) {
			setPath(path);
		}

		String version = (String)attributes.get("version");

		if (version != null) {
			setVersion(version);
		}

		Blob data = (Blob)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		Long size = (Long)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		String storeType = (String)attributes.get("storeType");

		if (storeType != null) {
			setStoreType(storeType);
		}
	}

	/**
	 * Returns the company ID of this cts content.
	 *
	 * @return the company ID of this cts content
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this cts content.
	 *
	 * @return the ct collection ID of this cts content
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the cts content ID of this cts content.
	 *
	 * @return the cts content ID of this cts content
	 */
	@Override
	public long getCtsContentId() {
		return model.getCtsContentId();
	}

	/**
	 * Returns the data of this cts content.
	 *
	 * @return the data of this cts content
	 */
	@Override
	public Blob getData() {
		return model.getData();
	}

	/**
	 * Returns the mvcc version of this cts content.
	 *
	 * @return the mvcc version of this cts content
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the path of this cts content.
	 *
	 * @return the path of this cts content
	 */
	@Override
	public String getPath() {
		return model.getPath();
	}

	/**
	 * Returns the primary key of this cts content.
	 *
	 * @return the primary key of this cts content
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the repository ID of this cts content.
	 *
	 * @return the repository ID of this cts content
	 */
	@Override
	public long getRepositoryId() {
		return model.getRepositoryId();
	}

	/**
	 * Returns the size of this cts content.
	 *
	 * @return the size of this cts content
	 */
	@Override
	public long getSize() {
		return model.getSize();
	}

	/**
	 * Returns the store type of this cts content.
	 *
	 * @return the store type of this cts content
	 */
	@Override
	public String getStoreType() {
		return model.getStoreType();
	}

	/**
	 * Returns the version of this cts content.
	 *
	 * @return the version of this cts content
	 */
	@Override
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cts content model instance should use the <code>CTSContent</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this cts content.
	 *
	 * @param companyId the company ID of this cts content
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this cts content.
	 *
	 * @param ctCollectionId the ct collection ID of this cts content
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the cts content ID of this cts content.
	 *
	 * @param ctsContentId the cts content ID of this cts content
	 */
	@Override
	public void setCtsContentId(long ctsContentId) {
		model.setCtsContentId(ctsContentId);
	}

	/**
	 * Sets the data of this cts content.
	 *
	 * @param data the data of this cts content
	 */
	@Override
	public void setData(Blob data) {
		model.setData(data);
	}

	/**
	 * Sets the mvcc version of this cts content.
	 *
	 * @param mvccVersion the mvcc version of this cts content
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the path of this cts content.
	 *
	 * @param path the path of this cts content
	 */
	@Override
	public void setPath(String path) {
		model.setPath(path);
	}

	/**
	 * Sets the primary key of this cts content.
	 *
	 * @param primaryKey the primary key of this cts content
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the repository ID of this cts content.
	 *
	 * @param repositoryId the repository ID of this cts content
	 */
	@Override
	public void setRepositoryId(long repositoryId) {
		model.setRepositoryId(repositoryId);
	}

	/**
	 * Sets the size of this cts content.
	 *
	 * @param size the size of this cts content
	 */
	@Override
	public void setSize(long size) {
		model.setSize(size);
	}

	/**
	 * Sets the store type of this cts content.
	 *
	 * @param storeType the store type of this cts content
	 */
	@Override
	public void setStoreType(String storeType) {
		model.setStoreType(storeType);
	}

	/**
	 * Sets the version of this cts content.
	 *
	 * @param version the version of this cts content
	 */
	@Override
	public void setVersion(String version) {
		model.setVersion(version);
	}

	@Override
	public Map<String, Function<CTSContent, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CTSContent, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CTSContentWrapper wrap(CTSContent ctsContent) {
		return new CTSContentWrapper(ctsContent);
	}

}