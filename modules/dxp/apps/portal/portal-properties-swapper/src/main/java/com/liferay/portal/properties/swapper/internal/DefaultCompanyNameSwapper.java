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

package com.liferay.portal.properties.swapper.internal;

import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true)
public class DefaultCompanyNameSwapper {

	@Activate
	public void activate() {
		if (PropsHelperUtil.isCustomized(PropsKeys.COMPANY_DEFAULT_NAME)) {
			return;
		}

		PropsValues.COMPANY_DEFAULT_NAME = "Liferay DXP";
	}

}