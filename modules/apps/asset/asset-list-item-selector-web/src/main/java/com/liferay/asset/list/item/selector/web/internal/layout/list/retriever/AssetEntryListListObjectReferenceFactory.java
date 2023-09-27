/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.item.selector.web.internal.layout.list.retriever;

import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReference;
import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(service = ListObjectReferenceFactory.class)
public class AssetEntryListListObjectReferenceFactory
	implements ListObjectReferenceFactory<InfoListItemSelectorReturnType> {

	@Override
	public ListObjectReference getListObjectReference(JSONObject jsonObject) {
		return new ClassedModelListObjectReference(jsonObject);
	}

}