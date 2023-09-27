/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.uuid;

/**
 * @author Brian Wing Shun Chan
 */
public interface PortalUUID {

	public String fromJsSafeUuid(String jsSafeUuid);

	public String generate();

	public String generate(byte[] bytes);

	public String toJsSafeUuid(String uuid);

}