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

package com.liferay.portal.kernel.bi.reporting;

/**
 * @author Michael C. Han
 */
public enum ReportFormat {

	CSV("csv"), HTML("html"), PDF("pdf"), RTF("rtf"), TXT("txt"), XLS("xls"),
	XML("xml");

	public static ReportFormat parse(String value) {
		if (CSV.getValue().equals(value)) {
			return CSV;
		}
		else if (HTML.getValue().equals(value)) {
			return HTML;
		}
		else if (PDF.getValue().equals(value)) {
			return PDF;
		}
		else if (RTF.getValue().equals(value)) {
			return RTF;
		}
		else if (TXT.getValue().equals(value)) {
			return TXT;
		}
		else if (XLS.getValue().equals(value)) {
			return XLS;
		}
		else if (XML.getValue().equals(value)) {
			return XML;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ReportFormat(String value) {
		_value = value;
	}

	private final String _value;

}