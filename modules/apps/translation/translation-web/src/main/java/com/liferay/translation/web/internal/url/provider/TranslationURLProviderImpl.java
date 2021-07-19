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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.translation.constants.TranslationPortletKeys;
import com.liferay.translation.url.provider.TranslationURLProvider;

import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = TranslationURLProvider.class)
public class TranslationURLProviderImpl implements TranslationURLProvider {

	@Override
	public PortletURL getExportTranslationURL(
		long groupId, long classNameId, long classPK,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory) {

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createRenderURL(
				TranslationPortletKeys.TRANSLATION)
		).setMVCRenderCommandName(
			"/translation/export_translation"
		).setParameter(
			"classNameId", classNameId
		).setParameter(
			"classPK", classPK
		).setParameter(
			"groupId", groupId
		).build();
	}

	@Override
	public PortletURL getImportTranslationURL(
			long groupId, long classNameId, long classPK,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory)
		throws PortalException {

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createControlPanelRenderURL(
				TranslationPortletKeys.TRANSLATION,
				_groupLocalService.getGroup(groupId), 0, 0)
		).setMVCRenderCommandName(
			"/translation/import_translation"
		).setParameter(
			"classNameId", classNameId
		).setParameter(
			"classPK", classPK
		).setParameter(
			"groupId", groupId
		).buildPortletURL();
	}

	@Override
	public PortletURL getTranslateURL(
			long groupId, long classNameId, long classPK,
			RequestBackedPortletURLFactory requestBackedPortletURLFactory)
		throws PortalException {

		return PortletURLBuilder.create(
			requestBackedPortletURLFactory.createControlPanelRenderURL(
				TranslationPortletKeys.TRANSLATION,
				_groupLocalService.getGroup(groupId), 0, 0)
		).setMVCRenderCommandName(
			"/translation/translate"
		).setParameter(
			"classNameId", classNameId
		).setParameter(
			"classPK", classPK
		).buildPortletURL();
	}

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
		).buildPortletURL();
	}

	@Reference
	private GroupLocalService _groupLocalService;

}