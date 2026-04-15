/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {CacheStatus} from '../contexts/CacheContext';

type Item = {id: number; name: string};

type Props<T> = {
	disabled?: boolean;
	id?: string;
	items: Item[];
	loader: () => Promise<T>;
	onBlur?: (event: React.FocusEvent<HTMLButtonElement>) => void;
	onSelectionChange?: (selectedKey: React.Key) => void;
	placeholder?: string;
	selectedKey: React.Key;
	small?: boolean;
	status: CacheStatus;
};

const Trigger = React.forwardRef(
	(
		{
			open,
			small,
			status,
			value,
			...otherProps
		}: {
			open: boolean;
			small: boolean;
			status: CacheStatus;
			value: string;
		},
		ref: React.Ref<HTMLButtonElement>
	) => {
		useEffect(() => {
			if (open && status === 'saved') {
				(ref as React.RefObject<HTMLButtonElement>).current?.focus();
			}
		});

		return (
			<button
				{...otherProps}
				className={classNames(
					'align-items-center d-flex form-control form-control-select-secondary justify-content-between',
					{
						'form-control-select': status !== 'saving',
						'form-control-sm': small,
					}
				)}
				ref={ref}
			>
				{value}

				{status === 'saving' ? (
					<ClayLoadingIndicator className="m-0" />
				) : null}
			</button>
		);
	}
);

export default function AsyncPicker<T>({
	disabled,
	id,
	items,
	loader,
	onBlur,
	onSelectionChange,
	placeholder = Liferay.Language.get('select-an-option'),
	selectedKey,
	small = false,
	status,
	...otherProps
}: Props<T>) {
	const [active, setActive] = useState(false);

	const [value, setValue] = useState(
		getItemName(items, selectedKey) || placeholder
	);

	return (
		<Picker
			active={active}
			as={Trigger}
			disabled={status === 'saving' || (disabled && status !== 'stale')}
			id={id}
			items={items}
			messages={{
				itemDescribedby: Liferay.Language.get(
					'you-are-currently-on-a-text-element,-inside-of-a-list-box'
				),
				itemSelected: Liferay.Language.get('x-selected'),
				scrollToBottomAriaLabel:
					Liferay.Language.get('scroll-to-bottom'),
				scrollToTopAriaLabel: Liferay.Language.get('scroll-to-top'),
			}}
			onActiveChange={async (active: boolean) => {
				if (active && status === 'stale') {
					await loader();
				}

				setActive(active);
			}}
			onBlur={onBlur}
			onSelectionChange={(selectedKey: React.Key) => {
				const name = getItemName(items, selectedKey);

				if (name) {
					setValue(name);
				}

				if (onSelectionChange) {
					onSelectionChange(selectedKey);
				}
			}}
			open={active}
			placeholder={placeholder}
			selectedKey={selectedKey ? String(selectedKey) : ''}
			small={small}
			status={status}
			value={value}
			{...otherProps}
		>
			{(item) => <Option key={item.id}>{item.name}</Option>}
		</Picker>
	);
}

function getItemName(items: Item[], id: React.Key) {
	const item = items.find((item) => item.id === Number(id));

	return item?.name;
}
