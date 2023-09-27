/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useMovementTarget} from '../contexts/KeyboardMovementContext';
import {useSelectorRef} from '../contexts/StoreContext';
import getDropContainerId from './drag_and_drop/getDropContainerId';
import {useDropTargetData} from './drag_and_drop/useDragAndDrop';

export default function useDropContainerId() {
	const {item: dropItem, position: dropPosition} = useDropTargetData();
	const {
		itemId: keyboardMovementItemId,
		position: keyboardMovementPosition,
	} = useMovementTarget();

	const layoutDataRef = useSelectorRef((state) => state.layoutData);
	const keyboardMovementItem =
		layoutDataRef.current.items[keyboardMovementItemId];

	if (!dropItem && !keyboardMovementItem) {
		return null;
	}

	const dropContainerId = getDropContainerId(
		layoutDataRef.current,
		dropItem || keyboardMovementItem,
		dropPosition || keyboardMovementPosition
	);

	return dropContainerId;
}
