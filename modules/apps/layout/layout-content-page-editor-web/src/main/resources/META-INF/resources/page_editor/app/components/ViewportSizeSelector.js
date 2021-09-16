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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {ALIGN_POSITIONS, align} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useLayoutEffect, useRef, useState} from 'react';

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
	const buttonRef = useRef(null);
	const popoverRef = useRef(null);
	const [showPopover, setShowPopover] = useState(false);

	useLayoutEffect(() => {
		if (showPopover) {
			align(
				popoverRef.current,
				buttonRef.current,
				ALIGN_POSITIONS.Bottom,
				false
			);
		}
	}, [showPopover]);

	return (
		<>
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
				onMouseEnter={() => setShowPopover(true)}
				onMouseLeave={() => setShowPopover(false)}
				ref={buttonRef}
				small
				symbol={icon}
			/>
			{showPopover && (
				<ClayPopover
					alignPosition="bottom"
					className="page-editor__viewport-size-selector__popover"
					header={label}
					ref={popoverRef}
				>
					{sizeId === VIEWPORT_SIZES.desktop && (
						<span className="d-block font-weight-semi-bold">
							{Liferay.Language.get('default-viewport')}
						</span>
					)}
					{VIEWPORT_DESCRIPTIONS[sizeId]}
				</ClayPopover>
			)}
		</>
	);
};

const SelectorButtonList = ({
	availableViewportSizes,
	dropdown,
	onSelect,
	selectedSize,
}) =>
	Object.values(availableViewportSizes).map(({icon, label, sizeId}) =>
		dropdown ? (
			<ClayDropDown.Item
				key={sizeId}
				onClick={() => onSelect(sizeId)}
				symbolLeft={icon}
			>
				{label}
			</ClayDropDown.Item>
		) : (
			<SelectorButton
				icon={icon}
				key={sizeId}
				label={label}
				onSelect={onSelect}
				selectedSize={selectedSize}
				sizeId={sizeId}
			/>
		)
	);

export default function ViewportSizeSelector({onSizeSelected, selectedSize}) {
	const {availableViewportSizes} = config;
	const [active, setActive] = useState(false);

	return (
		<>
			<ClayButton.Group className="d-lg-block d-none">
				<SelectorButtonList
					availableViewportSizes={availableViewportSizes}
					onSelect={onSizeSelected}
					selectedSize={selectedSize}
					setActive={setActive}
				/>
			</ClayButton.Group>

			<ClayDropDown
				active={active}
				className="d-lg-none"
				hasLeftSymbols
				hasRightSymbols
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						className="btn-monospaced"
						displayType="secondary"
						small
					>
						<ClayIcon
							symbol={availableViewportSizes[selectedSize].icon}
						/>
						<span className="sr-only">
							{availableViewportSizes[selectedSize].label}
						</span>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					<SelectorButtonList
						availableViewportSizes={availableViewportSizes}
						dropdown
						onSelect={onSizeSelected}
						selectedSize={selectedSize}
					/>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</>
	);
}

ViewportSizeSelector.propTypes = {
	onSizeSelected: PropTypes.func,
	selectedSize: PropTypes.string,
};
