/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ArrowHeadType, Node, Position} from 'react-flow-renderer';
export declare function getEdgeParams(
	source: Node,
	target: Node
): {
	sourcePos: Position;
	sx: number;
	sy: number;
	targetPos: Position;
	tx: number;
	ty: number;
};
export declare function createElements(): (
	| {
			data: {
				label: string;
			};
			id: string;
			position: {
				x: number;
				y: number;
			};
			arrowHeadType?: undefined;
			source?: undefined;
			target?: undefined;
			type?: undefined;
	  }
	| {
			arrowHeadType: ArrowHeadType;
			id: string;
			source: string;
			target: string;
			type: string;
			data?: undefined;
			position?: undefined;
	  }
)[];
