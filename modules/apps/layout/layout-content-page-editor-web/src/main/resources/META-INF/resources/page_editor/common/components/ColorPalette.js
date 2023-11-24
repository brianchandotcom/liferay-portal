/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {config} from '../../app/config/index';

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

			<div className="mb-1" id={colorPaletteId}>
				<ul className="d-flex flex-wrap list-unstyled">
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
								className="border-0 overflow-hidden p-1 palette-item-inner rounded-circle"
								displayType="unstyled"
								onClick={() => onColorSelect(color)}
								size="sm"
								style={{
									backgroundColor: color.rgbValue,
								}}
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
					size="sm"
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
