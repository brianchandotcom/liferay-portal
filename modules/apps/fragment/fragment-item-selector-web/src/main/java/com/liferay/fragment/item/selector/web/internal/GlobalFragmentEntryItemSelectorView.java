/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.item.selector.web.internal;

import com.liferay.fragment.item.selector.FragmentEntryItemSelectorReturnType;
import com.liferay.fragment.item.selector.criterion.FragmentEntryItemSelectorCriterion;
import com.liferay.fragment.item.selector.web.internal.display.context.FragmentEntriesDisplayContext;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "item.selector.view.order:Integer=200",
	service = ItemSelectorView.class
)
public class GlobalFragmentEntryItemSelectorView
	implements ItemSelectorView<FragmentEntryItemSelectorCriterion> {

	@Override
	public Class<? extends FragmentEntryItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return FragmentEntryItemSelectorCriterion.class;
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	@Override
	public String getTitle(Locale locale) {
		return _language.get(locale, "global");
	}

	@Override
	public boolean isVisible(
		FragmentEntryItemSelectorCriterion itemSelectorCriterion,
		ThemeDisplay themeDisplay) {

		if (Objects.equals(
				themeDisplay.getScopeGroupId(),
				themeDisplay.getCompanyGroupId())) {

			return false;
		}

		return true;
	}

	@Override
	public void renderHTML(
			ServletRequest servletRequest, ServletResponse servletResponse,
			FragmentEntryItemSelectorCriterion
				fragmentEntryItemSelectorCriterion,
			PortletURL portletURL, String itemSelectedEventName, boolean search)
		throws IOException, ServletException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/select_fragment_collection.jsp");

		ThemeDisplay themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		servletRequest.setAttribute(
			FragmentEntriesDisplayContext.class.getName(),
			new FragmentEntriesDisplayContext(
				(HttpServletRequest)servletRequest,
				fragmentEntryItemSelectorCriterion,
				_groupLocalService.fetchGroup(themeDisplay.getCompanyGroupId()),
				_portal.getLiferayPortletRequest(
					(PortletRequest)servletRequest.getAttribute(
						JavaConstants.JAVAX_PORTLET_REQUEST)),
				portletURL));

		requestDispatcher.include(servletRequest, servletResponse);
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new FragmentEntryItemSelectorReturnType());

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.fragment.item.selector.web)"
	)
	private ServletContext _servletContext;

}