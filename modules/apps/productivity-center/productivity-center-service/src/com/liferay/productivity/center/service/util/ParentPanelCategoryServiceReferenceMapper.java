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

package com.liferay.productivity.center.service.util;

import com.liferay.osgi.service.tracker.map.ServiceReferenceMapper;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.productivity.center.panel.PanelEntry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.osgi.framework.ServiceReference;

/**
* @author Adolfo Pérez
*/
public class ParentPanelCategoryServiceReferenceMapper<T extends PanelEntry>
	implements ServiceReferenceMapper<String, T> {

	public static <S extends PanelEntry>
		ParentPanelCategoryServiceReferenceMapper<S> create() {

		return new ParentPanelCategoryServiceReferenceMapper<>();
	}

	@Override
	public void map(
		ServiceReference<T> serviceReference, Emitter<String> emitter) {

		String categoryKey = (String)serviceReference.getProperty(
			"panel.category");

		if (Validator.isNull(categoryKey)) {
			_log.error(
				"Unable to register panel entry because of missing " +
					"service property \"panel.category\"");
		}
		else {
			emitter.emit(categoryKey);
		}
	}

	private static final Log _log = LogFactory.getLog(
		ParentPanelCategoryServiceReferenceMapper.class);

}