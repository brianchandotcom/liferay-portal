/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useCallback, useEffect, useState} from 'react';

import {useActiveItemIds} from '../../../../../app/contexts/ControlsContext';
import {useMovementTarget} from '../../../../../app/contexts/KeyboardMovementContext';
import {useSelector} from '../../../../../app/contexts/StoreContext';
import {deepEqual} from '../../../../../app/utils/checkDeepEqual';
import StructureTreeContent from './StructureTreeContent';

export default function StructureTree() {
	const activeItemIds = useActiveItemIds();
	const [expandedKeys, setExpandedKeys] = useState([]);
	const {itemId: keyboardMovementTargetId} = useMovementTarget();
	const layoutData = useSelector((state) => state.layoutData);
	const masterLayoutData = useSelector(
		(state) => state.masterLayout?.masterLayoutData
	);

	const previousActiveItemIds = usePrevious(activeItemIds);

	const getAncestorsIds = useCallback(
		(layoutDataItem, data) => {
			if (!layoutDataItem.parentId) {
				const itemInMasterLayout =
					masterLayoutData?.items[layoutDataItem.itemId];
				if (
					!itemInMasterLayout &&
					masterLayoutData?.rootItems?.dropZone
				) {
					const dropZoneItem =
						masterLayoutData.items[
							masterLayoutData.rootItems.dropZone
						];

					return [
						...[layoutDataItem.itemId],
						...getAncestorsIds(
							masterLayoutData.items[dropZoneItem.parentId],
							masterLayoutData
						),
					];
				}
				else {
					return [layoutDataItem.itemId];
				}
			}

			return [
				...[layoutDataItem.itemId],
				...getAncestorsIds(data.items[layoutDataItem.parentId], data),
			];
		},
		[masterLayoutData]
	);

	useEffect(() => {
		if (!activeItemIds.length) {
			return;
		}

		const expandedKeys = [];

		if (deepEqual(previousActiveItemIds || [], activeItemIds)) {
			return;
		}

		let layoutDataActiveItem = null;

		activeItemIds.forEach((itemId) => {
			if (layoutData.items[itemId]) {
				layoutDataActiveItem = layoutData.items[itemId];
			}

			if (!layoutDataActiveItem) {
				return;
			}

			expandedKeys.push(
				...getAncestorsIds(layoutDataActiveItem, layoutData)
			);
		});

		setExpandedKeys((previousExpanedKeys) => {
			return [...new Set([...previousExpanedKeys, ...expandedKeys])];
		});
	}, [activeItemIds, getAncestorsIds, layoutData, previousActiveItemIds]);

	useEffect(() => {
		if (keyboardMovementTargetId) {
			const layoutDataTargetItem =
				layoutData.items[keyboardMovementTargetId];

			if (!layoutDataTargetItem) {
				return;
			}

			setExpandedKeys((previousExpanedKeys) => [
				...new Set([
					...previousExpanedKeys,
					...getAncestorsIds(layoutDataTargetItem, layoutData),
				]),
			]);
		}
	}, [getAncestorsIds, keyboardMovementTargetId, layoutData]);

	return (
		<StructureTreeContent
			expandedKeys={expandedKeys}
			setExpandedKeys={setExpandedKeys}
		/>
	);
}
