/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Criteria} from '../../../types/Criteria';
export declare const ACTION_TYPES: {
	readonly add: 'add';
	readonly move: 'move';
};
declare type Contributor = {
	criteriaMap: Criteria;
	propertyKey: string;
};
declare type Props = {
	contributors: Contributor[];
	onMove: (criteria: Criteria, propertyKey: PropertyKey) => void;
};
export default function KeyboardMovementManager({
	contributors,
	onMove,
}: Props): null;
export {};
