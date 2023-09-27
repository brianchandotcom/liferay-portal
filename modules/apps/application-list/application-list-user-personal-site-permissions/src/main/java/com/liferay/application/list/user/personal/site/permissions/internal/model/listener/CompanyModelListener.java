/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.user.personal.site.permissions.internal.model.listener;

import com.liferay.application.list.PanelApp;
import com.liferay.application.list.PanelAppRegistry;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.application.list.display.context.logic.PanelCategoryHelper;
import com.liferay.application.list.user.personal.site.permissions.internal.UserPersonalSitePermissions;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterCreate(Company company) {
		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				PanelCategoryHelper panelCategoryHelper =
					new PanelCategoryHelper(
						_panelAppRegistry, _panelCategoryRegistry);

				List<PanelApp> panelApps = panelCategoryHelper.getAllPanelApps(
					PanelCategoryKeys.SITE_ADMINISTRATION);

				List<Portlet> portlets = new ArrayList<>(panelApps.size());

				for (PanelApp panelApp : panelApps) {
					Portlet portlet = _portletLocalService.getPortletById(
						panelApp.getPortletId());

					portlets.add(portlet);
				}

				_userPersonalSitePermissions.initPermissions(
					company.getCompanyId(), portlets);

				return null;
			});
	}

	@Reference
	private PanelAppRegistry _panelAppRegistry;

	@Reference
	private PanelCategoryRegistry _panelCategoryRegistry;

	@Reference
	private PortletLocalService _portletLocalService;

	@Reference
	private UserPersonalSitePermissions _userPersonalSitePermissions;

}