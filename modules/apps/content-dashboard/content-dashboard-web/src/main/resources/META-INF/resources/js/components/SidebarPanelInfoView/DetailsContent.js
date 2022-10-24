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

import PropTypes from 'prop-types';
import React from 'react';

import Categorization from './Categorization';
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
	tags = [],
	title,
	viewURLs = [],
	vocabularies,
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

				<Categorization tags={tags} vocabularies={vocabularies} />
			</>

			<CollapsibleSection title={Liferay.Language.get('details')}>
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
