/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

interface IBasePageProps extends React.HTMLAttributes<HTMLElement> {
	description?: string | JSX.Element;
	title: string;
}

const BasePage: React.FC<
	{children?: React.ReactNode | undefined} & IBasePageProps
> & {
	Footer: typeof BasePageFooter;
} = ({children, description, title}) => {
	return (
		<>
			<header className="mb-1 sheet-header">
				<h1 className="mb-1 sheet-title">{title}</h1>

				{description && (
					<p className="sheet-text text-secondary">{description}</p>
				)}
			</header>
			{children}
		</>
	);
};

const BasePageFooter: React.FC<
	{children?: React.ReactNode | undefined} & React.HTMLAttributes<HTMLElement>
> = ({children}) => {
	return <div className="sheet-footer">{children}</div>;
};

BasePage.Footer = BasePageFooter;

export default BasePage;
