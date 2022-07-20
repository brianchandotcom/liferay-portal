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
import classNames from 'classnames';
import React, {useEffect, useRef, useState} from 'react';

import {useGlobalContext} from '../../app/contexts/GlobalContext';
import {useId} from '../../app/utils/useId';
import {useStyleBook} from '../../plugins/page-design-options/hooks/useStyleBook';
import {Tooltip} from './Tooltip';

/**
 * These elements must be sorted from the most outer circle to the most inner
 * circle to facilitate keyboard navigation.
 * @type {string[]}
 */
const SPACING_TYPES = ['margin', 'padding'];

/**
 * We want to show spacing options in a clockwise order, according
 * to the standard of most CSS rules (top, right, bottom, left).
 * @type {string[]}
 */
const SPACING_POSITIONS = ['top', 'right', 'bottom', 'left'];

const ARROW_TO_POSITION = {
	ArrowDown: 'bottom',
	ArrowLeft: 'left',
	ArrowRight: 'right',
	ArrowUp: 'top',
};

const REVERSED_POSITION = {
	bottom: 'top',
	left: 'right',
	right: 'left',
	top: 'bottom',
};

const BUTTON_CLASSNAME = 'page-editor__spacing-selector__button';
const DROPDOWN_CLASSNAME = 'page-editor__spacing-selector__dropdown';

export default function SpacingBox({fields, onChange, value}) {
	const ref = useRef();

	const focusButton = (type, position) => {
		const button = document.querySelector(
			`.${BUTTON_CLASSNAME}[data-type=${type}][data-position=${position}]`
		);

		button?.focus();
	};

	const handleKeyDown = (event) => {
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

		event.preventDefault();

		const {
			position: currentPosition,
			type: currentType,
		} = document.activeElement.dataset;

		let nextPosition = ARROW_TO_POSITION[event.key];
		let nextType = currentType;

		if (nextPosition === currentPosition) {

			// Move to the outer type.
			// We try to update the type so we can move to the outer circle,
			// or stay in position if it is not possible.

			const currentTypeIndex = SPACING_TYPES.indexOf(currentType);
			nextType = SPACING_TYPES[Math.max(0, currentTypeIndex - 1)];
		}
		else if (nextPosition === REVERSED_POSITION[currentPosition]) {

			// Move to the inner type.
			// We try to update the type so we can move to the inner circle,
			// and keep currentPosition if it succeeds.

			const currentTypeIndex = SPACING_TYPES.indexOf(currentType);

			if (currentTypeIndex < SPACING_TYPES.length - 1) {
				nextType = SPACING_TYPES[currentTypeIndex + 1];
				nextPosition = currentPosition;
			}
		}

		focusButton(nextType, nextPosition);
	};

	return (
		<div
			className="page-editor__spacing-selector"
			onKeyDownCapture={handleKeyDown}
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
								field={fields[key]}
								key={key}
								onChange={(value) => onChange(key, value)}
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

function SpacingSelectorButton({field, onChange, position, type, value}) {
	const [active, setActive] = useState(false);
	const disabled = !field || field.disabled;
	const itemListRef = useRef();
	const [labelElement, setLabelElement] = useState(null);
	const {tokenValues} = useStyleBook();
	const tooltipId = useId();
	const triggerId = useId();
	const [triggerElement, setTriggerElement] = useState(null);

	useEffect(() => {
		if (active && itemListRef.current) {
			itemListRef.current
				.querySelector(
					Liferay.FeatureFlags['LPS-147895']
						? `button[data-value="${value || field?.defaultValue}"]`
						: 'button'
				)
				?.focus();
		}
	}, [active, field, value]);

	return (
		<ClayDropDown
			active={active}
			className={classNames(
				`${DROPDOWN_CLASSNAME} ${DROPDOWN_CLASSNAME}--${type} ${DROPDOWN_CLASSNAME}--${type}-${position} align-items-stretch d-flex text-center`,
				{disabled}
			)}
			menuElementAttrs={{className: 'cadmin'}}
			onActiveChange={setActive}
			renderMenuOnClick
			trigger={
				<ClayButton
					aria-describedby={tooltipId}
					aria-expanded={active}
					aria-haspopup={true}
					aria-label={field?.label}
					className={classNames(
						`${BUTTON_CLASSNAME} b-0 flex-grow-1 mb-0 text-center`,
						{'text-secondary': !active}
					)}
					data-position={position}
					data-type={type}
					disabled={disabled}
					displayType="unstyled"
					id={triggerId}
					onClick={() => setActive(!active)}
					ref={setTriggerElement}
					type="button"
				>
					{field ? (
						<Tooltip
							hoverElement={triggerElement}
							id={tooltipId}
							label={
								<>
									{field.label} -{' '}
									<SpacingOptionValue
										position={position}
										tokenValues={tokenValues}
										type={type}
										value={value || field.defaultValue}
									/>
								</>
							}
							positionElement={labelElement}
						/>
					) : null}

					<span className="text-truncate" ref={setLabelElement}>
						<SpacingOptionValue
							position={position}
							removeValueUnit
							tokenValues={tokenValues}
							type={type}
							value={value || field?.defaultValue}
						/>
					</span>
				</ClayButton>
			}
		>
			<div ref={itemListRef}>
				<ClayDropDown.ItemList aria-labelledby={triggerId}>
					<ClayDropDown.Group header={field?.label}>
						{field?.validValues?.map((option) => (
							<ClayDropDown.Item
								aria-label={Liferay.Util.sub(
									Liferay.Language.get('set-x-to-x'),
									[field.label, option.label]
								)}
								className="d-flex"
								data-value={option.value}
								key={option.value}
								onClick={() => {
									onChange(option.value);
									setActive(false);
									document.getElementById(triggerId)?.focus();
								}}
							>
								<span className="text-truncate w-50">
									{Liferay.FeatureFlags['LPS-147895']
										? tokenValues[`spacer${option.value}`]
												?.label || option.label
										: option.label}
								</span>

								<strong className="flex-grow-1 pl-2 text-right text-truncate">
									<SpacingOptionValue
										position={position}
										tokenValues={tokenValues}
										type={type}
										value={option.value}
									/>
								</strong>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.Group>
				</ClayDropDown.ItemList>
			</div>
		</ClayDropDown>
	);
}

function SpacingOptionValue({
	position,
	removeValueUnit = false,
	tokenValues,
	type,
	value: optionValue,
}) {
	const globalContext = useGlobalContext();
	const [value, setValue] = useState(optionValue);

	useEffect(() => {
		if (
			Liferay.FeatureFlags['LPS-147895'] &&
			tokenValues[`spacer${optionValue}`]
		) {
			setValue(tokenValues[`spacer${optionValue}`].value);

			return;
		}

		const element = globalContext.document.createElement('div');
		element.style.display = 'none';
		element.classList.add(`${type[0]}${position[0]}-${optionValue}`);
		globalContext.document.body.appendChild(element);

		let nextValue = globalContext.window
			.getComputedStyle(element)
			.getPropertyValue(`${type}-${position}`);

		if (!Liferay.FeatureFlags['LPS-147895'] && removeValueUnit) {
			nextValue = parseFloat(nextValue);

			if (isNaN(nextValue)) {
				nextValue = '0';
			}
			else {
				nextValue = nextValue.toString();
			}
		}

		setValue(nextValue);
		globalContext.document.body.removeChild(element);
	}, [
		globalContext,
		optionValue,
		position,
		removeValueUnit,
		type,
		tokenValues,
	]);

	return value === undefined ? '' : value;
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
