/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.web.internal.model.display.contacts;

import com.liferay.osb.faro.engine.client.model.Asset;
import com.liferay.osb.faro.web.internal.model.display.main.EntityDisplay;

/**
 * @author Matthew Kong
 */
@SuppressWarnings({"FieldCanBeLocal", "UnusedDeclaration"})
public class AssetDisplay extends EntityDisplay {

	public AssetDisplay() {
	}

	public AssetDisplay(Asset asset) {
		setId(asset.getId());

		_canonicalUrl = asset.getCanonicalUrl();
		_dataSourceAssetPK = asset.getDataSourceAssetPK();
		_dataSourceId = asset.getDataSourceId();
		_description = asset.getDescription();
		_name = asset.getName();
		_url = asset.getUrl();
		_type = asset.getAssetType();
	}

	private String _canonicalUrl;
	private String _dataSourceAssetPK;
	private String _dataSourceId;
	private String _description;
	private String _name;
	private String _type;
	private String _url;

}