/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
interface Error {
	error: string;
	value: string;
}
declare type State = Record<string, Record<string, Error>>;
interface Props {
	children: ReactNode;
	initialState?: State;
}
export declare function StyleErrorsContextProvider({
	children,
	initialState,
}: Props): JSX.Element;
export declare function useDeleteStyleError(): (
	fieldName: string,
	itemId?: string
) => void;
export declare function useHasStyleErrors(): boolean;
export declare function useSetStyleError(): (
	fieldName: any,
	value: any,
	itemId?: any
) => void;
export declare function useStyleErrors(): State;
export {};
