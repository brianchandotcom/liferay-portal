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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.security.permission.ResourceActions;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.util.Locale;

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
				"/dependencies/languages.xml.tpl");

		for (Locale availableLocale : _language.getAvailableLocales()) {
			_resourceActions.read(
				null,
				SAXReaderUtil.read(
					StringUtil.replace(
						xml, "[$LANGUAGE$]",
						_language.getLanguageId(availableLocale))),
				null);
		}
	}

	@Reference
	private Language _language;

	@Reference
	private ResourceActions _resourceActions;

}