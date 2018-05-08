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

import com.liferay.users.admin.user.action.contributor.UserActionContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(immediate = true, service = UserActionContributor.class)
public class ExportPersonalDataUserActionContributor
	extends BaseUADUserActionContributor {

	@Override
	protected String getKey() {
		return "export-personal-data";
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/view_uad_export_processes";
	}

}