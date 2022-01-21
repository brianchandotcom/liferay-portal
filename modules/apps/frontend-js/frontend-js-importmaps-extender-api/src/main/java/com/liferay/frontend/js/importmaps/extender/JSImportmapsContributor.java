/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.js.importmaps.extender;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Iván Zaera Avellón
 */
public interface JSImportmapsContributor {

	/**
	 * Contribute a chunk of the whole importmaps JSON object. The returned
	 * object must be a plain JSON object containing key value pairs, where keys
	 * are bare identifiers and values are JavaScript module URIs to which those
	 * bare identifiers must be resolved.
	 *
	 * @see <a href="https://github.com/WICG/import-maps">Import maps description</a>
	 * @return a pure JSON object
	 * @review
	 */
	public JSONObject getImportmapsJSONObject();

	/**
	 * Get the scope for the contributed chunks of importmaps.
	 *
	 * @return a valid URL or null to register a global mapping
	 * @review
	 */
	public default String getScope() {
		return null;
	}

}