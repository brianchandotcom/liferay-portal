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
import React, {useEffect, useMemo, useRef, useState} from 'react';

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

	const multiSelectRef = useRef();

	const onItemClick = (newItem) => {
		setValue('');
		setDropdownActive(false);

		if (!items.some((item) => item.value === newItem)) {
			onValueSelect(
				field.name,
				[...items, {label: newItem, value: newItem}].map(
					(item) => item.value
				)
			);
		}
	};

	return (
		<>
			<ClayForm.Group small>
				<label htmlFor={cssClassesInputId}>
					{Liferay.Language.get('css-classes')}
				</label>

				<ClayMultiSelect
					id={cssClassesInputId}
					items={items}
					onChange={setValue}
					onItemsChange={(items) => {
						setItems(items);

						onValueSelect(
							field.name,
							items.map((item) => item.value)
						);
					}}
					onKeyDown={(event) => {
						if (event.key === ' ' && value.trim().length > 0) {
							setDropdownActive(true);
						}
					}}
					placeholder={
						items.length > 0
							? null
							: Liferay.Language.get('type-to-add-a-class')
					}
					ref={multiSelectRef}
					value={value}
				/>

				<div className="mt-1 small text-secondary" id={helpTextId}>
					{Liferay.Language.get(
						'use-a-space-enter-or-comma-to-add-multiple-classes'
					)}
				</div>
			</ClayForm.Group>
			<CSSClassSelectorDropDown
				active={dropDownActive}
				cssClass={value}
				multiSelectRef={multiSelectRef}
				onItemClick={onItemClick}
				onSetActive={setDropdownActive}
			/>
		</>
	);
}

function CSSClassSelectorDropDown({
	active,
	cssClass,
	multiSelectRef,
	onItemClick,
	onSetActive,
}) {
	const dropdownRef = useRef();
	const dropdownItemRef = useRef();

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
			(availableCssClass) => availableCssClass.indexOf(cssClass) !== -1
		);
	}, [availableCssClasses, cssClass]);

	useEffect(() => {
		if (active) {
			dropdownItemRef.current?.focus();
		}
	}, [active]);

	return (
		<ClayDropDown.Menu
			active={active}
			alignElementRef={multiSelectRef}
			onSetActive={onSetActive}
			ref={dropdownRef}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Group header={Liferay.Language.get('new-class')}>
					<ClayDropDown.Item
						className="align-items-center d-flex text-3"
						innerRef={dropdownItemRef}
						onClick={() => onItemClick(cssClass)}
					>
						{Liferay.Language.get('create')}

						<ClayLabel className="ml-2" displayType="secondary">
							{cssClass}
						</ClayLabel>
					</ClayDropDown.Item>
				</ClayDropDown.Group>

				{filteredCssClasses.length > 0 && (
					<ClayDropDown.Group
						header={Liferay.Language.get('existing-classes')}
					>
						{filteredCssClasses.map((availableCssClass) => (
							<ClayDropDown.Item
								className="align-items-center d-flex text-3"
								key={availableCssClass}
								onClick={() => onItemClick(availableCssClass)}
							>
								<ClayLabel displayType="secondary">
									{availableCssClass}
								</ClayLabel>
							</ClayDropDown.Item>
						))}
					</ClayDropDown.Group>
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown.Menu>
	);
}
