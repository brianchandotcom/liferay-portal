/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.importmaps.extender;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Iván Zaera Avellón
 */
public interface JSImportMapsContributor {

	/**
	 * Contribute a chunk of the whole import maps JSON object. The returned
	 * object must be a plain JSON object containing key value pairs, where keys
	 * are bare identifiers and values are JavaScript module URIs to which those
	 * bare identifiers must be resolved.
	 *
	 * @see <a href="https://github.com/WICG/import-maps">Import maps description</a>
	 * @return a pure JSON object
	 * @review
	 */
	public JSONObject getImportMapsJSONObject();

	/**
	 * Get the scope for the contributed chunks of import maps.
	 *
	 * @return a valid URL or null to register a global mapping
	 * @review
	 */
	public default String getScope() {
		return null;
	}

}