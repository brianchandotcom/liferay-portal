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

import classNames from 'classnames';
import React from 'react';

interface ISidePanelContentBody extends React.HTMLAttributes<HTMLElement> {}
interface ISidePanelContentFooter extends React.HTMLAttributes<HTMLElement> {}

const SidePanelContent: React.FC<React.HTMLAttributes<HTMLElement>> & {
	Body: React.FC<ISidePanelContentBody>;
	Footer: React.FC<ISidePanelContentFooter>;
} = ({children, className}) => {
	return (
		<div className={classNames('side-panel-content', className)}>
			{children}
		</div>
	);
};

const SidePanelContentBody: React.FC<ISidePanelContentBody> = ({children}) => {
	return <div className="side-panel-content__body">{children}</div>;
};

const SidePanelContentFooter: React.FC<ISidePanelContentFooter> = ({
	children,
}) => {
	return <div className="side-panel-content__footer">{children}</div>;
};

SidePanelContent.Body = SidePanelContentBody;
SidePanelContent.Footer = SidePanelContentFooter;

export default SidePanelContent;
