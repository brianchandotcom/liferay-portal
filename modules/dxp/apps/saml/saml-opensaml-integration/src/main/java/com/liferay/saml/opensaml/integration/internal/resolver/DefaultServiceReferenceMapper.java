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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.ServiceReference;

/**
 * @author Carlos Sierra Andrés
 */
public class DefaultServiceReferenceMapper
	implements ServiceReferenceMapper<String, Object> {

	public DefaultServiceReferenceMapper(Log log) {
		_log = log;
	}

	@Override
	public void map(
		ServiceReference<Object> serviceReference, Emitter<String> emitter) {

		long companyId = GetterUtil.getLong(
			serviceReference.getProperty("companyId"));

		if (companyId == 0) {
			_log.error(
				"Invalid company ID " + companyId + " for " + serviceReference);

			return;
		}

		String entityId = GetterUtil.getString(
			serviceReference.getProperty("entityId"));

		if (Validator.isNull(entityId)) {
			_log.error("Entity ID required for " + serviceReference);

			return;
		}

		emitter.emit(companyId + "," + entityId);
	}

	private final Log _log;

}