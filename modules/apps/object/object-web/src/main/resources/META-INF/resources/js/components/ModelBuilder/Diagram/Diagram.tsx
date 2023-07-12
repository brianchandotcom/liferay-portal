/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import ReactFlow, {Background, Controls, MiniMap} from 'react-flow-renderer';

import './Diagram.scss';

function Diagram() {
	return (
		<div className="lfr-objects__model-builder-diagram">
			<ReactFlow elements={[]} minZoom={0.1}>
				<Background size={1} />

				<Controls showInteractive={false} />

				<MiniMap />
			</ReactFlow>
		</div>
	);
}

export default Diagram;
