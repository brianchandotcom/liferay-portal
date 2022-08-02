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

package com.liferay.address.web.internal.portlet.action;

import com.liferay.address.web.internal.constants.AddressPortletKeys;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.NoSuchRegionException;
import com.liferay.portal.kernel.exception.RegionNameException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AddressPortletKeys.COUNTRIES_MANAGEMENT_ADMIN,
		"mvc.command.name=/address/edit_region"
	},
	service = AopService.class
)
public class EditRegionMVCActionCommand
	extends BaseMVCActionCommand implements AopService, MVCActionCommand {

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String cmd = ParamUtil.getString(actionRequest, Constants.CMD);
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD)) {
				Region region = _addOrUpdateRegion(actionRequest);

				redirect = HttpComponentsUtil.setParameter(
					redirect, actionResponse.getNamespace() + "regionId",
					region.getRegionId());
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_addOrUpdateRegion(actionRequest);
			}

			if (Validator.isNotNull(redirect)) {
				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchRegionException ||
				throwable instanceof PrincipalException) {

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (throwable instanceof RegionNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "/address/edit_region");
			}
			else {
				throw new Exception(throwable);
			}
		}
	}

	private Region _addOrUpdateRegion(ActionRequest actionRequest)
		throws Exception {

		long regionId = ParamUtil.getLong(actionRequest, "regionId");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String regionCode = ParamUtil.getString(actionRequest, "regionCode");
		String name = ParamUtil.getString(actionRequest, "name");
		double position = ParamUtil.getDouble(actionRequest, "position");

		Region region = null;

		if (regionId <= 0) {
			long countryId = ParamUtil.getLong(actionRequest, "countryId");

			region = _regionService.addRegion(
				countryId, active, name, position, regionCode,
				ServiceContextFactory.getInstance(
					Region.class.getName(), actionRequest));
		}
		else {
			region = _regionService.updateRegion(
				regionId, active, name, position, regionCode);
		}

		_updateRegionLocalizations(
			region,
			LocalizationUtil.getLocalizationMap(actionRequest, "title"));

		return region;
	}

	private void _updateRegionLocalizations(
			Region region, Map<Locale, String> localizationMap)
		throws Exception {

		Map<String, String> map = new HashMap<>();

		for (Map.Entry<Locale, String> entry : localizationMap.entrySet()) {
			map.put(_language.getLanguageId(entry.getKey()), entry.getValue());
		}

		_regionLocalService.updateRegionLocalizations(region, map);
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference
	private RegionService _regionService;

}