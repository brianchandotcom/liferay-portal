/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/structure_builder/StructureBuilder.scss';

import React, {useEffect} from 'react';

import {Config, initializeConfig} from '../config';
import CacheContextProvider from '../contexts/CacheContext';
import StateContextProvider, {useSelector} from '../contexts/StateContext';
import selectStructureId from '../selectors/selectStructureId';
import {ObjectDefinition} from '../types/ObjectDefinition';
import buildState from '../utils/buildState';
import buildStructures from '../utils/buildStructures';
import Sidebar from './Sidebar';
import StructureBuilderToolbar from './StructureBuilderToolbar';
import Settings from './settings/Settings';

export default function StructureBuilder({
	config,
	state,
}: {
	config: Config;
	state: {
		mainObjectDefinition: ObjectDefinition;
		objectDefinitions: ObjectDefinition[];
	};
}) {
	initializeConfig(config);

	const objectDefinitions = new Map(
		state.objectDefinitions.map((objectDefinition) => [
			objectDefinition.externalReferenceCode,
			objectDefinition,
		])
	);

	return (
		<StateContextProvider
			initialState={buildState(state.mainObjectDefinition)}
		>
			<CacheContextProvider
				initialData={{
					'object-definitions': objectDefinitions,
				}}
			>
				<div className="d-flex flex-column structure-builder__wrapper">
					<HistoryManager />

					<StructureBuilderToolbar />

					<div className="d-flex flex-grow-1 p-4">
						<Sidebar />

						<Settings />
					</div>
				</div>
			</CacheContextProvider>
		</StateContextProvider>
	);
}

function HistoryManager() {
	const structureId = useSelector(selectStructureId);

	useEffect(() => {
		if (!structureId) {
			return;
		}

		const url = new URL(window.location.href);

		if (url.searchParams.has('objectFolderExternalReferenceCode')) {
			url.searchParams.delete('objectFolderExternalReferenceCode');
		}

		url.searchParams.set('objectDefinitionId', structureId.toString());

		history.replaceState(null, document.head.title, url.href);
	}, [structureId]);

	return null;
}
