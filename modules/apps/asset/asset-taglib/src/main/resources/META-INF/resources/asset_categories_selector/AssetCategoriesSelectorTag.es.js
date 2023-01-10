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

import ClayLink from '@clayui/link';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import AssetCategoriesSelector from './AssetCategoriesSelector.es';
import {ASSET_VOCABULARY_VISIBILITY_TYPES} from './assetVocabularyVisibilityTypes.es';

function AssetCategoriesSelectorTag({
	eventName,
	groupIds = [],
	id,
	initialVocabularies = [],
	inputName,
	portletURL,
	showLabel,
}) {
	const [vocabularies, setVocabularies] = useState(initialVocabularies);

	return (
		<AssetCategoriesSelector
			eventName={eventName}
			groupIds={groupIds}
			id={id}
			inputName={inputName}
			onVocabulariesChange={setVocabularies}
			portletURL={portletURL}
			showLabel={showLabel}
			vocabularies={vocabularies}
		/>
	);
}

AssetCategoriesSelectorTag.propTypes = {
	eventName: PropTypes.string,
	groupIds: PropTypes.array,
	id: PropTypes.string,
	initialVocabularies: PropTypes.array,
	inputName: PropTypes.string,
	learnHowLink: PropTypes.object,
	portletURL: PropTypes.string,
	showLabel: PropTypes.bool,
};

export default function (props) {
	const initialPublicVocabularies = props.vocabularies.filter(
		(vocabulary) =>
			vocabulary.visibilityType ===
			ASSET_VOCABULARY_VISIBILITY_TYPES.public
	);
	const initialInternalVocabularies = props.vocabularies.filter(
		(vocabulary) =>
			vocabulary.visibilityType ===
			ASSET_VOCABULARY_VISIBILITY_TYPES.internal
	);

	return (
		<>
			{props.showLabel && props.learnHowLink && (
				<ClayLink href={props.learnHowLink.url} target="_blank">
					{props.learnHowLink.message}
				</ClayLink>
			)}

			{initialPublicVocabularies && !!initialPublicVocabularies.length && (
				<>
					{props.showLabel && (
						<>
							<div className="border-0 mb-0 sheet-subtitle text-uppercase">
								{Liferay.Language.get('public-categories')}
							</div>

							<p className="small text-secondary">
								{Liferay.Language.get(
									'they-can-be-displayed-through-pages-widgets-fragments-and-searches'
								)}
							</p>
						</>
					)}

					<AssetCategoriesSelectorTag
						{...props}
						initialVocabularies={initialPublicVocabularies}
					/>
				</>
			)}

			{initialInternalVocabularies &&
				!!initialInternalVocabularies.length && (
					<>
						{props.showLabel && (
							<>
								<div className="border-0 mb-0 sheet-subtitle text-uppercase">
									{Liferay.Language.get(
										'internal-categories'
									)}
								</div>
								<p className="text-secondary">
									{Liferay.Language.get(
										'they-are-displayed-inside-the-administration-only'
									)}
								</p>{' '}
							</>
						)}

						<AssetCategoriesSelectorTag
							{...props}
							initialVocabularies={initialInternalVocabularies}
						/>
					</>
				)}
		</>
	);
}
