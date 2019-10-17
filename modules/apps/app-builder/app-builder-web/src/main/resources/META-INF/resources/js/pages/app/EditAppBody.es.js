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

import React, {useRef, useState} from 'react';

import Button from '../../components/button/Button.es';
import {useRequest} from '../../hooks/index.es';
import ListItems from './ListItems.es';

export default ({endpoint, title, ...restProps}) => {
	const [keywords, setKeywords] = useState('');
	const regex = new RegExp(keywords, 'ig');
	const searchInput = useRef(null);

	const {
		response: {items = []},
		isLoading
	} = useRequest(endpoint);

	const filteredItems = items.filter(item => regex.test(item.name.en_US));

	const onSearchClear = () => {
		setKeywords('');
		searchInput.current.focus();
	};

	return (
		<>
			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<h2>{title}</h2>
				</div>
			</div>

			<div className="autofit-row pl-4 pr-4 mb-4">
				<div className="autofit-col-expand">
					<div className="input-group">
						<div className="input-group-item">
							<input
								aria-label={Liferay.Language.get('search')}
								className="form-control input-group-inset input-group-inset-after"
								onChange={event =>
									setKeywords(event.target.value)
								}
								placeholder={`${Liferay.Language.get(
									'search'
								)}...`}
								ref={searchInput}
								type="text"
								value={keywords}
							/>
							<div className="input-group-inset-item input-group-inset-item-after">
								{keywords ? (
									<Button
										displayType="unstyled"
										key="timesButton"
										onClick={onSearchClear}
										symbol="times"
									/>
								) : (
									<Button
										displayType="unstyled"
										key="searchButton"
										symbol="search"
									/>
								)}
							</div>
						</div>
					</div>
				</div>
			</div>

			<div className="autofit-row pl-4 pr-4 scrollable-container">
				<div className="autofit-col-expand">
					<ListItems
						isEmpty={filteredItems.length === 0}
						isLoading={isLoading}
						items={filteredItems}
						keywords={keywords}
						{...restProps}
					/>
				</div>
			</div>
		</>
	);
};
