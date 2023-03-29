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

package com.liferay.osb.faro.web.internal.card.template.type;

import java.util.Collections;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public abstract class BaseContactsCardTemplateType
	implements ContactsCardTemplateType {

	@Override
	public Map<String, Object> getDefaultSettings() {
		return Collections.emptyMap();
	}

}