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

import Component from 'metal-component';
import Soy from 'metal-soy';
import {Config} from 'metal-state';

import '../../../common/AssetSelector.es';
import '../../common/FloatingToolbarDropdown.es';
import './ItemSelectorFieldDelegateTemplate.soy';
import templates from './ItemSelectorField.soy';
import getConnectedComponent from '../../../../store/ConnectedComponent.es';
import {openAssetBrowser} from '../../../../utils/FragmentsEditorDialogUtils';
import {setIn} from '../../../../utils/FragmentsEditorUpdateUtils.es';

/**
 * ItemSelectorField
 */
class ItemSelectorField extends Component {
	/**
	 * @inheritdoc
	 * @review
	 */
	prepareStateForRender(state) {
		let nextState = state;

		const {typeOptions} = this.field;

		if (typeOptions) {
			const {className} = typeOptions;

			if (className) {
				nextState = setIn(nextState, ['selectedClassName'], className);
			}
		}

		return nextState;
	}

	/**
	 * Handle the click in the plus button to open the dialog to select an item
	 * @review
	 */
	_handleItemSelectClick() {
		const className = this.field.typeOptions.className;

		const itemType = this.availableAssets.find(
			availableAsset => availableAsset.className === className
		);

		if (itemType) {
			this._openAssetBrowser(itemType.href, itemType.typeName);
		}
	}

	/**
	 * Handle the click in the item type dropdown
	 * @param {Event} event
	 * @review
	 */
	_handleItemTypeClick(event) {
		const {
			assetBrowserUrl,
			assetBrowserWindowTitle
		} = event.delegateTarget.dataset;

		this._openAssetBrowser(assetBrowserUrl, assetBrowserWindowTitle);
	}

	/**
	 * Opens asset browser
	 * @param {string} assetBrowserURL
	 * @param {string} assetBrowserWindowTitle
	 * @review
	 */
	_openAssetBrowser(assetBrowserURL, assetBrowserWindowTitle) {
		openAssetBrowser({
			assetBrowserURL,
			callback: selectedAssetEntry => {
				this.emit('fieldValueChanged', {
					name: this.field.name,
					value: {
						className: selectedAssetEntry.className,
						classNameId: selectedAssetEntry.classNameId,
						classPK: selectedAssetEntry.classPK,
						title: selectedAssetEntry.title
					}
				});
			},
			eventName: `${this.portletNamespace}selectAsset`,
			modalTitle: assetBrowserWindowTitle
		});
	}
}

ItemSelectorField.STATE = {
	/**
	 * The configuration field
	 * @review
	 * @type {object}
	 */
	field: Config.shapeOf({
		dataType: Config.string(),
		defaultValue: Config.string(),
		description: Config.string(),
		label: Config.string(),
		name: Config.string(),
		type: Config.string(),
		typeOptions: Config.object()
	})
};

const ConnectedItemSelectorField = getConnectedComponent(ItemSelectorField, [
	'availableAssets',
	'portletNamespace',
	'spritemap'
]);

Soy.register(ConnectedItemSelectorField, templates);

export {ItemSelectorField, ConnectedItemSelectorField};
export default ConnectedItemSelectorField;
