/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useContext,
	useState,
} from 'react';

type ItemId = string | null;

const INITIAL_STATE: {
	editedNodeId: ItemId;
	openShortcutModal: boolean;
	setEditedNodeId: Dispatch<SetStateAction<ItemId>>;
	setOpenShortcutModal: Dispatch<SetStateAction<boolean>>;
} = {
	editedNodeId: null,
	openShortcutModal: false,
	setEditedNodeId: () => null,
	setOpenShortcutModal: () => false,
};

const ShortcutContext = React.createContext(INITIAL_STATE);

function ShortcutContextProvider({children}: {children: ReactNode}) {
	const [editedNodeId, setEditedNodeId] = useState<ItemId>(null);
	const [openShortcutModal, setOpenShortcutModal] = useState<boolean>(false);

	return (
		<ShortcutContext.Provider
			value={{
				editedNodeId,
				openShortcutModal,
				setEditedNodeId,
				setOpenShortcutModal,
			}}
		>
			{children}
		</ShortcutContext.Provider>
	);
}

function useEditedNodeId() {
	return useContext(ShortcutContext).editedNodeId;
}

function useSetEditedNodeId() {
	return useContext(ShortcutContext).setEditedNodeId;
}

function useOpenShortcutModal() {
	return useContext(ShortcutContext).openShortcutModal;
}

function useSetOpenShortcutModal() {
	return useContext(ShortcutContext).setOpenShortcutModal;
}

export {
	ShortcutContextProvider,
	useEditedNodeId,
	useOpenShortcutModal,
	useSetEditedNodeId,
	useSetOpenShortcutModal,
};
