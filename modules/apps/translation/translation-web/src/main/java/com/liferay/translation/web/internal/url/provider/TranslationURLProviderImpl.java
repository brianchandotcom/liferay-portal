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

package com.liferay.translation.web.internal.url.provider;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.url.provider.TranslationURLProvider;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = TranslationURLProvider.class)
public class TranslationURLProviderImpl implements TranslationURLProvider {

	@Override
	public PortletURL getTranslateURL(
		long classNameId, long classPK,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				TranslationPortletKeys.TRANSLATION)
		).setMVCRenderCommandName(
			"/translation/translate"
		).setParameter(
			"classNameId", classNameId
		).setParameter(
			"classPK", classPK
		).build();
	}

}