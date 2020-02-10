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
import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import Button from '../button/Button.es';
import SearchInput from '../search-input/SearchInput.es';

const Sidebar = React.forwardRef(
	(
		{
			children,
			className,
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
			<div className={className} ref={ref}>
				<div
					className={classNames(
						'data-layout-builder-sidebar',
						'main',
						{
							closed: isClosed
						}
					)}
				>
					<div className="sidebar sidebar-light">
						{(closeable || onSearch) && (
							<ClayForm
								onSubmit={event => event.preventDefault()}
							>
								<SidebarSearchInput
									closeable={closeable}
									onSearch={onSearch}
									onToggle={handleToggle}
								/>
							</ClayForm>
						)}
						{children}
					</div>
				</div>
				{closeable && (
					<div
						className={classNames(
							'data-layout-builder-sidebar',
							'mini',
							{
								closed: !isClosed
							}
						)}
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
		<div className={classNames(className, 'sidebar-header')}>
			{children}
		</div>
	);
};

const SidebarSearchInput = ({closeable, onSearch, onToggle}) => (
	<SidebarHeader>
		<div className="autofit-row sidebar-section">
			<div className="autofit-col autofit-col-expand">
				{onSearch && (
					<SearchInput
						onChange={searchText => onSearch(searchText)}
					/>
				)}
			</div>
			<div className="autofit-col ml-2">
				{closeable && (
					<ClayButtonWithIcon
						displayType="secondary"
						onClick={onToggle}
						symbol="angle-right"
					/>
				)}
			</div>
		</div>
	</SidebarHeader>
);

const SidebarTab = ({tabs}) => {
	return (
		<nav className="component-tbar tbar">
			<div className="container-fluid">
				<ul className="nav nav-underline" role="tablist">
					{tabs.map(({active, label, onClick}, index) => (
						<li className="nav-item" key={index}>
							<a
								className={classNames('nav-link', {active})}
								data-senna-off
								href=""
								onClick={event => {
									event.preventDefault();

									if (onClick) {
										onClick(index);
									}
								}}
								role="tab"
							>
								{label}
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
