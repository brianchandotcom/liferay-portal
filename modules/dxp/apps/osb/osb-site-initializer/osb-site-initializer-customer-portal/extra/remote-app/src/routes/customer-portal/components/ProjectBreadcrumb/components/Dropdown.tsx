/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button} from '@clayui/core';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useState} from 'react';
import DropdownChildren from './DropdownChildren';
import ProjectNameTruncate from './ProjectNameTruncate';

type DropdownProps = {
	fetching: any;
	initialTotalCount: any;
	koroneikiAccounts: any;
	onIntersecting: any;
	onSearch: any;
	page: any;
	searching: any;
	selectedKoroneikiAccount: any;
	setPage: any;
	totalCount: any;
};

const MAX_ITEM_BEFORE_FILTER = 5;

const Dropdown: React.FC<DropdownProps> = ({
	fetching,
	initialTotalCount,
	koroneikiAccounts,
	onIntersecting,
	onSearch,

	// page,

	searching,
	selectedKoroneikiAccount,

	// setPage,

	totalCount,
}) => {
	const [active, setActive] = useState(false);

	const dropdownProjectsExceeded = initialTotalCount > MAX_ITEM_BEFORE_FILTER;

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={['br', 'tl'] as any}
			closeOnClickOutside
			hasRightSymbols
			menuElementAttrs={{
				className: classNames('cp-project-breadcrumbs-menu p-0', {
					'cp-extended-dropdown': dropdownProjectsExceeded,
					'cp-short-dropdown': !dropdownProjectsExceeded,
				}),
			}}
			onActiveChange={setActive}
			renderMenuOnClick
			trigger={
				<Button className="align-items-center bg-transparent cp-project-breadcrumbs-toggle d-flex p-0">
					<div
						className="font-weight-bold h5 m-0 text-neutral-9"
						title={selectedKoroneikiAccount?.name}
					>
						<ProjectNameTruncate>
							{selectedKoroneikiAccount?.name}
						</ProjectNameTruncate>
					</div>

					<span className="inline-item-after position-absolute text-brand-primary">
						<ClayIcon
							symbol={active ? 'caret-top' : 'caret-bottom'}
						/>
					</span>
				</Button>
			}
		>
			<DropdownChildren
				dropdownProjectsExceeded={dropdownProjectsExceeded}
				fetching={fetching}
				initialTotalCount={initialTotalCount}
				koroneikiAccounts={koroneikiAccounts}
				onIntersecting={onIntersecting}
				onSearch={onSearch}
				searching={searching}
				selectedKoroneikiAccount={selectedKoroneikiAccount}
				totalCount={totalCount}
			/>
		</ClayDropDown>
	);
};

export default Dropdown;
