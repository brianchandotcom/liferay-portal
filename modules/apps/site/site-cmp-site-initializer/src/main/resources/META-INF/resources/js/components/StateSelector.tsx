/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import Label from '@clayui/label';
import classNames from 'classnames';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type/api';
import React, {LegacyRef, useMemo, useState} from 'react';

import './StateSelector.scss';

import {mapStateKeyToDisplayType, mapStateKeyToLabel} from '../utils/constants';

export interface State {
	key: string;
	name: string;
	nextStates: string[];
}

const mapKeyToDisplayOrder: Record<string, number> = {
	blocked: 3,
	done: 4,
	inProgress: 2,
	notStarted: 1,
};

const Trigger = React.forwardRef(
	(
		{
			children,
			className,
			small,
			...otherProps
		}: {
			children: string;
			className?: string;
			otherProps: unknown;
			small: boolean;
		},
		ref: LegacyRef<HTMLDivElement>
	) => (
		<div
			{...otherProps}
			className={classNames('lfr-cmp__state-selector', className, {
				'lfr-cmp__state-selector--small': small,
			})}
			ref={ref}
			tabIndex={0}
		>
			<Label displayType={mapStateKeyToDisplayType[children]}>
				{mapStateKeyToLabel[children]}
			</Label>
		</div>
	)
);

export default function StateSelector({
	id,
	initialSelectedKey,
	name,
	onChange,
	showLabel = false,
	small,
	states,
}: {
	id?: string;
	initialSelectedKey: string;
	name?: string;
	onChange?: (key: string) => Promise<void>;
	showLabel?: boolean;
	small?: boolean;
	states: State[];
}) {
	const [selectedKey, setSelectedKey] = useState(initialSelectedKey);

	const filteredStates = useMemo(() => {
		const currentState = states.find(({key}) => key === selectedKey);

		if (!currentState) {
			return [];
		}

		return states
			.filter(
				({key}) =>
					currentState.nextStates.includes(key) || key === selectedKey
			)
			.sort(
				(a, b) =>
					(mapKeyToDisplayOrder[a.key] || 0) -
					(mapKeyToDisplayOrder[b.key] || 0)
			);
	}, [selectedKey, states]);

	const handleSelectionChange = (key: React.Key) => {
		const newKey = String(key);

		setSelectedKey(newKey);
		onChange?.(newKey);
	};

	return (
		<FieldBase
			accessible={false}
			hideEditedFlag
			label={Liferay.Language.get('state')}
			name="ObjectField_state"
			showLabel={showLabel}
			visible
		>
			<Picker<State>
				as={Trigger}
				defaultSelectedKey={initialSelectedKey}
				disabled={false}
				id={id}
				items={filteredStates}
				messages={{
					itemDescribedby: Liferay.Language.get(
						'you-are-currently-on-a-text-element,-inside-of-a-list-box'
					),
					itemSelected: Liferay.Language.get('x-selected'),
					scrollToBottomAriaLabel:
						Liferay.Language.get('scroll-to-bottom'),
					scrollToTopAriaLabel: Liferay.Language.get('scroll-to-top'),
				}}
				name={name}
				onSelectionChange={handleSelectionChange}
				selectedKey={selectedKey}
				small={small}
				width={125}
			>
				{(item) => (
					<Option key={item.key} textValue={item.key}>
						<Label displayType={mapStateKeyToDisplayType[item.key]}>
							{item.name}
						</Label>
					</Option>
				)}
			</Picker>

			<input name="ObjectField_state" type="hidden" value={selectedKey} />
		</FieldBase>
	);
}
