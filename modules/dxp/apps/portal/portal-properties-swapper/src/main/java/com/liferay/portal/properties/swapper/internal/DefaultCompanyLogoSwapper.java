/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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