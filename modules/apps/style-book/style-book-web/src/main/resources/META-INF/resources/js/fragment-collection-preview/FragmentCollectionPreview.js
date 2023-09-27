/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useMemo} from 'react';

import {FragmentPreview} from './FragmentPreview';

import '../../css/FragmentCollectionPreview.scss';

const FRAGMENT_COLLECTION_BLOCKLIST = {
	BASIC_COMPONENT: [
		'BASIC_COMPONENT-external-video',
		'BASIC_COMPONENT-html',
		'BASIC_COMPONENT-separator',
		'BASIC_COMPONENT-spacer',
		'BASIC_COMPONENT-video',
	],
};

export default function FragmentCollectionPreview({
	fragmentCollectionKey,
	fragments,
	namespace,
}) {
	const filteredFragments = useMemo(() => {
		const blocklist = FRAGMENT_COLLECTION_BLOCKLIST[fragmentCollectionKey];

		return blocklist
			? fragments.filter(
					(fragment) => !blocklist.includes(fragment.fragmentEntryKey)
			  )
			: fragments;
	}, [fragmentCollectionKey, fragments]);

	return (
		<>
			{filteredFragments.length ? (
				filteredFragments.map((fragment) => (
					<FragmentPreview
						fragment={fragment}
						key={fragment.fragmentEntryKey}
						namespace={namespace}
					/>
				))
			) : (
				<ClayAlert className="m-3" displayType="info">
					{Liferay.Language.get('there-are-no-fragments')}
				</ClayAlert>
			)}
		</>
	);
}
