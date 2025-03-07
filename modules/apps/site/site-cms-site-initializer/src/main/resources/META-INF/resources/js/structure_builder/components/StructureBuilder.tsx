/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/structure_builder/StructureBuilder.scss';

import React from 'react';

import {Config, initializeConfig} from '../config';
import StateContextProvider from '../contexts/StateContext';
import {ObjectDefinition} from '../types/ObjectDefinition';
import buildState from '../utils/buildState';
import ManagementBar from './ManagementBar';
import StructureFields from './StructureFields';
import StructureSettings from './StructureSettings';

export default function StructureBuilder({
	config,
	state,
}: {
	config: Config;
	state: {objectDefinition: ObjectDefinition};
}) {
	initializeConfig(config);

	return (
		<StateContextProvider initialState={buildState(state.objectDefinition)}>
			<div className="d-flex flex-column structure-builder__wrapper">
				<ManagementBar />

				<div className="d-flex flex-grow-1 p-4">
					<StructureFields />

					<StructureSettings />
				</div>
			</div>
		</StateContextProvider>
	);
}
