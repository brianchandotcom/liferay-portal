/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {FocusTrap} from '@clayui/core';
import {ClayInput} from '@clayui/form';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

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
	const [searchDisabled, setSearchDisabled] = useState(true);

	const onClick = () => {
		setSearchDisabled(true);

		const form = document.getElementById(`${searchFormName}_search`);

		submitForm(form);
	};

	useEffect(() => {
		if (searchMobile) {
			searchInputRef.current.focus();
		}
	}, [searchMobile]);

	useEffect(() => {
		const onPageLoad = () => {
			setSearchDisabled(false);
		};

		if (!disabled) {
			if (document.readyState === 'complete') {
				onPageLoad();
			}
			else {
				window.addEventListener('load', onPageLoad);

				return () => window.removeEventListener('load', onPageLoad);
			}
		}
	}, [disabled]);

	return (
		<>
			<ManagementToolbar.Search
				action={searchActionURL}
				id={`${searchFormName}_search`}
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
									disabled={searchDisabled}
									displayType="unstyled"
									onClick={onClick}
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
