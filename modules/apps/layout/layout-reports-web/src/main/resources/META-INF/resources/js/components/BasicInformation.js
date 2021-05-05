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
import PropTypes from 'prop-types';
import React from 'react';

import Translation from './Translation';

export default function BasicInformation({
	defaultLanguageId,
	pageURLs,
	selectedLanguageId,
}) {
	const selectedPageURL = pageURLs.find(
		({languageId}) =>
			languageId === (selectedLanguageId || defaultLanguageId)
	);

	return (
		<ClayLayout.ContentRow verticalAlign="center">
			<ClayLayout.ContentCol>
				<div className="inline-item-before">
					<ClayLayout.ContentRow>
						<ClayLayout.ContentCol>
							<Translation
								defaultLanguageId={defaultLanguageId}
								pageURLs={pageURLs}
								selectedLanguageId={selectedPageURL.languageId}
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</div>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol expand>
				<ClayLayout.ContentRow>
					<span className="font-weight-semi-bold text-truncate-inline">
						<span
							className="text-truncate"
							data-tooltip-align="bottom"
							title={selectedPageURL.title}
						>
							{selectedPageURL.title}
						</span>
					</span>
				</ClayLayout.ContentRow>
				<ClayLayout.ContentRow>
					<span
						className="text-truncate text-truncate-reverse"
						data-tooltip-align="bottom"
						title={selectedPageURL.url}
					>
						<bdi className="text-secondary">
							{selectedPageURL.url}
						</bdi>
					</span>
				</ClayLayout.ContentRow>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

BasicInformation.propTypes = {
	defaultLanguageId: PropTypes.string.isRequired,
	pageURLs: PropTypes.arrayOf(
		PropTypes.shape({
			languageId: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
			url: PropTypes.string.isRequired,
		})
	),
	selectedLanguageId: PropTypes.string,
};
