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

import {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayTabs from '@clayui/tabs';
import classNames from 'classnames';
import {useContext} from 'react';
import {useNavigate} from 'react-router-dom';

import {HeaderContext} from '../../context/HeaderContext';
import {Permission} from '../../util/permission';
import BreadcrumbFinder from '../BreadcrumbFinder';
import DropDown from '../DropDown';
import DropDownWithActions from '../DropDown/DropDown';

const Divider = () => <p className="mx-2 text-paragraph-lg">/</p>;

type BreadCrumbTriggerProps = {
	displayCarret?: boolean;
};

const Header = () => {
	const [{dropdown, headerActions, heading, symbol, tabs}] = useContext(
		HeaderContext
	);

	const filteredHeaderActions = Permission.filterActions(
		headerActions.actions,
		headerActions.item?.actions
	);

	const navigate = useNavigate();

	const BreadCrumbTrigger: React.FC<BreadCrumbTriggerProps> = ({
		displayCarret,
	}) => (
		<div className="align-items-end d-flex" title={heading[0]?.title}>
			<ClayIcon
				className="dropdown-poll-icon mr-2"
				color="darkblue"
				fontSize={22}
				symbol={symbol || 'polls'}
			/>

			{displayCarret && (
				<ClayIcon
					className={classNames('dropdown-arrow-icon')}
					color="darkblue"
					symbol="caret-bottom"
				/>
			)}
		</div>
	);

	return (
		<header className="d-flex flex-column header-container pt-4">
			<div className="d-flex">
				<div className="align-items-center d-flex justify-content-center mx-3">
					{dropdown.length ? (
						<DropDown
							items={dropdown}
							position={Align.BottomLeft}
							trigger={
								<div>
									<BreadCrumbTrigger displayCarret />
								</div>
							}
						/>
					) : (
						<BreadCrumbTrigger />
					)}
				</div>

				<BreadcrumbFinder />

				<div className="d-flex flex-row justify-content-between w-100">
					<div className="d-flex flex-1 flex-wrap">
						{heading.map((header, index) => {
							const isClickable =
								header.path && index !== heading.length - 1;

							return (
								<span
									className={classNames(
										'd-flex flex-column header-item',
										{
											'cursor-pointer': isClickable,
										}
									)}
									key={index}
									onClick={() => {
										if (isClickable && header.path) {
											navigate(header.path);
										}
									}}
								>
									<small className="pr-2 text-paragraph-xs text-secondary">
										{header.category ? (
											header.category.toUpperCase()
										) : (
											<>&ensp;</>
										)}
									</small>

									<div className="d-flex flex-row">
										<p
											className="header-title text-paragraph-xl"
											title={header.title}
										>
											{header.title}
										</p>

										{!!heading.length &&
											heading.length !== index + 1 && (
												<Divider />
											)}
									</div>
								</span>
							);
						})}
					</div>

					{!!filteredHeaderActions.length && (
						<DropDownWithActions
							actions={filteredHeaderActions}
							item={headerActions.item}
							mutate={headerActions.mutate}
							position={Align.BottomLeft}
						/>
					)}
				</div>
			</div>

			<ClayTabs className="header-container-tabs ml-3">
				{tabs.map((tab, index) => (
					<ClayTabs.Item
						active={tab.active}
						innerProps={{
							'aria-controls': `tabpanel-${index}`,
						}}
						key={index}
						onClick={() => navigate(tab.path)}
					>
						{tab.title}
					</ClayTabs.Item>
				))}
			</ClayTabs>
		</header>
	);
};

export default Header;
