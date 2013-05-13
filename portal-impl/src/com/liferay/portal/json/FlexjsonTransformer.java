/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONTransformer;

import flexjson.transformer.Transformer;

/**
 * @author Brian Wing Shun Chan
 */
public class FlexjsonTransformer implements Transformer {

	public FlexjsonTransformer(JSONTransformer jsonTransformer) {
		_jsonTransformer = jsonTransformer;
	}

	@Override
	public void transform(Object object) {
		_jsonTransformer.transform(object);
	}

	private JSONTransformer _jsonTransformer;

}