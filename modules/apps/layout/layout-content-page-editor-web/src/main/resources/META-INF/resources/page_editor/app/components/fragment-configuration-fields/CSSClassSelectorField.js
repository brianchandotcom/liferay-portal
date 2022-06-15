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

import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayMultiSelect from '@clayui/multi-select';
import {FocusScope} from '@clayui/shared';
import React, {useMemo, useRef, useState} from 'react';

import useControlledState from '../../../core/hooks/useControlledState';
import {useSelector} from '../../contexts/StoreContext';
import {useId} from '../../utils/useId';

export default function CSSClassSelectorField({
	field,
	onValueSelect,
	value: initialValue,
}) {
	const cssClass = useMemo(() => {
		return (
			initialValue?.map((cssClass) => ({
				label: cssClass,
				value: cssClass,
			})) ?? []
		);
	}, [initialValue]);

	const [items, setItems] = useControlledState(cssClass);

	const [dropDownActive, setDropdownActive] = useState(false);
	const [value, setValue] = useState('');

	const cssClassesInputId = useId();
	const helpTextId = useId();

	const alignElementRef = useRef();
	const firstOptionRef = useRef();
	const multiSelectRef = useRef();

	const onKeyDown = (event) => {
		if (event.key === 'Escape') {
			setDropdownActive(false);
			setValue((previousValue) => previousValue.trim());

			multiSelectRef.current?.focus();
		}
	};

	const addItem = (newItem) => {
		if (!newItem.trim()) {
			return;
		}

		if (!items.some((item) => item.value === newItem)) {
			const nextItems = [...items, {label: newItem, value: newItem}];

			setItems(nextItems);
			onValueSelect(
				field.name,
				nextItems.map((item) => item.value)
			);
		}
	};

	const onItemClick = (newItem) => {
		setValue('');
		setDropdownActive(false);

		addItem(newItem);

		multiSelectRef.current?.focus();
	};

	return (
		<>
			<ClayForm.Group
				className="page-editor__css-class-selector-field"
				small
			>
				<label htmlFor={cssClassesInputId}>
					{Liferay.Language.get('css-classes')}
				</label>

				<div ref={alignElementRef}>
					<ClayMultiSelect
						autoComplete="off"
						id={cssClassesInputId}
						items={items}
						onBlur={() => {
							if (!dropDownActive) {
								addItem(value);
								setValue('');
							}
						}}
						onChange={(value) => {
							setValue(value);

							if (!dropDownActive) {
								setDropdownActive(true);
							}
						}}
						onFocus={() => {
							setDropdownActive(false);
							setValue((previousValue) => previousValue.trim());
						}}
						onItemsChange={(items) => {
							const nextItems = [
								...new Set(items.map((item) => item.value)),
							];

							setItems(
								nextItems.map((item) => ({
									label: item,
									value: item,
								}))
							);

							onValueSelect(field.name, nextItems);
						}}
						onKeyDown={(event) => {
							if (event.key === ' ' && value.trim().length > 0) {
								addItem(value.trim());
								setValue('');
							}
							else if (event.key === 'ArrowDown') {
								event.preventDefault();

								firstOptionRef.current?.focus();
							}
						}}
						placeholder={Liferay.Language.get(
							'type-to-add-a-class'
						)}
						ref={multiSelectRef}
						value={value}
					/>
				</div>

				<div className="mt-1 small text-secondary" id={helpTextId}>
					{Liferay.Language.get(
						'use-a-comma,-line-break,-or-space-to-add-multiple-classes'
					)}
				</div>
			</ClayForm.Group>
			<CSSClassSelectorDropDown
				active={dropDownActive}
				alignElementRef={alignElementRef}
				cssClass={value}
				firstOptionRef={firstOptionRef}
				onItemClick={onItemClick}
				onKeyDown={onKeyDown}
				onSetActive={setDropdownActive}
			/>
		</>
	);
}

function CSSClassSelectorDropDown({
	active,
	alignElementRef,
	cssClass,
	firstOptionRef,
	onItemClick,
	onKeyDown,
	onSetActive,
}) {
	const dropdownRef = useRef();

	const availableCssClasses = useSelector((state) => {
		const layoutData = state.layoutData;

		const cssClasses = new Set();

		Object.values(layoutData.items)
			?.flatMap((item) => item.config?.cssClasses ?? [])
			.forEach((cssClass) => {
				cssClasses.add(cssClass);
			});

		return [...cssClasses];
	});

	const filteredCssClasses = useMemo(() => {
		return availableCssClasses.filter(
			(availableCssClass) =>
				availableCssClass.indexOf(cssClass.trim()) !== -1
		);
	}, [availableCssClasses, cssClass]);

	return (
		<ClayDropDown.Menu
			active={active && cssClass}
			alignElementRef={alignElementRef}
			className="page-editor__css-class-selector-dropdown"
			containerProps={{
				className: 'cadmin',
			}}
			onKeyDown={onKeyDown}
			onSetActive={onSetActive}
			ref={dropdownRef}
		>
			<FocusScope>
				<div>
					<ClayDropDown.ItemList>
						<ClayDropDown.Group
							header={Liferay.Language.get('new-class')}
						>
							<ClayDropDown.Item
								className="align-items-center d-flex text-3"
								innerRef={firstOptionRef}
								onClick={() => onItemClick(cssClass)}
							>
								{Liferay.Language.get('create')}

								<ClayLabel
									className="ml-2"
									displayType="secondary"
								>
									{cssClass}
								</ClayLabel>
							</ClayDropDown.Item>
						</ClayDropDown.Group>

						{filteredCssClasses.length > 0 && (
							<ClayDropDown.Group
								header={Liferay.Language.get(
									'existing-classes'
								)}
							>
								{filteredCssClasses.map((availableCssClass) => (
									<ClayDropDown.Item
										className="align-items-center d-flex text-3"
										key={availableCssClass}
										onClick={() =>
											onItemClick(availableCssClass)
										}
									>
										<ClayLabel displayType="secondary">
											{availableCssClass}
										</ClayLabel>
									</ClayDropDown.Item>
								))}
							</ClayDropDown.Group>
						)}
					</ClayDropDown.ItemList>
				</div>
			</FocusScope>
		</ClayDropDown.Menu>
	);
}
