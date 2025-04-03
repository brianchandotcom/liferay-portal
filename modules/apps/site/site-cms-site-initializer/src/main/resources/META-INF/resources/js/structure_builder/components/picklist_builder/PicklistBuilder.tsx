/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useEffect} from 'react';

import {Picklist} from '../../types/Picklist';

export default function PicklistBuilder({
	state,
}: {
	state: {listTypeDefinition: Picklist};
}) {
	return <HistoryManager picklistId={state.listTypeDefinition.id} />;
}

function HistoryManager({picklistId}: {picklistId: number}) {
	useEffect(() => {
		if (!picklistId) {
			return;
		}

		const url = new URL(window.location.href);

		url.searchParams.set('listTypeDefinitionId', picklistId.toString());

		history.replaceState(null, document.head.title, url.href);
	}, [picklistId]);

	return null;
}
