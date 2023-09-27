/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.settings;

import java.io.IOException;

import java.util.Date;

/**
 * @author Iván Zaera
 */
public interface ArchivedSettings extends ModifiableSettings {

	public void delete() throws IOException;

	public Date getModifiedDate();

	public String getName();

	public String getUserName();

}