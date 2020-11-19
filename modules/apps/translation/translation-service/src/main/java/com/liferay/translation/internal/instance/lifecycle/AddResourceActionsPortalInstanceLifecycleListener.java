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

package com.liferay.translation.internal.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddResourceActionsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		String xml = StringUtil.read(
			AddResourceActionsPortalInstanceLifecycleListener.class.
				getClassLoader(),
			"/com/liferay/translation/internal/instance/lifecycle" +
				"/dependencies/resource-actions.xml.tpl");

		String[] languageIds = ArrayUtil.sortedUnique(PropsValues.LOCALES);

		Set<String> names = new HashSet<>();

		for (int i = 0; i < languageIds.length; i++) {
			_resourceActions.read(
				SAXReaderUtil.read(
					StringUtil.replace(
						StringUtil.replace(
							xml, "[$LANGUAGE_ID$]", languageIds[i]),
						"[$WEIGHT$]", String.valueOf(i))),
				names);
		}

		for (String name : names) {
			_resourceActionLocalService.checkResourceActions(
				name, _resourceActions.getModelResourceActions(name));
		}
	}

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceActions _resourceActions;

}