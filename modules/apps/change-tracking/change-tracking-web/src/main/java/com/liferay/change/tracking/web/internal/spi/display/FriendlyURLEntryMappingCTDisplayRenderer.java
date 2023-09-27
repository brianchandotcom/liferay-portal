/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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