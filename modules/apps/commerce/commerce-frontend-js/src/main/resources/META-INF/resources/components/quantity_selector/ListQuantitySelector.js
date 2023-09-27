/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClaySelectWithOption} from '@clayui/form';
import React, {forwardRef} from 'react';

const ListQuantitySelector = forwardRef(
	(
		{allowedQuantities, className, disabled, name, onUpdate, quantity},
		_inputRef
	) => {
		return (
			<ClaySelectWithOption
				className={className}
				disabled={disabled}
				name={name}
				onChange={({target}) => {
					onUpdate({errors: [], value: Number(target.value)});
				}}
				options={allowedQuantities.map((value) => ({
					label: String(value),
					value,
				}))}
				value={quantity}
			/>
		);
	}
);

export default ListQuantitySelector;
