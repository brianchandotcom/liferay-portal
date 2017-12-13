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

package com.liferay.dynamic.data.lists.form.web.internal.layout.type.access.policy;

import com.liferay.dynamic.data.lists.form.web.internal.layout.type.constants.DDLFormPortletLayoutTypeConstants;
import com.liferay.portal.kernel.model.LayoutTypeAccessPolicy;
import com.liferay.portal.kernel.model.impl.ModificationDeniedLayoutTypeAccessPolicyImpl;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = {"layout.type=" + DDLFormPortletLayoutTypeConstants.LAYOUT_TYPE},
	service = LayoutTypeAccessPolicy.class
)
public class DDLFormPortletLayoutTypeAccessPolicy
	extends ModificationDeniedLayoutTypeAccessPolicyImpl {
}