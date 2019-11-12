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
import ClayForm, {ClayInput} from '@clayui/form';
import React, {useContext, useEffect, useState} from 'react';

import SearchContext from './SearchContext.es';

export default ({disabled}) => {
	const [searchContext, dispatch] = useContext(SearchContext);
	const [keywords, setKeywords] = useState(searchContext.keywords);

	useEffect(() => {
		setKeywords(searchContext.keywords);
	}, [searchContext.keywords]);

	const onChange = ({target: {value}}) => {
		setKeywords(value);
	};

	const handleSubmit = () => {
		dispatch({keywords: keywords.trim(), type: 'SEARCH'});
	};

	return (
		<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down">
			<div className="container-fluid container-fluid-max-xl">
				<ClayForm
					onSubmit={event => {
						event.preventDefault();

						handleSubmit();
					}}
				>
					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayInput
								aria-label={Liferay.Language.get('search-for')}
								className="input-group-inset input-group-inset-after"
								disabled={disabled}
								onChange={onChange}
								placeholder={Liferay.Language.get('search-for')}
								type="text"
								value={keywords}
							/>
							<ClayInput.GroupInsetItem after>
								<ClayButtonWithIcon
									disabled={disabled}
									displayType="unstyled"
									onClick={handleSubmit}
									symbol="search"
								/>
							</ClayInput.GroupInsetItem>
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm>
			</div>
		</div>
	);
};
