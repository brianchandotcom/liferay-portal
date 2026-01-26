/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {IFDSState} from '@liferay/frontend-data-set-web';
import {Atom, Selector, State} from '@liferay/frontend-js-state-web';

const getAtom = ({
	key,
}: {
	key: string;
}): Atom<IFDSState> | Selector<IFDSState> => {
	const fallbackAtom: Atom<IFDSState> | null =
		State.__unsafe__.getAtomOrSelectorKey(key) as Atom<IFDSState> | null;

	return (
		fallbackAtom ||
		State.atom<IFDSState>(key, {
			filters: [],
			search: {query: ''},
		})
	);
};

export {getAtom};
