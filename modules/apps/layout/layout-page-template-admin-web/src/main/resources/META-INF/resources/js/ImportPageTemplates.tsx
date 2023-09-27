/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Import} from '@liferay/layout-js-components-web';
import React from 'react';

interface Props {
	backURL: string;
	importURL: string;
	portletNamespace: string;
}

export default function ImportFragments(props: Props) {
	return (
		<Import
			helpLink={{
				href:
					'https://learn.liferay.com/en/w/dxp/site-building/creating-pages/adding-pages/exporting-and-importing-page-templates',
				message: Liferay.Language.get(
					'read-more-about-exporting-and-importing-page-templates'
				),
			}}
			{...props}
		/>
	);
}
