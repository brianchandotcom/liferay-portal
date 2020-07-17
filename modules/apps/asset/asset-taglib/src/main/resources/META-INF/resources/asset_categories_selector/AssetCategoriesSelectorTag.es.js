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
import React, {useState} from 'react';

import AssetCategoriesSelector from './AssetCategoriesSelector.es';

function AssetCategoriesSelectorTag({
	eventName,
	groupIds = [],
	id,
	initialVocabularies = [],
	inputName,
	portletURL,
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
	learnHowURL: PropTypes.string,
	portletURL: PropTypes.string,
};

export default function (props) {
	const description = Liferay.Util.sub(
		Liferay.Language.get('learn-how-to-tailor-categories-to-your-needs'),
		`<a href=${props.learnHowURL} target="_blank">`,
		'</a>'
	);

	return (
		<>
			{props.learnHowURL && <p>{description}</p>}

			<AssetCategoriesSelectorTag
				{...props}
				initialVocabularies={props.vocabularies}
			/>
		</>
	);
}
