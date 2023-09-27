/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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