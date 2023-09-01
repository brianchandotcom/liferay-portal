/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ArrowHeadType, Node, Position} from 'react-flow-renderer';
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
export declare function getEdgeParams(
	source: Node,
	sourceIncrementY: number,
	target: Node,
	targetIncrementY: number
): {
	sourcePos: Position;
	sourceX: number;
	sourceY: number;
	targetPos: Position;
	targetX: number;
	targetY: number;
};
export declare function getObjectFolderName(): string;
export declare function updateURLParam(
	paramType: string,
	paramValue: string
): void;
