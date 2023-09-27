/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClaySelectWithOption} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

export default function SelectFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label, validValues} = frontendToken;
	const [nextValue, setNextValue] = useControlledState(value);

	const id = useId();

	return (
		<ClayForm.Group small>
			<label htmlFor={id}>{label}</label>

			<ClaySelectWithOption
				id={id}
				onChange={(event) => {
					const nextValue =
						event.target.options[event.target.selectedIndex].value;

					setNextValue(nextValue);
					onValueSelect(nextValue);
				}}
				options={validValues}
				value={nextValue}
			/>
		</ClayForm.Group>
	);
}

SelectFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({
		label: PropTypes.string.isRequired,
		validValues: PropTypes.arrayOf(
			PropTypes.shape({
				label: PropTypes.string.isRequired,
				value: PropTypes.any.isRequired,
			})
		),
	}).isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.any,
};
