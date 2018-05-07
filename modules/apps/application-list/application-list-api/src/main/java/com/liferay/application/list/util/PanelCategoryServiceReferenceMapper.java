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

package com.liferay.application.list.util;

import com.liferay.application.list.PanelEntry;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.framework.ServiceReference;

/**
 * @author Adolfo Pérez
 */
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