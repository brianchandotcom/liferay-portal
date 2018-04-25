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

package com.liferay.forms.apio.internal.provider;

import com.liferay.apio.architect.provider.Provider;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.forms.apio.internal.FormInstanceRecordServiceContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;

import javax.servlet.http.HttpServletRequest;

import javax.ws.rs.InternalServerErrorException;

import org.osgi.service.component.annotations.Component;

/**
 * Lets resources provide the service context {@code ServiceContext} as a
 * parameter in the methods of the different routes builders.
 *
 * @author Paulo Cruz
 */
@Component(immediate = true)
public class FormInstanceRecordServiceContextProvider
	implements Provider<FormInstanceRecordServiceContext> {

	@Override
	public FormInstanceRecordServiceContext createContext(
		HttpServletRequest httpServletRequest) {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				DDMFormInstanceRecord.class.getName(), httpServletRequest);

			return new FormInstanceRecordServiceContext(serviceContext);
		}
		catch (PortalException pe) {
			throw new InternalServerErrorException(pe.getMessage(), pe);
		}
	}

}