/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.collapse;

import com.liferay.portal.search.sort.Sort;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface InnerHitBuilder {

	public InnerHitBuilder addSort(Sort sort);

	public InnerHit build();

	public InnerHitBuilder innerCollapse(InnerCollapse innerCollapse);

	public InnerHitBuilder name(String name);

	public InnerHitBuilder size(Integer size);

}