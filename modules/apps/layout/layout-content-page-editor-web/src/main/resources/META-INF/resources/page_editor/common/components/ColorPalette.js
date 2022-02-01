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
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {config} from '../../app/config/index';
import {useId} from '../../app/utils/useId';

export default function ColorPalette({
	label,
	onClear,
	onColorSelect,
	selectedColor,
}) {
	const colorPaletteId = useId();

	const themeColors = useMemo(() => {
		return config.themeColorsCssClasses.map((color) => {
			return {
				color,
				cssClass: color,
				rgbValue: getRgbValue(color),
			};
		});
	}, []);

	return (
		<div className="page-editor__color-palette">
			{label && <label htmlFor={colorPaletteId}>{label}</label>}

			<div className="palette-container" id={colorPaletteId}>
				<ul className="list-unstyled palette-items-container">
					{themeColors.map((color) => (
						<li
							className={classNames('palette-item', {
								'palette-item-selected':
									color.rgbValue === selectedColor ||
									color.cssClass === selectedColor,
							})}
							key={color.cssClass}
						>
							<ClayButton
								block
								className={classNames(
									`bg-${color.cssClass}`,
									'palette-item-inner',
									'p-1',
									'rounded-circle'
								)}
								displayType="unstyled"
								onClick={() => onColorSelect(color)}
								small
								title={color.cssClass}
							/>
						</li>
					))}
				</ul>
			</div>

			{onClear && (
				<ClayButton
					disabled={!selectedColor}
					displayType="secondary"
					onClick={onClear}
					small
				>
					{Liferay.Language.get('clear')}
				</ClayButton>
			)}
		</div>
	);
}

ColorPalette.propTypes = {
	label: PropTypes.string,
	onClear: PropTypes.func,
	onColorSelect: PropTypes.func.isRequired,
	selectedColor: PropTypes.string,
};

function getRgbValue(className) {
	const node = document.createElement('div');

	node.classList.add(`bg-${className}`);
	node.style.display = 'none';

	document.body.append(node);

	const rgbValue = getComputedStyle(node).backgroundColor;

	document.body.removeChild(node);

	return rgbValue;
}
