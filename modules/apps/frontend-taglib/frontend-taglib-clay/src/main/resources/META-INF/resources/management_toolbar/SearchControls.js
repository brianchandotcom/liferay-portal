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
import {FocusTrap} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useRef} from 'react';

const SearchControls = ({
	disabled,
	onCloseSearchMobile,
	searchActionURL,
	searchData,
	searchFormMethod,
	searchFormName,
	searchInputAutoFocus,
	searchInputName,
	searchMobile,
	searchValue,
}) => {
	const searchInputRef = useRef();

	useEffect(() => {
		if (searchMobile) {
			searchInputRef.current.focus();
		}
	}, [searchMobile]);

	return (
		<>
			<ManagementToolbar.Search
				action={searchActionURL}
				method={searchFormMethod}
				name={searchFormName}
				showMobile={searchMobile}
			>
				<FocusTrap active={searchMobile}>
					<ClayInput.Group
						onKeyDown={(event) => {
							if (searchMobile && event.key === 'Escape') {
								onCloseSearchMobile();
							}
						}}
					>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={`${Liferay.Language.get(
									'search'
								)}:`}
								autoFocus={searchInputAutoFocus}
								className="form-control input-group-inset input-group-inset-after"
								defaultValue={searchValue}
								disabled={disabled}
								name={searchInputName}
								placeholder={Liferay.Language.get('search-for')}
								ref={searchInputRef}
								type="search"
							/>

							<ClayInput.GroupInsetItem after tag="span">
								<ClayButtonWithIcon
									aria-label={Liferay.Language.get('search')}
									disabled={disabled}
									displayType="unstyled"
									symbol="search"
									title={Liferay.Language.get('search-for')}
									type="submit"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>

						<ClayInput.GroupItem
							className="navbar-breakpoint-d-none"
							shrink
						>
							<ClayButtonWithIcon
								aria-label={Liferay.Language.get(
									'close-search'
								)}
								disabled={disabled}
								displayType="unstyled"
								onClick={onCloseSearchMobile}
								size="sm"
								symbol="times"
								title={Liferay.Language.get('close-search')}
							/>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</FocusTrap>

				{searchData &&
					Object.keys(searchData).map((key) =>
						searchData[key].map((value, index) => (
							<ClayInput
								key={`${key}${index}`}
								name={key}
								type="hidden"
								value={value}
							/>
						))
					)}
			</ManagementToolbar.Search>
		</>
	);
};

const ShowMobileButton = React.forwardRef(
	({disabled, setSearchMobile}, ref) => {
		return (
			<ManagementToolbar.Item className="navbar-breakpoint-d-none">
				<ClayButtonWithIcon
					aria-haspopup="true"
					aria-label={Liferay.Language.get('open-search')}
					className="nav-link nav-link-monospaced"
					disabled={disabled}
					displayType="unstyled"
					onClick={() => setSearchMobile(true)}
					ref={ref}
					symbol="search"
					title={Liferay.Language.get('open-search')}
				/>
			</ManagementToolbar.Item>
		);
	}
);

SearchControls.ShowMobileButton = ShowMobileButton;

export default SearchControls;
