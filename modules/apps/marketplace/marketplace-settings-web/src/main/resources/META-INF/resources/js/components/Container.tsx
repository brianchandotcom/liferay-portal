/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';

type ContainerProps = {
	children?: ReactNode;
	description?: ReactNode;
	footer: ReactNode;
	title: ReactNode;
};

const Container = ({children, description, footer, title}: ContainerProps) => (
	<div className="border pb-4 rounded-lg">
		<div className="pt-4 px-4">
			<h3>{title}</h3>
		</div>

		<hr />

		<div className="px-4">
			<p>{description}</p>

			{children}
		</div>

		<hr />

		<div className="d-flex justify-content-end px-4">{footer}</div>
	</div>
);

export default Container;
