/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

import {
	State,
	StateContext,
} from '../../../../src/main/resources/META-INF/resources/js/structure_builder/contexts/PicklistBuilderContext';

export const DEFAULT_STATE: State = {
	erc: 'picklistERC',
	id: 1,
	name: {en_US: 'Picklist Name'},
	setErc: jest.fn(),
	setId: jest.fn(),
	setName: jest.fn(),
};

export function MockStateProvider({
	children,
	state = DEFAULT_STATE,
}: {
	children: ReactNode;
	state?: Partial<State>;
}) {
	return (
		<StateContext.Provider value={{...DEFAULT_STATE, ...state}}>
			{children}
		</StateContext.Provider>
	);
}
