/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
