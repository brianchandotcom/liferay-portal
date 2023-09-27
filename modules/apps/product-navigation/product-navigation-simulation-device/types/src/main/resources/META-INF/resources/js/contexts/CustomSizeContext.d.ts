/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const useSetCustomWidth: () => React.Dispatch<
	React.SetStateAction<number>
>;
declare const useSetCustomHeight: () => React.Dispatch<
	React.SetStateAction<number>
>;
declare const useCustomSize: () => {
	height: number;
	width: number;
};
declare function CustomSizeContextProvider({
	children,
}: {
	children: React.ReactNode;
}): JSX.Element;
export {
	CustomSizeContextProvider,
	useSetCustomHeight,
	useSetCustomWidth,
	useCustomSize,
};
