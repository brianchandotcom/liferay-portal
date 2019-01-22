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

package com.liferay.segments.internal.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.mobile.device.Device;
import com.liferay.portal.kernel.mobile.device.Dimensions;
import com.liferay.portal.kernel.servlet.BrowserSniffer;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.context.Context;

import java.time.LocalDate;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = RequestContextMapper.class)
public class RequestContextMapper {

	public Context map(HttpServletRequest request) {
		Context context = new Context();

		context.put(Context.BROWSER, _browserSniffer.getBrowserId(request));

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Device device = themeDisplay.getDevice();

		Dimensions screenResolution = null;

		if (device != null) {
			context.put(Context.DEVICE_BRAND, device.getBrand());
			context.put(Context.DEVICE_MODEL, device.getModel());

			screenResolution = device.getScreenResolution();
		}
		else {
			context.put(Context.DEVICE_BRAND, StringPool.BLANK);
			context.put(Context.DEVICE_MODEL, StringPool.BLANK);

			screenResolution = Dimensions.UNKNOWN;
		}

		context.put(
			Context.DEVICE_SCREEN_RESOLUTION_HEIGHT,
			screenResolution.getHeight());
		context.put(
			Context.DEVICE_SCREEN_RESOLUTION_WIDTH,
			screenResolution.getWidth());

		ZonedDateTime now = ZonedDateTime.now();

		context.put(Context.LANGUAGE_ID, themeDisplay.getLanguageId());
		context.put(Context.LOCAL_DATE, LocalDate.from(now));
		context.put(Context.URL, GetterUtil.getString(request.getRequestURL()));

		return context;
	}

	@Reference
	private BrowserSniffer _browserSniffer;

	@Reference
	private Portal _portal;

}