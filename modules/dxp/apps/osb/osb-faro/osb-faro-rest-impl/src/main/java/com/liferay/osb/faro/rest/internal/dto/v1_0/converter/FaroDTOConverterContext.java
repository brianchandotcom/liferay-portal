/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.rest.internal.dto.v1_0.converter;

import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;

import java.util.HashMap;
import java.util.Locale;

/**
 * @author Leslie Wong
 */
public class FaroDTOConverterContext extends DefaultDTOConverterContext {

	public FaroDTOConverterContext(
		boolean acceptAllLanguages, Object id, Locale locale) {

		super(
			acceptAllLanguages, new HashMap<>(), null, id, locale, null, null);
	}

}