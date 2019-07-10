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

package com.liferay.portal.vulcan.internal.context;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.security.access.control.AccessControlThreadLocal;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.accept.language.AcceptLanguage;
import com.liferay.portal.vulcan.context.VulcanContextProvider;
import com.liferay.portal.vulcan.internal.accept.language.AcceptLanguageImpl;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = VulcanContextProvider.class)
public class AcceptLanaguageVulcanContextProvider
	implements VulcanContextProvider<AcceptLanguage> {

	@Override
	public Class<AcceptLanguage> getType() {
		return AcceptLanguage.class;
	}

	@Override
	public AcceptLanguage getValue() {
		return LazyProxyUtil.createProxy(
			AcceptLanguage.class,
			() -> new AcceptLanguageImpl(
				AccessControlThreadLocal.getHttpServletRequest(), _language,
				_portal));
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}