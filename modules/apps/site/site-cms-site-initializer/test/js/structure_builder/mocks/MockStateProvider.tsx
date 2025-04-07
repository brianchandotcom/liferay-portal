/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import {
	Action,
	State,
	StateContext,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/StateContext';
import getUuid from '../../../../src/main/resources/META-INF/resources/js/structure_builder/utils/getUuid';

const DEFAULT_STATE: State = {
	erc: 'default-erc',
	error: null,
	fields: new Map(),
	history: {deletedFields: false},
	id: null,
	invalids: new Map(),
	label: 'untitled-structure',
	name: 'UntitledStructure',
	publishedFields: new Set(),
	selection: [],
	spaces: [],
	status: 'new',
	uuid: getUuid(),
};

export function MockStateProvider({
	children,
	state = DEFAULT_STATE,
	dispatch = jest.fn(),
}: {
	children: ReactNode;
	dispatch?: React.Dispatch<Action>;
	state?: Partial<State>;
}) {
	return (
		<StateContext.Provider
			value={{dispatch, state: {...DEFAULT_STATE, ...state}}}
		>
			{children}
		</StateContext.Provider>
	);
}
