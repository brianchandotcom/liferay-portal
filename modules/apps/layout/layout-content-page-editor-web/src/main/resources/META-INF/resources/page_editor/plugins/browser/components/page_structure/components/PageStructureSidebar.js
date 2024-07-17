/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ManagementToolbar} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React from 'react';

import {useActiveItemIds} from '../../../../../app/contexts/ControlsContext';
import StructureTree from './StructureTree';

export default function PageStructureSidebar() {
	const activeItemIds = useActiveItemIds();

	return (
		<div className="overflow-auto page-editor__page-structure">
			{Liferay.FeatureFlags['LPD-18221'] && activeItemIds.length > 1 ? (
				<ManagementToolbar.Container active>
					{sub(
						Liferay.Language.get('x-items-selected'),
						activeItemIds.length
					)}
				</ManagementToolbar.Container>
			) : null}

			<StructureTree />
		</div>
	);
}
