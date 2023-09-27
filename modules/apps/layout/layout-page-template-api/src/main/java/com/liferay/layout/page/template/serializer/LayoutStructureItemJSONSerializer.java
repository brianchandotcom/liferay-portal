/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.serializer;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Rubén Pulido
 * @author Jürgen Kappler
 */
public interface LayoutStructureItemJSONSerializer {

	public String toJSONString(
			Layout layout, String layoutStructureItemId,
			boolean saveInlineContent, boolean saveMappingConfiguration,
			long segmentsExperienceId)
		throws PortalException;

}