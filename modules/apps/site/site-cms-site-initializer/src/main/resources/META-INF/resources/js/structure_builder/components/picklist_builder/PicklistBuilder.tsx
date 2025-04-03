/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import React, {useEffect} from 'react';

import StateContextProvider, {
	buildState,
	useId,
} from '../../contexts/PicklistBuilderContext';
import {Picklist} from '../../types/Picklist';
import PicklistFields from './PicklistFields';

export default function PicklistBuilder({
	state,
}: {
	state: {listTypeDefinition: Picklist};
}) {
	return (
		<StateContextProvider
			initialState={buildState(state.listTypeDefinition)}
		>
			<div className="d-flex flex-column">
				<HistoryManager />

				<ClayLayout.ContainerFluid className="px-4" size="md" view>
					<PicklistFields />
				</ClayLayout.ContainerFluid>
			</div>
		</StateContextProvider>
	);
}

function HistoryManager() {
	const id = useId();

	useEffect(() => {
		if (!id) {
			return;
		}

		const url = new URL(window.location.href);

		url.searchParams.set('listTypeDefinitionId', id.toString());

		history.replaceState(null, document.head.title, url.href);
	}, [id]);

	return null;
}
