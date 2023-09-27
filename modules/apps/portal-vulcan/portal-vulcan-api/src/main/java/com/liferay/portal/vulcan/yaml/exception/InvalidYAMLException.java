/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.yaml.exception;

import com.liferay.petra.string.StringBundler;

import org.yaml.snakeyaml.error.Mark;
import org.yaml.snakeyaml.error.MarkedYAMLException;

/**
 * @author     Javier de Arcos
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class InvalidYAMLException extends IllegalArgumentException {

	public InvalidYAMLException(Exception exception) {
		super(_getProblem(exception), exception);
	}

	private static String _getProblem(Throwable throwable) {
		if (throwable.getCause() instanceof MarkedYAMLException) {
			return _getProblem(throwable.getCause());
		}

		StringBundler sb = new StringBundler(4);

		sb.append("Invalid YAML");

		MarkedYAMLException markedYAMLException =
			(MarkedYAMLException)throwable;

		Mark mark = markedYAMLException.getProblemMark();

		if (mark != null) {
			sb.append(mark.toString());
		}

		sb.append(": ");
		sb.append(markedYAMLException.getProblem());

		return sb.toString();
	}

}