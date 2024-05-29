/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.CustomEvent;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;

/**
 * @author Marcos Martins
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class CustomEventDisplay extends EntityDisplay {

	public CustomEventDisplay() {
	}

	public CustomEventDisplay(CustomEvent customEvent) {
		_blocked = customEvent.isBlocked();
		_displayName = customEvent.getDisplayName();
		_hidden = customEvent.isHidden();
		_name = customEvent.getName();

		setId(customEvent.getName());
	}

	private boolean _blocked;
	private String _displayName;
	private boolean _hidden;
	private String _name;

}