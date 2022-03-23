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
import ClayDropDown from '@clayui/drop-down';
import React, {useRef, useState} from 'react';

import {useId} from '../../app/utils/useId';

const SPACING_TYPES = ['margin', 'padding'];

/**
 * We want to show spacing options in a clockwise order, according
 * to the standard of most CSS rules (top, right, bottom, left).
 * @type {string[]}
 */
const SPACING_POSITIONS = ['top', 'right', 'bottom', 'left'];

const BUTTON_CLASSNAME = 'page-editor__spacing-selector__button';
const DROPDOWN_CLASSNAME = 'page-editor__spacing-selector__dropdown';

const HORIZONTAL_FOCUS_LIST = [
	['margin', 'left'],
	['padding', 'left'],
	['padding', 'right'],
	['margin', 'right'],
];

const VERTICAL_FOCUS_LIST = [
	['margin', 'top'],
	['padding', 'top'],
	['padding', 'bottom'],
	['margin', 'bottom'],
];

export default function SpacingBox({defaultValue, onChange, options, value}) {
	const ref = useRef();

	const focusButton = (type, position) => {
		const button = document.querySelector(
			`.${BUTTON_CLASSNAME}[data-type=${type}][data-position=${position}]`
		);

		button?.focus();
	};

	const handleKeyUp = (event) => {
		if (
			(event.key === 'Enter' || event.key === ' ') &&
			document.activeElement === ref.current
		) {
			event.preventDefault();
			focusButton('margin', 'top');

			return;
		}

		if (
			!document.activeElement?.classList.contains(BUTTON_CLASSNAME) ||
			document.activeElement?.getAttribute('aria-expanded') === 'true' ||
			!event.key.startsWith('Arrow')
		) {
			return;
		}

		const {
			position: currentPosition,
			type: currentType,
		} = document.activeElement.dataset;

		const focusList =
			event.key === 'ArrowLeft' || event.key === 'ArrowRight'
				? HORIZONTAL_FOCUS_LIST
				: VERTICAL_FOCUS_LIST;

		let nextIndex = focusList.findIndex(
			([type, position]) =>
				position === currentPosition && type === currentType
		);

		if (event.key === 'ArrowLeft' || event.key === 'ArrowUp') {
			nextIndex =
				nextIndex === -1
					? Math.floor(focusList.length / 2.001)
					: Math.max(0, nextIndex - 1);
		}
		else {
			nextIndex =
				nextIndex === -1
					? Math.ceil(focusList.length / 2.001)
					: Math.min(focusList.length - 1, nextIndex + 1);
		}

		focusButton(...focusList[nextIndex]);
	};

	return (
		<div
			className="page-editor__spacing-selector w-100"
			onKeyUp={handleKeyUp}
			ref={ref}
			role="grid"
		>
			<SpacingSelectorBackground />

			{SPACING_TYPES.map((type) => (
				<React.Fragment key={type}>
					{SPACING_POSITIONS.map((position) => {
						const key = `${type}${capitalize(position)}`;

						return (
							<SpacingSelectorButton
								defaultValue={defaultValue}
								key={position}
								onChange={(value) => onChange(key, value)}
								options={options}
								position={position}
								type={type}
								value={value[key]}
							/>
						);
					})}
				</React.Fragment>
			))}
		</div>
	);
}

function SpacingSelectorButton({
	defaultValue,
	onChange,
	options,
	position,
	type,
	value,
}) {
	const [active, setActive] = useState(false);
	const title = `${capitalize(type)} ${capitalize(position)}: ${
		value || defaultValue
	}px`;
	const triggerId = useId();

	return (
		<ClayDropDown
			active={active}
			className={`${DROPDOWN_CLASSNAME} ${DROPDOWN_CLASSNAME}--${type}-${position} align-items-stretch d-flex text-center`}
			onActiveChange={setActive}
			trigger={
				<ClayButton
					aria-expanded={active}
					aria-haspopup={true}
					className={`${BUTTON_CLASSNAME} b-0 flex-grow-1 mb-0 text-center`}
					data-position={position}
					data-type={type}
					displayType="unstyled"
					id={triggerId}
					onClick={() => setActive(!active)}
					title={title}
					type="button"
				>
					{value || defaultValue}
				</ClayButton>
			}
		>
			<ClayDropDown.ItemList
				aria-labelledby={triggerId}
				onKeyUp={(event) => event.stopPropagation()}
			>
				{options.map((option) => (
					<ClayDropDown.Item
						key={option.value}
						onClick={() => {
							onChange(option.value);
							setActive(false);
							document.getElementById(triggerId)?.focus();
						}}
					>
						{option.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

function capitalize(str) {
	return `${str.substring(0, 1).toUpperCase()}${str.substring(1)}`;
}

function SpacingSelectorBackground() {
	return (
		<svg
			fill="none"
			height="160"
			viewBox="0 0 240 160"
			width="240"
			xmlns="http://www.w3.org/2000/svg"
		>
			<path d="M0 1H240L199.331 31H41.6736L0 1Z" fill="#FFEDE0" />

			<path d="M0 160H240L198.5 130H41L0 160Z" fill="#FFEDE0" />

			<path d="M42 32.475L0 0V160L42 129V32.475Z" fill="#FFF4EC" />

			<path d="M198 32.475L240 0V160L198 129V32.475Z" fill="#FFF4EC" />

			<path
				d="M151 69.5471L193 37V124L151 91.0029V69.5471Z"
				fill="#EDF9F0"
			/>

			<path
				d="M89 69.5471L47 37V124L89 91.0029V69.5471Z"
				fill="#EDF9F0"
			/>

			<path d="M46 36.5L194 36L151.222 70H89.5L46 36.5Z" fill="#E4F6E9" />

			<path
				d="M46 124.5L194 125L151.222 91H89.5L46 124.5Z"
				fill="#E4F6E9"
			/>

			<rect height="88" stroke="#CBEBD3" width="147" x="46.5" y="36.5" />

			<rect height="22" stroke="#CBEBD3" width="63" x="88.5" y="69.5" />

			<path d="M41.5 31.5H198.5V129.5H41.5V31.5Z" stroke="#FFDCC2" />

			<path d="M41.5 31.5L0.5 0.5" stroke="#FFDCC2" />

			<path d="M198.5 31.5L239.5 0.5" stroke="#FFDCC2" />

			<path d="M198.5 129.5L239.5 159" stroke="#FFDCC2" />

			<path d="M41.5 129.5L0.5 159.5" stroke="#FFDCC2" />

			<path d="M151.5 69.5L193.5 36.5" stroke="#CBEBD3" />

			<path d="M88.5 69.5L46.5 36.5" stroke="#CBEBD3" />

			<path d="M88.5 91.5L46.5 124" stroke="#CBEBD3" />

			<path d="M151.5 91.5L193.5 124" stroke="#CBEBD3" />

			<rect height="159" stroke="#FFDCC2" width="239" x="0.5" y="0.5" />
		</svg>
	);
}
