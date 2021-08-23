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

package com.liferay.object.internal.scope;

import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "object.scope.provider.key=" + ObjectDefinitionConstants.SCOPE_COMPANY,
	service = ObjectScopeProvider.class
)
public class CompanyInstanceObjectScopeProviderImpl
	implements ObjectScopeProvider {

	@Override
	public long getGroupId(HttpServletRequest httpServletRequest) {
		return 0;
	}

	@Override
	public String getKey() {
		return ObjectDefinitionConstants.SCOPE_COMPANY;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "company");
	}

	@Override
	public String[] getRootPanelCategoryKeys() {
		return new String[] {
			PanelCategoryKeys.CONTROL_PANEL, PanelCategoryKeys.COMMERCE,
			PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS
		};
	}

	@Override
	public boolean isGroupAware() {
		return false;
	}

	@Override
	public boolean isValidGroupId(long groupId) {
		if (groupId == 0) {
			return true;
		}

		return false;
	}

}