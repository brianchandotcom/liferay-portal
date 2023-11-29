/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';
import {PropertyType} from '../utils/constants';
export declare const POSITIONS: {
	readonly bottom: 'bottom';
	readonly middle: 'middle';
	readonly top: 'top';
};
export declare type Position = typeof POSITIONS[keyof typeof POSITIONS];
export declare type Source = {
	defaultValue: string;
	groupId?: string;
	icon: string;
	index?: number;
	label: string;
	propertyKey: string;
	propertyName: string;
	type: PropertyType;
};
export declare type Target = {
	groupId: string;
	index: number;
	position: Position;
};
declare type Props = {
	children: ReactNode;
};
declare function KeyboardMovementContextProvider({
	children,
}: Props): JSX.Element;
declare function useDisableKeyboardMovement(): () => void;
declare function useMovementSource(): Source | null;
declare function useMovementTarget(): Target | null;
declare function useSendMovementMessage(): (message: string) => void;
declare function useSetMovementSource(): React.Dispatch<
	React.SetStateAction<Source | null>
>;
declare function useSetMovementTarget(): React.Dispatch<
	React.SetStateAction<Target | null>
>;
export {
	KeyboardMovementContextProvider,
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSendMovementMessage,
	useSetMovementSource,
	useSetMovementTarget,
};
