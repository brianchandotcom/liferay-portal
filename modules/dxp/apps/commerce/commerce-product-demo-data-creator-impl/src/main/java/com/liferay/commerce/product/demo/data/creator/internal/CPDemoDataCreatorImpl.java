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

package com.liferay.commerce.product.demo.data.creator.internal;

import com.liferay.commerce.product.demo.data.creator.CPDemoDataCreator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CPDemoDataCreator.class)
public class CPDemoDataCreatorImpl implements CPDemoDataCreator {

	@Override
	public void create(long userId, long groupId, boolean buildSkus)
		throws IOException, PortalException {
	}

	@Override
	public void delete() throws PortalException {
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPDemoDataCreatorImpl.class);

}