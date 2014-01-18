/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.dao.orm;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.MVCCModel;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.PersistedModelLocalService;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;

/**
 * @author Shuyang Zhou
 */
public class MVCCVersionCheckerUtil {

	public static boolean checkVersionChange(
			String logPrefix, MVCCModel mvccModel)
		throws PortalException, SystemException {

		Class<?> modelInterface = getModelInterface(mvccModel.getClass());

		PersistedModelLocalService persistedModelLocalService =
			PersistedModelLocalServiceRegistryUtil.
				getPersistedModelLocalService(modelInterface.getName());

		BaseModel<?> baseModel = (BaseModel<?>)mvccModel;

		MVCCModel databaseMVCCModel =
			(MVCCModel)persistedModelLocalService.getPersistedModel(
				baseModel.getPrimaryKeyObj());

		long databaseVersion = databaseMVCCModel.getMvccVersion();

		long memoryVersion = mvccModel.getMvccVersion();

		if (memoryVersion == databaseVersion) {
			if (_log.isInfoEnabled()) {
				_log.info(
					logPrefix + ". Version has not changed, version : " +
						memoryVersion);
			}

			return false;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					logPrefix + ". Version has changed, in memory version : " +
						memoryVersion + ", in database version : " +
							databaseVersion);
			}

			return true;
		}
	}

	private static Class<?> getModelInterface(Class<?> clazz) {
		while (clazz != null) {
			for (Class<?> interfaceClass : clazz.getInterfaces()) {
				if ((interfaceClass != PersistedModel.class) &&
					PersistedModel.class.isAssignableFrom(interfaceClass)) {

					return interfaceClass;
				}
			}

			clazz = clazz.getSuperclass();
		}

		throw new IllegalArgumentException(
			"Can not find model interface for class " + clazz);
	}

	private static Log _log = LogFactoryUtil.getLog(
		MVCCVersionCheckerUtil.class);

}