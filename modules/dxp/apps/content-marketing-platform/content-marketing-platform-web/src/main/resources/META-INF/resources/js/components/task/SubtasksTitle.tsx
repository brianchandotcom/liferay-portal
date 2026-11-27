/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDS_EVENT} from '@liferay/frontend-data-set-web';
import {fetch, sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useState} from 'react';

export default function SubtasksTitle({
	apiURL,
	fdsId,
}: {
	apiURL: string;
	fdsId: string;
}) {
	const [totalCount, setTotalCount] = useState<number | null>(null);

	const fetchTotalCount = useCallback(async () => {
		const response = await fetch(`${apiURL}&page=1&pageSize=1`);

		if (response.ok) {
			const {totalCount} = await response.json();

			setTotalCount(totalCount);
		}
	}, [apiURL]);

	useEffect(() => {
		fetchTotalCount();
	}, [fetchTotalCount]);

	useEffect(() => {
		const handleDisplayUpdated = ({id}: {id: string}) => {
			if (id === fdsId) {
				fetchTotalCount();
			}
		};

		Liferay.on(FDS_EVENT.DISPLAY_UPDATED, handleDisplayUpdated);

		return () => {
			Liferay.detach(FDS_EVENT.DISPLAY_UPDATED, handleDisplayUpdated);
		};
	}, [fdsId, fetchTotalCount]);

	return (
		<h5>
			{totalCount === null
				? Liferay.Language.get('subtasks')
				: sub(Liferay.Language.get('subtasks-x'), totalCount)}
		</h5>
	);
}
