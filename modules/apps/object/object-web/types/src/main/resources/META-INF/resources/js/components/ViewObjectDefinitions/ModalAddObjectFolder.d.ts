/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface ModalAddObjectFolderProps {
	handleOnClose: () => void;
	setObjectFolders: React.Dispatch<
		React.SetStateAction<Partial<ObjectFolder>[]>
	>;
	setSelectedObjectFolder: (values: Partial<ObjectFolder>) => void;
}
export declare function ModalAddObjectFolder({
	handleOnClose,
	setObjectFolders,
	setSelectedObjectFolder,
}: ModalAddObjectFolderProps): JSX.Element;
export {};
