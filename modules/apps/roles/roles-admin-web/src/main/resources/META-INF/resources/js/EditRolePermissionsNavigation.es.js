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
import {cancelDebounce, debounce} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

export default function GroupLabels({items, portletNamespace}) {
	const [activeItemResourceURL, setActiveItemResourceURL] = useState();
	const [filterQuery, setFilterQuery] = useState('');

	const handleItemClick = useCallback(
		(item, portletNamespace) => {
			const method = `${portletNamespace}loadContent`;

			window[method](item.resourceURL);

			setActiveItemResourceURL(item.resourceURL);
		},
		[setActiveItemResourceURL]
	);

	const processItems = (items) => {
		const processedItems = [];
		let hasActiveChild = false;

		items.forEach((item) => {
			const {label, resourceURL} = item;
			const normalizedFilterQuery = filterQuery.trim().toLowerCase();

			const hasFilterQuery = normalizedFilterQuery !== '';

			const showItem = !hasFilterQuery ||
				item.items ||
				item.ignoreFilter ||
				label.toLowerCase().includes(normalizedFilterQuery);

			if (showItem) {
				const active = activeItemResourceURL
					? activeItemResourceURL === resourceURL
					: item.active;

				if (active) {
					hasActiveChild = true;
				}

				const processedItem = {
					active,
					className: item.className,
					initialExpanded: hasFilterQuery || !!item.initialExpanded,
					label,
				};

				if (item.items) {
					const [
						processedChildItems,
						hasActiveGrandchild,
					] = processItems(item.items);

					if (!processedChildItems.length) {
						return;
					}

					if (hasActiveGrandchild) {
						hasActiveChild = true;

						processedItem.initialExpanded = true;
					}

					processedItem.items = processedChildItems;
				}

				if (resourceURL) {
					processedItem.onClick = () =>
						handleItemClick(item, portletNamespace);
				}

				processedItems.push(processedItem);
			}
		});

		return [processedItems, hasActiveChild];
	};

	const [processedItems] = processItems(items);

	const debouncedSetFilterQuery = debounce((event) => {
		setFilterQuery(event.target.value);
	}, 200);

	const inputHandler = (event) => {
		event.persist();

		debouncedSetFilterQuery(event);
	};

	useEffect(() => {
		return () => {
			cancelDebounce(debouncedSetFilterQuery);
		};
	}, [debouncedSetFilterQuery]);

	return (
		<>
			<div className="menubar-vertical-expand-lg">
				<ClayInput
					autoComplete="off"
					className="bg-white mb-4"
					onChange={inputHandler}
					placeholder={Liferay.Language.get('search')}
					type="text"
				/>
			</div>

			{processedItems.length > 1 ? (
				<ClayVerticalNav items={processedItems} large />
			) : (
				<div className="alert">
					{Liferay.Language.get('there-are-no-results')}
				</div>
			)}
		</>
	);
}