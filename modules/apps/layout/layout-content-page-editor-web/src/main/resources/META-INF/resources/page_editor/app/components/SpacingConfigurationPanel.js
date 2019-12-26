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

import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import React, {useContext, useState} from 'react';

import {DispatchContext} from '../../app/reducers/index';
import createColumn from '../actions/createColumn';
import removeColumn from '../actions/removeColumn';
import updateItemConfig from '../actions/updateItemConfig';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

const NUMBER_OF_COLUMNS_OPTIONS = ['0', '1', '2', '3', '4', '5', '6'];

const PADDING_OPTIONS = [
	{
		label: '0',
		value: '0'
	},
	{
		label: '1',
		value: '3'
	},
	{
		label: '2',
		value: '4'
	},
	{
		label: '4',
		value: '5'
	},
	{
		label: '6',
		value: '6'
	},
	{
		label: '8',
		value: '7'
	},
	{
		label: '10',
		value: '8'
	}
];

const SELECTORS = {
	gutters: 'gutters',
	numberOfColumns: 'numberOfColumns',
	paddingHorizontal: 'paddingHorizontal',
	paddingVertical: 'paddingVertical',
	type: 'type'
};

const ClayCheckboxWithState = ({onChange, ...otherProps}) => {
	const [value, setValue] = useState(false);

	return (
		<ClayCheckbox
			checked={value}
			onChange={event => {
				onChange(event);
				setValue(val => !val);
			}}
			{...otherProps}
		/>
	);
};

function initializeColumn() {
	const uuid = Math.random()
		.toString(36)
		.substr(2, 8);
	const itemType = LAYOUT_DATA_ITEM_TYPES.column;
	const config = LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[itemType];
	const itemId = `column-${uuid}`;

	return {
		config,
		itemId,
		itemType
	};
}

function updateColumns(item, newNumberOfColumns, dispatchFn) {
	const currentNumberOfColumns = item.config.numberOfColumns;

	if (item && item.itemId && dispatchFn) {
		const columnsToBeAdded = newNumberOfColumns - currentNumberOfColumns;

		if (columnsToBeAdded === 0) {
			return;
		}

		if (columnsToBeAdded > 0) {
			for (let index = 0; index < columnsToBeAdded; index++) {
				const {config, itemId, itemType} = initializeColumn();

				dispatchFn(
					createColumn({
						config,
						itemId,
						itemType,
						newNumberOfColumns,
						rowItemId: item.itemId
					})
				);
			}

			return;
		}

		for (let index = 0; index < Math.abs(columnsToBeAdded); index++) {
			dispatchFn(
				removeColumn({
					newNumberOfColumns,
					rowItemId: item.itemId
				})
			);
		}
	}
}

export const SpacingConfigurationPanel = ({item}) => {
	const dispatch = useContext(DispatchContext);

	const handleSelectValueChanged = (identifier, value) => {
		dispatch(
			updateItemConfig({
				config: {
					[identifier]: value
				},
				itemId: item.itemId
			})
		);

		if (identifier === SELECTORS.numberOfColumns) {
			updateColumns(item, value, dispatch);
		}
	};

	return (
		<div className="floating-toolbar-spacing-panel">
			<ClayForm.Group>
				<label htmlFor="floatingToolbarSpacingPanelNumberOfColumnsOption">
					{Liferay.Language.get('number-of-columns')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('number-of-columns')}
					id="floatingToolbarSpacingPanelNumberOfColumnsOption"
					onChange={({target: {value}}) => {
						handleSelectValueChanged(
							SELECTORS.numberOfColumns,
							Number(value)
						);
					}}
					options={NUMBER_OF_COLUMNS_OPTIONS.map(value => ({
						label: value,
						value
					}))}
					value={String(item.config.numberOfColumns)}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				<label htmlFor="floatingToolbarSpacingPanelContainerTypeOption">
					{Liferay.Language.get('container')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('container')}
					id="floatingToolbarSpacingPanelContainerTypeOption"
					onChange={({target: {value}}) =>
						handleSelectValueChanged(SELECTORS.type, value)
					}
					options={[
						{
							label: Liferay.Language.get('fixed-width'),
							value: 'fixed'
						},
						{
							label: Liferay.Language.get('fluid'),
							value: 'fluid'
						}
					]}
					value={item.config.type}
				/>
			</ClayForm.Group>
			<ClayForm.Group className="form-group-autofit">
				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingVerticalOption">
						{Liferay.Language.get('padding-v')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-v')}
						id="floatingToolbarSpacingPanelPaddingVerticalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingVertical,
								Number(value)
							)
						}
						options={PADDING_OPTIONS}
						value={String(item.config.paddingVertical)}
					/>
				</div>

				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingHorizontalOption">
						{Liferay.Language.get('padding-h')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-h')}
						id="floatingToolbarSpacingPanelPaddingHorizontalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingHorizontal,
								Number(value)
							)
						}
						options={PADDING_OPTIONS}
						value={String(item.config.paddingHorizontal)}
					/>
				</div>
			</ClayForm.Group>

			{item.config.numberOfColumns > 1 && (
				<ClayForm.Group>
					<ClayCheckboxWithState
						aria-label={Liferay.Language.get(
							'space-between-columns'
						)}
						label={Liferay.Language.get('space-between-columns')}
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.gutters,
								value === 'true'
							)
						}
						value={item.config.gutters}
					/>
				</ClayForm.Group>
			)}
		</div>
	);
};
