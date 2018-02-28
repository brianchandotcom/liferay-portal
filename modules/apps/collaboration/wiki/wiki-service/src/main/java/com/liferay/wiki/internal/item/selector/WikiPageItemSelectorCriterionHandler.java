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

package com.liferay.wiki.internal.item.selector;

import com.liferay.item.selector.BaseItemSelectorCriterionHandler;
import com.liferay.item.selector.ItemSelectorCriterionHandler;
import com.liferay.wiki.item.selector.criterion.WikiPageItemSelectorCriterion;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Roberto Díaz
 */
@Component(service = ItemSelectorCriterionHandler.class)
public class WikiPageItemSelectorCriterionHandler
	extends BaseItemSelectorCriterionHandler<WikiPageItemSelectorCriterion> {

	@Override
	public Class<WikiPageItemSelectorCriterion>
		getItemSelectorCriterionClass() {

		return WikiPageItemSelectorCriterion.class;
	}

	@Activate
	@Override
	protected void activate(BundleContext bundleContext) {
		super.activate(bundleContext);
	}

	@Deactivate
	@Override
	protected void deactivate() {
		super.deactivate();
	}

}