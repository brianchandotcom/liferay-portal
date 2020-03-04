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

package com.liferay.data.engine.service.impl;

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.base.DataDefinitionAppLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;

import com.liferay.portal.kernel.exception.PortalException;
import org.osgi.service.component.annotations.Component;

import java.util.Locale;
import java.util.Map;

/**
 * The implementation of the data definition app local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.data.engine.service.DataDefinitionAppLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DataDefinitionAppLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.data.engine.model.DataDefinitionApp",
	service = AopService.class
)
public class DataDefinitionAppLocalServiceImpl
	extends DataDefinitionAppLocalServiceBaseImpl {

	public DEDataDefinition addDataDefinition(Map<Locale, String> name)
			throws PortalException {
		return null;
	}
}