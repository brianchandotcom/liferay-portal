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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FinderWhereClauseEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FinderWhereClauseEntry
 * @generated
 */
public class FinderWhereClauseEntryWrapper
	extends BaseModelWrapper<FinderWhereClauseEntry>
	implements FinderWhereClauseEntry, ModelWrapper<FinderWhereClauseEntry> {

	public FinderWhereClauseEntryWrapper(
		FinderWhereClauseEntry finderWhereClauseEntry) {

		super(finderWhereClauseEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"finderWhereClauseEntryId", getFinderWhereClauseEntryId());
		attributes.put("name", getName());
		attributes.put("nickname", getNickname());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long finderWhereClauseEntryId = (Long)attributes.get(
			"finderWhereClauseEntryId");

		if (finderWhereClauseEntryId != null) {
			setFinderWhereClauseEntryId(finderWhereClauseEntryId);
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
	 * Returns the finder where clause entry ID of this finder where clause entry.
	 *
	 * @return the finder where clause entry ID of this finder where clause entry
	 */
	@Override
	public long getFinderWhereClauseEntryId() {
		return model.getFinderWhereClauseEntryId();
	}

	/**
	 * Returns the name of this finder where clause entry.
	 *
	 * @return the name of this finder where clause entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the nickname of this finder where clause entry.
	 *
	 * @return the nickname of this finder where clause entry
	 */
	@Override
	public String getNickname() {
		return model.getNickname();
	}

	/**
	 * Returns the primary key of this finder where clause entry.
	 *
	 * @return the primary key of this finder where clause entry
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
	 * Sets the finder where clause entry ID of this finder where clause entry.
	 *
	 * @param finderWhereClauseEntryId the finder where clause entry ID of this finder where clause entry
	 */
	@Override
	public void setFinderWhereClauseEntryId(long finderWhereClauseEntryId) {
		model.setFinderWhereClauseEntryId(finderWhereClauseEntryId);
	}

	/**
	 * Sets the name of this finder where clause entry.
	 *
	 * @param name the name of this finder where clause entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the nickname of this finder where clause entry.
	 *
	 * @param nickname the nickname of this finder where clause entry
	 */
	@Override
	public void setNickname(String nickname) {
		model.setNickname(nickname);
	}

	/**
	 * Sets the primary key of this finder where clause entry.
	 *
	 * @param primaryKey the primary key of this finder where clause entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected FinderWhereClauseEntryWrapper wrap(
		FinderWhereClauseEntry finderWhereClauseEntry) {

		return new FinderWhereClauseEntryWrapper(finderWhereClauseEntry);
	}

}