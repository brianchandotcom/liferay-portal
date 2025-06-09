/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

import {getImage} from '../../../main/util/getImage';
import {useSelector} from '../../contexts/StateContext';
import selectSelection from '../../selectors/selectSelection';
import selectStructureUuid from '../../selectors/selectStructureUuid';
import StructureFieldSettings from './StructureFieldSettings';
import StructureSettings from './StructureSettings';

export default function Settings() {
	const selection = useSelector(selectSelection);
	const structureUuid = useSelector(selectStructureUuid);

	if (selection.length > 1) {
		return <MultiselectionState />;
	}

	const [uuid] = selection;

	if (!uuid || uuid === structureUuid) {
		return <StructureSettings />;
	}

	return <StructureFieldSettings key={uuid} uuid={uuid} />;
}

function MultiselectionState() {
	return (
		<ClayEmptyState
			className="justify-content-center structure-builder__empty-state"
			description=""
			imgSrc={getImage('multiselection_state.svg')}
			imgSrcReducedMotion={getImage('multiselection_state.svg')}
			small
			title={Liferay.Language.get('multiple-items-selected')}
		/>
	);
}
