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

package com.liferay.change.tracking.service.impl;

import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.base.CTProcessServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Preston Crary
 */
@Component(
	property = {
		"json.web.service.context.name=ct",
		"json.web.service.context.path=CTProcess"
	},
	service = AopService.class
)
public class CTProcessServiceImpl extends CTProcessServiceBaseImpl {

	@Override
	public List<CTProcess> getCTProcesses(
		long companyId, long userId, String keywords, int status, int start,
		int end, OrderByComparator<CTProcess> orderByComparator) {

		return ctProcessFinder.filterFindByC_U_N_D_S(
			companyId, userId, keywords, status, start, end, orderByComparator);
	}

}