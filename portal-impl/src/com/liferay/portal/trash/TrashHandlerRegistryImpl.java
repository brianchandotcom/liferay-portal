/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.trash;

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistry;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Alexander Chow
 */
@DoPrivileged
public class TrashHandlerRegistryImpl implements TrashHandlerRegistry {

	@Override
	public TrashHandler getTrashHandler(String className) {
		return _trashHandlers.get(className);
	}

	@Override
	public List<TrashHandler> getTrashHandlers() {
		return ListUtil.fromMapValues(_trashHandlers);
	}

	@Override
	public void register(TrashHandler trashHandler) {
		_trashHandlers.put(trashHandler.getClassName(), trashHandler);
	}

	@Override
	public void unregister(TrashHandler trashHandler) {
		_trashHandlers.remove(trashHandler.getClassName());
	}

	private Map<String, TrashHandler> _trashHandlers =
		new TreeMap<String, TrashHandler>();

}