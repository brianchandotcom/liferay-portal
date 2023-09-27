/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.frontend.taglib.clay.servlet.taglib;

import com.liferay.fragment.model.FragmentCollection;
import com.liferay.frontend.taglib.clay.servlet.taglib.NavigationCard;
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
		return "selector-button";
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