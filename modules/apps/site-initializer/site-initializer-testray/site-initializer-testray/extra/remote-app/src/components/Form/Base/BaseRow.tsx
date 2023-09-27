/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ReactNode} from 'react';

const BaseRow: React.FC<{
	children: ReactNode;
	separator?: boolean;
	title: string;
}> = ({children, separator = true, title}) => (
	<>
		<ClayLayout.Row justify="start">
			<ClayLayout.Col size={3} sm={12} xl={2}>
				<h5 className="font-weight-bold">{title}</h5>
			</ClayLayout.Col>

			<ClayLayout.Col size={3} sm={12} xl={10}>
				{children}
			</ClayLayout.Col>
		</ClayLayout.Row>

		{separator && <hr />}
	</>
);

export default BaseRow;
