/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox, ClaySelectWithOption} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {COLUMN_SIZE_MODULE_PER_ROW_SIZES} from '../../../../../../app/config/constants/columnSizes';
import {COMMON_STYLES_ROLES} from '../../../../../../app/config/constants/commonStylesRoles';
import {VIEWPORT_SIZES} from '../../../../../../app/config/constants/viewportSizes';
import {
	useDispatch,
	useSelector,
} from '../../../../../../app/contexts/StoreContext';
import updateItemConfig from '../../../../../../app/thunks/updateItemConfig';
import updateRowColumns from '../../../../../../app/thunks/updateRowColumns';
import {deepEqual} from '../../../../../../app/utils/checkDeepEqual';
import {getResponsiveColumnSize} from '../../../../../../app/utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../../../../../app/utils/getResponsiveConfig';
import {getLayoutDataItemPropTypes} from '../../../../../../prop_types/index';
import {CommonStyles} from './CommonStyles';

const NUMBER_OF_COLUMNS_OPTIONS = [1, 2, 3, 4, 5, 6, 12];

const ROW_CONFIGURATION_IDENTIFIERS = {
	gutters: 'gutters',
	numberOfColumns: 'numberOfColumns',
};

const CUSTOM_ROW = 'custom';

const MODULES_PER_ROW_OPTIONS = [
	[1],
	[1, 2],
	[1, 3],
	[1, 2, 4],
	[1, 2, 5],
	[1, 2, 3, 6],
	[1, 2, 3, 6, 12],
];
const MODULES_PER_ROW_OPTIONS_WITH_CUSTOM = MODULES_PER_ROW_OPTIONS.map(
	(option) => [CUSTOM_ROW, ...option]
);

const VERTICAL_ALIGNMENT_OPTIONS = [
	{label: Liferay.Language.get('top'), value: 'top'},
	{label: Liferay.Language.get('middle'), value: 'middle'},
	{label: Liferay.Language.get('bottom'), value: 'bottom'},
];

const ROW_STYLE_IDENTIFIERS = {
	modulesPerRow: 'modulesPerRow',
	reverseOrder: 'reverseOrder',
	verticalAlignment: 'verticalAlignment',
};

