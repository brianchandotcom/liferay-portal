/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import com.liferay.petra.string.StringPool;

/**
 * @author Iván Zaera
 */
public abstract class BaseModifiableSettings
	extends BaseSettings implements ModifiableSettings {

	public BaseModifiableSettings() {
	}

	public BaseModifiableSettings(Settings parentSettings) {
		super(parentSettings);
	}

	@Override
	public void reset() {
		for (String key : getModifiedKeys()) {
			reset(key);
		}
	}

	@Override
	public ModifiableSettings setValues(ModifiableSettings modifiableSettings) {
		for (String key : modifiableSettings.getModifiedKeys()) {
			String[] values = modifiableSettings.getValues(
				key, StringPool.EMPTY_ARRAY);

			if (values.length == 1) {
				setValue(key, values[0]);
			}
			else {
				setValues(key, values);
			}
		}

		return this;
	}

}