/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {ScreenSize} from '../constants/sizes';
declare const useSetCustomSize: () => React.Dispatch<
	React.SetStateAction<ScreenSize>
>;
declare const useCustomSize: () => ScreenSize;
declare function CustomSizeContextProvider({
	children,
}: {
	children: React.ReactNode;
}): JSX.Element;
export {CustomSizeContextProvider, useCustomSize, useSetCustomSize};
