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
import ClayForm, {ClayCheckbox, ClayToggle} from '@clayui/form';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {useId} from '../../../core/hooks/useId';
import {ConfigurationFieldPropTypes} from '../../../prop-types/index';
import {VIEWPORT_SIZES} from '../../config/constants/viewportSizes';
import {useSelector} from '../../contexts/StoreContext';

export function CheckboxField({
	className,
	disabled,
	field,
	onValueSelect,
	title,
	value,
}) {
	const helpTextId = useId();
	const [nextValue, setNextValue] = useControlledState(value || false);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const customValues = field.typeOptions?.customValues;

	const checked = customValues
		? nextValue === customValues.checked
		: nextValue;

	const handleChange = (nextChecked) => {
		let eventValue = nextChecked;

		if (customValues) {
			eventValue = eventValue
				? customValues.checked
				: customValues.unchecked;
		}

		setNextValue(eventValue);
		onValueSelect(field.name, eventValue);
	};

	const label = (
		<span className="font-weight-normal text-3">{field.label}</span>
	);

	return (
		<ClayForm.Group className={classNames(className, 'h-100')}>
			<div
				className="align-items-center d-flex justify-content-between page-editor__sidebar__fieldset__field-checkbox"
				data-tooltip-align="bottom"
				title={title}
			>
				{field.typeOptions?.displayType === 'toggle' ? (
					<ClayToggle
						aria-describedby={helpTextId}
						containerProps={{className: 'mb-0'}}
						disabled={disabled}
						label={label}
						onToggle={(nextChecked) => handleChange(nextChecked)}
						toggled={checked}
					/>
				) : (
					<ClayCheckbox
						aria-describedby={helpTextId}
						checked={checked}
						containerProps={{className: 'mb-0'}}
						disabled={disabled}
						label={label}
						onChange={(event) => handleChange(event.target.checked)}
					/>
				)}

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

			{field.description ? (
				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{field.description}
				</p>
			) : null}
		</ClayForm.Group>
	);
}

CheckboxField.propTypes = {
	disabled: PropTypes.bool,
	field: PropTypes.shape(ConfigurationFieldPropTypes).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.oneOfType([PropTypes.bool, PropTypes.string]),
};
