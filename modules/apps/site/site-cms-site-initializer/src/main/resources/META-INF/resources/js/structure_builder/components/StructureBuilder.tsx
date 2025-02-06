/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import ManagementBar from './ManagementBar';

import '../../../css/structure_builder/StructureBuilder.scss';

export default function StructureBuilder() {
	return (
		<div className="d-flex flex-column structure-builder__wrapper">
			<ManagementBar />
		</div>
	);
}
