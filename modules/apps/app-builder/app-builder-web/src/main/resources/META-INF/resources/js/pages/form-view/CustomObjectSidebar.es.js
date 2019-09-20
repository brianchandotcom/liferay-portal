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
import React, {useRef, useState, useEffect} from 'react';
import Sidebar from '../../components/sidebar/Sidebar.es';
import classNames from 'classnames';
import {useKeyDown} from '../../hooks/index.es';
import CustomObjectFieldsList from './CustomObjectFieldsList.es';

const Header = ({keywords, onSearch}) => {
	const [searchMode, setSearchMode] = useState(false);

	const onClickClose = () => setSearchMode(false);
	const onClickSearch = () => setSearchMode(true);

	const onChangeSearchInput = ({target}) => onSearch(target.value);

	useKeyDown(() => {
		if (searchMode) {
			setSearchMode(false);
		}
	}, 27);

	const searchInputRef = useRef();

	useEffect(() => {
		if (searchMode && searchInputRef.current) {
			searchInputRef.current.focus();
		}
	}, [searchInputRef, searchMode]);

	return (
		<div
			className={classNames(
				'custom-object-sidebar-header',
				'mt-4',
				'p-2',
				{
					'ml-4': !searchMode
				}
			)}
		>
			<div className="autofit-row autofit-row-center">
				{searchMode ? (
					<>
						<div className="autofit-col autofit-col-expand">
							<ClayInput.Group>
								<ClayInput.GroupItem>
									<ClayInput
										aria-label={Liferay.Language.get(
											'search'
										)}
										className="input-group-inset input-group-inset-after"
										onChange={onChangeSearchInput}
										placeholder={Liferay.Language.get(
											'search'
										)}
										ref={searchInputRef}
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
							</ClayInput.Group>
						</div>
						<div className="autofit-col ml-2" key="closeButton">
							<ClayButtonWithIcon
								displayType="unstyled"
								onClick={onClickClose}
								symbol="times"
							/>
						</div>
					</>
				) : (
					<>
						<div className="autofit-col autofit-col-expand">
							<h3>Product</h3>
						</div>

						<div className="autofit-col" key="searchButton">
							<ClayButtonWithIcon
								displayType="unstyled"
								onClick={onClickSearch}
								symbol="search"
							/>
						</div>

						<div className="autofit-col" key="addButton">
							<ClayButtonWithIcon
								displayType="unstyled"
								symbol="plus"
							/>
						</div>
					</>
				)}
			</div>
		</div>
	);
};

export default () => {
	const [keywords, setKeywords] = useState('');
	const sidebarRef = useRef();

	return (
		<Sidebar closeable={false} ref={sidebarRef}>
			<>
				<Header
					keywords={keywords}
					onSearch={keywords => setKeywords(keywords)}
				/>

				<Sidebar.Body>
					<CustomObjectFieldsList keywords={keywords} />
				</Sidebar.Body>
			</>
		</Sidebar>
	);
};
