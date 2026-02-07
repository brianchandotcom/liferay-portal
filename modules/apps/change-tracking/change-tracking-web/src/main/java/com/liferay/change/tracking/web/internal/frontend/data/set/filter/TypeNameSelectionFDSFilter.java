/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.change.tracking.web.internal.constants.PublicationsFDSNames;
import com.liferay.change.tracking.web.internal.display.context.DisplayContextUtil;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(
	property = "frontend.data.set.name=" + PublicationsFDSNames.PUBLICATIONS_CHANGES,
	service = FDSFilter.class
)
public class TypeNameSelectionFDSFilter extends BaseSelectionFDSFilter {

	@Override
	public String getId() {
		return "modelClassNameId";
	}

	@Override
	public String getLabel() {
		return "types";
	}

	@Override
	public Map<String, Object> getPreloadedData() {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		LiferayPortletRequest liferayPortletRequest =
			serviceContext.getLiferayPortletRequest();

		long modelClassNameId = ParamUtil.getLong(
			liferayPortletRequest, "modelClassNameId");

		if (modelClassNameId == 0) {
			return null;
		}

		return HashMapBuilder.<String, Object>put(
			"selectedItems",
			() -> {
				Map<Long, String> typeNamesMap = _getTypeNamesMap(
					liferayPortletRequest);

				return TransformUtil.transform(
					typeNamesMap.entrySet(),
					entry -> {
						if (entry.getKey() != modelClassNameId) {
							return null;
						}

						return new SelectionFDSFilterItem(
							entry.getValue(), String.valueOf(entry.getKey()));
					});
			}
		).build();
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Map<Long, String> typeNamesMap = _getTypeNamesMap(
			serviceContext.getLiferayPortletRequest());

		return TransformUtil.transform(
			typeNamesMap.entrySet(),
			entry -> new SelectionFDSFilterItem(
				entry.getValue(), String.valueOf(entry.getKey())));
	}

	private Map<Long, String> _getTypeNamesMap(
		LiferayPortletRequest liferayPortletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DisplayContextUtil.getTypeNames(
			ParamUtil.getLong(liferayPortletRequest, "ctCollectionId"),
			ParamUtil.getBoolean(liferayPortletRequest, "showHideable"),
			themeDisplay);
	}

}