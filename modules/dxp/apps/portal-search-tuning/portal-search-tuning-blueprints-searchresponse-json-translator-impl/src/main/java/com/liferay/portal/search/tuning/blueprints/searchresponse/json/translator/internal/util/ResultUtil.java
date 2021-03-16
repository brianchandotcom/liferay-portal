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

package com.liferay.portal.search.tuning.blueprints.searchresponse.json.translator.internal.util;

import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Petteri Karttunen
 */
public class ResultUtil {

	public static String stripHTML(String string, int length) throws Exception {
		if (Validator.isBlank(string)) {
			return string;
		}

		// Replace other than highlight tags

		string = string.replaceAll("<liferay-hl>", "---LR-HL-START---");
		string = string.replaceAll("</liferay-hl>", "---LR-HL-STOP---");
		string = HtmlUtil.stripHtml(string);
		string = string.replaceAll("---LR-HL-START---", "<liferay-hl>");
		string = string.replaceAll("---LR-HL-STOP---", "</liferay-hl>");

		if ((length > -1) && (string.length() > length)) {
			String temp = string.substring(0, length);

			// Check that we are not breaking the HTML

			if (temp.lastIndexOf("<") > temp.lastIndexOf(">")) {
				temp = string.substring(
					0, 1 + string.indexOf('>', temp.lastIndexOf('<')));
			}

			string = temp.concat("...");
		}

		return string;
	}

}