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

import ClayButton from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useContext} from 'react';

import {ConfigContext} from '../../app/config/index';
import {openInfoItemSelector} from '../../core/openInfoItemSelector';

export default function ItemSelector({label, onItemSelect, selectedItemTitle}) {
	const config = useContext(ConfigContext);

	return (
		<>
			{label && <label htmlFor="itemSelectorInput">{label}</label>}

			<div className="d-flex">
				<ClayInput
					className="mr-2"
					id="itemSelectorInput"
					readOnly
					sizing="sm"
					type="text"
					value={selectedItemTitle || ''}
				/>

				<ClayButton.Group>
					<ClayButton
						displayType="secondary"
						onClick={() =>
							openInfoItemSelector(onItemSelect, config)
						}
						small
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</ClayButton.Group>
			</div>
		</>
	);
}
