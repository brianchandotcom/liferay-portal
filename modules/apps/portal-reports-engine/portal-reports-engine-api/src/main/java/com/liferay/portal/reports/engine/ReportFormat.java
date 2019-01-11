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

package com.liferay.portal.reports.engine;

import java.util.Objects;

/**
 * @author Michael C. Han
 */
public enum ReportFormat {

	CSV("csv"), HTML("html"), PDF("pdf"), RTF("rtf"), TXT("txt"), XLS("xls"),
	XML("xml");

	public static ReportFormat parse(String value) {
		if (Objects.equals(CSV.getValue(), value)) {
			return CSV;
		}
		else if (Objects.equals(HTML.getValue(), value)) {
			return HTML;
		}
		else if (Objects.equals(PDF.getValue(), value)) {
			return PDF;
		}
		else if (Objects.equals(RTF.getValue(), value)) {
			return RTF;
		}
		else if (Objects.equals(TXT.getValue(), value)) {
			return TXT;
		}
		else if (Objects.equals(XLS.getValue(), value)) {
			return XLS;
		}
		else if (Objects.equals(XML.getValue(), value)) {
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