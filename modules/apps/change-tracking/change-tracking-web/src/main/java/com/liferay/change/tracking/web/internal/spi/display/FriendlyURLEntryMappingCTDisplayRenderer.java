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

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.friendly.url.model.FriendlyURLEntryMapping;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(service = CTDisplayRenderer.class)
public class FriendlyURLEntryMappingCTDisplayRenderer
	extends BaseCTDisplayRenderer<FriendlyURLEntryMapping> {

	@Override
	public Class<FriendlyURLEntryMapping> getModelClass() {
		return FriendlyURLEntryMapping.class;
	}

	@Override
	public String getTitle(
		Locale locale, FriendlyURLEntryMapping friendlyURLEntryMapping) {

		ClassName className = _classNameLocalService.fetchClassName(
			friendlyURLEntryMapping.getClassNameId());

		if (className == null) {
			return null;
		}

		String[] classNames = StringUtil.split(
			className.getClassName(),
			ResourceActionsUtil.getCompositeModelNameSeparator());

		String modelResource = _resourceActions.getModelResource(
			locale, classNames[0]);

		if (modelResource.startsWith("model.resource.")) {
			modelResource = classNames[0];
		}

		return _language.format(
			locale, "x-for-x",
			new String[] {
				"model.resource." + FriendlyURLEntryMapping.class.getName(),
				modelResource
			});
	}

	@Override
	public boolean isHideable(FriendlyURLEntryMapping friendlyURLEntryMapping) {
		return true;
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Language _language;

	@Reference
	private ResourceActions _resourceActions;

}