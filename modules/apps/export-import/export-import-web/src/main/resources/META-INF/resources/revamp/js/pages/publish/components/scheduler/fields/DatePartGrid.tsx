/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import classnames from 'classnames';
import React from 'react';

export default function DatePartGrid({
	className,
	getLabel,
	items,
	onToggle,
	selected,
}: {
	className?: string;
	getLabel: (item: number) => string;
	items: number[];
	onToggle: (items: number[]) => void;
	selected: number[];
}) {
	return (
		<div className={classnames('date-part-grid', className)}>
			{items.map((item) => (
				<ClayButton
					aria-pressed={selected.includes(item)}
					displayType={
						selected.includes(item) ? 'primary' : 'secondary'
					}
					key={item}
					onClick={() => onToggle(toggleIn(selected, item))}
				>
					{getLabel(item)}
				</ClayButton>
			))}
		</div>
	);
}

function toggleIn(items: number[], item: number): number[] {
	if (!items.includes(item)) {
		return [...items, item];
	}

	if (items.length === 1) {
		return items;
	}

	return items.filter((selectedItem) => selectedItem !== item);
}
