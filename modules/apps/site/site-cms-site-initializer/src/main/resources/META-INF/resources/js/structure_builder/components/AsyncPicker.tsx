/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useState} from 'react';

import {CacheStatus} from '../contexts/CacheContext';

type Item = {id: number; name: string};

type Props = {
	disabled?: boolean;
	id?: string;
	items: Item[];
	loader: () => Promise<void>;
	onBlur?: (event: React.FocusEvent<HTMLButtonElement>) => void;
	onSelectionChange?: (selectedKey: React.Key) => void;
	placeholder?: string;
	selectedKey: React.Key;
	status: CacheStatus;
};

const Trigger = React.forwardRef(
	(
		{
			status,
			value,
			...otherProps
		}: {
			status: CacheStatus;
			value: string;
		},
		ref: React.Ref<HTMLButtonElement>
	) => {
		return (
			<button
				{...otherProps}
				className="align-items-center d-flex form-control form-control-select-secondary justify-content-between"
				ref={ref}
			>
				{value}

				{status === 'saving' ? (
					<ClayLoadingIndicator className="m-0" />
				) : (
					<ClayIcon
						className="text-secondary"
						symbol="caret-double"
					/>
				)}
			</button>
		);
	}
);

export default function AsyncPicker({
	disabled,
	id,
	items,
	loader,
	onBlur,
	onSelectionChange,
	placeholder = Liferay.Language.get('select-an-option'),
	selectedKey,
	status,
	...otherProps
}: Props) {
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
			placeholder={placeholder}
			selectedKey={selectedKey ? String(selectedKey) : ''}
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
