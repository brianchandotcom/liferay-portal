/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.metadata;

import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.io.InputStream;

import java.util.Map;
import java.util.Set;

/**
 * @author Miguel Pastor
 */
public class RawMetadataProcessorUtil {

	public static Set<String> getFieldNames() {
		RawMetadataProcessor rawMetadataProcessor =
			_rawMetadataProcessorSnapshot.get();

		return rawMetadataProcessor.getFieldNames();
	}

	public static Map<String, DDMFormValues> getRawMetadataMap(
			String mimeType, InputStream inputStream)
		throws PortalException {

		RawMetadataProcessor rawMetadataProcessor =
			_rawMetadataProcessorSnapshot.get();

		return rawMetadataProcessor.getRawMetadataMap(mimeType, inputStream);
	}

	public static RawMetadataProcessor getRawMetadataProcessor() {
		return _rawMetadataProcessorSnapshot.get();
	}

	private static final Snapshot<RawMetadataProcessor>
		_rawMetadataProcessorSnapshot = new Snapshot<>(
			RawMetadataProcessorUtil.class, RawMetadataProcessor.class);

}