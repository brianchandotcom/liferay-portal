/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ReactNode} from 'react';
interface Props {
	children: ReactNode;
	error?: string;
	id: string;
	name: string;
}
export default function FormField({
	children,
	error,
	id,
	name,
}: Props): JSX.Element;
export {};
