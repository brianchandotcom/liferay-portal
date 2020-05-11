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

import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../config/constants/layoutDataItemDefaultConfigurations';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {config} from '../../config/index';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';
import {useDispatch, useSelector} from '../../store/index';
import updateItemConfig from '../../thunks/updateItemConfig';
import updateRowColumns from '../../thunks/updateRowColumns';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {RowConfigurationCheckboxField} from './RowConfigurationCheckboxField';
import {RowConfigurationSelectField} from './RowConfigurationSelectField';

const MODULES_PER_ROW_OPTIONS = [
	[1],
	[1, 2],
	[1, 3],
	[2, 4],
	[2, 5],
	[2, 3, 6],
];

const NUMBER_OF_COLUMNS_OPTIONS = ['1', '2', '3', '4', '5', '6'];

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'top'},
	{label: Liferay.Language.get('middle'), value: 'middle'},
	{label: Liferay.Language.get('bottom'), value: 'bottom'},
];

const ROW_CONFIGURATION_IDENTIFIERS = {
	gutters: 'gutters',
	modulesPerRow: 'modulesPerRow',
	numberOfColumns: 'numberOfColumns',
	reverseOrder: 'reverseOrder',
	verticalAlignment: 'verticalAlignment',
};

export const RowConfigurationPanel = ({item}) => {
	const {availableViewportSizes} = config;
	const dispatch = useDispatch();
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const handleConfigurationValueChanged = (identifier, value) => {
		let itemConfig = {[identifier]: value};

		if (
			selectedViewportSize !== VIEWPORT_SIZES.desktop &&
			identifier !== ROW_CONFIGURATION_IDENTIFIERS.gutters
		) {
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

	const getModulesPerRowOptionLabel = (value) => {
		return value > 1
			? Liferay.Language.get('x-modules-per-row')
			: Liferay.Language.get('x-module-per-row');
	};

	const rowConfig = getResponsiveConfig(
		{
			...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS[
				LAYOUT_DATA_ITEM_TYPES.row
			],
			...item.config,
		},
		selectedViewportSize
	);

	const {
		gutters,
		modulesPerRow,
		numberOfColumns,
		reverseOrder,
		verticalAlignment,
	} = rowConfig;

	return (
		<>
			<RowConfigurationSelectField
				fieldValue={numberOfColumns}
				id="rowNumberOfColumns"
				identifier={ROW_CONFIGURATION_IDENTIFIERS.numberOfColumns}
				label={Liferay.Language.get('number-of-columns')}
				onValueChange={handleConfigurationValueChanged}
				options={NUMBER_OF_COLUMNS_OPTIONS.map((option) => ({
					label: option,
				}))}
			/>
			{numberOfColumns > 1 && (
				<RowConfigurationCheckboxField
					fieldValue={gutters}
					identifier={ROW_CONFIGURATION_IDENTIFIERS.gutters}
					label={Liferay.Language.get('show-gutter')}
					onValueChange={handleConfigurationValueChanged}
				/>
			)}
			{config.responsiveEnabled && (
				<>
					<div className="align-items-center d-flex justify-content-between page-editor__floating-toolbar__label pt-3">
						<p className="mb-3 text-uppercase">
							{Liferay.Language.get('styles')}
						</p>
						<p>
							{Liferay.Language.get(
								availableViewportSizes[selectedViewportSize]
									.label
							)}
							<ClayIcon
								className="ml-1"
								symbol={
									availableViewportSizes[selectedViewportSize]
										.icon
								}
							/>
						</p>
					</div>
					<RowConfigurationSelectField
						fieldValue={modulesPerRow}
						getOptionLabel={getModulesPerRowOptionLabel}
						id="rowModulesPerRow"
						identifier={ROW_CONFIGURATION_IDENTIFIERS.modulesPerRow}
						label={Liferay.Language.get('layout')}
						onValueChange={handleConfigurationValueChanged}
						options={MODULES_PER_ROW_OPTIONS[
							rowConfig.numberOfColumns - 1
						].map((option) => ({
							label: Liferay.Util.sub(
								getModulesPerRowOptionLabel(option),
								option
							),
							value: option,
						}))}
					/>
					{numberOfColumns === 2 && modulesPerRow === 1 && (
						<RowConfigurationCheckboxField
							fieldValue={reverseOrder}
							identifier={
								ROW_CONFIGURATION_IDENTIFIERS.reverseOrder
							}
							label={Liferay.Language.get('inverse-order')}
							onValueChange={handleConfigurationValueChanged}
						/>
					)}
					<RowConfigurationSelectField
						fieldValue={verticalAlignment}
						id="rowVerticalAlignment"
						identifier={
							ROW_CONFIGURATION_IDENTIFIERS.verticalAlignment
						}
						label={Liferay.Language.get('vertical-alignment')}
						onValueChange={handleConfigurationValueChanged}
						options={VERTICAL_ALIGNMENT_OPTIONS}
					/>
				</>
			)}
		</>
	);
};

RowConfigurationPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({numberOfColumns: PropTypes.number}),
	}),
};
