/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export declare const initialState: {
	isPanelStateOpen: boolean;
	layoutReportsDataURL: string;
	learnResources: {};
};
export declare const ConstantsContext: React.Context<{
	isPanelStateOpen: boolean;
	layoutReportsDataURL: string;
	learnResources: {};
}>;
interface Props {
	children: React.ReactNode;
	constants: {
		isPanelStateOpen: boolean;
		layoutReportsDataURL: string;
		learnResources: object;
	};
}
export declare function ConstantsContextProvider({
	children,
	constants,
}: Props): JSX.Element;
export {};
