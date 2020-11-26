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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.NavigationCard;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class FragmentCollectionNavigationCard implements NavigationCard {

	public FragmentCollectionNavigationCard(BaseModel<?> baseModel) {
		_fragmentCollection = (FragmentCollection)baseModel;
	}

	@Override
	public String getCssClass() {
		return "card-interactive card-interactive-primary selector-button";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-id",
			String.valueOf(_fragmentCollection.getFragmentCollectionId())
		).put(
			"data-name", _fragmentCollection.getName()
		).build();
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getTitle() {
		return HtmlUtil.escape(_fragmentCollection.getName());
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	@Override
	public Boolean isSmall() {
		return true;
	}

	private final FragmentCollection _fragmentCollection;

}