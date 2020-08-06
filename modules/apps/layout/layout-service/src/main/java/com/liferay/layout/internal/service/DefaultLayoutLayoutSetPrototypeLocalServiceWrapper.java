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

package com.liferay.layout.internal.service;

import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DefaultLayoutLayoutSetPrototypeLocalServiceWrapper
	extends LayoutSetPrototypeLocalServiceWrapper {

	public DefaultLayoutLayoutSetPrototypeLocalServiceWrapper() {
		super(null);
	}

	public DefaultLayoutLayoutSetPrototypeLocalServiceWrapper(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		super(layoutSetPrototypeLocalService);
	}

	@Override
	public LayoutSetPrototype addLayoutSetPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype = super.addLayoutSetPrototype(
			userId, companyId, nameMap, descriptionMap, active,
			layoutsUpdateable, serviceContext);

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("addDefaultLayout"), true)) {

			Layout defaultLayout = _layoutLocalService.addLayout(
				userId, layoutSetPrototype.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", null, null,
				LayoutConstants.TYPE_CONTENT, false, "/home", serviceContext);

			_layoutPageTemplateStructureLocalService.
				rebuildLayoutPageTemplateStructure(
					layoutSetPrototype.getGroupId(), defaultLayout.getPlid());

			Layout draftLayout = defaultLayout.fetchDraftLayout();

			UnicodeProperties unicodeProperties =
				defaultLayout.getTypeSettingsProperties();

			unicodeProperties.setProperty("published", Boolean.TRUE.toString());

			draftLayout.setTypeSettingsProperties(unicodeProperties);

			draftLayout = _layoutLocalService.updateLayout(draftLayout);

			_layoutPageTemplateStructureLocalService.
				rebuildLayoutPageTemplateStructure(
					layoutSetPrototype.getGroupId(), draftLayout.getPlid());
		}

		return layoutSetPrototype;
	}

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

}