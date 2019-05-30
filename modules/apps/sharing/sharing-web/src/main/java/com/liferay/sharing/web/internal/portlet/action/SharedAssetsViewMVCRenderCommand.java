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

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.configuration.SharingConfigurationFactory;
import com.liferay.sharing.display.context.util.SharingMenuItemFactory;
import com.liferay.sharing.filter.SharedAssetsFilterItem;
import com.liferay.sharing.interpreter.SharingEntryInterpreter;
import com.liferay.sharing.interpreter.SharingEntryInterpreterProvider;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.renderer.SharingEntryViewRenderer;
import com.liferay.sharing.security.permission.SharingPermission;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.context.SharedAssetsViewDisplayContext;
import com.liferay.sharing.web.internal.servlet.taglib.ui.SharingEntryMenuItemContributorRegistry;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
		"javax.portlet.name=" + SharingPortletKeys.SHARED_ASSETS,
		"mvc.command.name=/",
		"mvc.command.name=/shared_assets/select_asset_type",
		"mvc.command.name=/shared_assets/view",
		"mvc.command.name=/shared_assets/view_sharing_entry"
	},
	service = MVCRenderCommand.class
)
public class SharedAssetsViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String mvcRenderCommandName = ParamUtil.getString(
			renderRequest, "mvcRenderCommandName");

		renderRequest.setAttribute(
			SharedAssetsViewDisplayContext.class.getName(),
			_getSharedAssetsViewDisplayContext(renderRequest, renderResponse));

		if (Objects.equals(
				mvcRenderCommandName, "/shared_assets/view_sharing_entry")) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			try {
				SharingEntry sharingEntry = _getSharingEntry(
					renderRequest, themeDisplay);

				if ((sharingEntry.getUserId() != themeDisplay.getUserId()) &&
					(sharingEntry.getToUserId() != themeDisplay.getUserId())) {

					throw new PrincipalException(
						StringBundler.concat(
							"User ", themeDisplay.getUserId(),
							" does not have permission to view sharing entry ",
							sharingEntry.getSharingEntryId()));
				}

				SharingEntryInterpreter sharingEntryInterpreter =
					_sharingEntryInterpreterProvider.getSharingEntryInterpreter(
						sharingEntry);

				if (sharingEntryInterpreter == null) {
					throw new PortletException(
						"Sharing entry interpreter is null for class name ID " +
							sharingEntry.getClassNameId());
				}

				SharingEntryViewRenderer sharingEntryViewRenderer =
					sharingEntryInterpreter.getSharingEntryViewRenderer();

				sharingEntryViewRenderer.render(
					sharingEntry, _portal.getHttpServletRequest(renderRequest),
					_portal.getHttpServletResponse(renderResponse));

				return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
			}
			catch (PortalException pe) {
				SessionErrors.add(renderRequest, pe.getClass());

				return "/shared_assets/error.jsp";
			}
			catch (IOException ioe) {
				throw new PortletException(ioe);
			}
		}

		if (Objects.equals(
				mvcRenderCommandName, "/shared_assets/select_asset_type")) {

			return "/shared_assets/select_asset_type.jsp";
		}

		return "/shared_assets/view.jsp";
	}

	@Activate
	protected void activate(final BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SharedAssetsFilterItem.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"navigation.item.order")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private SharedAssetsViewDisplayContext _getSharedAssetsViewDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(renderResponse);

		List<SharedAssetsFilterItem> sharedAssetsFilterItems =
			new ArrayList<>();

		_serviceTrackerList.forEach(sharedAssetsFilterItems::add);

		return new SharedAssetsViewDisplayContext(
			_groupLocalService, liferayPortletRequest, liferayPortletResponse,
			sharedAssetsFilterItems, _sharingConfigurationFactory,
			_sharingEntryInterpreterProvider::getSharingEntryInterpreter,
			_sharingEntryLocalService, _sharingEntryMenuItemContributorRegistry,
			_sharingMenuItemFactory, _sharingPermission);
	}

	private SharingEntry _getSharingEntry(
			RenderRequest renderRequest, ThemeDisplay themeDisplay)
		throws PortalException {

		long sharingEntryId = ParamUtil.getLong(
			renderRequest, "sharingEntryId");

		SharingEntry sharingEntry = _sharingEntryLocalService.fetchSharingEntry(
			sharingEntryId);

		if (sharingEntry != null) {
			return sharingEntry;
		}

		long classNameId = ParamUtil.getLong(renderRequest, "classNameId");
		long classPK = ParamUtil.getLong(renderRequest, "classPK");

		return _sharingEntryLocalService.getSharingEntry(
			themeDisplay.getUserId(), classNameId, classPK);
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private ServiceTrackerList<SharedAssetsFilterItem, SharedAssetsFilterItem>
		_serviceTrackerList;

	@Reference
	private SharingConfigurationFactory _sharingConfigurationFactory;

	@Reference
	private SharingEntryInterpreterProvider _sharingEntryInterpreterProvider;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryMenuItemContributorRegistry
		_sharingEntryMenuItemContributorRegistry;

	@Reference
	private SharingMenuItemFactory _sharingMenuItemFactory;

	@Reference
	private SharingPermission _sharingPermission;

}