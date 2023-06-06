/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {ClayInput} from '@clayui/form';
import {ClayVerticalNav} from '@clayui/nav';
import {useDebounce} from '@clayui/shared';
import React, {useCallback, useEffect, useMemo, useState} from 'react';

export default function EditRolePermissionsNavigation({
	items,
	portletNamespace,
}) {
	const [activeItemId, setActiveItemId] = useState();
	const [expandedKeys, setExpandedKeys] = useState(new Set([]));
	const [filterQuery, setFilterQuery] = useState('');

	const debouncedFilterQuery = useDebounce(filterQuery, 200);

	const handleItemClick = useCallback(
		(item) => {
			const method = `${portletNamespace}loadContent`;

			window[method](item.resourceURL);

			setActiveItemId(item.id);
		},
		[portletNamespace, setActiveItemId]
	);

	const processItems = useCallback(
		(items) => {
			const processedExpandedKeys = new Set([]);
			const processedItems = [];
			let hasActiveChild = false;

			items.forEach((item) => {
				const {
					className,
					id,
					items: childItems,
					label,
					resourceURL,
				} = item;
				const normalizedFilterQuery = debouncedFilterQuery
					.trim()
					.toLowerCase();

				const hasFilterQuery = normalizedFilterQuery !== '';

				const showItem =
					!hasFilterQuery ||
					item.items ||
					item.ignoreFilter ||
					label.toLowerCase().includes(normalizedFilterQuery);

				if (showItem) {
					const processedItem = {
						className,
						id,
						label,
						resourceURL,
					};

					const active = activeItemId
						? activeItemId === id
						: item.active;

					if (active) {
						hasActiveChild = true;

						processedItem.active = true;
					}

					if (childItems) {
						const [
							processedChildItems,
							childExpandedKeys,
							hasActiveGrandchild,
						] = processItems(childItems);

						if (!processedChildItems.length) {
							return;
						}

						processedItem.items = processedChildItems;

						if (
							hasFilterQuery ||
							(!hasFilterQuery && hasActiveGrandchild) ||
							item.initialExpanded
						) {
							processedExpandedKeys.add(id);
						}

						childExpandedKeys.forEach((key) =>
							processedExpandedKeys.add(key)
						);

						if (hasActiveGrandchild) {
							hasActiveChild = true;
						}
					}

					processedItems.push(processedItem);
				}
			});

			return [processedItems, processedExpandedKeys, hasActiveChild];
		},
		[activeItemId, debouncedFilterQuery]
	);

	const [processedItems, processedExpandedKeys] = useMemo(
		() => processItems(items),
		[processItems, items]
	);

	useEffect(
		() => setExpandedKeys(processedExpandedKeys),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[debouncedFilterQuery]
	);

	return (
		<>
			<div className="menubar-vertical-expand-lg">
				<ClayInput
					autoComplete="off"
					className="bg-white mb-4"
					onChange={(event) => setFilterQuery(event.target.value)}
					placeholder={Liferay.Language.get('search')}
					type="text"
					value={filterQuery}
				/>
			</div>

			{processedItems.length > 1 ? (
				<ClayVerticalNav
					expandedKeys={expandedKeys}
					items={processedItems}
					large
					onExpandedChange={setExpandedKeys}
				>
					{(item) => (
						<ClayVerticalNav.Item
							active={item.active}
							className={item.className}
							items={item.items}
							key={item.id}
							onClick={
								item.resourceURL
									? () => handleItemClick(item)
									: undefined
							}
						>
							{item.label}
						</ClayVerticalNav.Item>
					)}
				</ClayVerticalNav>
			) : (
				<div className="alert">
					{Liferay.Language.get('there-are-no-results')}
				</div>
			)}
		</>
	);
}
