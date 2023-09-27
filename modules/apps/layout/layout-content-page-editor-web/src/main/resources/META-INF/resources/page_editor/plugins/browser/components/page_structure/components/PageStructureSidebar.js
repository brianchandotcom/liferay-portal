/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React from 'react';

import StructureTree from './StructureTree';

export default function PageStructureSidebar() {
	return (
		<div
			className={classNames('overflow-auto page-editor__page-structure')}
		>
			<StructureTree />
		</div>
	);
}
