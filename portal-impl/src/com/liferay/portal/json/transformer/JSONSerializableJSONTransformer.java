/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json.transformer;

import com.liferay.portal.kernel.json.JSONContext;
import com.liferay.portal.kernel.json.JSONSerializable;
import com.liferay.portal.kernel.json.JSONTransformer;

/**
 * @author Igor Spasic
 */
public class JSONSerializableJSONTransformer implements JSONTransformer {

	@Override
	public void transform(JSONContext jsonContext, Object object) {
		JSONSerializable jsonSerializable = (JSONSerializable)object;

		jsonContext.write(jsonSerializable.toJSONString());
	}

}