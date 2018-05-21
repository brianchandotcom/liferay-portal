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