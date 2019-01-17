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

package com.liferay.users.admin.web.internal.manager;

import java.util.List;

import javax.portlet.ActionRequest;

/**
 * @author Drew Brokke
 */
public abstract class BaseContactInfoManager<T>
	implements ContactInfoManager<T> {

	@Override
	public void delete(long primaryKey) throws Exception {
		T contactInfo = get(primaryKey);

		doDelete(primaryKey);

		if (isPrimary(contactInfo)) {
			List<T> contactInfos = getAll();

			if (contactInfos.isEmpty()) {
				return;
			}

			makePrimary(contactInfos.get(0));
		}
	}

	@Override
	public void edit(ActionRequest actionRequest) throws Exception {
		T contactInfo = construct(actionRequest);

		if (getPrimaryKey(contactInfo) > 0L) {
			doUpdate(contactInfo);
		}
		else {
			contactInfo = doAdd(contactInfo);
		}

		List<T> contactInfos = getAll();

		if (contactInfos.isEmpty()) {
			return;
		}

		if (!hasPrimary(contactInfos)) {
			long size = contactInfos.size();

			for (T tempContactInfo : contactInfos) {
				if ((size == 1) ||
					(getPrimaryKey(tempContactInfo) != getPrimaryKey(
						contactInfo))) {

					setPrimary(tempContactInfo, true);

					doUpdate(tempContactInfo);
				}
			}
		}
	}

	@Override
	public void makePrimary(long primaryKey) throws Exception {
		makePrimary(get(primaryKey));
	}

	protected abstract T construct(ActionRequest actionRequest)
		throws Exception;

	protected abstract T doAdd(T contactInfo) throws Exception;

	protected abstract void doDelete(long primaryKey) throws Exception;

	protected abstract void doUpdate(T contactInfo) throws Exception;

	protected abstract T get(long primaryKey) throws Exception;

	protected abstract List<T> getAll() throws Exception;

	protected abstract long getPrimaryKey(T contactInfo);

	protected boolean hasPrimary(List<T> contactInfos) {
		for (T contacInfo : contactInfos) {
			if (isPrimary(contacInfo)) {
				return true;
			}
		}

		return false;
	}

	protected abstract boolean isPrimary(T contactInfo);

	protected void makePrimary(T contactInfo) throws Exception {
		setPrimary(contactInfo, true);

		doUpdate(contactInfo);
	}

	protected abstract void setPrimary(T contactInfo, boolean primary);

}