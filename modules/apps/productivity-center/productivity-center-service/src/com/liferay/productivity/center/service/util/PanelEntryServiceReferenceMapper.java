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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.productivity.center.panel.PanelEntry;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Adolfo Pérez
 */
public class PanelEntryServiceReferenceMapper<T extends PanelEntry>
	implements ServiceReferenceMapper<String, T> {

	public static final <S extends PanelEntry>
		PanelEntryServiceReferenceMapper<S> create() {

		return new PanelEntryServiceReferenceMapper<>();
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
		PanelEntryServiceReferenceMapper.class);

}