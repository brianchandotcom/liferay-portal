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

import {setIn} from '../utils/setIn';

type ItemConfig = Record<string, string | boolean>;

type Config = Record<string, ItemConfig>;

const INITIAL_STATE: {
	config: Config;
	setConfig: Dispatch<SetStateAction<Config>>;
} = {
	config: {},
	setConfig: () => {},
};

const LocalConfigContext = React.createContext(INITIAL_STATE);

function LocalConfigContextProvider({children}: {children: ReactNode}) {
	const [config, setConfig] = useState({});

	return (
		<LocalConfigContext.Provider
			value={{
				config,
				setConfig,
			}}
		>
			{children}
		</LocalConfigContext.Provider>
	);
}

function useItemLocalConfig(itemId: string) {
	const {config} = useContext(LocalConfigContext);

	return config[itemId] || {};
}

const useUpdateItemLocalConfig = () => {
	const {setConfig} = useContext(LocalConfigContext);

	return useCallback(
		(itemId: string, itemConfig: ItemConfig) =>
			setConfig((previousConfig) => {
				const currentItemConfig = previousConfig[itemId] || {};

				return setIn(previousConfig, [itemId], {
					...currentItemConfig,
					...itemConfig,
				});
			}),
		[setConfig]
	);
};

export {
	LocalConfigContextProvider,
	useItemLocalConfig,
	useUpdateItemLocalConfig,
};
