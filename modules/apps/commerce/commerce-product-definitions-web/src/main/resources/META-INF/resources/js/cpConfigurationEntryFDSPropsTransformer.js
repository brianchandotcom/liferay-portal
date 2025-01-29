/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDS_INTERNAL_CELL_RENDERERS} from '@liferay/frontend-data-set-web';
import React from 'react';

import '../css/main.scss';

function render(props) {
	const {rootPropertyName, value} = props;

	let rendererName = 'default';

	if (rootPropertyName === 'entityName') {
		rendererName = 'actionLink';
	}
	else if (typeof value === 'boolean') {
		rendererName = 'boolean';
	}

	const renderer = FDS_INTERNAL_CELL_RENDERERS.find(
		(renderer) => renderer.name === rendererName
	);

	if (renderer) {
		const CellRendererComponent = renderer.component;

		return <CellRendererComponent {...props} />;
	}

	return props.value;
}

function CPConfigurationEntryDataRenderer(props) {
	const {itemData, valuePath} = props;

	const rootPropertyName = valuePath.pop();

	const differences = itemData.differences || [];
	const showIcon =
		differences.includes(rootPropertyName) ||
		(rootPropertyName === 'entityName' && !!differences.length);

	return (
		<span className="align-items-center d-flex product-configuration-value w-100">
			<span className="flex-grow-1">{render(props)}</span>

			{showIcon && <span className="icon" />}
		</span>
	);
}

export default function propsTransformer({...props}) {
	return {
		...props,
		customDataRenderers: {
			cpConfigurationEntryDataRenderer: CPConfigurationEntryDataRenderer,
		},
	};
}
