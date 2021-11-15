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

import com.liferay.frontend.data.set.constants.FrontendDataSetConstants;
import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.FrontendDataSetViewContextContributor;
import com.liferay.frontend.data.set.view.cards.BaseCardsFrontendDataSetView;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	property = "frontend.data.set.view.name=" + FrontendDataSetConstants.CARDS,
	service = FrontendDataSetViewContextContributor.class
)
public class CardsFrontendDataSetViewContextContributor
	implements FrontendDataSetViewContextContributor {

	@Override
	public Map<String, Object> getFrontendDataSetViewContext(
		FrontendDataSetView frontendDataSetView, Locale locale) {

		if (frontendDataSetView instanceof BaseCardsFrontendDataSetView) {
			return _serialize(
				(BaseCardsFrontendDataSetView)frontendDataSetView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseCardsFrontendDataSetView baseCardsFrontendDataSetView) {

		return HashMapBuilder.<String, Object>put(
			"schema",
			HashMapBuilder.<String, Object>put(
				"description", baseCardsFrontendDataSetView.getDescription()
			).put(
				"href", baseCardsFrontendDataSetView.getLink()
			).put(
				"image", baseCardsFrontendDataSetView.getImage()
			).put(
				"sticker", baseCardsFrontendDataSetView.getSticker()
			).put(
				"symbol", baseCardsFrontendDataSetView.getSymbol()
			).put(
				"title", baseCardsFrontendDataSetView.getTitle()
			).build()
		).build();
	}

	@Reference
	private JSONFactory _jsonFactory;

}