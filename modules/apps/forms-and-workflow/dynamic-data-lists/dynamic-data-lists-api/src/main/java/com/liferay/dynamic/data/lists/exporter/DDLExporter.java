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

package com.liferay.dynamic.data.lists.exporter;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Locale;

/**
 * Provides a service for exporting the records of a given Record Set. The
 * format used to export the Record Set is defined by the format value passed to
 * DDLExporterFactory when instantiating this service.
 *
 * @author Marcellus Tavares
 * @author Manuel de la Peña
 * @see    DDLExporterFactory
 */
public interface DDLExporter {

	/**
	 * Exports a range of the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records
	 * @param  start the lower bound of the range of records to export
	 * @param  end the upper bound of the range of records to export (not
	 *         inclusive)
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @return the byte values of the exported Records.
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(
			long recordSetId, int status, int start, int end, Locale locale)
		throws Exception;

	/**
	 * Exports an ordered range of the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records
	 * @param  start the lower bound of the range of records to export
	 * @param  end the upper bound of the range of records to export (not
	 *         inclusive)
	 * @param  orderByComparator the comparator to order the records nt s*
	 *         (optionally <code>null</code>)
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @return the byte values of the exported Records.
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(
			long recordSetId, int status, int start, int end,
			OrderByComparator<DDLRecord> orderByComparator, Locale locale)
		throws Exception;

	/**
	 * Exports the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  status the workflow status of the records
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @return the byte values of the exported Records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(long recordSetId, int status, Locale locale)
		throws Exception;

	/**
	 * Exports the record set's records as a byte array.
	 *
	 * @param  recordSetId the record set ID
	 * @param  locale the locale used to retrieve the localized values of the
	 *         record
	 * @return the byte values of the exported Records
	 * @throws Exception if an unexpected exception occurred
	 */
	public byte[] export(long recordSetId, Locale locale) throws Exception;

	/**
	 * Returns the export format of the current DDL Exporter service instance.
	 *
	 * @return the format value of the current service instance
	 */
	public String getFormat();

}