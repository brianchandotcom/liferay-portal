/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.rest.dto.v1_0.util;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Node;

import java.util.ResourceBundle;

/**
 * @author Inácio Nery
 */
public class NodeUtil {

	public static Node toNode(
		Document document, Language language, ResourceBundle resourceBundle) {

		return new Node() {
			{
				id = document.getLong("nodeId");
				initial = GetterUtil.getBoolean(document.getValue("initial"));
				name = language.get(resourceBundle, document.getString("name"));
				terminal = GetterUtil.getBoolean(document.getValue("terminal"));
				type = document.getString("type");
			}
		};
	}

}