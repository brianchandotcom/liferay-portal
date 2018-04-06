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

package com.liferay.fragment.display.web.internal.portlet;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletLayoutListener;
import com.liferay.portal.kernel.portlet.PortletLayoutListenerException;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + FragmentPortletKeys.FRAGMENT_DISPLAY,
	service = PortletLayoutListener.class
)
public class FragmentEntryDisplayPortletLayoutListener
	implements PortletLayoutListener {

	@Override
	public void onAddToLayout(String portletId, long plid)
		throws PortletLayoutListenerException {
	}

	@Override
	public void onMoveInLayout(String portletId, long plid)
		throws PortletLayoutListenerException {
	}

	@Override
	public void onRemoveFromLayout(String portletId, long plid)
		throws PortletLayoutListenerException {

		try {
			Layout layout = _layoutLocalService.getLayout(plid);

			PortletPreferences portletPreferences =
				PortletPreferencesFactoryUtil.getPortletSetup(
					layout, portletId, StringPool.BLANK);

			long fragmentEntryLinkId = GetterUtil.getLong(
				portletPreferences.getValue("fragmentEntryLinkId", null));

			if (fragmentEntryLinkId <= 0) {
				return;
			}

			_fragmentEntryLinkLocalService.deleteFragmentEntryLink(
				fragmentEntryLinkId);
		}
		catch (Exception e) {
			throw new PortletLayoutListenerException(e);
		}
	}

	@Override
	public void onSetup(String portletId, long plid)
		throws PortletLayoutListenerException {
	}

	@Override
	public void updatePropertiesOnRemoveFromLayout(
			String portletId, UnicodeProperties typeSettingsProperties)
		throws PortletLayoutListenerException {
	}

	@Reference
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

}