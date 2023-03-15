/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.service.persistence.impl;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.osb.faro.service.persistence.FaroProjectPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.lang.reflect.Field;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectFinderBaseImpl
	extends BasePersistenceImpl<FaroProject> {

	public FaroProjectFinderBaseImpl() {
		setModelClass(FaroProject.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("state", "state_");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getFaroProjectPersistence().getBadColumnNames();
	}

	/**
	 * Returns the faro project persistence.
	 *
	 * @return the faro project persistence
	 */
	public FaroProjectPersistence getFaroProjectPersistence() {
		return faroProjectPersistence;
	}

	/**
	 * Sets the faro project persistence.
	 *
	 * @param faroProjectPersistence the faro project persistence
	 */
	public void setFaroProjectPersistence(
		FaroProjectPersistence faroProjectPersistence) {

		this.faroProjectPersistence = faroProjectPersistence;
	}

	@BeanReference(type = FaroProjectPersistence.class)
	protected FaroProjectPersistence faroProjectPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		FaroProjectFinderBaseImpl.class);

}