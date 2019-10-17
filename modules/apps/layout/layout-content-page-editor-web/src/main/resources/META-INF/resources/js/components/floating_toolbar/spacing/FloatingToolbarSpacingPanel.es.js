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

import 'clay-checkbox';
import Component from 'metal-component';
import Soy, {Config} from 'metal-soy';

import './FloatingToolbarSpacingPanelDelegateTemplate.soy';
import {updateRowColumnsNumberAction} from '../../../actions/updateRowColumnsNumber.es';
import {updateRowConfigAction} from '../../../actions/updateRowConfig.es';
import getConnectedComponent from '../../../store/ConnectedComponent.es';
import {
	CONFIG_KEYS,
	CONTAINER_TYPES,
	NUMBER_OF_COLUMNS_OPTIONS,
	PADDING_OPTIONS
} from '../../../utils/rowConstants';
import templates from './FloatingToolbarSpacingPanel.soy';

/**
 * FloatingToolbarSpacingPanel
 */
class FloatingToolbarSpacingPanel extends Component {
	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleColumnSpacingOptionChange(event) {
		this._updateRowConfig({
			[CONFIG_KEYS.columnSpacing]: event.target.checked
		});
	}

	/**
	 * Handle container spacing checkbox mousedown
	 * @param {Event} event
	 */
	_handleColumnSpacingOptionMousedown(event) {
		event.preventDefault();
	}

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleContainerPaddingOptionChange(event) {
		const {delegateTarget} = event;
		const {paddingDirectionId} = delegateTarget.dataset;
		const {value} = delegateTarget;

		this._updateRowConfig({
			[CONFIG_KEYS[`padding${paddingDirectionId}`]]: value
		});
	}

	/**
	 * Handle container option change
	 * @param {Event} event
	 */
	_handleContainerTypeOptionChange(event) {
		this._updateRowConfig({
			[CONFIG_KEYS.containerType]: event.delegateTarget.value
		});
	}

	/**
	 * Handle number of columns option change
	 * @param {Event} event
	 */
	_handleNumberOfColumnsOptionChange(event) {
		const newValue = event.delegateTarget.value;
		const prevValue = this.item.columns.length;

		let updateRowColumns = true;

		if (newValue < prevValue) {
			const columnsToRemove = this.item.columns.slice(
				newValue - prevValue
			);
			let showConfirmation;

			for (let i = 0; i < columnsToRemove.length; i++) {
				if (columnsToRemove[i].fragmentEntryLinkIds.length > 0) {
					showConfirmation = true;
					break;
				}
			}

			if (
				showConfirmation &&
				!confirm(
					Liferay.Language.get(
						'reducing-the-number-of-columns-will-lose-the-content-added-to-the-deleted-columns-are-you-sure-you-want-to-proceed'
					)
				)
			) {
				event.preventDefault();
				event.delegateTarget.querySelector(
					`option[value="${prevValue}"]`
				).selected = true;
				updateRowColumns = false;
			}
		}

		if (updateRowColumns) {
			this.store.dispatch(
				updateRowColumnsNumberAction(
					event.delegateTarget.value,
					this.itemId
				)
			);
		}
	}

	/**
	 * Updates row configuration
	 * @param {object} config Row configuration
	 * @private
	 * @review
	 */
	_updateRowConfig(config) {
		this.store.dispatch(updateRowConfigAction(this.itemId, config));
	}
}

/**
 * State definition.
 * @review
 * @static
 * @type {!Object}
 */
FloatingToolbarSpacingPanel.STATE = {
	/**
	 * @default CONTAINER_TYPES
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_containerTypes: Config.array()
		.internal()
		.value(CONTAINER_TYPES),

	/**
	 * @default CONTAINER_TYPES
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_numberOfColumnsOptions: Config.array()
		.internal()
		.value(NUMBER_OF_COLUMNS_OPTIONS),

	/**
	 * @default PADDING_OPTIONS
	 * @memberOf FloatingToolbarSpacingPanel
	 * @private
	 * @review
	 * @type {object[]}
	 */
	_paddingOptions: Config.array()
		.internal()
		.value(PADDING_OPTIONS),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarSpacingPanel
	 * @review
	 * @type {!string}
	 */
	itemId: Config.string().required(),

	/**
	 * @default undefined
	 * @memberof FloatingToolbarSpacingPanel
	 * @review
	 * @type {object}
	 */
	store: Config.object().value(null)
};

const ConnectedFloatingToolbarSpacingPanel = getConnectedComponent(
	FloatingToolbarSpacingPanel,
	['layoutData', 'spritemap']
);

Soy.register(ConnectedFloatingToolbarSpacingPanel, templates);

export {FloatingToolbarSpacingPanel};
export default FloatingToolbarSpacingPanel;
