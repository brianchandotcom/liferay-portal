/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLabel from '@clayui/label';
import {sub} from 'frontend-js-web';
import React from 'react';

export interface Item {
	label: string;
	value: string;
}

interface Props {
	items: Item[];
	onItemsChange: (nextItems: Item[]) => void;
}

export function LabelList({items, onItemsChange}: Props) {
	const onRemoveButtonClick = (item: Item) => {
		onItemsChange(items.filter(({value}) => value !== item.value));
	};

	return (
		<ul className="form-control form-control-tag-group list-unstyled">
			{items.map((item) => (
				<li key={item.value}>
					<ClayLabel
						closeButtonProps={{
							'aria-label': sub(
								Liferay.Language.get('remove-x'),
								item.label
							),
							'onClick': () => onRemoveButtonClick(item),
						}}
					>
						{item.label}
					</ClayLabel>
				</li>
			))}
		</ul>
	);
}
