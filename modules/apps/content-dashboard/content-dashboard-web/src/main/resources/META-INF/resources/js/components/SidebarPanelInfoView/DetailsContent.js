/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import CollapsibleSection from './CollapsibleSection';
import ItemLanguages from './ItemLanguages';
import PreviewActionsDescriptionSection from './PreviewActionsDescriptionSection';
import SpecificFields from './SpecificFields';
import formatDate from './utils/formatDate';

const DetailsContent = ({
	classPK,
	createDate,
	description,
	downloadURL,
	fetchSharingButtonURL,
	handleError,
	languageTag = 'en',
	modifiedDate,
	preview,
	specificFields,
	title,
	viewURLs = [],
}) => {
	const specificItems = Object.values(specificFields);

	return (
		<>
			<>
				<PreviewActionsDescriptionSection
					description={description}
					downloadURL={downloadURL}
					fetchSharingButtonURL={fetchSharingButtonURL}
					handleError={handleError}
					preview={preview}
					title={title}
				/>
			</>

			<CollapsibleSection title={Liferay.Language.get('properties')}>
				<div className="sidebar-section">
					<SpecificFields
						fields={specificItems}
						languageTag={languageTag}
					/>

					<div className="c-mb-4 sidebar-section" key="creation-date">
						<h5 className="c-mb-1 font-weight-semi-bold">
							{Liferay.Language.get('creation-date')}
						</h5>

						<p className="text-secondary">
							{formatDate(createDate, languageTag)}
						</p>
					</div>

					<div className="c-mb-4 sidebar-section" key="modified-date">
						<h5 className="c-mb-1 font-weight-semi-bold">
							{Liferay.Language.get('modified-date')}
						</h5>

						<p className="text-secondary">
							{formatDate(modifiedDate, languageTag)}
						</p>
					</div>

					<div className="c-mb-4 sidebar-section" key="id">
						<h5 className="c-mb-1 font-weight-semi-bold">
							{Liferay.Language.get('id')}
						</h5>

						<p className="text-secondary">{classPK}</p>
					</div>
				</div>

				{!!viewURLs.length && <ItemLanguages urls={viewURLs} />}
			</CollapsibleSection>
		</>
	);
};

DetailsContent.defaultProps = {
	languageTag: 'en-US',
};

DetailsContent.propTypes = {
	classPK: PropTypes.string.isRequired,
	createDate: PropTypes.string.isRequired,
	modifiedDate: PropTypes.string.isRequired,
	specificFields: PropTypes.object.isRequired,
	viewURLs: PropTypes.array.isRequired,
};

export default DetailsContent;
