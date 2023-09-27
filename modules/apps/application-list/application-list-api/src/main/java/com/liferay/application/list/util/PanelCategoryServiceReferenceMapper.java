/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.util;

import com.liferay.application.list.PanelEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.ServiceReference;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
@Deprecated
public class PanelCategoryServiceReferenceMapper
	implements ServiceReferenceMapper<String, PanelEntry> {

	@Override
	public void map(
		ServiceReference<PanelEntry> serviceReference,
		Emitter<String> emitter) {

		String panelCategoryKey = (String)serviceReference.getProperty(
			"panel.category.key");

		if (Validator.isNull(panelCategoryKey)) {
			_log.error(
				"Unable to register panel entry because of missing service " +
					"property \"panel.category.key\"");
		}
		else {
			emitter.emit(panelCategoryKey);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PanelCategoryServiceReferenceMapper.class);

}