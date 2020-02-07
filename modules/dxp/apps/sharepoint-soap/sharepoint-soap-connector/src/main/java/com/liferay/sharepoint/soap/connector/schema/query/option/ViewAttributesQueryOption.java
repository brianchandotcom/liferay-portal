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

import com.liferay.portal.kernel.xml.simple.Element;

/**
 * @author Iván Zaera
 */
public class ViewAttributesQueryOption extends BaseQueryOption {

	public ViewAttributesQueryOption(boolean recursive) {
		_recursive = recursive;
	}

	@Override
	protected String getNodeName() {
		return "ViewAttributes";
	}

	@Override
	protected void populate(Element element) {
		super.populate(element);

		if (_recursive) {
			element.addAttribute("Scope", "RecursiveAll");
		}
	}

	private final boolean _recursive;

}