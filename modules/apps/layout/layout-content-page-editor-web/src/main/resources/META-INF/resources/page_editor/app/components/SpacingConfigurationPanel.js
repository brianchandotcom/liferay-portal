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
import updateItemConfig from '../actions/updateItemConfig';

const NUMBER_OF_COLUMNS_OPTIONS = ['0', '1', '2', '3', '4', '5', '6'];

const PADDING_OPTIONS = ['0', '1', '2', '4', '6', '8', '10'];

const SELECTORS = {
	columnSpacing: 'columnSpacing',
	numberOfColumns: 'numberofColumns',
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

export const SpacingConfigurationPanel = ({itemId}) => {
	const dispatch = useContext(DispatchContext);
	const [showSpaceBetweenCheckbox, setShowSpaceBetweenCheckbox] = useState(
		false
	);

	const handleSelectValueChanged = (identifier, value) => {
		dispatch(
			updateItemConfig({
				config: {
					[identifier]: value
				},
				itemId
			})
		);
	};

	return (
		<div className="floating-toolbar-spacing-panel">
			<ClayForm.Group>
				<label htmlFor="floatingToolbarSpacingPanelNumberOfColumnsOption">
					{Liferay.Language.get('number-of-columns')}
				</label>
				<ClaySelectWithOption
					aria-label={Liferay.Language.get('number-of-columns')}
					defaultValue="1"
					id="floatingToolbarSpacingPanelNumberOfColumnsOption"
					onChange={({target: {value}}) => {
						if (value > 1 && !showSpaceBetweenCheckbox) {
							setShowSpaceBetweenCheckbox(true);
						}
						handleSelectValueChanged(
							SELECTORS.numberOfColumns,
							value
						);
					}}
					options={NUMBER_OF_COLUMNS_OPTIONS.map(value => ({
						label: value,
						value
					}))}
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
				/>
			</ClayForm.Group>
			<ClayForm.Group className="form-group-autofit">
				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingVerticalOption">
						{Liferay.Language.get('padding-v')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-v')}
						defaultValue="1"
						id="floatingToolbarSpacingPanelPaddingVerticalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingVertical,
								value
							)
						}
						options={PADDING_OPTIONS.map(value => ({
							label: value,
							value
						}))}
					/>
				</div>

				<div className="form-group-item">
					<label htmlFor="floatingToolbarSpacingPanelPaddingHorizontalOption">
						{Liferay.Language.get('padding-h')}
					</label>
					<ClaySelectWithOption
						aria-label={Liferay.Language.get('padding-h')}
						defaultValue="1"
						id="floatingToolbarSpacingPanelPaddingHorizontalOption"
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.paddingHorizontal,
								value
							)
						}
						options={PADDING_OPTIONS.map(value => ({
							label: value,
							value
						}))}
					/>
				</div>
			</ClayForm.Group>

			{showSpaceBetweenCheckbox && (
				<ClayForm.Group>
					<ClayCheckboxWithState
						aria-label={Liferay.Language.get(
							'space-between-columns'
						)}
						defaultValue={true}
						label={Liferay.Language.get('space-between-columns')}
						onChange={({target: {value}}) =>
							handleSelectValueChanged(
								SELECTORS.columnSpacing,
								value
							)
						}
					/>
				</ClayForm.Group>
			)}
		</div>
	);
};
