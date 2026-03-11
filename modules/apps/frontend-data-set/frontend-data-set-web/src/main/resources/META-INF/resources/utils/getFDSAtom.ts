/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Atom, Selector, State} from '@liferay/frontend-js-state-web';

import {IFDSState} from './types';

const getFDSAtom = ({
	fdsName,
}: {
	fdsName: string;
}): Atom<IFDSState> | Selector<IFDSState> => {
	const fdsStateKey = `${fdsName}_fdsState`;

	const fallbackAtom: Atom<IFDSState> | null =
		State.__unsafe__.getAtomOrSelectorKey(
			fdsStateKey
		) as Atom<IFDSState> | null;

	return (
		fallbackAtom ||
		State.atom<IFDSState>(fdsStateKey, {
			filters: [],
			search: {query: ''},
		})
	);
};

export {getFDSAtom};
