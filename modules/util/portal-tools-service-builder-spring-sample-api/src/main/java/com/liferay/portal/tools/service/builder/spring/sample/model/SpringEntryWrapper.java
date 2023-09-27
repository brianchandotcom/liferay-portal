/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.spring.sample.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SpringEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SpringEntry
 * @generated
 */
public class SpringEntryWrapper
	extends BaseModelWrapper<SpringEntry>
	implements ModelWrapper<SpringEntry>, SpringEntry {

	public SpringEntryWrapper(SpringEntry springEntry) {
		super(springEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("springEntryId", getSpringEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long springEntryId = (Long)attributes.get("springEntryId");

		if (springEntryId != null) {
			setSpringEntryId(springEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}
	}

	@Override
	public SpringEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this spring entry.
	 *
	 * @return the company ID of this spring entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this spring entry.
	 *
	 * @return the create date of this spring entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the mvcc version of this spring entry.
	 *
	 * @return the mvcc version of this spring entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this spring entry.
	 *
	 * @return the primary key of this spring entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the spring entry ID of this spring entry.
	 *
	 * @return the spring entry ID of this spring entry
	 */
	@Override
	public long getSpringEntryId() {
		return model.getSpringEntryId();
	}

	/**
	 * Returns the uuid of this spring entry.
	 *
	 * @return the uuid of this spring entry
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this spring entry.
	 *
	 * @param companyId the company ID of this spring entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this spring entry.
	 *
	 * @param createDate the create date of this spring entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the mvcc version of this spring entry.
	 *
	 * @param mvccVersion the mvcc version of this spring entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this spring entry.
	 *
	 * @param primaryKey the primary key of this spring entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the spring entry ID of this spring entry.
	 *
	 * @param springEntryId the spring entry ID of this spring entry
	 */
	@Override
	public void setSpringEntryId(long springEntryId) {
		model.setSpringEntryId(springEntryId);
	}

	/**
	 * Sets the uuid of this spring entry.
	 *
	 * @param uuid the uuid of this spring entry
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected SpringEntryWrapper wrap(SpringEntry springEntry) {
		return new SpringEntryWrapper(springEntry);
	}

}