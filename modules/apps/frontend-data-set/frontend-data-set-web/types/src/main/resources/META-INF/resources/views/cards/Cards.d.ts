/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {ICardSchema} from '../../index';
declare const Cards: ({
	items,
	schema,
}: {
	items: Array<any>;
	schema: ICardSchema;
}) => JSX.Element;
export default Cards;
