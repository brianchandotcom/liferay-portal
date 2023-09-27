/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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