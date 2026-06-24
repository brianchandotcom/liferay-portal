/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ReassociateEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ReassociateEntry
 * @generated
 */
public class ReassociateEntryWrapper
	extends BaseModelWrapper<ReassociateEntry>
	implements ModelWrapper<ReassociateEntry>, ReassociateEntry {

	public ReassociateEntryWrapper(ReassociateEntry reassociateEntry) {
		super(reassociateEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("reassociateEntryId", getReassociateEntryId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long reassociateEntryId = (Long)attributes.get("reassociateEntryId");

		if (reassociateEntryId != null) {
			setReassociateEntryId(reassociateEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public ReassociateEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the name of this reassociate entry.
	 *
	 * @return the name of this reassociate entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this reassociate entry.
	 *
	 * @return the primary key of this reassociate entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the reassociate entry ID of this reassociate entry.
	 *
	 * @return the reassociate entry ID of this reassociate entry
	 */
	@Override
	public long getReassociateEntryId() {
		return model.getReassociateEntryId();
	}

	/**
	 * Sets the name of this reassociate entry.
	 *
	 * @param name the name of this reassociate entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this reassociate entry.
	 *
	 * @param primaryKey the primary key of this reassociate entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the reassociate entry ID of this reassociate entry.
	 *
	 * @param reassociateEntryId the reassociate entry ID of this reassociate entry
	 */
	@Override
	public void setReassociateEntryId(long reassociateEntryId) {
		model.setReassociateEntryId(reassociateEntryId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected ReassociateEntryWrapper wrap(ReassociateEntry reassociateEntry) {
		return new ReassociateEntryWrapper(reassociateEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:-1097003379