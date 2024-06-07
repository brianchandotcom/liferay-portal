/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ScreenReaderAnnouncer} from '@liferay/layout-js-components-web';
import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	createContext,
	useCallback,
	useContext,
	useRef,
	useState,
} from 'react';

import {PropertyType} from '../utils/constants';

export const POSITIONS = {
	bottom: 'bottom',
	middle: 'middle',
	top: 'top',
} as const;

export type Position = (typeof POSITIONS)[keyof typeof POSITIONS];

export type Source = {
	defaultValue: string;
	groupId?: string;
	icon: string;
	index?: number;
	label: string;
	propertyKey: string;
	propertyName: string;
	type: PropertyType;
};

export type Target = {
	groupId: string;
	index: number;
	position: Position;
};

interface Context {
	sendMessage: (message: string) => void;
	setSource: Dispatch<SetStateAction<Source | null>>;
	setTarget: Dispatch<SetStateAction<Target | null>>;
	source: Source | null;
	target: Target | null;
}

const KeyboardMovementContext = createContext<Context>({
	sendMessage: () => {},
	setSource: () => {},
	setTarget: () => {},
	source: null,
	target: null,
});

type Props = {
	children: ReactNode;
};

function KeyboardMovementContextProvider({children}: Props) {
	const [source, setSource] = useState<Context['source']>(null);
	const [target, setTarget] = useState<Context['target']>(null);

	const screenReaderAnnouncerRef = useRef<any>();

	const sendMessage = useCallback((message) => {
		const ref = screenReaderAnnouncerRef;

		if (ref.current) {
			ref.current?.sendMessage(message);
		}
	}, []);

	return (
		<KeyboardMovementContext.Provider
			value={{
				sendMessage,
				setSource,
				setTarget,
				source,
				target,
			}}
		>
			<ScreenReaderAnnouncer
				aria-live="assertive"
				ref={screenReaderAnnouncerRef}
			/>

			{children}
		</KeyboardMovementContext.Provider>
	);
}

function useDisableKeyboardMovement() {
	const {setSource, setTarget} = useContext(KeyboardMovementContext);

	return useCallback(() => {
		setSource(null);

		setTarget(null);
	}, [setSource, setTarget]);
}

function useMovementSource() {
	return useContext(KeyboardMovementContext).source;
}

function useMovementTarget() {
	return useContext(KeyboardMovementContext).target;
}
function useSendMovementMessage() {
	return useContext(KeyboardMovementContext).sendMessage;
}

function useSetMovementSource() {
	return useContext(KeyboardMovementContext).setSource;
}

function useSetMovementTarget() {
	return useContext(KeyboardMovementContext).setTarget;
}

export {
	KeyboardMovementContextProvider,
	useDisableKeyboardMovement,
	useMovementSource,
	useMovementTarget,
	useSendMovementMessage,
	useSetMovementSource,
	useSetMovementTarget,
};
