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

package com.liferay.friendly.url.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FriendlyURLEntryMapping}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryMapping
 * @generated
 */
public class FriendlyURLEntryMappingWrapper
	extends BaseModelWrapper<FriendlyURLEntryMapping>
	implements FriendlyURLEntryMapping, ModelWrapper<FriendlyURLEntryMapping> {

	public FriendlyURLEntryMappingWrapper(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {

		super(friendlyURLEntryMapping);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"friendlyURLEntryMappingId", getFriendlyURLEntryMappingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long friendlyURLEntryMappingId = (Long)attributes.get(
			"friendlyURLEntryMappingId");

		if (friendlyURLEntryMappingId != null) {
			setFriendlyURLEntryMappingId(friendlyURLEntryMappingId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}
	}

	/**
	 * Returns the fully qualified class name of this friendly url entry mapping.
	 *
	 * @return the fully qualified class name of this friendly url entry mapping
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this friendly url entry mapping.
	 *
	 * @return the class name ID of this friendly url entry mapping
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this friendly url entry mapping.
	 *
	 * @return the class pk of this friendly url entry mapping
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this friendly url entry mapping.
	 *
	 * @return the company ID of this friendly url entry mapping
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the friendly url entry ID of this friendly url entry mapping.
	 *
	 * @return the friendly url entry ID of this friendly url entry mapping
	 */
	@Override
	public long getFriendlyURLEntryId() {
		return model.getFriendlyURLEntryId();
	}

	/**
	 * Returns the friendly url entry mapping ID of this friendly url entry mapping.
	 *
	 * @return the friendly url entry mapping ID of this friendly url entry mapping
	 */
	@Override
	public long getFriendlyURLEntryMappingId() {
		return model.getFriendlyURLEntryMappingId();
	}

	/**
	 * Returns the mvcc version of this friendly url entry mapping.
	 *
	 * @return the mvcc version of this friendly url entry mapping
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this friendly url entry mapping.
	 *
	 * @return the primary key of this friendly url entry mapping
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this friendly url entry mapping.
	 *
	 * @param classNameId the class name ID of this friendly url entry mapping
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this friendly url entry mapping.
	 *
	 * @param classPK the class pk of this friendly url entry mapping
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this friendly url entry mapping.
	 *
	 * @param companyId the company ID of this friendly url entry mapping
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the friendly url entry ID of this friendly url entry mapping.
	 *
	 * @param friendlyURLEntryId the friendly url entry ID of this friendly url entry mapping
	 */
	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		model.setFriendlyURLEntryId(friendlyURLEntryId);
	}

	/**
	 * Sets the friendly url entry mapping ID of this friendly url entry mapping.
	 *
	 * @param friendlyURLEntryMappingId the friendly url entry mapping ID of this friendly url entry mapping
	 */
	@Override
	public void setFriendlyURLEntryMappingId(long friendlyURLEntryMappingId) {
		model.setFriendlyURLEntryMappingId(friendlyURLEntryMappingId);
	}

	/**
	 * Sets the mvcc version of this friendly url entry mapping.
	 *
	 * @param mvccVersion the mvcc version of this friendly url entry mapping
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this friendly url entry mapping.
	 *
	 * @param primaryKey the primary key of this friendly url entry mapping
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected FriendlyURLEntryMappingWrapper wrap(
		FriendlyURLEntryMapping friendlyURLEntryMapping) {

		return new FriendlyURLEntryMappingWrapper(friendlyURLEntryMapping);
	}

}