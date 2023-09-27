/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.petra.string.StringBundler;
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
				StringBundler.concat(
					"Invalid company ID ", companyId, " for ",
					serviceReference));

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