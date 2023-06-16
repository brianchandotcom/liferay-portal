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

import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import isNullOrUndefined from '../../app/utils/isNullOrUndefined';

export default function SearchResultsMessage({
	resultType = Liferay.Language.get('results'),
	numberOfResults = null,
}) {
	const [text, setText] = useState('');

	useEffect(() => {
		if (!isNullOrUndefined(numberOfResults)) {
			const timeout = setTimeout(() => {
				const message = numberOfResults
					? sub(Liferay.Language.get('showing-x-x'), [
							numberOfResults,
							resultType,
					  ])
					: Liferay.Language.get('no-results-found');

				setText(message);
			}, 500);

			return () => {
				clearTimeout(timeout);
			};
		}
	}, [resultType, numberOfResults]);

	return (
		<span className="sr-only" role="status">
			{text}
		</span>
	);
}

SearchResultsMessage.propTypes = {
	numberOfResults: PropTypes.number,
	resultType: PropTypes.string,
};
