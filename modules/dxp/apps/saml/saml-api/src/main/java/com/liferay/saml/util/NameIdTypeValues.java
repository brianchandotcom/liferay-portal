/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.util;

/**
 * @author Michael C. Han
 */
public interface NameIdTypeValues {

	public String getEmail();

	public String getEncrypted();

	public String getEntity();

	public String getKerberos();

	public String getPersistent();

	public String getTransient();

	public String getUnspecified();

	public String getWinDomainQualified();

	public String getX509Subject();

}