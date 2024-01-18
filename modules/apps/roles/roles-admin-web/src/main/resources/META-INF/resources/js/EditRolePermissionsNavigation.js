/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
