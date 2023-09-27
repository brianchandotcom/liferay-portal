/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.configuration;

/**
 * @author Iván Zaera
 */
public interface WikiGroupServiceConfigurationOverride {

	public String emailPageAddedBodyXml();

	public String emailPageAddedSubjectXml();

	public String emailPageUpdatedBodyXml();

	public String emailPageUpdatedSubjectXml();

	public boolean enableRss();

}