/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown from '@clayui/drop-down';
import {FieldBase} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {CustomSelect} from './CustomSelect';

import './index.scss';
interface AutoCompleteProps<T> extends React.HTMLAttributes<HTMLElement> {
	children: (item: T) => React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	emptyStateMessage: string;
	error?: string;
	feedbackMessage?: string;
	hasEmptyItem?: boolean;
	items: T[];
	label: string;
	onActive?: (item: T) => boolean;
	onChangeQuery: (value: string) => void;
	onSelectEmptyStateItem?: (emptyStateItem: EmptyStateItem) => void;
	onSelectItem: (item: T) => void;
	placeholder?: string;
	query: string;
	required?: boolean;
	tooltip?: string;
	value?: string;
}

type EmptyStateItem = {
	externalReferenceCode: string;
	id: string;
	label: string;
};

export default function AutoComplete<T>({
	children,
	className,
	contentRight,
	disabled,
	emptyStateMessage,
	error,
	feedbackMessage,
	hasEmptyItem,
	id,
	items,
	label,
	onActive,
	onChangeQuery,
	onSelectEmptyStateItem,
	onSelectItem,
	placeholder,
	query,
	required = false,
	tooltip,
	value,
}: AutoCompleteProps<T>) {
	const [active, setActive] = useState<boolean>(false);

	const emptyStateItem = {
		externalReferenceCode: '',
		id: '',
		label: Liferay.Language.get('choose-an-option'),
	};

	return (
		<FieldBase
			className={className}
			disabled={disabled}
			errorMessage={error}
			helpMessage={feedbackMessage}
			id={id}
			label={label}
			required={required}
			tooltip={tooltip}
		>
			<ClayDropDown
				active={!disabled && active}
				menuElementAttrs={{className: 'autocomplete-expand'}}
				onActiveChange={(value: boolean) =>
					!disabled ? setActive(value) : setActive(false)
				}
				trigger={
					<CustomSelect
						contentRight={<>{value && contentRight}</>}
						disabled={disabled}
						placeholder={
							placeholder ??
							Liferay.Language.get('choose-an-option')
						}
						value={value}
					/>
				}
			>
				<ClayDropDown.Search
					onChange={onChangeQuery}
					placeholder={Liferay.Language.get('search')}
					value={query}
				/>

				{!items.length ? (
					<ClayDropDown.ItemList>
						<ClayDropDown.Item>
							{emptyStateMessage}
						</ClayDropDown.Item>
					</ClayDropDown.ItemList>
				) : (
					<ClayDropDown.ItemList>
						{hasEmptyItem && (
							<ClayDropDown.Item
								onClick={() => {
									setActive(false);

									if (onSelectEmptyStateItem) {
										onSelectEmptyStateItem(emptyStateItem);
									}
								}}
							>
								<div className="d-flex justify-content-between">
									<div>{emptyStateItem.label}</div>
								</div>
							</ClayDropDown.Item>
						)}

						{items.map((item, index) => {
							return (
								<ClayDropDown.Item
									active={onActive && onActive(item)}
									key={index}
									onClick={() => {
										setActive(false);
										onSelectItem(item);
									}}
								>
									{children && children(item)}
								</ClayDropDown.Item>
							);
						})}
					</ClayDropDown.ItemList>
				)}
			</ClayDropDown>
		</FieldBase>
	);
}
