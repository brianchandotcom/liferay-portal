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

import '../../../common/InfoItemSelector.es';

import './ItemSelectorFieldDelegateTemplate.soy';
import getConnectedComponent from '../../../../store/ConnectedComponent.es';
import {openInfoItemSelector} from '../../../../utils/FragmentsEditorDialogUtils';
import {getAvailableTemplates} from '../../../../utils/FragmentsEditorFetchUtils.es';
import {setIn} from '../../../../utils/FragmentsEditorUpdateUtils.es';
import templates from './ItemSelectorField.soy';

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

		const typeOptions = this.field.typeOptions || this.field.defaultValue;

		if (typeOptions) {
			const {className, enableSelectTemplate = false} = typeOptions;

			if (className) {
				nextState = setIn(nextState, ['selectedClassName'], className);
			}

			nextState = setIn(
				nextState,
				['enableSelectTemplate'],
				enableSelectTemplate
			);
		}

		return nextState;
	}

	/**
	 * @inheritDoc
	 * @review
	 */
	syncConfigurationValues() {
		if (
			this.configurationValues &&
			this.configurationValues[this.field.name] &&
			this.configurationValues[this.field.name].className &&
			this.configurationValues[this.field.name].classPK
		) {
			getAvailableTemplates(
				this.configurationValues[this.field.name].className,
				this.configurationValues[this.field.name].classPK
			).then(availableTemplates => {
				this.availableTemplates = availableTemplates;
			});
		}
		else {
			this.availableTemplates = [];
		}
	}

	/**
	 * Handle the click in the plus button to open the dialog to select an item
	 * @review
	 */
	_handleItemSelectClick() {
		this._openInfoItemSelector();
	}

	/**
	 *
	 * @review
	 */
	_handleSelectTemplateValueChanged() {
		const targetElement = window.event.delegateTarget;

		const selectedItem = this.configurationValues[this.field.name];

		selectedItem.template =
			targetElement.options[targetElement.selectedIndex].dataset;

		this.emit('fieldValueChanged', {
			name: this.field.name,
			value: selectedItem
		});
	}

	/**
	 * Handle the click in the item type dropdown
	 * @review
	 */
	_handleItemTypeClick() {
		this._openInfoItemSelector();
	}

	/**
	 * Opens item selector
	 * @review
	 */
	_openInfoItemSelector() {
		let itemSelectorUrl = this.infoItemSelectorURL;

		if (this.field.typeOptions && this.field.typeOptions.itemSelectorUrl) {
			itemSelectorUrl = this.field.typeOptions.itemSelectorUrl;
		}

		openInfoItemSelector(itemSelectorUrl, selectedInfoItem => {
			this.emit('fieldValueChanged', {
				name: this.field.name,
				value: {
					className: selectedInfoItem.className,
					classNameId: selectedInfoItem.classNameId,
					classPK: selectedInfoItem.classPK,
					title: selectedInfoItem.title
				}
			});
		});
	}
}

ItemSelectorField.STATE = {
	/**
	 * Available templates of the current className
	 * @default []
	 * @instance
	 * @memberOf ItemSelectorField
	 * @type {array}
	 */
	availableTemplates: Config.arrayOf(
		Config.shapeOf({
			infoItemRendererKey: Config.string().value(''),
			label: Config.string(),
			templates: Config.arrayOf(
				Config.shapeOf({
					infoItemRendererKey: Config.string().value(''),
					label: Config.string(),
					templateKey: Config.string().value('')
				})
			).value([])
		})
	)
		.internal()
		.value([]),

	/**
	 * Fragment Entry Link Configuration values
	 * @instance
	 * @memberOf ItemSelectorField
	 * @type {object}
	 */
	configurationValues: Config.object(),

	/**
	 * The configuration field
	 * @review
	 * @type {object}
	 */
	field: Config.shapeOf({
		dataType: Config.string(),
		defaultValue: Config.object(),
		description: Config.string(),
		label: Config.string(),
		name: Config.string(),
		type: Config.string(),
		typeOptions: Config.object()
	})
};

const ConnectedItemSelectorField = getConnectedComponent(ItemSelectorField, [
	'getAvailableTemplatesURL',
	'infoItemSelectorURL',
	'spritemap'
]);

Soy.register(ConnectedItemSelectorField, templates);

export {ItemSelectorField, ConnectedItemSelectorField};
export default ConnectedItemSelectorField;
