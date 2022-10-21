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

package com.liferay.frontend.data.set.internal.view.cards;

import com.liferay.frontend.data.set.constants.FDSConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.FDSViewContextContributor;
import com.liferay.frontend.data.set.view.cards.BaseCardsFDSView;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Bruno Basto
 */
@Component(
	property = "frontend.data.set.view.name=" + FDSConstants.CARDS,
	service = FDSViewContextContributor.class
)
public class CardsFDSViewContextContributor
	implements FDSViewContextContributor {

	@Override
	public Map<String, Object> getFDSViewContext(
		FDSView fdsView, Locale locale) {

		if (fdsView instanceof BaseCardsFDSView) {
			return _serialize((BaseCardsFDSView)fdsView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(BaseCardsFDSView baseCardsFDSView) {
		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", baseCardsFDSView.getDescription()
			).put(
				"href", baseCardsFDSView.getLink()
			).put(
				"image", baseCardsFDSView.getImage()
			).put(
				"sticker", baseCardsFDSView.getSticker()
			).put(
				"symbol", baseCardsFDSView.getSymbol()
			).put(
				"title", baseCardsFDSView.getTitle()
			).build()
		).build();
	}

}