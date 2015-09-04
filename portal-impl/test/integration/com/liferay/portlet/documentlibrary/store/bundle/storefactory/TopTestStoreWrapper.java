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

package com.liferay.portlet.documentlibrary.store.bundle.storefactory;

import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreWrapper;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {"service.ranking:Integer=1", "store.type=test"},
	service = StoreWrapper.class
)
public class TopTestStoreWrapper implements StoreWrapper {

	@Override
	public Store wrap(Store store) {
		return new Delegate(store);
	}

	public static class Delegate extends StoreWrapperDelegate {

		public Delegate(Store store) {
			super(store);
		}

	}

}