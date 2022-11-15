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

package com.liferay.translation.internal.security.permission;

import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.util.PropsValues;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia Garcia
 */
@Component(service = {})
public class TranslationResourceActionsActivator {

	@Activate
	protected void activate(BundleContext bundleContext) throws Exception {
		String xml = StringUtil.read(
			TranslationResourceActionsActivator.class.getClassLoader(),
			"/com/liferay/translation/internal/security/permission" +
				"/dependencies/resource-actions.xml.tpl");

		String[] languageIds = ArrayUtil.sortedUnique(PropsValues.LOCALES);

		for (int i = 0; i < languageIds.length; i++) {
			_resourceActions.populateModelResources(
				SAXReaderUtil.read(
					StringUtil.replace(
						StringUtil.replace(
							xml, "[$LANGUAGE_ID$]", languageIds[i]),
						"[$WEIGHT$]", String.valueOf(i))));
		}
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private ResourceActions _resourceActions;

}