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

package com.liferay.knowledge.base.service.persistence.impl;

import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.persistence.KBFolderPersistence;

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
public class KBFolderFinderBaseImpl extends BasePersistenceImpl<KBFolder> {
	public KBFolderFinderBaseImpl() {
		setModelClass(KBFolder.class);

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
		return getKBFolderPersistence().getBadColumnNames();
	}

	/**
	 * Returns the kb folder persistence.
	 *
	 * @return the kb folder persistence
	 */
	public KBFolderPersistence getKBFolderPersistence() {
		return kbFolderPersistence;
	}

	/**
	 * Sets the kb folder persistence.
	 *
	 * @param kbFolderPersistence the kb folder persistence
	 */
	public void setKBFolderPersistence(KBFolderPersistence kbFolderPersistence) {
		this.kbFolderPersistence = kbFolderPersistence;
	}

	@BeanReference(type = KBFolderPersistence.class)
	protected KBFolderPersistence kbFolderPersistence;
	private static final Log _log = LogFactoryUtil.getLog(KBFolderFinderBaseImpl.class);
}