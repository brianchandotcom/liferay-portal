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

package com.liferay.item.selector.editor.configuration;

import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.URLItemSelectorReturnType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.RequestBackedPortletURLFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Sergio González
 */
public abstract class BaseEditorConfigContributor
	extends
	com.liferay.portal.kernel.editor.configuration.BaseEditorConfigContributor {

	public PortletURL getItemSelectorPortletURL(
		Map<String, Object> inputEditorTaglibAttributes,
		RequestBackedPortletURLFactory requestBackedPortletURLFactory,
		ItemSelectorCriterion... itemSelectorCriteria) {

		List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
			new ArrayList<>();

		desiredItemSelectorReturnTypes.add(new URLItemSelectorReturnType());

		for (ItemSelectorCriterion itemSelectorCriterion :
				itemSelectorCriteria) {

			itemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				desiredItemSelectorReturnTypes);
		}

		String name = GetterUtil.getString(
			inputEditorTaglibAttributes.get("liferay-ui:input-editor:name"));

		boolean inlineEdit = GetterUtil.getBoolean(
			inputEditorTaglibAttributes.get(
				"liferay-ui:input-editor:inlineEdit"));

		if (!inlineEdit) {
			String namespace = GetterUtil.getString(
				inputEditorTaglibAttributes.get(
					"liferay-ui:input-editor:namespace"));

			name = namespace + name;
		}

		ItemSelector itemSelector = getItemSelector();

		return itemSelector.getItemSelectorURL(
			requestBackedPortletURLFactory, name + "selectItem",
			itemSelectorCriteria);
	}

	protected abstract ItemSelector getItemSelector();

}