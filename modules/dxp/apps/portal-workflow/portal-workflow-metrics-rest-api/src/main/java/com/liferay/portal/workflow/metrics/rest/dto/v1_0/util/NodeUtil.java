/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.dto.v1_0.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;

import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author Inácio Nery
 */
public class NodeUtil {

	public static Node toNode(
		Document document, Language language, ResourceBundle resourceBundle) {

		return new Node() {
			{
				setDateCreated(
					() -> _parseDate(document.getDate("createDate")));
				setDateModified(
					() -> _parseDate(document.getDate("modifiedDate")));
				setId(() -> document.getLong("nodeId"));
				setInitial(
					() -> GetterUtil.getBoolean(document.getValue("initial")));
				setLabel(
					() -> language.get(
						resourceBundle, document.getString("name")));
				setName(() -> document.getString("name"));
				setTerminal(
					() -> GetterUtil.getBoolean(document.getValue("terminal")));
				setType(() -> document.getString("type"));
			}
		};
	}

	private static Date _parseDate(String dateString) {
		try {
			return DateUtil.parseDate(
				"yyyyMMddHHmmss", dateString, LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(NodeUtil.class);

}