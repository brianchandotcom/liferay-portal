/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
declare type ItemConfig = Record<string, string | boolean>;
declare function LocalConfigContextProvider({
	children,
}: {
	children: ReactNode;
}): JSX.Element;
declare function useItemLocalConfig(itemId: string): ItemConfig;
declare const useUpdateItemLocalConfig: () => (
	itemId: string,
	itemConfig: ItemConfig
) => void;
export {
	LocalConfigContextProvider,
	useItemLocalConfig,
	useUpdateItemLocalConfig,
};
