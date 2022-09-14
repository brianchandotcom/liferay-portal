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

import React from 'react';

const Sheet = ({children, description, title}) => (
	<div className="sheet sheet-lg">
		<div className="sheet-header">
			<h2 className="sheet-title">{title}</h2>

			<div className="sheet-text">{description}</div>
		</div>

		{children}
	</div>
);

const Content = ({children}) => <div className="sheet-section">{children}</div>;

const Footer = ({children}) => <div className="sheet-footer">{children}</div>;

Sheet.Content = Content;
Sheet.Footer = Footer;

export default Sheet;
