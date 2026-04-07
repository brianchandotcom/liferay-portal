/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.frontend.data.set.action;

import com.liferay.frontend.data.set.action.FDSItemsActions;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemBuilder;
import com.liferay.frontend.data.set.model.FDSActionDropdownItemList;
import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juanjo Fernández
 */
@Component(
	property = "frontend.data.set.name=" + FDSSampleFDSNames.MY_USER_TASKS,
	service = FDSItemsActions.class
)
public class MyUserTasksFDSItemsActions implements FDSItemsActions {

	@Override
	public List<FDSActionDropdownItem> getFDSActionDropdownItems(
		HttpServletRequest httpServletRequest) {

		return FDSActionDropdownItemList.of(
			FDSActionDropdownItemBuilder.setFDSActionDropdownItems(
				FDSActionDropdownItemList.of(
					FDSActionDropdownItemBuilder.setTarget(
						"modal-workflow-transition"
					).build(
						"workflow-transition"
					))
			).setSeparator(
				true
			).setType(
				"group"
			).build(
				"workflow-transitions"
			),
			FDSActionDropdownItemBuilder.setIcon(
				"emoji"
			).setLabel(
				LanguageUtil.get(httpServletRequest, "assign-to-me")
			).setPermissionKey(
				"assignToMe"
			).setTarget(
				"headless"
			).setVisibilityFilters(
				HashMapBuilder.<String, Object>put(
					"assignedToMe", false
				).put(
					"completed", false
				).build()
			).build(
				"assignToMeWorkflowTask"
			));
	}

}