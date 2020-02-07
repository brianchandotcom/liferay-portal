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

package com.liferay.sharepoint.soap.connector.schema.query.option;

import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Iván Zaera
 */
public class IncludeMandatoryColumnsQueryOption extends BaseQueryOption {

	public IncludeMandatoryColumnsQueryOption(boolean include) {
		_include = include;
	}

	@Override
	protected String getNodeName() {
		return "IncludeMandatoryColumns";
	}

	@Override
	protected String getNodeText() {
		return StringUtil.toUpperCase(String.valueOf(_include));
	}

	private final boolean _include;

}