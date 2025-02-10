/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ManagementBar from './ManagementBar';
import StructureFields from './StructureFields';
import StructureSettings from './StructureSettings';

import '../../../css/structure_builder/StructureBuilder.scss';
import {StructureSettingsContextProvider} from '../contexts/StructureSettingsContext';

export default function StructureBuilder() {
	return (
		<div className="d-flex flex-column structure-builder__wrapper">
			<StructureSettingsContextProvider>
				<ManagementBar />

				<div className="d-flex flex-grow-1 p-4">
					<StructureFields />

					<StructureSettings />
				</div>
			</StructureSettingsContextProvider>
		</div>
	);
}
