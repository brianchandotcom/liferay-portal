/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayCheckbox} from '@clayui/form';
import PropTypes from 'prop-types';
import React from 'react';

export function ShowGutterSelector({checked, handleConfigurationChanged}) {
	return (
		<ClayForm.Group small>
			<ClayCheckbox
				checked={checked}
				label={Liferay.Language.get('show-gutter')}
				onChange={({target: {checked}}) =>
					handleConfigurationChanged({
						gutters: checked,
					})
				}
			/>
		</ClayForm.Group>
	);
}

ShowGutterSelector.propTypes = {
	checked: PropTypes.bool.isRequired,
	handleConfigurationChanged: PropTypes.func.isRequired,
};
