/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.service;

import com.liferay.exportimport.kernel.staging.MergeLayoutPrototypesThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.sites.kernel.util.Sites;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author To Trinh
 */
@Component(service = ServiceWrapper.class)
public class LayoutSetLocalServiceWrapper
	extends com.liferay.portal.kernel.service.LayoutSetLocalServiceWrapper {

	@Override
	public void updateLayoutSetPrototypeLinkEnabled(
			long groupId, boolean privateLayout,
			boolean layoutSetPrototypeLinkEnabled,
			String layoutSetPrototypeUuid)
		throws PortalException {

		super.updateLayoutSetPrototypeLinkEnabled(
			groupId, privateLayout, layoutSetPrototypeLinkEnabled,
			layoutSetPrototypeUuid);

		try {
			MergeLayoutPrototypesThreadLocal.setSkipMerge(false);

			_sites.mergeLayoutSetPrototypeLayouts(
				_groupLocalService.getGroup(groupId),
				_layoutSetLocalService.getLayoutSet(groupId, privateLayout));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Could not force propagation from site template to site",
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSetLocalServiceWrapper.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference
	private Sites _sites;

}