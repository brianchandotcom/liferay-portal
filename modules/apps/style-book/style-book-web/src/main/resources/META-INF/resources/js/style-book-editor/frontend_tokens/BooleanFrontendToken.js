/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import {useControlledState} from '@liferay/layout-js-components-web';
import PropTypes from 'prop-types';
import React from 'react';

export default function BooleanFrontendToken({
	frontendToken,
	onValueSelect,
	value,
}) {
	const {label} = frontendToken;
	const [nextValue, setNextValue] = useControlledState(value || false);

	return (
		<ClayForm.Group small>
			<ClayCheckbox
				checked={nextValue}
				label={label}
				onChange={(event) => {
					const nextValue = event.target.checked;

					setNextValue(nextValue);
					onValueSelect(nextValue);
				}}
			/>
		</ClayForm.Group>
	);
}

BooleanFrontendToken.propTypes = {
	frontendToken: PropTypes.shape({label: PropTypes.string.isRequired})
		.isRequired,
	onValueSelect: PropTypes.func.isRequired,
	value: PropTypes.bool,
};
