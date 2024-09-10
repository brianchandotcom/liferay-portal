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

type ItemIds = [];

const INITIAL_STATE: {
	copiedItemIds: ItemIds;
	setCopiedItemIds: Dispatch<SetStateAction<ItemIds>>;
} = {
	copiedItemIds: [],
	setCopiedItemIds: () => [],
};

const ClipboardContext = React.createContext(INITIAL_STATE);

function ClipboardContextProvider({children}: {children: ReactNode}) {
	const [copiedItemIds, setCopiedItemIds] = useState<ItemIds>([]);

	return (
		<ClipboardContext.Provider
			value={{
				copiedItemIds,
				setCopiedItemIds,
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
