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

import {PortletBase} from 'frontend-js-web';

class SharedAssets extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._selectAssetTypeURL = config.selectAssetTypeURL;
		this._viewAssetTypeURL = config.viewAssetTypeURL;
	}

	handleFilterItemClicked(event) {
		const namespace = this.namespace;
		const viewAssetTypeURL = this._viewAssetTypeURL;

		AUI().use('liferay-item-selector-dialog', A => {
			var itemData = event.data.item.data;

			if (itemData.action === 'openAssetTypesSelector') {
				var itemSelectorDialog = new A.LiferayItemSelectorDialog({
					eventName: namespace + 'selectAssetType',
					on: {
						selectedItemChange(event) {
							var selectedItem = event.newVal;

							if (selectedItem) {
								var uri = viewAssetTypeURL;

								uri = Liferay.Util.addParams(
									namespace + 'className=' + selectedItem,
									uri
								);

								location.href = uri;
							}
						}
					},
					'strings.add': Liferay.Language.get('select'),
					title: Liferay.Language.get('select-asset-type'),
					url: this._selectAssetTypeURL
				});

				itemSelectorDialog.open();
			}
		});
	}
}

export default SharedAssets;
