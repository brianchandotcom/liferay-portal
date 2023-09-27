/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model.portlet;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Neil Griffin
 */
public interface PortletDependency extends Serializable {

	public String getName();

	public String getScope();

	public Type getType();

	public String getVersion();

	public StringBundler toStringBundler();

	public enum Type {

		CSS, JAVASCRIPT, OTHER

	}

}