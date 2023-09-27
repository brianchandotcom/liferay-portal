/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.layout.prototype;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author André de Oliveira
 */
public interface SearchLayoutFactory {

	public void createSearchLayout(Group group);

	public Layout createSearchLayoutPrototype(long companyId);

}