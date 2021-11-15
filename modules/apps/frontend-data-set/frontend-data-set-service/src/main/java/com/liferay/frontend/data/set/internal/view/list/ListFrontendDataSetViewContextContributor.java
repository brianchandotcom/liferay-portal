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

package com.liferay.frontend.data.set.internal.view.list;

import com.liferay.frontend.data.set.constants.FrontendDataSetConstants;
import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributor;
import com.liferay.frontend.data.set.view.list.BaseListFrontendDataSetView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "frontend.data.set.view.name=" + FrontendDataSetConstants.LIST,
	service = FrontendDataSetViewContextContributor.class
)
public class ListFrontendDataSetViewContextContributor
	implements FrontendDataSetViewContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetViewContext(
		FrontendDataSetView frontendDataSetView, Locale locale) {

		if (frontendDataSetView instanceof BaseListFrontendDataSetView) {
			return _serialize((BaseListFrontendDataSetView)frontendDataSetView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseListFrontendDataSetView baseListFrontendDataSetView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", baseListFrontendDataSetView.getDescription()
			).put(
				"image", baseListFrontendDataSetView.getImage()
			).put(
				"sticker", baseListFrontendDataSetView.getSticker()
			).put(
				"symbol", baseListFrontendDataSetView.getSymbol()
			).put(
				"title",
				() -> {
					String title = baseListFrontendDataSetView.getTitle();

					if (title.contains(StringPool.PERIOD)) {
						return StringUtil.split(title, StringPool.PERIOD);
					}

					return title;
				}
			).build()
		).build();
	}

}