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

package com.liferay.asset.kernel.model.adapter;

import com.liferay.asset.kernel.model.AssetLink;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Máté Thurzó
 */
public interface StagedAssetLink extends AssetLink, StagedModel {

	public String getEntry1ClassName();

	public String getEntry1Uuid();

	public String getEntry2ClassName();

	public String getEntry2Uuid();

}