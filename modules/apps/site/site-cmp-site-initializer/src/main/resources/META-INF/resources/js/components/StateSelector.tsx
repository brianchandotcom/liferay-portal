/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import classNames from 'classnames';
import React, {LegacyRef, useState} from 'react';

import './StateSelector.scss';

import Label from '@clayui/label';

export interface State {
	key: string;
	name: string;
	nextStates: string[];
}

declare type LabelDisplayType =
	| 'secondary'
	| 'info'
	| 'warning'
	| 'danger'
	| 'success'
	| 'unstyled';

const mapLabelToLabelDisplayType: {[key: string]: LabelDisplayType} = {
	'Blocked': 'danger',
	'Done': 'success',
	'In Progress': 'info',
	'Not Started': 'secondary',
	'Overdue': 'warning',
};

const Trigger = React.forwardRef(
	(
		{
			children,
			className,
			triggerClassName,
			...otherProps
		}: {
			children: string;
			className?: string;
			otherProps: unknown;
			triggerClassName?: string;
		},
		ref: LegacyRef<HTMLDivElement>
	) => (
		<div
			{...otherProps}
			className={
				triggerClassName
					? triggerClassName
					: classNames('lfr-cmp__state-selector', className)
			}
			ref={ref}
			tabIndex={0}
		>
			{children !== Liferay.Language.get('select-state') ? (
				<Label
					className="my-0"
					displayType={mapLabelToLabelDisplayType[children]}
				>
					{children}
				</Label>
			) : (
				<span className="text-muted">{children}</span>
			)}
		</div>
	)
);

export default function StateSelector({
	initialSelectedKey,
	onChange,
	states,
	triggerClassName,
}: {
	initialSelectedKey: string;
	onChange: (key: string) => Promise<void>;
	states: State[];
	triggerClassName?: string;
}) {
	const [selectedKey, setSelectedKey] = useState(initialSelectedKey);

	function getNextStates() {
		if (!selectedKey) {
			return states;
		}

		const {nextStates} = states.find(
			({key}) => key === selectedKey
		) as State;

		return states.filter(({key}) => {
			return nextStates.includes(key) || key === selectedKey;
		});
	}

	return (
		<div>
			<Picker<State>
				as={Trigger}
				defaultSelectedKey={initialSelectedKey}
				disabled={false}
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
				onSelectionChange={async (item) => {
					setSelectedKey(item as string);

					await onChange(item as string);
				}}
				placeholder={Liferay.Language.get('select-state')}
				selectedKey={selectedKey}
				triggerClassName={triggerClassName}
				width={125}
			>
				{(item) => (
					<Option key={item.key} textValue={item.name}>
						<Label
							displayType={mapLabelToLabelDisplayType[item.name]}
						>
							{item.name}
						</Label>
					</Option>
				)}
			</Picker>
		</div>
	);
}
