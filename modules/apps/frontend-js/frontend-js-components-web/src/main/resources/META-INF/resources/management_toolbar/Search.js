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

import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import React from 'react';

const Search = ({children, onlySearch, showMobile, ...otherProps}) => {
	const content = (
		<form {...otherProps} role="search">
			{children}
		</form>
	);

	return (
		<div
			className={classNames('navbar-form navbar-form-autofit', {
				'navbar-overlay navbar-overlay-sm-down': !onlySearch,
				'show': showMobile,
			})}
		>
			{onlySearch ? (
				content
			) : (
				<ClayLayout.ContainerFluid>{content}</ClayLayout.ContainerFluid>
			)}
		</div>
	);
};

Search.propTypes = {
	onlySearch: Boolean,
	showMobile: Boolean,
};

export default Search;
