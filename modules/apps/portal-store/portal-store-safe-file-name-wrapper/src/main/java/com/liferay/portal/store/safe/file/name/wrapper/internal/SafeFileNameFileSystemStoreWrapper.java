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

package com.liferay.portal.store.safe.file.name.wrapper.internal;

import com.liferay.document.library.kernel.store.Store;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Roberto Díaz
 */
@Component(
	immediate = true,
	property = {
		"service.ranking:Integer=" + SafeFileNameStore.SERVICE_RANKING,
		"store.type=com.liferay.portal.store.file.system.FileSystemStore"
	},
	service = Store.class
)
public class SafeFileNameFileSystemStoreWrapper extends SafeFileNameStore {

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(&(service.ranking<=" + (SafeFileNameStore.SERVICE_RANKING - 1) + ")(store.type=com.liferay.portal.store.file.system.FileSystemStore)(!(current.store=*)))"
	)
	protected void setStore(Store store) {
		this.store = store;
	}

	protected void unsetStore(Store store) {
		if (this.store == store) {
			this.store = null;
		}
	}

}