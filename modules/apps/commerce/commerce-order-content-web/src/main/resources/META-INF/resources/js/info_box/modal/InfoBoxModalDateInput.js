/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import React from 'react';

const InfoBoxModalDateInput = ({inputValue, label, setInputValue}) => {
	return (
		<>
			<label htmlFor="infoBoxModalInput">{label}</label>

			<ClayInput
				id="infoBoxModalInput"
				onChange={(event) => {
					event.preventDefault();

					setInputValue(event.target.value);
				}}
				type="date"
				value={inputValue}
			/>
		</>
	);
};

export default InfoBoxModalDateInput;
