/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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