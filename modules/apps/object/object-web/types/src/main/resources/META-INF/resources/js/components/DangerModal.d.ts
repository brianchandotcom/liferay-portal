/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Observer} from '@clayui/modal/lib/types';
import React from 'react';
export default function DangerModal({
	children,
	errorMessage,
	observer,
	onClose,
	onDelete,
	placeholder,
	title,
	token,
}: IProps): JSX.Element;
interface IProps {
	children?: React.ReactNode;
	errorMessage: string;
	observer: Observer;
	onClose: () => void;
	onDelete: (event: React.MouseEvent<HTMLButtonElement>) => void;
	placeholder?: string;
	title: string;
	token: string;
}
export {};
