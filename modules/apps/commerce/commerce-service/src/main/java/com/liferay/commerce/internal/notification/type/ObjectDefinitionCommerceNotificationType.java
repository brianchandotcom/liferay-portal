/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.notification.type;

import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionCommerceNotificationType
	implements CommerceNotificationType {

	public ObjectDefinitionCommerceNotificationType(
		String action, String key, String label) {

		_action = action;
		_key = key;
		_label = label;
	}

	@Override
	public String getClassName(Object object) {
		if (!(object instanceof ObjectEntry)) {
			return null;
		}

		return ObjectEntry.class.getName();
	}

	@Override
	public long getClassPK(Object object) {
		if (!(object instanceof ObjectEntry)) {
			return 0;
		}

		ObjectEntry objectEntry = (ObjectEntry)object;

		return objectEntry.getObjectEntryId();
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public String getLabel(Locale locale) {
		return _label + " " + LanguageUtil.get(locale, _action);
	}

	private final String _action;
	private final String _key;
	private final String _label;

}