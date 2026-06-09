/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {useLiveProcess} from '../liveProcesses';

export default function ProcessCompletionDateRenderer({
	itemData,
	value,
}: {
	itemData?: {id?: number};
	value?: string;
}) {
	const liveProcess = useLiveProcess(itemData?.id);

	const dateCompleted = liveProcess?.dateCompleted ?? value;

	if (!dateCompleted) {
		return <>{Liferay.Language.get('processing')}...</>;
	}

	return (
		<>
			{new Intl.DateTimeFormat(
				Liferay.ThemeDisplay.getBCP47LanguageId(),
				{
					day: 'numeric',
					hour: 'numeric',
					minute: 'numeric',
					month: 'short',
					second: 'numeric',
					year: 'numeric',
				}
			).format(new Date(dateCompleted))}
		</>
	);
}
