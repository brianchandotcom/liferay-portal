/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React from 'react';

const Body = ({children, elementClasses}) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) {
		return null;
	}

	return <div className={classes}>{children}</div>;
};

const Footer = ({children, elementClasses, label}) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) {
		return null;
	}

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

const Header = ({children, elementClasses, title}) => {
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes}>
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

const HeaderWithOptions = ({
	children,
	description,
	elementClasses,
	title,
	tooltipPosition = 'right',
}) => {
	return (
		<Header elementClasses={elementClasses}>
			<ClayLayout.ContentRow>
				<ClayLayout.ContentRow className="flex-row" expand>
					<span className="mr-2">{title}</span>

					<ClayTooltipProvider>
						<span>
							<span
								className="workflow-tooltip"
								data-tooltip-align={tooltipPosition}
								title={description}
							>
								<ClayIcon symbol="question-circle-full" />
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentRow>

				{children}
			</ClayLayout.ContentRow>
		</Header>
	);
};

const Panel = ({children, elementClasses}) => {
	const classes = getCN('panel', 'panel-secondary', elementClasses);

	return (
		<ClayLayout.ContainerFluid className="mt-4">
			<div className={classes}>{children}</div>
		</ClayLayout.ContainerFluid>
	);
};

Panel.Body = Body;
Panel.Footer = Footer;
Panel.Header = Header;
Panel.HeaderWithOptions = HeaderWithOptions;

export default Panel;
