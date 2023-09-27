/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Observer} from '@clayui/modal/lib/types';
import React from 'react';
export default function WarningModal({
	children,
	observer,
	onClose,
	title,
}: IProps): JSX.Element;
interface IProps {
	children?: React.ReactNode;
	observer: Observer;
	onClose: () => void;
	title: string;
}
export {};
