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
import React from 'react';

const COLORS = [
	'primary',
	'success',
	'danger',
	'warning',
	'info',
	'dark',
	'gray-dark',
	'secondary',
	'light',
	'lighter',
	'white'
];

/**
 * Renders BackgroundColor Configuration Panel Configuration Panel.
 * *WARNING:* This component is unfinished since this is just used
 * to test FloatingToolbar ConfigurationPanel mechanism.
 */
export const BackgroundColorConfigurationPanel = () => (
	<div className="form-group">
		<label htmlFor="floatingToolbarBackgroundColorPanelPalette">
			{Liferay.Language.get('background-color')}
		</label>
		<div
			className="palette-container"
			id="floatingToolbarBackgroundColorPanelPalette"
		>
			<ul className="list-unstyled palette-items-container">
				{COLORS.map(color => (
					<li className="palette-item" key={color}>
						<ClayButton
							block
							className={`bg-${color} palette-item-inner p-1 rounded-circle`}
							displayType="unstyled"
							small
						/>
					</li>
				))}
			</ul>
		</div>
		<ClayButton displayType="secondary" small>
			{Liferay.Language.get('clear')}
		</ClayButton>
	</div>
);
