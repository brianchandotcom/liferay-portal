/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.user.action.contributor;

import com.liferay.users.admin.user.action.contributor.UserActionContributor;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pei-Jung Lan
 */
@Component(service = UserActionContributor.class)
public class ExportPersonalDataUserActionContributor
	extends BaseUADUserActionContributor {

	@Override
	protected String getKey() {
		return "export-personal-data";
	}

	@Override
	protected String getMVCRenderCommandName() {
		return "/user_associated_data/view_uad_export_processes";
	}

}