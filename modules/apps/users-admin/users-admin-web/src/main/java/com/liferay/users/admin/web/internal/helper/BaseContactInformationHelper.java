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

package com.liferay.users.admin.web.internal.helper;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Drew Brokke
 */
public abstract class BaseContactInformationHelper<T>
	implements ContactInformationHelper<T> {

	@Override
	public void delete(long primaryKey) throws Exception {
		T entry = getEntry(primaryKey);

		deleteEntry(primaryKey);

		if (isPrimaryEntry(entry)) {
			List<T> entries = getEntries();

			if (entries.isEmpty()) {
				return;
			}

			makePrimary(entries.get(0));
		}
	}

	@Override
	public void edit(ActionRequest actionRequest) throws Exception {
		T entry = constructEntry(actionRequest);

		if (getEntryId(entry) > 0L) {
			updateEntry(entry);
		}
		else {
			entry = addEntry(entry);
		}

		List<T> entries = getEntries();

		if (entries.isEmpty()) {
			return;
		}

		if (!hasPrimaryEntry(entries)) {
			long size = entries.size();

			for (T tempEntry : entries) {
				if ((size == 1) ||
					(getEntryId(tempEntry) != getEntryId(entry))) {

					setEntryPrimary(tempEntry, true);

					updateEntry(tempEntry);
				}
			}
		}
	}

	@Override
	public void makePrimary(long primaryKey) throws Exception {
		makePrimary(getEntry(primaryKey));
	}

	protected abstract T addEntry(T entry) throws Exception;

	protected abstract T constructEntry(ActionRequest actionRequest)
		throws Exception;

	protected abstract void deleteEntry(long primaryKey) throws Exception;

	protected abstract List<T> getEntries() throws Exception;

	protected abstract T getEntry(long primaryKey) throws Exception;

	protected abstract long getEntryId(T entry);

	protected boolean hasPrimaryEntry(List<T> entries) {
		for (T entry : entries) {
			if (isPrimaryEntry(entry)) {
				return true;
			}
		}

		return false;
	}

	protected abstract boolean isPrimaryEntry(T entry);

	protected void makePrimary(T entry) throws Exception {
		setEntryPrimary(entry, true);

		updateEntry(entry);
	}

	protected abstract void setEntryPrimary(T entry, boolean primary);

	protected abstract void updateEntry(T entry) throws Exception;

}