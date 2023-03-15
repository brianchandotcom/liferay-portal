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
import {Link, useNavigate} from 'react-router-dom';
import Permission from '~/core/Permission';

import {HeaderContext} from '../../context/HeaderContext';
import BreadcrumbFinder from '../BreadcrumbFinder';
import DropDown from '../DropDown';
import DropDownWithActions from '../DropDown/DropDown';
import TestrayIcons from '../Icons/TestrayIcon';

const Divider = () => <p className="mx-2 text-paragraph-lg">/</p>;

type BreadCrumbTriggerProps = {
	displayCarret?: boolean;
};

const Header = () => {
	const [{dropdown, headerActions, heading, symbol, tabs}] = useContext(
		HeaderContext
	);
	const navigate = useNavigate();

	const filteredHeaderActions = Permission.filterActions(
		headerActions.actions,
		headerActions.item?.actions
	);

	const BreadCrumbTrigger: React.FC<BreadCrumbTriggerProps> = ({
		displayCarret,
	}) => (
		<div className="align-items-center d-flex" title={heading[0]?.title}>
			<TestrayIcons
				className="dropdown-poll-icon mr-2"
				fill="darkblue"
				size={35}
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
		<header className="tr-header-container">
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

				<BreadcrumbFinder heading={heading} />

				<div className="d-flex flex-row justify-content-between w-100">
					<div className="d-flex flex-1">
						{heading.map((header, index) => {
							const isClickable =
								header.path && index !== heading.length - 1;

							const Component =
								isClickable && header.path
									? Link
									: (props: any) => <span {...props} />;

							return (
								<Component
									className={classNames(
										'tr-header-container__item'
									)}
									key={index}
									to={header.path as string}
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
											className="tr-header-container__item__title"
											title={header.title}
										>
											{header.title}
										</p>

										{!!heading.length &&
											heading.length !== index + 1 && (
												<Divider />
											)}
									</div>
								</Component>
							);
						})}
					</div>

					<div className="align-items-center d-flex justify-content-center">
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
			</div>

			<ClayTabs className="tr-header-container__tabs">
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
