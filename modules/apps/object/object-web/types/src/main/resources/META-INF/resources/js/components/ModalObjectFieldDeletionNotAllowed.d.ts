/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface ModalObjectFieldDeletionNotAllowedProps {
	content: React.ReactNode;
	onVisibilityChange: () => void;
}
declare function ModalObjectFieldDeletionNotAllowed({
	content,
	onVisibilityChange,
}: ModalObjectFieldDeletionNotAllowedProps): JSX.Element;
export default ModalObjectFieldDeletionNotAllowed;
