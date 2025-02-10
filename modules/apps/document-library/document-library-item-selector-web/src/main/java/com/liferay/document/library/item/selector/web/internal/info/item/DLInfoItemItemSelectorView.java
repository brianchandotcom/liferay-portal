/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.item.selector.web.internal.info.item;

import com.liferay.document.library.item.selector.web.internal.BaseDLItemSelectorView;
import com.liferay.document.library.item.selector.web.internal.constants.DLItemSelectorViewConstants;
import com.liferay.info.item.selector.InfoItemSelectorView;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.ItemSelectorView;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.portal.kernel.repository.model.FileEntry;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"item.selector.view.key=" + DLItemSelectorViewConstants.DL_FILE_ITEM_SELECTOR_VIEW_KEY,
		"item.selector.view.order:Integer=100"
	},
	service = ItemSelectorView.class
)
public class DLInfoItemItemSelectorView
	extends BaseDLItemSelectorView<InfoItemItemSelectorCriterion>
	implements InfoItemSelectorView {

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	@Override
	public Class<InfoItemItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return InfoItemItemSelectorCriterion.class;
	}

	@Override
	public String[] getMimeTypes() {
		return new String[0];
	}

	@Override
	public List<ItemSelectorReturnType> getSupportedItemSelectorReturnTypes() {
		return _supportedItemSelectorReturnTypes;
	}

	private static final List<ItemSelectorReturnType>
		_supportedItemSelectorReturnTypes = Collections.singletonList(
			new InfoItemItemSelectorReturnType());

}