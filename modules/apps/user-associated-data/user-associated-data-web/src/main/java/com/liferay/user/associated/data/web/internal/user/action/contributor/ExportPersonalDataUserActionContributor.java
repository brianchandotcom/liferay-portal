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

package com.liferay.user.associated.data.web.internal.user.action.contributor;

import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.users.admin.user.action.contributor.UserActionContributor;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UserActionContributor.class)
public class ExportPersonalDataUserActionContributor
	extends BaseUADUserActionContributor {

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return _resourceBundleLoader.loadResourceBundle(locale);
	}

	@Override
	protected String getKey() {
		return "export-personal-data";
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/view_uad_export_processes";
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(bundle.symbolic.name=com.liferay.user.associated.data.web)"
	)
	private volatile ResourceBundleLoader _resourceBundleLoader;

}