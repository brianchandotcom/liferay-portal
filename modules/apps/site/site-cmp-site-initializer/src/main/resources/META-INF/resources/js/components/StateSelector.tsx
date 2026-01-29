/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import classNames from 'classnames';
import React, {LegacyRef, useState} from 'react';

import './StateSelector.scss';

import Label from '@clayui/label';

import {mapStateKeyToDisplayType, mapStateKeyToLabel} from '../utils/constants';

export interface State {
	key: string;
	name: string;
	nextStates: string[];
}

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
	small,
	states,
}: {
	id?: string;
	initialSelectedKey: string;
	name?: string;
	onChange: (key: string) => Promise<void>;
	small?: boolean;
	states: State[];
}) {
	const [selectedKey, setSelectedKey] = useState(initialSelectedKey);

	function getNextStates() {
		const state = states.find(({key}) => key === selectedKey);

		if (state?.nextStates) {
			return states.filter(({key}) => {
				return state.nextStates.includes(key) || key === selectedKey;
			});
		}
		else {
			return states;
		}
	}

	return (
		<div>
			<Picker<State>
				as={Trigger}
				defaultSelectedKey={initialSelectedKey}
				disabled={false}
				id={id}
				items={getNextStates()}
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
				onSelectionChange={async (item) => {
					setSelectedKey(item as string);

					await onChange(item as string);
				}}
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
		</div>
	);
}
