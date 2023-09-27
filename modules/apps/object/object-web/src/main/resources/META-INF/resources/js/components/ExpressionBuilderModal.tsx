/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ExpressionBuilderModal as Modal,
	SidebarCategory,
} from '@liferay/object-js-components-web';
import React from 'react';

interface IProps {
	sidebarElements: SidebarCategory[];
}

export default function ExpressionBuilderModal({sidebarElements}: IProps) {
	return <Modal sidebarElements={sidebarElements} />;
}
