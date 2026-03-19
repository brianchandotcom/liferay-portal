/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {BaseLinkRenderer, BaseLinkRendererProps} from './BaseLinkRenderer';

export default function DesignLibraryLinkRenderer(
	props: BaseLinkRendererProps
) {
	return (
		<BaseLinkRenderer
			{...props}
			className="design-library-fds-sticker-books"
			symbol="books"
		/>
	);
}
