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

import getCN from 'classnames';
import React from 'react';

import Icon from './Icon.es';
import Tooltip from './Tooltip.es';

const Body = ({children, elementClasses}) => {
	const classes = getCN('panel-body', elementClasses);

	if (!children) return null;

	return (
		<div className={classes} data-testid="panelBody">
			{children}
		</div>
	);
};

const Footer = ({children, elementClasses, label}) => {
	const classes = getCN('panel-footer', elementClasses);

	if (!children) return null;

	return (
		<div className={classes}>
			{label && <div>{label}</div>}

			{!!children && <div>{children}</div>}
		</div>
	);
};

const Header = props => {
	const {children, elementClasses, title} = props;
	const classes = getCN('panel-header', elementClasses);

	return (
		<div className={classes} data-testid="panelHeader">
			{title && <div className="panel-title">{title}</div>}
			{!!children && <div>{children}</div>}
		</div>
	);
};

const HeaderWithOptions = props => {
	const {
		children,
		description,
		elementClasses,
		title,
		tooltipPosition = 'right'
	} = props;

	return (
		<Header elementClasses={elementClasses}>
			<div className="autofit-row">
				<div className="autofit-col autofit-col-expand flex-row">
					<span className="mr-2">{title}</span>

					<Tooltip
						message={description}
						position={tooltipPosition}
						width="288"
					>
						<Icon iconName={'question-circle-full'} />
					</Tooltip>
				</div>

				{children}
			</div>
		</Header>
	);
};

class Panel extends React.Component {
	render() {
		const {children, elementClasses} = this.props;
		const classes = getCN('panel', 'panel-secondary', elementClasses);

		return (
			<div className={'container-fluid-1280 mt-4'}>
				<div className={classes}>{children}</div>
			</div>
		);
	}
}

Panel.Body = Body;
Panel.Footer = Footer;
Panel.Header = Header;
Panel.HeaderWithOptions = HeaderWithOptions;

export default Panel;
