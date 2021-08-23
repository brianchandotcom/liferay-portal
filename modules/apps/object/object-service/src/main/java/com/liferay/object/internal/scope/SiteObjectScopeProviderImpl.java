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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "object.scope.provider.key=" + ObjectDefinitionConstants.SCOPE_SITE,
	service = ObjectScopeProvider.class
)
public class SiteObjectScopeProviderImpl implements ObjectScopeProvider {

	@Override
	public long getGroupId(HttpServletRequest httpServletRequest)
		throws PortalException {

		return _portal.getScopeGroupId(httpServletRequest);
	}

	@Override
	public String getKey() {
		return ObjectDefinitionConstants.SCOPE_SITE;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "site");
	}

	@Override
	public String[] getRootPanelCategoryKeys() {
		return new String[] {PanelCategoryKeys.SITE_ADMINISTRATION};
	}

	@Override
	public boolean isGroupAware() {
		return true;
	}

	@Override
	public boolean isValidGroupId(long groupId) {
		Group group = _groupLocalService.fetchGroup(groupId);

		if ((group != null) && group.isSite()) {
			return true;
		}

		return false;
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}