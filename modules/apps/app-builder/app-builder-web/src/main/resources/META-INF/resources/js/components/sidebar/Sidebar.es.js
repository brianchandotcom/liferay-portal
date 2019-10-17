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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import React, {useState, useEffect} from 'react';

import Button from '../button/Button.es';

const Sidebar = React.forwardRef(
	(
		{
			children,
			closeable = true,
			closed = false,
			onSearch = null,
			onToggle = () => {}
		},
		ref
	) => {
		const [isClosed, setClosed] = useState(closed);

		const handleToggle = () => {
			const closed = !isClosed;
			setClosed(closed);
			onToggle(closed);
		};

		useEffect(() => {
			setClosed(closed);
		}, [closed]);

		return (
			<div ref={ref}>
				<div
					className={classNames('app-builder-sidebar', 'main', {
						closed: isClosed
					})}
				>
					<div className="sidebar sidebar-light">
						{(closeable || onSearch) && (
							<SidebarSearchInput
								closeable={closeable}
								onSearch={onSearch}
								onToggle={handleToggle}
							/>
						)}
						{children}
					</div>
				</div>
				{closeable && (
					<div
						className={classNames('app-builder-sidebar', 'mini', {
							closed: !isClosed
						})}
					>
						<Button
							displayType="secondary"
							onClick={handleToggle}
							symbol="angle-left"
						/>
					</div>
				)}
			</div>
		);
	}
);

const SidebarBody = ({children, className}) => {
	return (
		<div className={classNames(className, 'sidebar-body')}>{children}</div>
	);
};

const SidebarFooter = ({children}) => {
	return <div className="sidebar-footer">{children}</div>;
};

const SidebarHeader = ({children, className}) => {
	return (
		<div className={classNames(className, 'pb-0 sidebar-header')}>
			{children}
		</div>
	);
};

const SidebarSearchInput = ({closeable, onSearch, onToggle}) => {
	const [keywords, setKeywords] = useState('');

	const onChange = event => {
		const {value} = event.target;

		setKeywords(value);

		if (onSearch) {
			onSearch(value);
		}
	};

	return (
		<SidebarHeader>
			<div className="autofit-row sidebar-section">
				<div className="autofit-col autofit-col-expand">
					<ClayInput.Group>
						{onSearch && (
							<ClayInput.GroupItem>
								<ClayInput
									aria-label={Liferay.Language.get('search')}
									className="input-group-inset input-group-inset-after"
									onChange={onChange}
									placeholder={`${Liferay.Language.get(
										'search'
									)}...`}
									type="text"
									value={keywords}
								/>

								<ClayInput.GroupInsetItem after>
									<ClayButtonWithIcon
										displayType="unstyled"
										symbol="search"
									/>
								</ClayInput.GroupInsetItem>
							</ClayInput.GroupItem>
						)}
						{closeable && (
							<ClayInput.GroupItem shrink>
								<ClayButtonWithIcon
									displayType="secondary"
									onClick={onToggle}
									symbol="angle-right"
								/>
							</ClayInput.GroupItem>
						)}
					</ClayInput.Group>
				</div>
			</div>
		</SidebarHeader>
	);
};

const SidebarTab = ({tabs}) => {
	return (
		<nav className="component-tbar tbar">
			<div className="container-fluid">
				<ul className="nav nav-underline" role="tablist">
					{tabs.map((tab, index) => (
						<li className="nav-item" key={index}>
							<a
								className="active nav-link"
								data-senna-off
								href=""
								onClick={event => {
									event.preventDefault();
								}}
								role="tab"
							>
								{tab}
							</a>
						</li>
					))}
				</ul>
			</div>
		</nav>
	);
};

const SidebarTabContent = ({children}) => {
	return (
		<div className="tab-content">
			<div className="active fade mt-3 show tab-pane" role="tabpanel">
				{children}
			</div>
		</div>
	);
};

Sidebar.Body = SidebarBody;
Sidebar.Footer = SidebarFooter;
Sidebar.Header = SidebarHeader;
Sidebar.SearchInput = SidebarSearchInput;
Sidebar.Tab = SidebarTab;
Sidebar.TabContent = SidebarTabContent;

export default Sidebar;

export {
	SidebarBody,
	SidebarFooter,
	SidebarHeader,
	SidebarSearchInput,
	SidebarTab,
	SidebarTabContent
};
