/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import {FocusScope, Keys, noop, sub} from '@clayui/shared';
import classNames from 'classnames';
import React, {useLayoutEffect, useRef, useState} from 'react';

const DELIMITER_KEYS = ['Enter', ','];

const escapeRegExp = (str) => {
	return str.replace(/[-[\]{}()*+?.^$|]/g, '\\$&');
};

const defaultFilter = (items, item, inputValue, locator) =>
	!items
		.map((element) => element[locator.label])
		.includes(item[locator.label]) &&
	(inputValue
		? item[locator.label].match(escapeRegExp(inputValue))
		: item[locator.label]);

const MultiSelectMenuRenderer = ({
	filter = defaultFilter,
	inputValue,
	items,
	locator,
	onItemClick = () => {},
	sourceItems,
}) => (
	<ClayDropDown.ItemList>
		{sourceItems
			.filter((item) => filter(items, item, inputValue, locator))
			.map((item) => (
				<ClayAutocomplete.Item
					key={item[locator.value]}
					match={inputValue}
					onClick={() => onItemClick(item)}
					value={item[locator.label]}
				/>
			))}
	</ClayDropDown.ItemList>
);

export default function MultiSelect({
	clearAllTitle = Liferay.Language.get('clear-all'),
	closeButtonAriaLabel = Liferay.Language.get('remove-x'),
	disabled,
	disabledClearAll,
	filter = defaultFilter,
	inputName,
	inputValue = '',
	isValid = true,
	items = [],
	locator = {
		label: 'label',
		value: 'value',
	},
	menuRenderer: MenuRenderer = MultiSelectMenuRenderer,
	onBlur = noop,
	onChange = noop,
	onFocus = noop,
	onItemsChange = noop,
	onKeyClearAllButtonClick = () => {
		onItemsChange([]);
		onChange('');
	},
	onKeyDown = noop,
	sourceItems = [],
	spritemap,
	...otherProps
}) {
	const containerRef = useRef(null);
	const inputRef = useRef(null);
	const lastItemRef = useRef(null);
	const [active, setActive] = useState(false);
	const [isFocused, setIsFocused] = useState(false);
	const [isOpenMenu, setIsOpenMenu] = useState(false);

	useLayoutEffect(() => {
		if (sourceItems) {
			const matchedItems = sourceItems.filter((item) =>
				filter(items, item, inputValue, locator)
			);

			setActive(matchedItems.length !== 0 && isOpenMenu);
		}
	}, [filter, inputValue, isOpenMenu, items, locator, sourceItems]);

	const setNewValue = (newVal) => {
		if (!items.includes(newVal) && sourceItems.includes(newVal)) {
			onItemsChange([...items, newVal]);
		}

		onChange('');

		setIsOpenMenu(false);
	};

	const getFirstOfMatches = () => {
		const matchedItems = sourceItems.filter((item) =>
			filter(items, item, inputValue, locator)
		);

		return matchedItems[0];
	};

	const handleKeyDown = (event) => {
		onKeyDown(event);

		const {key} = event;

		if (key === Keys.Backspace && !inputValue) {
			event.preventDefault();
		}

		if (key === 'ArrowDown') {
			event.preventDefault();

			setIsOpenMenu(true);
		}

		if (key === 'ArrowUp') {
			event.preventDefault();

			setIsOpenMenu(false);
		}

		if (inputValue && DELIMITER_KEYS.includes(key)) {
			event.preventDefault();

			setNewValue(getFirstOfMatches());
		}
		else if (
			!inputValue &&
			key === Keys.Backspace &&
			inputRef.current &&
			lastItemRef.current
		) {
			inputRef.current.blur();
			lastItemRef.current.focus();
		}
	};

	return (
		<FocusScope arrowKeysUpDown={false}>
			<div
				className={classNames(
					'form-control form-control-tag-group input-group',
					{
						focus: isFocused && isValid,
					}
				)}
				onClick={() => setIsOpenMenu(!isOpenMenu)}
				ref={containerRef}
			>
				<ClayInput.GroupItem>
					{items.map((item, i) => {
						const removeItem = () => {
							setIsOpenMenu(false);
							onItemsChange([
								...items.slice(0, i),
								...items.slice(i + 1),
							]);
						};

						return (
							<React.Fragment key={i}>
								<ClayLabel
									aria-label={item[locator.label]}
									closeButtonProps={{
										'aria-label': sub(
											closeButtonAriaLabel,
											[item[locator.label]]
										),
										disabled,
										onClick: (event) => {
											event.stopPropagation();

											if (inputRef.current) {
												inputRef.current.focus();
											}
											removeItem();
										},
										ref: (ref) => {
											if (i === items.length - 1) {
												lastItemRef.current = ref;
											}
										},
									}}
									onKeyDown={({key}) => {
										if (key !== Keys.Backspace) {
											return;
										}
										if (inputRef.current) {
											inputRef.current.focus();
										}
										removeItem();
									}}
									spritemap={spritemap}
								>
									{item[locator.label]}
								</ClayLabel>

								{inputName && (
									<input
										name={inputName}
										type="hidden"
										value={item[locator.value]}
									/>
								)}
							</React.Fragment>
						);
					})}

					<input
						{...otherProps}
						className="form-control-inset"
						disabled={disabled}
						onBlur={(e) => {
							onBlur(e);
							setIsFocused(false);
						}}
						onChange={(event) => {
							setIsOpenMenu(true);

							onChange(event.target.value.replace(',', ''));
						}}
						onFocus={(e) => {
							onFocus(e);
							setIsFocused(true);
						}}
						onKeyDown={handleKeyDown}
						ref={inputRef}
						type="text"
						value={inputValue}
					/>
				</ClayInput.GroupItem>

				{!disabled &&
					!disabledClearAll &&
					(inputValue || items.length > 0) && (
						<ClayInput.GroupItem shrink>
							<button
								className="component-action"
								onClick={() => {
									onKeyClearAllButtonClick();

									if (inputRef.current) {
										inputRef.current.focus();
									}
								}}
								title={clearAllTitle}
								type="button"
							>
								<ClayIcon
									spritemap={spritemap}
									symbol="times-circle"
								/>
							</button>
						</ClayInput.GroupItem>
					)}

				<ClayInput.GroupItem shrink>
					<ClayIcon symbol="caret-double-l" />
				</ClayInput.GroupItem>

				{sourceItems.length > 0 && (
					<div onMouseLeave={() => setIsOpenMenu(false)}>
						<ClayAutocomplete.DropDown
							active={active}
							alignElementRef={containerRef}
							onSetActive={setIsOpenMenu}
						>
							<MenuRenderer
								filter={filter}
								inputValue={inputValue}
								items={items}
								locator={locator}
								onItemClick={(item) => {
									setNewValue(item);

									if (inputRef.current) {
										inputRef.current.focus();
									}
								}}
								sourceItems={sourceItems}
							/>
						</ClayAutocomplete.DropDown>
					</div>
				)}
			</div>
		</FocusScope>
	);
}
