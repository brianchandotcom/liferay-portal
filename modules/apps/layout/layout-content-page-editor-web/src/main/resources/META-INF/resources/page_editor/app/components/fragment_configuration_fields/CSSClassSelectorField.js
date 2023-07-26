/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import ClayForm from '@clayui/form';
import ClayLabel from '@clayui/label';
import ClayMultiSelect from '@clayui/multi-select';
import {FocusScope} from '@clayui/shared';
import {useControlledState} from '@liferay/layout-js-components-web';
import {useId} from 'frontend-js-components-web';
import React, {useMemo, useRef, useState} from 'react';

import {useSelector} from '../../contexts/StoreContext';

const MAX_SUGGESTED_CLASSES = 20;

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
	const dropdownRef = useRef();
	const firstOptionRef = useRef();
	const multiSelectRef = useRef();

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

	const onKeyDown = (event) => {
		if (event.key === 'Escape') {
			setDropdownActive(false);
			setValue((previousValue) => previousValue.trim());

			multiSelectRef.current?.focus();
		}
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
							requestAnimationFrame(() => {
								if (
									!dropdownRef.current.contains(
										document.activeElement
									)
								) {
									addItem(value);
									setValue('');
								}
							});
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
							if (event.key === ' ' && !!value.trim().length) {
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

				<p className="m-0 mt-1 small text-secondary" id={helpTextId}>
					{Liferay.Language.get(
						'use-a-comma,-line-break,-or-space-to-add-multiple-classes'
					)}
				</p>
			</ClayForm.Group>
			<CSSClassSelectorDropDown
				active={dropDownActive}
				alignElementRef={alignElementRef}
				cssClass={value}
				dropdownRef={dropdownRef}
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
	dropdownRef,
	firstOptionRef,
	onItemClick,
	onKeyDown,
	onSetActive,
}) {
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
		return availableCssClasses
			.filter(
				(availableCssClass) =>
					availableCssClass.indexOf(cssClass.trim()) !== -1
			)
			.slice(0, MAX_SUGGESTED_CLASSES);
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

						{!!filteredCssClasses.length && (
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
