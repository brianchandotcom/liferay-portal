/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const ExportFormModal: React.FC<IProps>;
interface IProps {
	csvExport: string;
	exportFormURL: string;
	fileExtensions: string[];
	observer: any;
	onClose: () => void;
	portletNamespace: string;
}
export default ExportFormModal;
