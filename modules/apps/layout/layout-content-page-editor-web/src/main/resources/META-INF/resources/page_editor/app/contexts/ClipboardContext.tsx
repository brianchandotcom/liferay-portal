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
	copiedNodeIds: ItemIds;
	setCopiedNodeIds: Dispatch<SetStateAction<ItemIds>>;
} = {
	copiedNodeIds: [],
	setCopiedNodeIds: () => [],
};

const ClipboardContext = React.createContext(INITIAL_STATE);

function ClipboardContextProvider({children}: {children: ReactNode}) {
	const [copiedNodeIds, setCopiedNodeIds] = useState<ItemIds>([]);

	return (
		<ClipboardContext.Provider
			value={{
				copiedNodeIds,
				setCopiedNodeIds,
			}}
		>
			{children}
		</ClipboardContext.Provider>
	);
}

function useCopiedNodeIds() {
	return useContext(ClipboardContext).copiedNodeIds;
}

function useSetCopiedNodeIds() {
	return useContext(ClipboardContext).setCopiedNodeIds;
}

export {ClipboardContextProvider, useCopiedNodeIds, useSetCopiedNodeIds};
