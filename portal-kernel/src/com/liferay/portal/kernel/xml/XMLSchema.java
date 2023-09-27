/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.xml;

import org.osgi.annotation.versioning.ProviderType;

import org.xml.sax.InputSource;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public interface XMLSchema {

	public String getPublicId();

	public String getSchemaLanguage();

	public InputSource getSchemaSource();

	public String getSystemId();

}