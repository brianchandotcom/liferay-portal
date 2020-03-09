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

import com.liferay.layout.list.retriever.ListObjectReferenceFactory;
import com.liferay.layout.list.retriever.ListObjectReferenceFactoryTracker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = ListObjectReferenceFactoryTracker.class)
public class ListObjectReferenceTrackerImpl
	implements ListObjectReferenceFactoryTracker {

	public ListObjectReferenceFactory getListObjectReference(String type) {
		return _listObjectReferenceFactories.get(type);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setListObjectReferenceFactories(
		ListObjectReferenceFactory listObjectReferenceFactory) {

		_listObjectReferenceFactories.put(
			GenericsUtil.getItemClassName(listObjectReferenceFactory),
			listObjectReferenceFactory);
	}

	protected void unsetListObjectReferenceFactories(
		ListObjectReferenceFactory listObjectReferenceFactory) {

		_listObjectReferenceFactories.remove(
			GenericsUtil.getItemClassName(listObjectReferenceFactory));
	}

	private final Map<String, ListObjectReferenceFactory>
		_listObjectReferenceFactories = new ConcurrentHashMap<>();

}