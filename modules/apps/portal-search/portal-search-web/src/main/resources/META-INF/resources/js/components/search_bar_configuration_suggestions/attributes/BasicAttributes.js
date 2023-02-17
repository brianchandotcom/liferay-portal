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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import React from 'react';

function BasicAttributes({onChange, value}) {
	const _handleChangeAttribute = (property) => (event) => {
		onChange({
			attributes: {...value.attributes, [property]: event.target.value},
		});
	};

	return (
		<div className="form-group-autofit">
			<ClayInput.GroupItem>
				<label>
					{Liferay.Language.get('character-threshold')}

					<ClayTooltipProvider>
						<span
							className="ml-2"
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'character-threshold-for-displaying-suggestions-contributor-help'
							)}
						>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				</label>

				<ClayInput
					aria-label={Liferay.Language.get('character-threshold')}
					min="0"
					onChange={_handleChangeAttribute('characterThreshold')}
					type="number"
					value={value.attributes?.characterThreshold || ''}
				/>
			</ClayInput.GroupItem>
		</div>
	);
}

export default BasicAttributes;
