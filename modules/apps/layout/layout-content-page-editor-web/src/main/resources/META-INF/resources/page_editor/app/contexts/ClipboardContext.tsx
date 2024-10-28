/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useCallback, useContext, useState} from 'react';

import {LayoutDataItem} from '../../types/layout_data/LayoutData';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {useSelectorRef} from './StoreContext';

type Clipboard = Array<LayoutDataItem['itemId']>;

const INITIAL_STATE: {
	clipboard: Clipboard;
	setClipboard: (itemIds: Clipboard) => void;
} = {
	clipboard: [],
	setClipboard: () => [],
};

const ClipboardContext = React.createContext(INITIAL_STATE);

function ClipboardContextProvider({children}: {children: ReactNode}) {
	const [clipboard, setClipboard] = useState<Clipboard>([]);

	const layoutDataRef = useSelectorRef((state) => state.layoutData);

	const updateClipboard = useCallback(
		(itemIds: Clipboard) => {
			const nextItemIds = [];

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

				setClipboard(nextItemIds);
			}
		},
		[layoutDataRef]
	);

	return (
		<ClipboardContext.Provider
			value={{
				clipboard,
				setClipboard: updateClipboard,
			}}
		>
			{children}
		</ClipboardContext.Provider>
	);
}

function useClipboard() {
	return useContext(ClipboardContext).clipboard;
}

function useSetClipboard() {
	return useContext(ClipboardContext).setClipboard;
}

export {ClipboardContextProvider, useClipboard, useSetClipboard};
