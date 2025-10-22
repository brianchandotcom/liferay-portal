/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.helper;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Layout;

/**
 * @author Javier Moral
 */
@ProviderType
public interface LayoutReferenceResolver {

	public Layout resolve(
		long companyId, JSONObject jsonObject, long scopeGroupId);

}