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
import classNames from 'classnames';
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

const SELECTORS = {
	backgroundColorCssClass: 'backgroundColorCssClass'
};

export default function ColorPalette({clearButton, label, onColorSelect}) {
	return (
		<>
			<label htmlFor="colorPalette">{label}</label>

			<div className="palette-container" id="colorPalette">
				<ul className="list-unstyled palette-items-container">
					{COLORS.map(color => (
						<li className="palette-item" key={color}>
							<ClayButton
								block
								className={classNames(
									`bg-${color}`,
									'palette-item-inner',
									'p-1',
									'rounded-circle'
								)}
								displayType="unstyled"
								onClick={() =>
									onColorSelect(
										SELECTORS.backgroundColorCssClass,
										color
									)
								}
								small
							/>
						</li>
					))}
				</ul>
			</div>

			{clearButton && (
				<ClayButton displayType="secondary" small>
					{Liferay.Language.get('clear')}
				</ClayButton>
			)}
		</>
	);
}
