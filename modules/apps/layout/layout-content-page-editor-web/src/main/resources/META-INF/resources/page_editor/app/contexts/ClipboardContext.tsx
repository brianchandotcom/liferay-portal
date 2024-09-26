/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useCallback, useContext, useState} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectorRef} from './StoreContext';

type ItemIds = [];

const INITIAL_STATE: {
	copiedItemIds: ItemIds;
	setCopiedItemIds: (itemIds: ItemIds) => void;
} = {
	copiedItemIds: [],
	setCopiedItemIds: () => [],
};

const ClipboardContext = React.createContext(INITIAL_STATE);

function ClipboardContextProvider({children}: {children: ReactNode}) {
	const [copiedItemIds, setCopiedItemIds] = useState<ItemIds>([]);

	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const updateCopiedItemIds = useCallback(
		(itemIds: ItemIds) => {
			const nextItemIds: ItemIds = [];

			for (const itemId of itemIds) {
				const item = layoutDataRef.current?.items[itemId];

				if (!item) {
					continue;
				}

				if (
					item.type !== LAYOUT_DATA_ITEM_TYPES.formStep &&
					item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
					item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
					item.type !== LAYOUT_DATA_ITEM_TYPES.root
				) {
					nextItemIds.push(itemId);
				}

				setCopiedItemIds(nextItemIds);
			}
		},
		[layoutDataRef]
	);

	return (
		<ClipboardContext.Provider
			value={{
				copiedItemIds,
				setCopiedItemIds: updateCopiedItemIds,
			}}
		>
			{children}
		</ClipboardContext.Provider>
	);
}

function useCopiedItemIds() {
	return useContext(ClipboardContext).copiedItemIds;
}

function useSetCopiedItemIds() {
	return useContext(ClipboardContext).setCopiedItemIds;
}

export {ClipboardContextProvider, useCopiedItemIds, useSetCopiedItemIds};
