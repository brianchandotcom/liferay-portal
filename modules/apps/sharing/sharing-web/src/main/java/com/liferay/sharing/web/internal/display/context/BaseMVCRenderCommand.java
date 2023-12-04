/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.web.internal.display.context;

import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingDropdownItemFactory;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
public abstract class BaseMVCRenderCommand {

	public ViewSharedAssetsDisplayContext getViewSharedAssetsDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		return new ViewSharedAssetsDisplayContext(
			groupLocalService, itemSelector,
			portal.getLiferayPortletRequest(renderRequest),
			portal.getLiferayPortletResponse(renderResponse),
			sharingConfigurationFactory, sharingDropdownItemFactory,
			sharingEntryInterpreterProvider::getSharingEntryInterpreter,
			sharingEntryLocalService, sharingPermission);
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected ItemSelector itemSelector;

	@Reference
	protected Portal portal;

	@Reference
	protected SharingConfigurationFactory sharingConfigurationFactory;

	@Reference
	protected SharingDropdownItemFactory sharingDropdownItemFactory;

	@Reference
	protected SharingEntryInterpreterProvider sharingEntryInterpreterProvider;

	@Reference
	protected SharingEntryLocalService sharingEntryLocalService;

	@Reference
	protected SharingPermission sharingPermission;

}