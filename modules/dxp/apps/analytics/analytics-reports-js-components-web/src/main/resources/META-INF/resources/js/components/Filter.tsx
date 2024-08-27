/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {Text} from '@clayui/core';
import ClayDropdown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React from 'react';

type Item = {
	description?: string;
	label: string;
	value: string;
};

interface IFilterProps extends React.HTMLAttributes<HTMLElement> {
	active: string;
	items: Item[];
	onSelectItem: (item: Item) => void;
	triggerLabel: string;
}

const Filter: React.FC<IFilterProps> = ({
	active,
	className,
	items,
	onSelectItem,
	triggerLabel,
}) => {
	return (
		<ClayDropdown
			className={className}
			closeOnClick
			hasLeftSymbols
			trigger={
				<ClayButton borderless displayType="secondary" size="xs">
					<ClayIcon className="mr-2" symbol="users" />

					{triggerLabel}

					<ClayIcon className="ml-2" symbol="caret-bottom" />
				</ClayButton>
			}
		>
			{items.map(({description, label, value}) => (
				<ClayDropdown.Item
					active={value === active}
					key={value}
					onClick={() => onSelectItem({label, value})}
					symbolLeft={value === active ? 'check' : ''}
				>
					{description ? (
						<div>
							<Text size={4}>{label}</Text>
						</div>
					) : (
						label
					)}

					{description && (
						<Text size={1}>
							<span className="text-uppercase">
								{description}
							</span>
						</Text>
					)}
				</ClayDropdown.Item>
			))}
		</ClayDropdown>
	);
};

export default Filter;
