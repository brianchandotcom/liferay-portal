/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {Key} from 'react';

import {VIEWPORT_SIZES, ViewportSize} from '../config/constants/viewportSizes';
import {config} from '../config/index';

interface Props {
	onSizeSelected: (sizeId: ViewportSize) => void;
	selectedSize: ViewportSize;
}

export default function ViewportSizeSelector({
	onSizeSelected,
	selectedSize,
}: Props) {
	const {availableViewportSizes} = config;

	return (
		<ClayButton.Group
			className={classNames('flex-nowrap flex-shrink-0', {
				'd-lg-block d-none': Liferay.FeatureFlags['LPD-10988'],
			})}
		>
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
						onClick={() => onSizeSelected(sizeId as ViewportSize)}
						size="sm"
						symbol={icon}
						title={label}
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
