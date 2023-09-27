/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayCheckbox, ClayToggle} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

import {ConfigurationFieldPropTypes} from '../../../prop_types/index';
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
						aria-describedby={field.description ? helpTextId : null}
						containerProps={{className: 'mb-0'}}
						disabled={disabled}
						label={label}
						onToggle={(nextChecked) => handleChange(nextChecked)}
						toggled={checked}
					/>
				) : (
					<ClayCheckbox
						aria-describedby={field.description ? helpTextId : null}
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
							aria-label={Liferay.Language.get('restore-default')}
							data-tooltip-align="bottom"
							displayType="secondary"
							onClick={() => {
								onValueSelect(field.name, null);
							}}
							size="sm"
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
