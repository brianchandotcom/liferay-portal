/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface IBasePageProps extends React.HTMLAttributes<HTMLElement> {
	description?: string;
	title: string;
}

const BasePage: React.FC<IBasePageProps> & {
	Footer: typeof BasePageFooter;
} = ({children, description, title}) => {
	return (
		<div className="mb-5 sheet sheet-lg">
			<div>
				<h2 className="sheet-title">{title}</h2>

				{description && <div className="sheet-text">{description}</div>}
			</div>

			{children}
		</div>
	);
};

const BasePageFooter: React.FC<React.HTMLAttributes<HTMLElement>> = ({
	children,
}) => {
	return <div className="sheet-footer">{children}</div>;
};

BasePage.Footer = BasePageFooter;

export default BasePage;
