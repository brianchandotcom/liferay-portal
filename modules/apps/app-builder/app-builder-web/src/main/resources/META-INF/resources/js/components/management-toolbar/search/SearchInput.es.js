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

import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';
import {SearchContext} from '../../search-container/SearchContext.es';

export default () => {
	const {dispatch, state} = useContext(SearchContext);
	const [keywords, setKeywords] = useState(state.keywords);

	const onChange = event => {
		setKeywords(event.target.value);
	};

	const onClick = () => {
		dispatch({keywords: keywords.trim(), type: 'SEARCH'});
	};

	const onKeyDown = event => {
		if (event.key === 'Enter') {
			onClick();
		}
	};

	return (
		<div className="navbar-form navbar-form-autofit navbar-overlay navbar-overlay-sm-down">
			<div className="container-fluid container-fluid-max-xl">
				<div className="input-group">
					<div className="input-group-item">
						<input
							aria-label={Liferay.Language.get('search-for')}
							className="form-control input-group-inset input-group-inset-after"
							onChange={onChange}
							onKeyDown={onKeyDown}
							placeholder={Liferay.Language.get('search-for')}
							type="text"
							value={keywords}
						/>

						<div className="input-group-inset-item input-group-inset-item-after">
							<button
								className="btn btn-unstyled"
								onClick={onClick}
								type="button"
							>
								<ClayIcon symbol="search" />
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
