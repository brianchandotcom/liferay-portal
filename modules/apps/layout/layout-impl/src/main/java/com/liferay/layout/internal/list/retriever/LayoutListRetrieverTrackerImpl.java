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

package com.liferay.layout.internal.list.retriever;

import com.liferay.layout.list.retriever.LayoutListRetriever;
import com.liferay.layout.list.retriever.LayoutListRetrieverTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = LayoutListRetrieverTracker.class)
public class LayoutListRetrieverTrackerImpl
	implements LayoutListRetrieverTracker {

	public LayoutListRetriever getLayoutListRetriever(String type) {
		return _layoutListRetrievers.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setLayoutListRetrievers(
		LayoutListRetriever layoutListRetriever) {

		_layoutListRetrievers.put(
			GenericsUtil.getItemClassName(layoutListRetriever),
			layoutListRetriever);
	}

	protected void unsetLayoutListRetrievers(
		LayoutListRetriever layoutListRetriever) {

		_layoutListRetrievers.remove(
			GenericsUtil.getItemClassName(layoutListRetriever));
	}

	private final Map<String, LayoutListRetriever> _layoutListRetrievers =
		new ConcurrentHashMap<>();

}