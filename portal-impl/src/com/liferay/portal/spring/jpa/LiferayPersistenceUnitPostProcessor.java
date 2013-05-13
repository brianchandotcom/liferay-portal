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

package com.liferay.portal.spring.jpa;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Properties;

import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;

/**
 * @author Prashant Dighe
 * @author Brian Wing Shun Chan
 */
public class LiferayPersistenceUnitPostProcessor
	implements PersistenceUnitPostProcessor {

	@Override
	public void postProcessPersistenceUnitInfo(
		MutablePersistenceUnitInfo mutablePersistenceUnitInfo) {

		for (String mappingFileName : PropsValues.JPA_CONFIGS) {
			mutablePersistenceUnitInfo.addMappingFileName(mappingFileName);
		}

		Properties properties = PropsUtil.getProperties(
			PropsKeys.JPA_PROVIDER_PROPERTY_PREFIX, true);

		if (_log.isInfoEnabled()) {
			_log.info(PropertiesUtil.list(properties));
		}

		mutablePersistenceUnitInfo.setProperties(properties);
	}

	private static Log _log = LogFactoryUtil.getLog(
		LiferayPersistenceUnitPostProcessor.class);

}