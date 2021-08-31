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

package com.liferay.template.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemFormVariation;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFormVariationsProvider;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.template.constants.TemplatePortletKeys;
import com.liferay.template.info.item.capability.TemplateInfoItemCapability;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class InformationTemplatesManagementToolbarDisplayContext
	extends BaseTemplateManagementToolbarDisplayContext {

	public InformationTemplatesManagementToolbarDisplayContext(
		HttpServletRequest httpServletRequest,
		InformationTemplatesTemplateDisplayContext
			informationTemplatesTemplateDisplayContext,
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse) {

		super(
			httpServletRequest, liferayPortletRequest, liferayPortletResponse,
			informationTemplatesTemplateDisplayContext.
				getTemplateSearchContainer());

		_informationTemplatesTemplateDisplayContext =
			informationTemplatesTemplateDisplayContext;

		_infoItemServiceTracker =
			(InfoItemServiceTracker)liferayPortletRequest.getAttribute(
				InfoItemServiceTracker.class.getName());
	}

	public Map<String, Object> getAdditionalProps() {
		return HashMapBuilder.<String, Object>put(
			"addDDMTemplateURL",
			PortletURLBuilder.createActionURL(
				liferayPortletResponse
			).setActionName(
				"/template/add_ddm_template"
			).setRedirect(
				themeDisplay.getURLCurrent()
			).setParameter(
				"resourceClassNameId",
				_informationTemplatesTemplateDisplayContext.
					getResourceClassNameId()
			).buildString()
		).put(
			"itemTypes", _getItemTypesJSONArray()
		).build();
	}

	@Override
	public CreationMenu getCreationMenu() {
		if (!containsAddPortletDisplayTemplatePermission(
				TemplatePortletKeys.TEMPLATE, DDMActionKeys.ADD_TEMPLATE)) {

			return null;
		}

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.putData("action", "addInformationTemplate");
				dropdownItem.setLabel(
					LanguageUtil.get(themeDisplay.getLocale(), "add"));
			}
		).build();
	}

	private JSONArray _getItemTypesJSONArray() {
		JSONArray itemTypesJSONArray = JSONFactoryUtil.createJSONArray();

		if (!containsAddPortletDisplayTemplatePermission(
				TemplatePortletKeys.TEMPLATE, DDMActionKeys.ADD_TEMPLATE)) {

			return itemTypesJSONArray;
		}

		for (InfoItemClassDetails infoItemClassDetails :
				_infoItemServiceTracker.getInfoItemClassDetails(
					TemplateInfoItemCapability.KEY)) {

			JSONArray itemSubtypesJSONArray = JSONFactoryUtil.createJSONArray();

			InfoItemFormVariationsProvider<?> infoItemFormVariationsProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormVariationsProvider.class,
					infoItemClassDetails.getClassName());

			if (infoItemFormVariationsProvider != null) {
				Collection<InfoItemFormVariation> infoItemFormVariations =
					infoItemFormVariationsProvider.getInfoItemFormVariations(
						themeDisplay.getScopeGroupId());

				infoItemFormVariations = ListUtil.sort(
					new ArrayList<>(infoItemFormVariations),
					Comparator.comparing(
						infoItemFormVariation -> infoItemFormVariation.getLabel(
							themeDisplay.getLocale())));

				for (InfoItemFormVariation infoItemFormVariation :
						infoItemFormVariations) {

					itemSubtypesJSONArray.put(
						JSONUtil.put(
							"label",
							infoItemFormVariation.getLabel(
								themeDisplay.getLocale())
						).put(
							"value", infoItemFormVariation.getKey()
						));
				}
			}

			itemTypesJSONArray.put(
				JSONUtil.put(
					"label",
					infoItemClassDetails.getLabel(themeDisplay.getLocale())
				).put(
					"subtypes", itemSubtypesJSONArray
				).put(
					"value",
					String.valueOf(
						PortalUtil.getClassNameId(
							infoItemClassDetails.getClassName()))
				));
		}

		return itemTypesJSONArray;
	}

	private final InfoItemServiceTracker _infoItemServiceTracker;
	private final InformationTemplatesTemplateDisplayContext
		_informationTemplatesTemplateDisplayContext;

}