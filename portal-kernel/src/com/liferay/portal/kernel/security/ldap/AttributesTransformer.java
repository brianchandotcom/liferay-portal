/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.ldap;

import javax.naming.directory.Attributes;

/**
 * @author Brian Wing Shun Chan
 */
public interface AttributesTransformer {

	public Attributes transformGroup(Attributes attributes);

	public Attributes transformUser(Attributes attributes);

}