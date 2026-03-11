/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Atom, Selector, State} from '@liferay/frontend-js-state-web';

import {IFDSState} from './types';

const getFDSAtom = ({
	atom,
	atomKey,
	fdsName,
}: {
	atom?: Atom<IFDSState> | undefined;
	atomKey?: string;
	fdsName?: string;
}): Atom<IFDSState> | Selector<IFDSState> => {
	if (!atom && !atomKey && !fdsName) {
		throw new Error(
			'getFDSAtom requires at least one of the following parameters: atom, atomKey, fdsName'
		);
	}

	if (atom) {
		return atom;
	}

	const fdsStateKey = atomKey ?? `${fdsName}_fdsState`;

	const fdsAtom: Atom<IFDSState> | null =
		State.__unsafe__.getAtomOrSelectorKey(
			fdsStateKey
		) as Atom<IFDSState> | null;

	return (
		fdsAtom ||
		State.atom<IFDSState>(fdsStateKey, {
			filters: [],
			search: {query: ''},
		})
	);
};

export {getFDSAtom};
