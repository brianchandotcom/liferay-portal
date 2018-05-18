/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.user.segment.web.internal.servlet.taglib.ui;

import com.liferay.commerce.user.segment.constants.CommerceUserSegmentScreenNavigationConstants;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentEntry;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
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
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class CommerceUserSegmentEntryCriteriaScreenNavigationEntry
	implements ScreenNavigationEntry<CommerceUserSegmentEntry> {

	@Override
	public String getCategoryKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_USER_SEGMENT_DETAIL;
	}

	@Override
	public String getEntryKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_USER_SEGMENT_ENTRY_CRITERIA;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceUserSegmentScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_USER_SEGMENT_ENTRY_CRITERIA);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceUserSegmentScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_USER_SEGMENT_ENTRY_GENERAL;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/user_segment_entry/criteria.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.user.segment.web)"
	)
	private ServletContext _servletContext;

}