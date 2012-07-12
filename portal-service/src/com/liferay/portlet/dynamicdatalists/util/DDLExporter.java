/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Locale;

/**
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 */
public interface DDLExporter {

	public byte[] export(long recordSetId) throws Exception;

	public byte[] export(long recordSetId, int status) throws Exception;

	public byte[] export(long recordSetId, int status, int start, int end)
		throws Exception;

	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator orderByComparator)
		throws Exception;

	public Locale getLocale();

	public void setLocale(Locale locale);

}