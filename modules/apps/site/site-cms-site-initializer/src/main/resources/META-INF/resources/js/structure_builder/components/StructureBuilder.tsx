/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/structure_builder/StructureBuilder.scss';

import React from 'react';

import {Config, initializeConfig} from '../config';
import StateContextProvider from '../contexts/StateContext';
import ManagementBar from './ManagementBar';
import StructureSettings from './StructureSettings';
import StructureFields from './structure_fields/StructureFields';

export default function StructureBuilder({config}: {config: Config}) {
	initializeConfig(config);

	return (
		<StateContextProvider>
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
