/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.collapse;

import com.liferay.portal.search.sort.Sort;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Petteri Karttunen
 */
@ProviderType
public interface InnerHit {

	public InnerCollapse getInnerCollapse();

	public String getName();

	public int getSize();

	public List<Sort> getSorts();

}