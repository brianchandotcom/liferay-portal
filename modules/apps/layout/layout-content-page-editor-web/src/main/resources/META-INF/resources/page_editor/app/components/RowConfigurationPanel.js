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
import {ConfigContext} from '../config/index';
import selectPrefixedSegmentsExperienceId from '../selectors/selectPrefixedSegmentsExperienceId';
import {StoreContext} from '../store/index';
import updateItemConfig from '../thunks/updateItemConfig';
import updateRowColumns from '../thunks/updateRowColumns';

const NUMBER_OF_COLUMNS_OPTIONS = ['0', '1', '2', '3', '4', '5', '6'];

const SELECTORS = {
	gutters: 'gutters',
	numberOfColumns: 'numberOfColumns'
};

const ClayCheckboxWithState = ({onValueChange, ...otherProps}) => {
	const [value, setValue] = useState(false);

	return (
		<ClayCheckbox
			checked={value}
			onChange={({target: {checked}}) => {
				setValue(val => !val);
				onValueChange(checked);
			}}
			{...otherProps}
		/>
	);
};

export const RowConfigurationPanel = ({item}) => {
	const config = useContext(ConfigContext);
	const dispatch = useContext(DispatchContext);
	const segmentsExperienceId = selectPrefixedSegmentsExperienceId(
		useContext(StoreContext)
	);

	const handleSelectValueChanged = (identifier, value) => {
		if (identifier === SELECTORS.numberOfColumns) {
			const currentNumberOfColumns = item.config.numberOfColumns;
			const newNumberOfColumns = value;

			const columnsToBeModified = Math.abs(
				newNumberOfColumns - currentNumberOfColumns
			);

			if (columnsToBeModified === 0) {
				return;
			}

			if (item && item.itemId) {
				dispatch(
					updateRowColumns({
						config,
						itemId: item.itemId,
						numberOfColumns: newNumberOfColumns,
						segmentsExperienceId
					})
				);
			}

			return;
		}

		dispatch(
			updateItemConfig({
				config,
				itemConfig: {
					[identifier]: value
				},
				itemId: item.itemId,
				segmentsExperienceId
			})
		);
	};

	return (
		<>
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
			{item.config.numberOfColumns > 1 && (
				<ClayForm.Group>
					<ClayCheckboxWithState
						aria-label={Liferay.Language.get('columns-gutter')}
						label={Liferay.Language.get('columns-gutter')}
						onValueChange={value =>
							handleSelectValueChanged(SELECTORS.gutters, value)
						}
						value={item.config.gutters}
					/>
				</ClayForm.Group>
			)}
		</>
	);
};
