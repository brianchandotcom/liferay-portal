/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.instance.lifecycle.InitialRequestPortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Lino Alves
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddLayoutPrototypePortalInstanceLifecycleListener
	extends InitialRequestPortalInstanceLifecycleListener {

	@Override
	protected void doPortalInstanceRegistered(long companyId) throws Exception {
		Layout layout = searchLayoutFactory.createSearchLayoutPrototype(
			companyId);

		if (layout == null) {
			return;
		}

		Group guestGroup = groupLocalService.getGroup(
			companyId, GroupConstants.GUEST);

		try {
			MergeLayoutPrototypesThreadLocal.setInProgress(true);

			searchLayoutFactory.createSearchLayout(guestGroup);
		}
		finally {
			MergeLayoutPrototypesThreadLocal.setInProgress(false);
		}
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected SearchLayoutFactory searchLayoutFactory;

}