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

package com.liferay.dynamic.data.mapping.io.exporter;

import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Locale;

/**
 * @author     Marcellus Tavares
 * @author     Manuel de la Peña
 * @deprecated As of Judson (7.1.x), replaced by {@link
 *             DDMFormInstanceRecordExporter}
 */
@Deprecated
public interface DDMFormExporter {

	public byte[] export(long formInstanceId) throws Exception;

	public byte[] export(long formInstanceId, int status) throws Exception;

	public byte[] export(long formInstanceId, int status, int start, int end)
		throws Exception;

	public byte[] export(
			long formInstanceId, int status, int start, int end,
			OrderByComparator<DDMFormInstanceRecord> orderByComparator)
		throws Exception;

	public String getFormat();

	public default String getLabel() {
		return StringUtil.toUpperCase(getFormat());
	}

	public Locale getLocale();

	public void setLocale(Locale locale);

}