/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Position} from './Hint';
interface Props {
	className: string;
	dataProvider: () => Promise<string>;
	label: string;
	percentage?: boolean;
	popoverHeader: string;
	popoverMessage: string;
	popoverPosition?: Position;
}
declare function TotalCount({
	className,
	dataProvider,
	label,
	percentage,
	popoverHeader,
	popoverMessage,
	popoverPosition,
}: Props): JSX.Element;
export default TotalCount;
