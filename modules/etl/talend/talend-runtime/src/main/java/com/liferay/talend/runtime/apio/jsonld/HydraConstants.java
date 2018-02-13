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

package com.liferay.talend.runtime.apio.jsonld;

import static com.liferay.talend.runtime.apio.jsonld.ApioConstants.HYDRA;

/**
 * Hydra Vocabulary for Hypermedia-Driven Web APIs,
 * see <a href="https://www.w3.org/ns/hydra/core">Hydra Core Vocabulary</a>
 *
 *
 * @author Zoltán Takács
 */
public interface HydraConstants {

	public static final String COLLECTION = HYDRA.concat("Collection");

	public static final String OPERATION = HYDRA.concat("Operation");

	public static final String PARTIAL_COLLECTION_VIEW = HYDRA.concat(
		"PartialCollectionView");

}