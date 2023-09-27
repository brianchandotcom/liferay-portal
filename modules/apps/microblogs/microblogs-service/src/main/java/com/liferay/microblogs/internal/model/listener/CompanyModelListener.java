/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.microblogs.internal.model.listener;

import com.liferay.microblogs.model.MicroblogsEntry;
import com.liferay.microblogs.service.MicroblogsEntryLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Roberto Díaz
 */
@Component(service = ModelListener.class)
public class CompanyModelListener extends BaseModelListener<Company> {

	@Override
	public void onAfterRemove(Company company) {
		try {
			ActionableDynamicQuery actionableDynamicQuery =
				_microblogsEntryLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setCompanyId(company.getCompanyId());
			actionableDynamicQuery.setPerformActionMethod(
				(MicroblogsEntry microblogsEntry) ->
					_microblogsEntryLocalService.deleteMicroblogsEntry(
						microblogsEntry));

			actionableDynamicQuery.performActions();
		}
		catch (Exception exception) {
			throw new ModelListenerException(exception);
		}
	}

	@Reference
	private MicroblogsEntryLocalService _microblogsEntryLocalService;

}