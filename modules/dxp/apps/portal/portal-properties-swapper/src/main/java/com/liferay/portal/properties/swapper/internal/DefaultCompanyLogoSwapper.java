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

import com.liferay.portal.kernel.image.ImageTool;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;

import java.lang.reflect.Method;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(enabled = false, immediate = true, service = {})
public class DefaultCompanyLogoSwapper {

	@Activate
	protected void activate(BundleContext bundleContext) {
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

		ImageTool imageTool = ImageToolUtil.getImageTool();

		Class<?> clazz = imageTool.getClass();

		try {
			Method method = clazz.getMethod("afterPropertiesSet");

			method.invoke(imageTool);
		}
		catch (ReflectiveOperationException roe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to swap default company logo", roe);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultCompanyLogoSwapper.class);

}