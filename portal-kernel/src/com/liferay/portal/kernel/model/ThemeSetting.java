/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

/**
 * @author Julio Camarero
 */
public interface ThemeSetting {

	public String[] getOptions();

	public String getScript();

	public String getType();

	public String getValue();

	public boolean isConfigurable();

	public void setConfigurable(boolean configurable);

	public void setOptions(String[] options);

	public void setScript(String script);

	public void setType(String type);

	public void setValue(String value);

}