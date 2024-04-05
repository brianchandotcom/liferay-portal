/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.csv.ColumnDescriptorProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Matija Petanjek
 */
public class CSVBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public CSVBatchEngineExportTaskItemWriterImpl(
			ColumnDescriptorProvider columnDescriptorProvider, long companyId,
			String delimiter,
			Map<String, ObjectValuePair<Field, Method>>
				fieldNameObjectValuePairs,
			List<String> fieldNames, OutputStream outputStream,
			Map<String, Serializable> parameters, String taskItemDelegateName)
		throws IOException, PortalException {

		if (fieldNames.isEmpty()) {
			throw new IllegalArgumentException("Field names are not set");
		}

		_csvPrinter = new CSVPrinter(
			new BufferedWriter(new OutputStreamWriter(outputStream)),
			_getCSVFormat(delimiter));

		fieldNames = ListUtil.sort(
			fieldNames, (value1, value2) -> value1.compareToIgnoreCase(value2));

		_columnValuesExtractor = new ColumnValuesExtractor(
			columnDescriptorProvider, companyId, fieldNameObjectValuePairs,
			fieldNames, taskItemDelegateName);

		if (Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE))) {

			_csvPrinter.printRecord(_columnValuesExtractor.getHeaders());
		}
	}

	@Override
	public void close() throws IOException {
		_csvPrinter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		for (Object item : items) {
			for (Object[] values : _columnValuesExtractor.extractValues(item)) {
				_csvPrinter.printRecord(values);
			}
		}
	}

	private CSVFormat _getCSVFormat(String delimiter) {
		CSVFormat.Builder builder = CSVFormat.Builder.create();

		builder.setDelimiter(delimiter);

		return builder.build();
	}

	private final ColumnValuesExtractor _columnValuesExtractor;
	private final CSVPrinter _csvPrinter;

}