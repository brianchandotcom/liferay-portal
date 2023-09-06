/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import './Header.scss';
interface Header {
	hasDraftObjectDefinitions: boolean;
	objectFolder: ObjectFolder;
	setShowModal: (value: React.SetStateAction<ModelBuilderModals>) => void;
}
export default function ({
	hasDraftObjectDefinitions,
	objectFolder,
	setShowModal,
}: Header): JSX.Element;
export {};
