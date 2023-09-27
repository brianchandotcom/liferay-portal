/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.text.localizer.address;

import com.liferay.portal.kernel.model.Address;

/**
 * @author Pei-Jung Lan
 */
public interface AddressTextLocalizer {

	public String format(Address address);

}