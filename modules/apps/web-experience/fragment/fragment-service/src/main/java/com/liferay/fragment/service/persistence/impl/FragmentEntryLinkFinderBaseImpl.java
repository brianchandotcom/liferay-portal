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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class FragmentEntryLinkFinderBaseImpl extends BasePersistenceImpl<FragmentEntryLink> {
	public FragmentEntryLinkFinderBaseImpl() {
		setModelClass(FragmentEntryLink.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("uuid", "uuid_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getFragmentEntryLinkPersistence().getBadColumnNames();
	}

	/**
	 * Returns the fragment entry link persistence.
	 *
	 * @return the fragment entry link persistence
	 */
	public FragmentEntryLinkPersistence getFragmentEntryLinkPersistence() {
		return fragmentEntryLinkPersistence;
	}

	/**
	 * Sets the fragment entry link persistence.
	 *
	 * @param fragmentEntryLinkPersistence the fragment entry link persistence
	 */
	public void setFragmentEntryLinkPersistence(
		FragmentEntryLinkPersistence fragmentEntryLinkPersistence) {
		this.fragmentEntryLinkPersistence = fragmentEntryLinkPersistence;
	}

	@BeanReference(type = FragmentEntryLinkPersistence.class)
	protected FragmentEntryLinkPersistence fragmentEntryLinkPersistence;
	private static final Log _log = LogFactoryUtil.getLog(FragmentEntryLinkFinderBaseImpl.class);
}