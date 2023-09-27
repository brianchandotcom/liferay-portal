/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconTracker;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.PortletConfigurationIconComparator;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class IconOptionsTag extends IncludeTag {

	@Override
	public int doEndTag() throws JspException {
		if (ListUtil.isEmpty(_getPortletConfigurationIcons())) {
			return SKIP_PAGE;
		}

		return super.doEndTag();
	}

	public boolean isMonospaced() {
		return _monospaced;
	}

	public void setMonospaced(boolean monospaced) {
		_monospaced = monospaced;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		setServletContext(ServletContextUtil.getServletContext());
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_monospaced = false;
		_portletConfigurationIcons = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-frontend:icon-options:dropdownItems", _getDropdownItems());
		httpServletRequest.setAttribute(
			"liferay-frontend:icon-options:monospaced", isMonospaced());
		httpServletRequest.setAttribute(
			"liferay-frontend:icon-options:portletConfigurationIcons",
			_getPortletConfigurationIcons());
	}

	private List<DropdownItem> _getDropdownItems() {
		return new DropdownItemList() {
			{
				for (PortletConfigurationIcon portletConfigurationIcon :
						_getPortletConfigurationIcons()) {

					add(
						dropdownItem -> {
							String url = portletConfigurationIcon.getURL(
								_getPortletRequest(), _getPortletResponse());

							if (portletConfigurationIcon.isUseDialog()) {
								dropdownItem.setData(
									HashMapBuilder.<String, Object>put(
										"action", "openDialog"
									).put(
										"portletId", _getId()
									).put(
										"senna-off", "true"
									).put(
										"title",
										portletConfigurationIcon.getMessage(
											_getPortletRequest())
									).put(
										"url", url
									).build());
							}
							else if (Validator.isNotNull(url)) {
								if (_isForcePush(
										portletConfigurationIcon.getMethod(),
										url)) {

									dropdownItem.setData(
										HashMapBuilder.<String, Object>put(
											"action", "send"
										).put(
											"senna-off", "true"
										).put(
											"url", url
										).build());
								}
								else {
									dropdownItem.setHref(url);
								}
							}
							else {
								dropdownItem.setData(
									portletConfigurationIcon.getContext(
										_getPortletRequest()));
							}

							dropdownItem.setIcon(
								portletConfigurationIcon.getIconCssClass());
							dropdownItem.setLabel(
								portletConfigurationIcon.getMessage(
									_getPortletRequest()));
							dropdownItem.setTarget(
								portletConfigurationIcon.getTarget());
						});
				}
			}
		};
	}

	private String _getId() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
	}

	private List<PortletConfigurationIcon> _getPortletConfigurationIcons() {
		if (_portletConfigurationIcons != null) {
			return _portletConfigurationIcons;
		}

		_portletConfigurationIcons =
			PortletConfigurationIconTracker.getPortletConfigurationIcons(
				_getPortletId(), _getPortletRequest(),
				PortletConfigurationIconComparator.INSTANCE);

		return _portletConfigurationIcons;
	}

	private String _getPortletId() {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getRootPortletId();
	}

	private PortletRequest _getPortletRequest() {
		HttpServletRequest httpServletRequest = getRequest();

		return (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	private PortletResponse _getPortletResponse() {
		HttpServletRequest httpServletRequest = getRequest();

		return (PortletResponse)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE);
	}

	private boolean _isForcePush(String method, String url) {
		if (Validator.isNull(method)) {
			method = "post";

			if (url.contains("p_p_lifecycle=0")) {
				method = "get";
			}
		}

		if (method.equals("post") &&
			(url.startsWith(Http.HTTP_WITH_SLASH) ||
			 url.startsWith(Http.HTTPS_WITH_SLASH))) {

			return true;
		}

		return false;
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _PAGE = "/icon_options/page.jsp";

	private boolean _monospaced;
	private List<PortletConfigurationIcon> _portletConfigurationIcons;

}