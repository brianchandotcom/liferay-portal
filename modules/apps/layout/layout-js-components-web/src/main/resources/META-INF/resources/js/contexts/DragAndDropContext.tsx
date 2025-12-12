/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useContext,
	useState,
} from 'react';

import {DropPosition} from '../hooks/drag_and_drop/useDragAndDrop';

type KeyboardItem = {
	index: number | null;
	name: string;
	position: DropPosition;
};

const INITIAL_KEYBOARD_ITEM = {
	index: null,
	name: '',
	position: '' as DropPosition,
};

const DragAndDropContext = React.createContext<{
	keyboardItem: KeyboardItem;
	setKeyboardItem: Dispatch<SetStateAction<KeyboardItem>>;
}>({
	keyboardItem: INITIAL_KEYBOARD_ITEM,
	setKeyboardItem: () => INITIAL_KEYBOARD_ITEM,
});

function DragAndDropContextProvider({children}: {children: ReactNode}) {
	const [keyboardItem, setKeyboardItem] = useState<KeyboardItem>(
		INITIAL_KEYBOARD_ITEM
	);

	return (
		<DragAndDropContext.Provider
			value={{
				keyboardItem,
				setKeyboardItem,
			}}
		>
			{children}
		</DragAndDropContext.Provider>
	);
}

function useKeyboardItem() {
	return useContext(DragAndDropContext).keyboardItem;
}

function useUpdateKeyboardItem() {
	const {setKeyboardItem} = useContext(DragAndDropContext);

	return useCallback(
		(values: Partial<KeyboardItem>) => {
			setKeyboardItem((item: KeyboardItem) => ({...item, ...values}));
		},
		[setKeyboardItem]
	);
}

export {
	DragAndDropContext,
	DragAndDropContextProvider,
	useKeyboardItem,
	useUpdateKeyboardItem,
};
