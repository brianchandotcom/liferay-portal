/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.collapse;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface CollapseBuilder {

	public CollapseBuilder addInnerHit(InnerHit innerHit);

	public Collapse build();

	public CollapseBuilder field(String field);

	public CollapseBuilder maxConcurrentGroupRequests(
		Integer maxConcurrentGroupRequests);

}