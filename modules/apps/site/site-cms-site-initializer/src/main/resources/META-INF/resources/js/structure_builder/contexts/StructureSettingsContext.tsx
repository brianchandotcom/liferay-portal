/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {Dispatch, ReactNode, SetStateAction, useState} from 'react';

const DEFAULT_NAME = Liferay.Language.get('untitled-structure');

type Error = string | null;

const StructureSettingsContext = React.createContext<{
	error: Error;
	name: string;
	setError: Dispatch<SetStateAction<Error>>;
	setName: Dispatch<SetStateAction<string>>;
}>({
	error: null,
	name: DEFAULT_NAME,
	setError: () => {},
	setName: () => {},
});

function StructureSettingsContextProvider({children}: {children: ReactNode}) {
	const [name, setName] = useState(DEFAULT_NAME);
	const [error, setError] = useState<Error>(null);

	return (
		<StructureSettingsContext.Provider
			value={{
				error,
				name,
				setError,
				setName,
			}}
		>
			{children}
		</StructureSettingsContext.Provider>
	);
}

export {StructureSettingsContext, StructureSettingsContextProvider};
