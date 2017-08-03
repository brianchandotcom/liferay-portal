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
import com.liferay.portal.util.PropsUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true)
public class DefaultCompanyLogoSwapper {

	@Activate
	public void activate(BundleContext bundleContext) {
		if (PropsHelperUtil.isCustomized(
				PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO)) {

			return;
		}

		Bundle bundle = bundleContext.getBundle();

		PropsUtil.set(
			PropsKeys.IMAGE_DEFAULT_COMPANY_LOGO,
			bundle.getBundleId() +
				";com/liferay/portal/properties/swapper/internal" +
					"/default_company_logo.png");
	}

}