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

import {ClayTooltipProvider} from '@clayui/tooltip';
import React, {useState} from 'react';

export default ({checked = false, onChange}) => {
	const [isChecked, setChecked] = useState(checked);

	return (
		<ClayTooltipProvider>
			<label className="toggle-switch">
				<input
					checked={isChecked}
					className="toggle-switch-check"
					data-tooltip-align="top"
					data-tooltip-delay="0"
					onChange={() => {
						const newChecked = !isChecked;
						setChecked(newChecked);
						onChange(newChecked);
					}}
					title={
						isChecked
							? Liferay.Language.get('turn-off')
							: Liferay.Language.get('turn-on')
					}
					type="checkbox"
				/>

				<span aria-hidden="true" className="toggle-switch-bar">
					<span className="toggle-switch-handle"></span>
				</span>
			</label>
		</ClayTooltipProvider>
	);
};
