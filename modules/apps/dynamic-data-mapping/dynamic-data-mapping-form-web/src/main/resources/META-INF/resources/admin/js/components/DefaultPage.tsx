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

import React from 'react';

import './DefaultPage.scss';
import DefaultPageHeader from './DefaultPageHeader';

const DefaultPage: React.FC<IProps> = ({
	formDescription,
	formTitle,
	pageDescription,
	pageTitle,
}) => {
	return (
		<div className="container-fluid container-fluid-max-xl lfr-ddm__default-page">
			<DefaultPageHeader
				description={formDescription}
				title={formTitle}
			/>

			<div className="lfr-ddm__default-page-container">
				<h2 className="lfr-ddm__default-page-title">{pageTitle}</h2>

				<p className="lfr-ddm__default-page-description">
					{pageDescription}
				</p>
			</div>
		</div>
	);
};

DefaultPage.displayName = 'DefaultPage';

export default DefaultPage;

interface IProps {
	formDescription?: string;
	formTitle: string;
	pageDescription: string;
	pageTitle: string;
}