export function RowGeneralPanel({item}) {
	const dispatch = useDispatch();
	const layoutData = useSelector((state) => state.layoutData);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const rowConfig = getResponsiveConfig(item.config, selectedViewportSize);

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
					})
				);
			}

			return;
		}

		dispatch(
			updateItemConfig({
				itemConfig,
				itemId: item.itemId,
			})
		);
	};

	const onCustomStylesValueSelect = (identifier, value) => {
		let itemStyles = {[identifier]: value};

		if (
			selectedViewportSize !== VIEWPORT_SIZES.desktop &&
			identifier !== ROW_STYLE_IDENTIFIERS.gutters
		) {
			itemStyles = {[selectedViewportSize]: itemStyles};
		}

		if (identifier === ROW_STYLE_IDENTIFIERS.numberOfColumns) {
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
					})
				);
			}

			return;
		}

		dispatch(
			updateItemConfig({
				itemConfig: itemStyles,
				itemId: item.itemId,
			})
		);
	};

	const getModulesPerRowOptionLabel = (value) => {
		return value > 1
			? Liferay.Language.get('x-modules-per-row')
			: Liferay.Language.get('x-module-per-row');
	};

	const isCustomRow = useMemo(() => {
		const {numberOfColumns} = rowConfig;

		const {modulesPerRow} = getResponsiveConfig(
			rowConfig,
			selectedViewportSize
		);

		const columnSizes = item.children.map((columnId) => {
			const columnSizeConfig = getResponsiveColumnSize(
				layoutData.items[columnId].config,
				selectedViewportSize
			);

			return columnSizeConfig;
		});

		const columnConfiguration =
			COLUMN_SIZE_MODULE_PER_ROW_SIZES[numberOfColumns]?.[
				modulesPerRow
			] ?? [];

		return !deepEqual(columnConfiguration, columnSizes);
	}, [item.children, layoutData.items, rowConfig, selectedViewportSize]);

	const modulesPerRowOptions = isCustomRow
		? MODULES_PER_ROW_OPTIONS_WITH_CUSTOM
		: MODULES_PER_ROW_OPTIONS;

	return (
		<>
			<div className="mb-3 panel-group-sm">
				<ClayPanel
					collapsable
					defaultExpanded
					displayTitle={Liferay.Language.get('grid-options')}
					displayType="unstyled"
					showCollapseIcon
				>
					<ClayPanel.Body>
						{selectedViewportSize === VIEWPORT_SIZES.desktop && (
							<>
								<Select
									configurationKey="numberOfColumns"
									handleChange={
										handleConfigurationValueChanged
									}
									label={Liferay.Language.get(
										'number-of-modules'
									)}
									options={NUMBER_OF_COLUMNS_OPTIONS.map(
										(option) => ({
											label: option,
										})
									)}
									value={rowConfig.numberOfColumns}
								/>

								{rowConfig.numberOfColumns > 1 && (
									<>
										<ClayCheckbox
											checked={rowConfig.gutters}
											label={Liferay.Language.get(
												'show-gutter'
											)}
											onChange={({target: {checked}}) =>
												handleConfigurationValueChanged(
													'gutters',
													checked
												)
											}
										/>
									</>
								)}
							</>
						)}

						<Select
							configurationKey="modulesPerRow"
							handleChange={onCustomStylesValueSelect}
							label={Liferay.Language.get('layout')}
							options={modulesPerRowOptions[
								NUMBER_OF_COLUMNS_OPTIONS.indexOf(
									rowConfig.numberOfColumns
								)
							].map((option) => ({
								disabled: option === CUSTOM_ROW,
								label:
									option === CUSTOM_ROW
										? Liferay.Language.get('custom')
										: sub(
												getModulesPerRowOptionLabel(
													option
												),
												option
										  ),
								value: option,
							}))}
							value={
								isCustomRow
									? CUSTOM_ROW
									: rowConfig.modulesPerRow
							}
						/>

						{rowConfig.numberOfColumns === 2 &&
							rowConfig.modulesPerRow === 1 &&
							!isCustomRow && (
								<ClayCheckbox
									checked={rowConfig.reverseOrder}
									label={Liferay.Language.get(
										'inverse-order'
									)}
									onChange={({target: {checked}}) =>
										onCustomStylesValueSelect(
											'reverseOrder',
											checked
										)
									}
								/>
							)}

						<Select
							configurationKey="verticalAlignment"
							handleChange={onCustomStylesValueSelect}
							label={Liferay.Language.get('vertical-alignment')}
							options={VERTICAL_ALIGNMENT_OPTIONS}
							value={rowConfig.verticalAlignment || ''}
						/>
					</ClayPanel.Body>
				</ClayPanel>
			</div>

			<CommonStyles
				commonStylesValues={rowConfig.styles}
				item={item}
				role={COMMON_STYLES_ROLES.general}
			/>
		</>
	);
}

RowGeneralPanel.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({numberOfColumns: PropTypes.number}),
	}),
};

const Select = ({configurationKey, handleChange, label, options, value}) => {
	const inputId = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={inputId}>{label}</label>

			<ClaySelectWithOption
				id={inputId}
				onChange={(event) => {
					const nextValue = event.target.value;

					handleChange(
						configurationKey,
						typeof value === 'string'
							? String(nextValue)
							: Number(nextValue)
					);
				}}
				options={options}
				value={String(value)}
			/>
		</ClayForm.Group>
	);
};

Select.propTypes = {
	configurationKey: PropTypes.string.isRequired,
	handleChange: PropTypes.func.isRequired,
	label: PropTypes.string.isRequired,
	options: PropTypes.arrayOf(
		PropTypes.shape({
			label: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
			value: PropTypes.oneOfType([
				PropTypes.string.isRequired,
				PropTypes.number.isRequired,
			]),
		})
	),
	value: PropTypes.oneOfType([PropTypes.string, PropTypes.number]),
};
