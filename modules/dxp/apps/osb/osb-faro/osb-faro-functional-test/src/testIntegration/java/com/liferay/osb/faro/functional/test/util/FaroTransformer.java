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

package com.liferay.osb.faro.functional.test.util;

import com.liferay.portal.kernel.util.StringUtil;

import cucumber.api.Transformer;

/**
 * @author Shinn Lok
 */
public class FaroTransformer extends Transformer<String> {

	@Override
	public String transform(String value) {
		return FaroTestDataUtil.parsePlaceholders(
			StringUtil.unquote(value.trim()));
	}

}