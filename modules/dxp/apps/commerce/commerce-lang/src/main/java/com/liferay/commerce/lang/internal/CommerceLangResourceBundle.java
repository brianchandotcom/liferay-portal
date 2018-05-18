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

package com.liferay.commerce.lang.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Enumeration;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "language.id=" + StringPool.BLANK, service = ResourceBundle.class
)
public class CommerceLangResourceBundle extends ResourceBundle {

	@Override
	public Enumeration<String> getKeys() {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return resourceBundle.getKeys();
	}

	@Override
	protected Object handleGetObject(String key) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		return resourceBundle.getObject(key);
	}

}