/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.model.adapter;

import com.liferay.asset.link.model.AssetLink;
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