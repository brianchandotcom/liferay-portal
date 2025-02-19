/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.sample.web.internal.serializer;

import com.liferay.frontend.data.set.serializer.FDSSerializer;
import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Marko Cikos
 */
public class FDSSerializerUtil {

	public static FDSSerializer getFDSSerializer() {
		return _fdsSerializerSnapshot.get();
	}

	private static final Snapshot<FDSSerializer> _fdsSerializerSnapshot =
		new Snapshot<>(
			FDSSerializerUtil.class, FDSSerializer.class,
			"(frontend.data.set.serializer.type=" + FDSSerializer.TYPE_SYSTEM +
				")");

}