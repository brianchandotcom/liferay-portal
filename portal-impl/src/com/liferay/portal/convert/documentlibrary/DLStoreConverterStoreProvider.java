/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.convert.documentlibrary;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

/**
 * @author Roberto Díaz
 */
public class DLStoreConverterStoreProvider {

	public DLStoreConverterStoreProvider(String targetStoreClassName) {
		StoreFactory storeFactory = StoreFactory.getInstance();

		_sourceStore = storeFactory.getStore();
		_targetStore = storeFactory.getStore(targetStoreClassName);
	}

	private DLStoreConverterStoreProvider() {
	}

	public Store getSourceStore() {
		return _sourceStore;
	}

	public Store getTargetStore() {
		return _targetStore;
	}

	private Store _sourceStore;
	private Store _targetStore;

}