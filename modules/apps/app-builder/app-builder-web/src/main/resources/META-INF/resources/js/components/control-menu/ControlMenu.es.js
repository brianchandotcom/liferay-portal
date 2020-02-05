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

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React, {useContext, useEffect, useState} from 'react';
import {createPortal} from 'react-dom';
import {Link as InternalLink, withRouter} from 'react-router-dom';

import {AppContext} from '../../AppContext.es';
import Button from '../button/Button.es';

const {Item, ItemList} = ClayDropDown;

const ActionsMenu = ({actions}) => {
	const [active, setActive] = useState(false);

	if (!actions || actions.length === 0) {
		return <></>;
	}

	return createPortal(
		<ClayDropDown
			active={active}
			alignmentPosition={Align.TopLeft}
			className="lfr-icon-menu portlet-options"
			onActiveChange={newActive => setActive(newActive)}
			trigger={
				<Button displayType="unstyled">
					<span className="icon-monospaced">
						<ClayIcon symbol="ellipsis-v" />
					</span>
				</Button>
			}
		>
			<ItemList>
				{actions.map(({action, name}, index) => (
					<Item
						key={index}
						onClick={event => {
							event.preventDefault();
							setActive(false);

							if (action) {
								action();
							}
						}}
					>
						{name}
					</Item>
				))}
			</ItemList>
		</ClayDropDown>,
		document.querySelector(
			'li.control-menu-nav-category.user-control-group > ul > li > div.control-menu-icon'
		)
	);
};

const BackButton = ({backURL}) => {
	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	return createPortal(
		<li className="control-menu-nav-item">
			<Link
				className="control-menu-icon lfr-icon-item"
				tabIndex={1}
				to={backURL}
			>
				<span className="icon-monospaced">
					<ClayIcon symbol="angle-left" />
				</span>
			</Link>
		</li>,
		document.querySelector('.sites-control-group .control-menu-nav')
	);
};

const ExternalLink = ({children, to, ...props}) => {
	return (
		<a href={to} {...props}>
			{children}
		</a>
	);
};

const resolveBackURL = (backURL, url) => {
	if (backURL === '../') {
		const paths = url.split('/');

		paths.pop();
		backURL = paths.join('/');
	}

	return backURL;
};

const setDocumentTitle = title => {
	if (title) {
		const titles = document.title.split(' - ');

		titles[0] = title;

		document.title = titles.join(' - ');
	}
};

export const InlineControlMenu = ({backURL, title, tooltip, url}) => {
	const {appDeploymentType, controlMenuElementId} = useContext(AppContext);

	backURL = resolveBackURL(backURL, url);

	const Link =
		backURL && backURL.startsWith('http') ? ExternalLink : InternalLink;

	const controlMenuElement = document.getElementById(controlMenuElementId);

	const ControlMenu = () => (
		<div
			className={classNames(
				'app-builder-control-menu',
				appDeploymentType
			)}
		>
			{backURL && (
				<Link
					className={classNames(
						'control-menu-back-button',
						appDeploymentType
					)}
					tabIndex={1}
					to={backURL}
				>
					<span className="icon-monospaced">
						<ClayIcon symbol="angle-left" />
					</span>
				</Link>
			)}
			{title && (
				<span
					className={classNames(
						'control-menu-title',
						appDeploymentType
					)}
				>
					{title}
				</span>
			)}
			{tooltip && (
				<span
					className="lfr-portal-tooltip taglib-icon-help"
					data-title={tooltip}
				>
					<ClayIcon symbol="question-circle-full" />
				</span>
			)}
		</div>
	);

	return controlMenuElement ? (
		createPortal(<ControlMenu />, controlMenuElement)
	) : (
		<ControlMenu />
	);
};

export const PortalControlMenu = ({actions, backURL, title, tooltip, url}) => {
	backURL = resolveBackURL(backURL, url);

	useEffect(() => {
		document.querySelector(
			'.tools-control-group .control-menu-level-1-heading'
		).innerHTML = title;
	}, [title]);

	useEffect(() => {
		const tooltipNode = document.querySelector(
			'.tools-control-group .taglib-icon-help'
		);

		if (!tooltipNode) {
			return;
		}

		if (tooltip) {
			tooltipNode.classList.remove('hide');
			tooltipNode.setAttribute('title', tooltip);
		}
		else {
			tooltipNode.classList.add('hide');
		}
	}, [tooltip]);

	return (
		<>
			{backURL && <BackButton backURL={backURL} />}
			{actions && <ActionsMenu actions={actions} />}
		</>
	);
};

export const ControlMenuBase = props => {
	useEffect(() => {
		setDocumentTitle(props.title);
	}, [props.title]);

	const {appDeploymentType} = useContext(AppContext);

	if (
		appDeploymentType &&
		(appDeploymentType === 'standalone' || appDeploymentType === 'widget')
	) {
		return <InlineControlMenu {...props} />;
	}
	else {
		return <PortalControlMenu {...props} />;
	}
};

export default withRouter(({match: {url}, ...props}) => {
	return <ControlMenuBase {...props} url={url} />;
});
