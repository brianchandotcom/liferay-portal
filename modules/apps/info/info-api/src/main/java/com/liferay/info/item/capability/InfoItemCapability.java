/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.item.capability;

import com.liferay.info.exception.CapabilityVerificationException;
import com.liferay.info.type.Keyed;
import com.liferay.info.type.Labeled;

import java.util.Locale;

/**
 * @author Jorge Ferrer
 */
public interface InfoItemCapability extends Keyed, Labeled {

	@Override
	public String getLabel(Locale locale);

	public void verify(String itemClassName)
		throws CapabilityVerificationException;

}