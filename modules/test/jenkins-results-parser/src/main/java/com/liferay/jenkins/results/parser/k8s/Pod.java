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

package com.liferay.jenkins.results.parser.k8s;

import io.kubernetes.client.models.V1ObjectMeta;
import io.kubernetes.client.models.V1Pod;

/**
 * @author Kenji Heigel
 */
public class Pod {

	public String getName() {
		V1ObjectMeta v1ObjectMeta = _v1Pod.getMetadata();

		return v1ObjectMeta.getName();
	}

	protected Pod(V1Pod v1Pod) {
		_v1Pod = v1Pod;
	}

	protected V1Pod getV1Pod() {
		return _v1Pod;
	}

	private final V1Pod _v1Pod;

}