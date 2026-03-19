/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat720.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WhereClauseEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WhereClauseEntry
 * @generated
 */
public class WhereClauseEntryWrapper
	extends BaseModelWrapper<WhereClauseEntry>
	implements ModelWrapper<WhereClauseEntry>, WhereClauseEntry {

	public WhereClauseEntryWrapper(WhereClauseEntry whereClauseEntry) {
		super(whereClauseEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("whereClauseEntryId", getWhereClauseEntryId());
		attributes.put("name", getName());
		attributes.put("nickname", getNickname());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long whereClauseEntryId = (Long)attributes.get("whereClauseEntryId");

		if (whereClauseEntryId != null) {
			setWhereClauseEntryId(whereClauseEntryId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String nickname = (String)attributes.get("nickname");

		if (nickname != null) {
			setNickname(nickname);
		}
	}

	/**
	 * Returns the name of this where clause entry.
	 *
	 * @return the name of this where clause entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the nickname of this where clause entry.
	 *
	 * @return the nickname of this where clause entry
	 */
	@Override
	public String getNickname() {
		return model.getNickname();
	}

	/**
	 * Returns the primary key of this where clause entry.
	 *
	 * @return the primary key of this where clause entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the where clause entry ID of this where clause entry.
	 *
	 * @return the where clause entry ID of this where clause entry
	 */
	@Override
	public long getWhereClauseEntryId() {
		return model.getWhereClauseEntryId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the name of this where clause entry.
	 *
	 * @param name the name of this where clause entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the nickname of this where clause entry.
	 *
	 * @param nickname the nickname of this where clause entry
	 */
	@Override
	public void setNickname(String nickname) {
		model.setNickname(nickname);
	}

	/**
	 * Sets the primary key of this where clause entry.
	 *
	 * @param primaryKey the primary key of this where clause entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the where clause entry ID of this where clause entry.
	 *
	 * @param whereClauseEntryId the where clause entry ID of this where clause entry
	 */
	@Override
	public void setWhereClauseEntryId(long whereClauseEntryId) {
		model.setWhereClauseEntryId(whereClauseEntryId);
	}

	@Override
	protected WhereClauseEntryWrapper wrap(WhereClauseEntry whereClauseEntry) {
		return new WhereClauseEntryWrapper(whereClauseEntry);
	}

}
// LIFERAY-SERVICE-BUILDER-HASH:4250136