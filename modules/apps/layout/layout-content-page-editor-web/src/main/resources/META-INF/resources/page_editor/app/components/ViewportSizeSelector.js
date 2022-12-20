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

import {ClayButtonWithIcon, default as ClayButton} from '@clayui/button';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {PopoverTooltip} from '../../common/components/PopoverTooltip';
import {useId} from '../../core/hooks/useId';
import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {config} from '../config/index';

const VIEWPORT_DESCRIPTIONS = {
	desktop: Liferay.Language.get(
		'the-styles-you-define-in-the-desktop-viewport-apply-to-all-other-viewports-unless-you-specify-a-style-in-another-viewport'
	),
	landscapeMobile: Liferay.Language.get(
		'the-styles-you-define-in-the-landscape-phone-viewport-apply-to-the-portrait-phone-viewport-unless-you-specify-a-style-in-the-portrait-phone-viewport'
	),
	portraitMobile: Liferay.Language.get(
		'the-portrait-phone-viewport-reflects-the-style-changes-you-make-in-any-other-viewport-unless-you-specify-a-style-in-the-portrait-phone-viewport'
	),
	tablet: Liferay.Language.get(
		'the-styles-you-define-in-the-tablet-viewport-apply-to-all-the-phone-viewports-unless-you-specify-a-style-in-the-landscape-or-portrait-phone-viewports'
	),
};

const SelectorButton = ({icon, label, onSelect, selectedSize, sizeId}) => {
	const tooltipId = useId();

	return (
		<PopoverTooltip
			alignPosition="bottom"
			content={VIEWPORT_DESCRIPTIONS[sizeId]}
			header={
				sizeId === VIEWPORT_SIZES.desktop &&
				Liferay.Language.get('default-viewport')
			}
			id={tooltipId}
			showTooltipOnClick={false}
			trigger={
				<ClayButtonWithIcon
					aria-label={label}
					aria-pressed={selectedSize === sizeId}
					className={classNames({
						'page-editor__viewport-size-selector--default':
							sizeId === VIEWPORT_SIZES.desktop,
					})}
					displayType="secondary"
					key={sizeId}
					onClick={() => onSelect(sizeId)}
					size="sm"
					symbol={icon}
				/>
			}
		/>
	);
};

export default function ViewportSizeSelector({onSizeSelected, selectedSize}) {
	const {availableViewportSizes} = config;

	return (
		<ClayButton.Group>
			{Object.values(availableViewportSizes).map(
				({icon, label, sizeId}) => (
					<SelectorButton
						icon={icon}
						key={sizeId}
						label={label}
						onSelect={onSizeSelected}
						selectedSize={selectedSize}
						sizeId={sizeId}
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
