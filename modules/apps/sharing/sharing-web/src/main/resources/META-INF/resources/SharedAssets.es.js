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

import {ItemSelectorDialog} from 'frontend-js-web';
import {PortletBase} from 'frontend-js-web';

class SharedAssets extends PortletBase {
	constructor(config, ...args) {
		super(config, ...args);

		this._selectAssetTypeURL = config.selectAssetTypeURL;
		this._viewAssetTypeURL = config.viewAssetTypeURL;
	}

	handleFilterItemClicked(event) {
		const itemData = event.data.item.data;
		const namespace = this.namespace;
		const viewAssetTypeURL = this._viewAssetTypeURL;

		if (itemData.action === 'openAssetTypesSelector') {
			const itemSelectorDialog = new ItemSelectorDialog({
				buttonAddLabel: Liferay.Language.get('select'),
				eventName: namespace + 'selectAssetType',
				title: Liferay.Language.get('select-asset-type'),
				url: this._selectAssetTypeURL
			});

			itemSelectorDialog.open();

			itemSelectorDialog.on('selectedItemChange', event => {
				const selectedItem = event.selectedItem;

				if (selectedItem) {
					let uri = viewAssetTypeURL;

					uri = Liferay.Util.addParams(
						namespace + 'className=' + selectedItem,
						uri
					);

					location.href = uri;
				}
			});
		}
	}
}

export default SharedAssets;
