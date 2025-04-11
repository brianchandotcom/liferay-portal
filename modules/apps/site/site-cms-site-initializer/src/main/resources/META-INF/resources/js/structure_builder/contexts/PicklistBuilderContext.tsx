/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	createContext,
	useContext,
	useState,
} from 'react';

import {Picklist} from '../types/Picklist';
import getRandomId from '../utils/getRandomId';
import normalizeI18n from '../utils/normalizeI18n';

const noop = () => null;

const DEFAULT_PICKLIST_NAME = Liferay.Language.get('untitled-picklist');

const INITIAL_STATE = {
	erc: getRandomId(),
	id: null,
	name: {
		[Liferay.ThemeDisplay.getDefaultLanguageId()]: DEFAULT_PICKLIST_NAME,
	},
	options: new Map(),
	setErc: noop,
	setId: noop,
	setName: noop,
	setOptions: noop,
};

export type Option = {
	erc: string;
	key: string;
	name: Liferay.Language.LocalizedValue<string>;
};

export type Options = Map<string, Partial<Option>>;

export type State = {
	erc: string;
	id: number | null;
	name: Liferay.Language.LocalizedValue<string>;
	options: Options;
	setErc: Dispatch<SetStateAction<string>>;
	setId: Dispatch<SetStateAction<number | null>>;
	setName: Dispatch<SetStateAction<Liferay.Language.LocalizedValue<string>>>;
	setOptions: Dispatch<SetStateAction<Options>>;
};

const StateContext = createContext<State>(INITIAL_STATE);

export default function StateContextProvider({
	children,
	initialState,
}: {
	children: ReactNode;
	initialState: State;
}) {
	const [erc, setErc] = useState<string>(initialState.erc);
	const [id, setId] = useState<number | null>(initialState.id);
	const [name, setName] = useState<Liferay.Language.LocalizedValue<string>>(
		initialState.name
	);
	const [options, setOptions] = useState<Options>(new Map());

	return (
		<StateContext.Provider
			value={{
				erc,
				id,
				name,
				options,
				setErc,
				setId,
				setName,
				setOptions,
			}}
		>
			{children}
		</StateContext.Provider>
	);
}

const buildState = (picklist: Picklist): State => {
	if (!picklist) {
		return INITIAL_STATE;
	}

	return {
		...INITIAL_STATE,
		erc: picklist.externalReferenceCode,
		id: picklist.id,
		name: normalizeI18n(picklist.name_i18n),
	};
};

const useAddOption = () => {
	const {setOptions} = useContext(StateContext);

	return ({erc, key, name}: Option) =>
		setOptions((options) => {
			options.set(erc, {key, name});

			return options;
		});
};

const useErc = () => useContext(StateContext).erc;

const useId = () => useContext(StateContext).id;

const useName = () => useContext(StateContext).name;

const useSetErc = () => useContext(StateContext).setErc;

const useSetId = () => useContext(StateContext).setId;

const useSetName = () => useContext(StateContext).setName;

const useOptions = () => useContext(StateContext).options;

export {
	buildState,
	INITIAL_STATE,
	StateContext,
	StateContextProvider,
	useAddOption,
	useErc,
	useSetErc,
	useId,
	useOptions,
	useSetId,
	useName,
	useSetName,
};
