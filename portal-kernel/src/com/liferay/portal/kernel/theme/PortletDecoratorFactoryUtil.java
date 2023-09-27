/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.theme;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.internal.portlet.decorator.PortletDecoratorImpl;
import com.liferay.portal.kernel.model.PortletDecorator;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;

/**
 * @author Eduardo García
 */
public class PortletDecoratorFactoryUtil {

	public static PortletDecorator getDefaultPortletDecorator() {
		return new PortletDecoratorImpl(
			getDefaultPortletDecoratorId(), StringPool.BLANK,
			getDefaultPortletDecoratorCssClass());
	}

	public static String getDefaultPortletDecoratorCssClass() {
		return PropsUtil.get(PropsKeys.DEFAULT_PORTLET_DECORATOR_CSS_CLASS);
	}

	public static String getDefaultPortletDecoratorId() {
		return PrefsPropsUtil.getString(
			CompanyThreadLocal.getCompanyId(),
			PropsKeys.DEFAULT_PORTLET_DECORATOR_ID);
	}

	public static PortletDecorator getPortletDecorator() {
		return new PortletDecoratorImpl();
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId) {

		return new PortletDecoratorImpl(portletDecoratorId);
	}

	public static PortletDecorator getPortletDecorator(
		String portletDecoratorId, String name, String cssClass) {

		return new PortletDecoratorImpl(portletDecoratorId, name, cssClass);
	}

}