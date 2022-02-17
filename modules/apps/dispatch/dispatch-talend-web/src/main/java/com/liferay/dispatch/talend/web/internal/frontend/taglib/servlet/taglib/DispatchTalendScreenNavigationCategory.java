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

package com.liferay.dispatch.talend.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.admin.kernel.util.Omniadmin;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.constants.DispatchWebKeys;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataProvider;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.repository.DispatchFileRepository;
import com.liferay.dispatch.talend.web.internal.display.context.TalendDispatchDisplayContext;
import com.liferay.dispatch.talend.web.internal.executor.TalendDispatchTaskExecutor;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class DispatchTalendScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<DispatchTrigger> {

	@Override
	public String getCategoryKey() {
		return TalendDispatchTaskExecutor.TALEND;
	}

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return DispatchConstants.SCREEN_NAVIGATION_KEY_DISPATCH_GENERAL;
	}

	@Override
	public boolean isVisible(User user, DispatchTrigger dispatchTrigger) {
		if ((dispatchTrigger == null) ||
			!Objects.equals(
				dispatchTrigger.getDispatchTaskExecutorType(),
				TalendDispatchTaskExecutor.TALEND) ||
			!_omniadmin.isOmniadmin(user)) {

			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			new TalendDispatchDisplayContext(
				_dispatchTriggerMetadataProvider.getDispatchTriggerMetadata(
					_getDispatchTriggerId(httpServletRequest))));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/view.jsp");
	}

	private long _getDispatchTriggerId(HttpServletRequest httpServletRequest) {
		DispatchTrigger dispatchTrigger =
			(DispatchTrigger)httpServletRequest.getAttribute(
				DispatchWebKeys.DISPATCH_TRIGGER);

		if (dispatchTrigger == null) {
			return 0;
		}

		return dispatchTrigger.getDispatchTriggerId();
	}

	@Reference
	private DispatchFileRepository _dispatchFileRepository;

	@Reference
	private DispatchTriggerMetadataProvider _dispatchTriggerMetadataProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Omniadmin _omniadmin;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.dispatch.talend.web)"
	)
	private ServletContext _servletContext;

}