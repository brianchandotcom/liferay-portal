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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useSelector} from '../../contexts/StoreContext';

export const CheckboxField = ({field, onValueSelect, value}) => {
	const [nextValue, setNextValue] = useControlledState(value);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const customValues = field.typeOptions?.customValues;

	return (
		<ClayForm.Group className="mt-1">
			<div className="align-items-center d-flex justify-content-between">
				<ClayCheckbox
					aria-label={field.label}
					checked={
						customValues
							? nextValue === customValues.checked
							: nextValue
					}
					containerProps={{className: 'mb-0'}}
					label={field.label}
					onChange={(event) => {
						let eventValue = event.target.checked;

						if (customValues) {
							eventValue = eventValue
								? customValues.checked
								: customValues.unchecked;
						}

						setNextValue(eventValue);
						onValueSelect(field.name, eventValue);
					}}
				/>
				{field.responsive &&
					selectedViewportSize !== VIEWPORT_SIZES.desktop && (
						<ClayButtonWithIcon
							data-tooltip-align="bottom"
							displayType="secondary"
							onClick={() => {
								onValueSelect(field.name, null);
							}}
							small
							symbol="restore"
							title={Liferay.Language.get('restore-default')}
						/>
					)}
			</div>
		</ClayForm.Group>
	);
};

CheckboxField.propTypes = {
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
