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

package com.liferay.layout.admin.web.internal.portlet.action;

import com.liferay.layout.admin.constants.LayoutAdminPortletKeys;
import com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration;
import com.liferay.layout.admin.web.internal.display.context.LayoutsAdminDisplayContext;
import com.liferay.layout.admin.web.internal.display.context.MillerColumnsDisplayContext;
import com.liferay.layout.admin.web.internal.handler.LayoutExceptionRequestHandler;
import com.liferay.layout.util.LayoutCopyHelper;
import com.liferay.layout.util.template.LayoutConverterRegistry;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.LayoutService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.staging.StagingGroupHelper;

import java.util.Iterator;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	configurationPid = "com.liferay.layout.admin.web.internal.configuration.LayoutConverterConfiguration",
	immediate = true,
	property = {
		"javax.portlet.name=" + LayoutAdminPortletKeys.GROUP_PAGES,
		"mvc.command.name=/layout_admin/move_layout"
	},
	service = MVCActionCommand.class
)
public class MoveLayoutMVCActionCommand extends BaseAddLayoutMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_layoutConverterConfiguration = ConfigurableUtil.createConfigurable(
			LayoutConverterConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long parentPlid = ParamUtil.getLong(actionRequest, "parentPlid");

		JSONArray plidsJSONArray = JSONFactoryUtil.createJSONArray(
			ParamUtil.getString(actionRequest, "plids"));

		Iterator<JSONObject> iterator = plidsJSONArray.iterator();

		try {
			while (iterator.hasNext()) {
				JSONObject jsonObject = iterator.next();

				long plid = jsonObject.getLong("plid");
				int priority = jsonObject.getInt("position");

				Layout layout = layoutLocalService.fetchLayout(plid);

				if (layout.getParentPlid() == parentPlid) {
					_layoutService.updatePriority(plid, priority);
				}
				else {
					_layoutService.updatePriority(plid, Integer.MAX_VALUE);

					_layoutService.updateParentLayoutIdAndPriority(
						plid, parentPlid, priority);
				}
			}

			LiferayPortletRequest liferayPortletRequest =
				_portal.getLiferayPortletRequest(actionRequest);
			LiferayPortletResponse liferayPortletResponse =
				_portal.getLiferayPortletResponse(actionResponse);

			MillerColumnsDisplayContext millerColumnsDisplayContext =
				new MillerColumnsDisplayContext(
					new LayoutsAdminDisplayContext(
						_layoutConverterConfiguration, _layoutConverterRegistry,
						_layoutCopyHelper, liferayPortletRequest,
						liferayPortletResponse, _stagingGroupHelper),
					liferayPortletRequest, liferayPortletResponse);

			JSONObject jsonObject = JSONUtil.put(
				"layoutColumns",
				millerColumnsDisplayContext.getLayoutColumnsJSONArray());

			JSONPortletResponseUtil.writeJSON(
				liferayPortletRequest, liferayPortletResponse, jsonObject);
		}
		catch (Exception exception) {
			hideDefaultErrorMessage(actionRequest);

			_layoutExceptionRequestHandler.handleException(
				actionRequest, actionResponse, exception);
		}
	}

	private volatile LayoutConverterConfiguration _layoutConverterConfiguration;

	@Reference
	private LayoutConverterRegistry _layoutConverterRegistry;

	@Reference
	private LayoutCopyHelper _layoutCopyHelper;

	@Reference
	private LayoutExceptionRequestHandler _layoutExceptionRequestHandler;

	@Reference
	private LayoutService _layoutService;

	@Reference
	private Portal _portal;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}