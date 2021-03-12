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
import {ClayTooltipProvider} from '@clayui/tooltip';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import LanguagesDropdown from './LanguagesDropdown';

export default function BasicInformation({canonicalURLs, defaultLanguageId}) {
	const [selectedCanonicalURL, setSeletedCanonicalURL] = useState(
		canonicalURLs.find(({languageId}) => languageId === defaultLanguageId)
	);

	const handleSelectLanguageId = (selectedLanguageId) => {
		if (selectedCanonicalURL.languageId !== selectedLanguageId) {
			setSeletedCanonicalURL(
				canonicalURLs.find(
					({languageId}) => languageId === selectedLanguageId
				)
			);
		}
	};

	return (
		<ClayLayout.ContentRow>
			<ClayLayout.ContentCol>
				<div className="inline-item-before">
					<ClayLayout.ContentRow>
						<ClayLayout.ContentCol>
							<LanguagesDropdown
								canonicalURLs={canonicalURLs}
								defaultLanguageId={defaultLanguageId}
								onSelectedLanguageId={handleSelectLanguageId}
								selectedLanguageId={
									selectedCanonicalURL.languageId
								}
							/>
						</ClayLayout.ContentCol>
					</ClayLayout.ContentRow>
				</div>
			</ClayLayout.ContentCol>
			<ClayLayout.ContentCol expand>
				<ClayLayout.ContentRow>
					<ClayTooltipProvider>
						<span className="font-weight-semi-bold text-truncate-inline">
							<span
								className="text-truncate"
								data-tooltip-align="bottom"
								title={selectedCanonicalURL.title}
							>
								{selectedCanonicalURL.title}
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentRow>
				<ClayLayout.ContentRow>
					<ClayTooltipProvider>
						<span className="text-secondary text-truncate-inline text-truncate-reverse">
							<span
								className="text-truncate"
								data-tooltip-align="bottom"
								title={selectedCanonicalURL.canonicalURL}
							>
								{selectedCanonicalURL.canonicalURL}
							</span>
						</span>
					</ClayTooltipProvider>
				</ClayLayout.ContentRow>
			</ClayLayout.ContentCol>
		</ClayLayout.ContentRow>
	);
}

BasicInformation.propTypes = {
	canonicalURLs: PropTypes.arrayOf(
		PropTypes.shape({
			canonicalURL: PropTypes.string.isRequired,
			languageId: PropTypes.string.isRequired,
			title: PropTypes.string.isRequired,
		})
	),
	defaultLanguageId: PropTypes.string.isRequired,
};
