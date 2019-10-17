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

import {PropTypes} from 'prop-types';
import React, {useEffect, useState} from 'react';

const MAX_LENGTH_DESCIPTION = 160;

const PreviewSeo = ({
	description = '',
	suffixTitle = '',
	title = '',
	url = ''
}) => (
	<div className="preview-seo preview-seo-serp">
		<div className="preview-seo-title text-truncate">
			{title}
			{suffixTitle && ` - ${suffixTitle}`}
		</div>
		<div className="preview-seo-url text-truncate">{url}</div>
		<div className="preview-seo-description">
			{description > MAX_LENGTH_DESCIPTION
				? description
				: `${description.slice(0, MAX_LENGTH_DESCIPTION)} \u2026`}
		</div>
	</div>
);
PreviewSeo.propTypes = {
	description: PropTypes.string,
	suffixTitle: PropTypes.string,
	title: PropTypes.string,
	url: PropTypes.string
};

const PreviewSeoContainer = ({
	portletNamespace,
	suffixTitle,
	targetsIds,
	url
}) => {
	const [description, setDescription] = useState('');
	const [title, setTitle] = useState('');

	const handlerInputChange = ({event, type}) => {
		const value = event.target && event.target.value;
		if (typeof value === undefined) {
			return;
		}

		if (type === 'description') {
			setDescription(value);
		} else if (type === 'title') {
			setTitle(value);
		}
	};

	useEffect(() => {
		const inputs = Object.entries(targetsIds).reduce(
			(memo, [key, targetId]) => {
				const listener = event => {
					handlerInputChange({event, type: key});
				};

				const node = document.getElementById(
					`_${portletNamespace}_${targetId}`
				);

				node.addEventListener('input', listener);
				node.dispatchEvent(new Event('input'));

				memo[key] = {listener, node};

				return memo;
			},
			{}
		);

		return () => {
			Object.values(inputs).forEach(({listener, node}) =>
				node.removeEventListener('input', listener)
			);
		};
	}, [portletNamespace, targetsIds]);

	return (
		<PreviewSeo
			description={description}
			suffixTitle={suffixTitle}
			title={title}
			url={url}
		/>
	);
};

PreviewSeoContainer.propTypes = {
	targetsIds: PropTypes.shape({
		description: PropTypes.string.isRequired,
		title: PropTypes.string.isRequired
	}).isRequired,
	url: PropTypes.string.isRequired
};

export default PreviewSeoContainer;
