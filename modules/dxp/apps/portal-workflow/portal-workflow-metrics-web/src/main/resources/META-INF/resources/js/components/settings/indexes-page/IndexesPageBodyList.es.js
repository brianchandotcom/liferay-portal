/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import ClayProgressBar from '@clayui/progress-bar';
import React from 'react';

function Item({
	btnLabel,
	disabled,
	handleReindex,
	item: {completionPercentage = 0, key, label, reindexing},
}) {
	return (
		<ClayList.Item
			className="autofit-row-center reindex-action"
			flex
			key={key}
		>
			<ClayList.ItemField expand>{label}</ClayList.ItemField>

			<ClayList.ItemField>
				{reindexing ? (
					<ClayProgressBar value={completionPercentage} />
				) : (
					<ClayButton
						disabled={disabled}
						displayType="secondary"
						onClick={() => handleReindex(key, label)}
						small
					>
						{btnLabel}
					</ClayButton>
				)}
			</ClayList.ItemField>
		</ClayList.Item>
	);
}

function List({
	indexes = [],
	getReindexStatus,
	isReindexing,
	label,
	...otherProps
}) {
	const items = indexes.map((item) => {
		const status = getReindexStatus(item.key);

		return {
			...item,
			reindexing: isReindexing(item.key),
			...status,
		};
	});

	return (
		<ClayList>
			<ClayList.Header>{label}</ClayList.Header>

			{items.map((item, index) => (
				<List.Item
					btnLabel={
						index === 0
							? Liferay.Language.get('reindex-all')
							: Liferay.Language.get('reindex')
					}
					item={item}
					key={index}
					{...otherProps}
				/>
			))}
		</ClayList>
	);
}

List.Item = Item;

export default List;
