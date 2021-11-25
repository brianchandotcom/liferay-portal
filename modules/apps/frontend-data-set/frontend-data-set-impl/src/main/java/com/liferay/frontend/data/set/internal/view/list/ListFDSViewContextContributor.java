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

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.list.BaseListFDSView;
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
	property = "frontend.data.set.view.name=" + FDSConstants.LIST,
	service = FDSViewContextContributor.class
)
public class ListFDSViewContextContributor
	implements FDSViewContextContributor {

	@Override
	public Map<String, Object> getFDSViewContext(
		FDSView fdsView, Locale locale) {

		if (fdsView instanceof BaseListFDSView) {
			return _serialize((BaseListFDSView)fdsView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(BaseListFDSView baseListFDSView) {
		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", baseListFDSView.getDescription()
			).put(
				"image", baseListFDSView.getImage()
			).put(
				"sticker", baseListFDSView.getSticker()
			).put(
				"symbol", baseListFDSView.getSymbol()
			).put(
				"title",
				() -> {
					String title = baseListFDSView.getTitle();

					if (title.contains(StringPool.PERIOD)) {
						return StringUtil.split(title, StringPool.PERIOD);
					}

					return title;
				}
			).build()
		).build();
	}

}