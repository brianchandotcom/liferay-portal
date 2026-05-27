/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

/**
 * Stand-in for the staging-taglib PagesTree. It honors the same contract the
 * real component exposes to PageTreeModal: a hidden input named
 * `${portletNamespace}layoutIds` holding the selected ids, plus one checkbox
 * per page whose checked state the modal scrapes to detect the "all" case.
 */
export function MockPagesTree({
	items,
	portletNamespace,
	selectedLayoutIds,
}: {
	items: Array<{children: Array<{layoutId: number; name: string}>}>;
	portletNamespace: string;
	selectedLayoutIds: string[];
}) {
	const leaves = items?.[0]?.children ?? [];
	const [checkedLayoutIds, setCheckedLayoutIds] = useState(
		() => new Set(selectedLayoutIds.map(Number))
	);

	return (
		<div>
			<input
				name={`${portletNamespace}layoutIds`}
				readOnly
				type="hidden"
				value={JSON.stringify([...checkedLayoutIds])}
			/>

			{leaves.map((leaf) => (
				<input
					aria-label={`page-${leaf.layoutId}`}
					checked={checkedLayoutIds.has(leaf.layoutId)}
					key={leaf.layoutId}
					onChange={() =>
						setCheckedLayoutIds((previousCheckedLayoutIds) => {
							const nextCheckedLayoutIds = new Set(
								previousCheckedLayoutIds
							);

							if (nextCheckedLayoutIds.has(leaf.layoutId)) {
								nextCheckedLayoutIds.delete(leaf.layoutId);
							}
							else {
								nextCheckedLayoutIds.add(leaf.layoutId);
							}

							return nextCheckedLayoutIds;
						})
					}
					type="checkbox"
				/>
			))}
		</div>
	);
}
