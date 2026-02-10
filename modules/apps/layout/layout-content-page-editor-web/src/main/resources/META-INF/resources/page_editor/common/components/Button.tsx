/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React from 'react';

import Loader from './Loader';

type Props = React.ComponentProps<typeof ClayButton> & {
	loading?: boolean;
};

export default function Button({children, loading = false, ...props}: Props) {
	return (
		<ClayButton {...props}>
			<span className="d-inline-flex page-editor__button">
				{loading && <Loader />}

				{children}
			</span>
		</ClayButton>
	);
}
