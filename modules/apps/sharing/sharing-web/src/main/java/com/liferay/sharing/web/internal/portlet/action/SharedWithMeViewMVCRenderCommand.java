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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.context.SharedWithMeViewDisplayContext;

import java.util.Objects;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARED_WITH_ME,
		"mvc.command.name=/", "mvc.command.name=/shared_with_me/view",
		"mvc.command.name=/shared_with_me/view_sharing_entry"
	},
	service = MVCRenderCommand.class
)
public class SharedWithMeViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Objects.equals(
				renderRequest.getParameter("mvcRenderCommandName"),
				"/shared_with_me/view_sharing_entry")) {

			long sharingEntryId = ParamUtil.getLong(
				renderRequest, "sharingEntryId");

			try {
				SharingEntry sharingEntry =
					_sharingEntryLocalService.getSharingEntry(sharingEntryId);

				if (sharingEntry.getToUserId() != themeDisplay.getUserId()) {
					throw new PrincipalException(
						StringBundler.concat(
							"user id ", themeDisplay.getUserId(),
							" does not have permission to view sharing entry ",
							"id ", sharingEntryId));
				}

				SharingEntryInterpreter<?> sharingEntryInterpreter =
					_getSharingEntryInterpreter(sharingEntry.getClassNameId());

				if (sharingEntryInterpreter == null) {
					throw new PortletException(
						"sharing entry interpreter is null for class name id " +
							sharingEntry.getClassNameId());
				}

				renderRequest.setAttribute(
					SharingEntryInterpreter.class.getName(),
					sharingEntryInterpreter);

				return "/shared_with_me/view_sharing_entry.jsp";
			}
			catch (Exception e) {
				throw new PortletException(e);
			}
		}

		SharedWithMeViewDisplayContext sharedWithMeViewDisplayContext =
			new SharedWithMeViewDisplayContext(
				themeDisplay, _sharingEntryLocalService,
				sharingEntry -> _getSharingEntryInterpreter(
					sharingEntry.getClassNameId()));

		renderRequest.setAttribute(
			SharedWithMeViewDisplayContext.class.getName(),
			sharedWithMeViewDisplayContext);

		return "/shared_with_me/view.jsp";
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap =
			(ServiceTrackerMap<Long, SharingEntryInterpreter<?>>)
				(ServiceTrackerMap)
					ServiceTrackerMapFactory.openSingleValueMap(
						bundleContext, SharingEntryInterpreter.class,
						"(model.class.name=*)",
						(serviceReference, emitter) -> emitter.emit(
							_classNameLocalService.getClassNameId(
								(String)serviceReference.getProperty(
									"model.class.name"))));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private <T> SharingEntryInterpreter<T> _getSharingEntryInterpreter(
		long classNameId) {

		return (SharingEntryInterpreter<T>)_serviceTrackerMap.getService(
			classNameId);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private ServiceTrackerMap<Long, SharingEntryInterpreter<?>>
		_serviceTrackerMap;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}