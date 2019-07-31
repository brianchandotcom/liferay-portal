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
import React, {useState} from 'react';

export default ({keywords, onSearch}) => {
	const [value, setValue] = useState(keywords);

	const onChange = event => {
		setValue(event.target.value);
	};

	const onClick = () => {
		const trimmed = value.trim();

		if (trimmed !== '') {
			onSearch(trimmed);
		}
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
							aria-label="Search for"
							className="form-control input-group-inset input-group-inset-after"
							onChange={onChange}
							onKeyDown={onKeyDown}
							placeholder="Search for"
							type="text"
							value={value}
						/>

						<div className="input-group-inset-item input-group-inset-item-after">
							<button
								className="btn btn-unstyled"
								onClick={onClick}
								type="button"
							>
								<ClayIcon
									spritemap={`${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`}
									symbol="search"
								/>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};
