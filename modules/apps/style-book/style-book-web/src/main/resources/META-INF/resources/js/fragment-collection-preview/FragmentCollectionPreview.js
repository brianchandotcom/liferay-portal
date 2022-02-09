/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
