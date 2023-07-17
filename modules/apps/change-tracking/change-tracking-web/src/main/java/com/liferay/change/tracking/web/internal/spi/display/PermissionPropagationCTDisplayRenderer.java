/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.spi.display;

import com.liferay.change.tracking.spi.display.BaseCTDisplayRenderer;
import com.liferay.change.tracking.spi.display.CTDisplayRenderer;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.PermissionPropagation;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Quan Huynh
 */
@Component(service = CTDisplayRenderer.class)
public class PermissionPropagationCTDisplayRenderer
	extends BaseCTDisplayRenderer<PermissionPropagation> {

	@Override
	public Class<PermissionPropagation> getModelClass() {
		return PermissionPropagation.class;
	}

	@Override
	public String getTitle(
		Locale locale, PermissionPropagation permissionPropagation) {

		ClassName className = _classNameLocalService.fetchClassName(
			permissionPropagation.getClassNameId());

		if (className == null) {
			return null;
		}

		String modelResource = _resourceActions.getModelResource(
			locale, className.getClassName());

		return _language.format(
			locale, "x-for-x",
			new String[] {
				_resourceActions.getModelResourceNamePrefix() +
					PermissionPropagation.class.getName(),
				modelResource
			});
	}

	@Override
	public boolean isHideable(PermissionPropagation permissionPropagation) {
		return true;
	}

	@Override
	protected void buildDisplay(
		DisplayBuilder<PermissionPropagation> displayBuilder) {

		PermissionPropagation permissionPropagation = displayBuilder.getModel();

		displayBuilder.display(
			"propagate", permissionPropagation.isPropagate());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private Language _language;

	@Reference
	private ResourceActions _resourceActions;

}