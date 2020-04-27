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

import ClayForm, {ClayCheckbox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateItemConfig from '../../thunks/updateItemConfig';
import updateRowColumns from '../../thunks/updateRowColumns';
import {RowItemConfigurationPanel} from './RowItemConfigurationPanel';

const MODULES_PER_ROW_OPTIONS = {
	1: [1],
	2: [1, 2],
	3: [1, 3],
	4: [2, 4],
	5: [2, 5],
	6: [2, 3, 6],
};
const NUMBER_OF_COLUMNS_OPTIONS = ['1', '2', '3', '4', '5', '6'];
const VERTICAL_ALIGNMENT = ['top', 'middle', 'bottom'];

const ROW_CONFIGURATION_IDENTIFIERS = {
	gutters: 'gutters',
	modulesPerRow: 'modulesPerRow',
	numberOfColumns: 'numberOfColumns',
	reverseOrder: 'reverseOrder',
	verticalAlignment: 'verticalAlignment',
};

const ClayCheckboxWithState = ({onValueChange, ...otherProps}) => {
	const [value, setValue] = useState(false);

	return (
		<ClayCheckbox
			checked={value}
			onChange={({target: {checked}}) => {
				setValue((val) => !val);
				onValueChange(checked);
			}}
			{...otherProps}
		/>
	);
};

export const RowConfigurationPanel = ({item}) => {
	const {availableViewportSizes} = config;
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const rowConfig = {
		...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[LAYOUT_DATA_ITEM_TYPES.row],
		...item.config,
	};

	const handleConfigurationValueChanged = (identifier, value) => {
		let itemConfig = {[identifier]: value};

		if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
			itemConfig = {[selectedViewportSize]: itemConfig};
		}

		if (identifier === ROW_CONFIGURATION_IDENTIFIERS.numberOfColumns) {
			const currentNumberOfColumns = rowConfig.numberOfColumns;
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
						itemId: item.itemId,
						numberOfColumns: newNumberOfColumns,
						segmentsExperienceId,
						viewportSizeId: selectedViewportSize,
					})
				);
			}

			return;
		}

		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
				segmentsExperienceId,
			})
		);
	};

	const configRender = rowConfig[selectedViewportSize] || rowConfig;

	return (
		<>
			<ClayForm.Group small>
				<RowItemConfigurationPanel
					config={configRender.numberOfColumns}
					id="rowNumberOfColumns"
					identifier={ROW_CONFIGURATION_IDENTIFIERS.numberOfColumns}
					label={Liferay.Language.get('number-of-columns')}
					onValueChange={handleConfigurationValueChanged}
					options={NUMBER_OF_COLUMNS_OPTIONS}
				/>
			</ClayForm.Group>
			<div className="align-items-center d-flex justify-content-between">
				<p className="mb-3">{Liferay.Language.get('styles')}</p>
				<p>
					{Liferay.Language.get(
						availableViewportSizes[selectedViewportSize].label
					)}
					<ClayIcon
						className="ml-2"
						symbol={
							availableViewportSizes[selectedViewportSize].icon
						}
					/>
				</p>
			</div>
			<ClayForm.Group small>
				<RowItemConfigurationPanel
					config={configRender.modulesPerRow}
					id="rowModulesPerRow"
					identifier={ROW_CONFIGURATION_IDENTIFIERS.modulesPerRow}
					label={Liferay.Language.get('layout')}
					onValueChange={handleConfigurationValueChanged}
					options={MODULES_PER_ROW_OPTIONS[rowConfig.numberOfColumns]}
				/>
			</ClayForm.Group>
			<ClayForm.Group small>
				<RowItemConfigurationPanel
					config={configRender.verticalAlignment}
					id="rowVerticalAlignment"
					identifier={ROW_CONFIGURATION_IDENTIFIERS.verticalAlignment}
					label={Liferay.Language.get('vertical-alignment')}
					onValueChange={handleConfigurationValueChanged}
					options={VERTICAL_ALIGNMENT}
				/>
			</ClayForm.Group>
			{rowConfig.numberOfColumns > 1 && (
				<ClayForm.Group>
					<ClayCheckboxWithState
						aria-label={Liferay.Language.get('columns-gutter')}
						checked={configRender.gutters}
						label={Liferay.Language.get('columns-gutter')}
						onValueChange={(value) =>
							handleConfigurationValueChanged(
								ROW_CONFIGURATION_IDENTIFIERS.gutters,
								value
							)
						}
					/>
				</ClayForm.Group>
			)}
		</>
	);
};

RowConfigurationPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({numberOfColumns: PropTypes.number}),
	}),
};
