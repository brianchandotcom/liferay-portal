/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.web.internal.dynamic.data.mapping.util;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.util.DDMTemplatePermissionSupport;
import com.liferay.template.constants.TemplatePortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"add.template.action.id=" + DDMActionKeys.ADD_TEMPLATE,
		"default.model.resource.name=true",
		"model.class.name=com.liferay.template.model.TemplateEntry"
	},
	service = DDMTemplatePermissionSupport.class
)
public class InformationTemplateDDMTemplatePermissionSupport
	implements DDMTemplatePermissionSupport {

	@Override
	public String getResourceName(long classNameId) {
		return TemplatePortletKeys.TEMPLATE;
	}

}