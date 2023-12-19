/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
interface IEditClientDetailsProps extends React.HTMLAttributes<HTMLElement> {
	baseResourceURL: string;
	clientId: string;
	clientSecret: string;
	portletNamespace: string;
}
declare const EditClientDetails: React.FC<IEditClientDetailsProps>;
export default EditClientDetails;
