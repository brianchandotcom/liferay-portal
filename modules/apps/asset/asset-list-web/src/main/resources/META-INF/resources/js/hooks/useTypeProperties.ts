/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useLiferayState} from '@liferay/frontend-js-state-web/react';
import {deepClone} from 'frontend-js-web';
import {useMemo} from 'react';

import {propertiesAtom} from '../atoms/propertiesAtom';

import type {FilterPropertyGroup} from '../components/CollectionFilterBuilder/types';

/**
 * Subscribes to the shared properties atom and returns its current value,
 * falling back to `initialValue` until the atom has been seeded.
 *
 * Atom values from `@liferay/frontend-js-state-web` are deeply frozen, but
 * ClayUI components (e.g. `<Picker>` via `useCollection`) mutate items to
 * attach internal keys. The returned value is cloned so consumers can hand
 * it off to ClayUI without hitting "object is not extensible" errors.
 */
export default function useTypeProperties(
	initialValue: FilterPropertyGroup[] = []
): FilterPropertyGroup[] {
	const [atomProperties] = useLiferayState(propertiesAtom);

	const cloned = useMemo(
		() => deepClone(atomProperties) as FilterPropertyGroup[],
		[atomProperties]
	);

	return cloned.length ? cloned : initialValue;
}
