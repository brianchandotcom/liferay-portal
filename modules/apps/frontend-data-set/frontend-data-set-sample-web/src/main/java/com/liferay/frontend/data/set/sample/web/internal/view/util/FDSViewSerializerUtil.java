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

package com.liferay.frontend.data.set.sample.web.internal.view.util;

import com.liferay.frontend.data.set.view.FDSViewSerializer;
import com.liferay.osgi.util.service.Snapshot;

/**
 * @author Marko Cikos
 */
public class FDSViewSerializerUtil {

	public static FDSViewSerializer getFDSViewSerializer() {
		return _fdsViewSerializerSnapshot.get();
	}

	private static final Snapshot<FDSViewSerializer>
		_fdsViewSerializerSnapshot = new Snapshot<>(
			FDSViewSerializerUtil.class, FDSViewSerializer.class);

}