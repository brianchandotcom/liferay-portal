/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';

export default function ViewportSizeSelector({onSizeSelected, selectedSize}) {
	const {availableViewportSizes} = config;

	return (
		<ClayButton.Group className="flex-nowrap flex-shrink-0">
			{Object.values(availableViewportSizes).map(
				({icon, label, sizeId}) => (
					<ClayButtonWithIcon
						aria-label={label}
						aria-pressed={selectedSize === sizeId}
						className={classNames({
							'page-editor__viewport-size-selector--default':
								sizeId === VIEWPORT_SIZES.desktop,
						})}
						displayType="secondary"
						key={sizeId}
						onClick={() => onSizeSelected(sizeId)}
						size="sm"
						symbol={icon}
					/>
				)
			)}
		</ClayButton.Group>
	);
}

ViewportSizeSelector.propTypes = {
	onSizeSelected: PropTypes.func,
	selectedSize: PropTypes.string,
};
