/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
