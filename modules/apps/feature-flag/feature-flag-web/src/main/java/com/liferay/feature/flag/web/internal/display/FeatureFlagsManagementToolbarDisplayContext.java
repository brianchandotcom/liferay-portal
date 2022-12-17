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

package com.liferay.feature.flag.web.internal.display;

import com.liferay.feature.flag.web.internal.model.FeatureFlag;
import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

import javax.portlet.MutableRenderParameters;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class FeatureFlagsManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public static final Filter[] FILTERS = {
		new Filter(
			"enabled", new String[] {"enabled", "disabled"},
			(featureFlag, currentValue) -> {
				if ((currentValue == null) ||
					Objects.equals(currentValue, "all")) {

					return true;
				}

				if (Objects.equals(currentValue, "disabled") &&
					!featureFlag.isEnabled()) {

					return true;
				}
				else if (Objects.equals(currentValue, "enabled") &&
						 featureFlag.isEnabled()) {

					return true;
				}

				return false;
			})
	};

	public FeatureFlagsManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		SearchContainer<?> searchContainer) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			searchContainer);
	}

	@Override
	public String getClearResultsURL() {
		PortletURL portletURL = getPortletURL();

		MutableRenderParameters renderParameters =
			portletURL.getRenderParameters();

		renderParameters.removeParameter("keywords");

		for (Filter filter : FILTERS) {
			renderParameters.removeParameter(filter.getParameterName());
		}

		return portletURL.toString();
	}

	@Override
	public List<LabelItem> getFilterLabelItems() {
		LabelItemList labelItemList = new LabelItemList();

		for (Filter filter : FILTERS) {
			String currentValue = filter.getCurrentValue(httpServletRequest);

			if (Objects.equals("all", currentValue)) {
				continue;
			}

			labelItemList.add(
				labelItem -> {
					labelItem.putData(
						"removeLabelURL",
						PortletURLBuilder.create(
							getPortletURL()
						).setParameter(
							filter.getParameterName(), (String)null
						).buildString());
					labelItem.setDismissible(true);
					labelItem.setLabel(
						String.format(
							"%s: %s", langGet(filter.getName()),
							langGet(currentValue)));
				});
		}

		return labelItemList;
	}

	@Override
	public List<DropdownItem> getFilterNavigationDropdownItems() {
		DropdownItemList dropdownItemList = new DropdownItemList();

		for (Filter filter : FILTERS) {
			dropdownItemList.addGroup(
				dropdownGroupItem -> dropdownGroupItem.setLabel(
					langGet("filter-by-x", filter.getName())));

			String currentValue = filter.getCurrentValue(httpServletRequest);

			PortletURL portletURL = getPortletURL();

			for (String value : filter.getValues()) {
				dropdownItemList.add(
					dropdownItem -> {
						dropdownItem.setActive(
							Objects.equals(currentValue, value));
						dropdownItem.setHref(
							portletURL, filter.getParameterName(), value);
						dropdownItem.setLabel(langGet(value));
					});
			}
		}

		return dropdownItemList;
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	public static class Filter {

		public Filter(
			String name, String[] values,
			BiPredicate<FeatureFlag, String> biPredicate) {

			_name = name;
			_values = values;
			_biPredicate = biPredicate;
		}

		public String getCurrentValue(HttpServletRequest httpServletRequest) {
			return ParamUtil.get(httpServletRequest, getParameterName(), "all");
		}

		public String getName() {
			return _name;
		}

		public String getParameterName() {
			return "filter-" + _name;
		}

		public Predicate<FeatureFlag> getPredicate(
			HttpServletRequest httpServletRequest) {

			String currentValue = getCurrentValue(httpServletRequest);

			return featureFlag -> {
				if (Objects.equals("all", currentValue) ||
					_biPredicate.test(featureFlag, currentValue)) {

					return true;
				}

				return false;
			};
		}

		public String[] getValues() {
			return _values;
		}

		private final BiPredicate<FeatureFlag, String> _biPredicate;
		private final String _name;
		private final String[] _values;

	}

	@Override
	protected String getDefaultDisplayStyle() {
		return "descriptive";
	}

	@Override
	protected String getFilterNavigationDropdownItemsLabel() {
		return null;
	}

	protected String langGet(String key, String... args) {
		if (ArrayUtil.isEmpty(args)) {
			return LanguageUtil.get(httpServletRequest, key);
		}

		return LanguageUtil.format(httpServletRequest, key, args);
	}

}