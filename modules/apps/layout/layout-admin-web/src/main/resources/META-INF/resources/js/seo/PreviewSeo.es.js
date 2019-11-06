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
	title = '',
	titleSuffix = '',
	url = ''
}) => (
	<div className="preview-seo preview-seo-serp">
		<div className="preview-seo-title text-truncate">
			{title}
			{titleSuffix && ` - ${titleSuffix}`}
		</div>
		<div className="preview-seo-url text-truncate">{url}</div>
		<div className="preview-seo-description">
			{description.length < MAX_LENGTH_DESCIPTION
				? description
				: `${description.slice(0, MAX_LENGTH_DESCIPTION)}\u2026`}
		</div>
	</div>
);
PreviewSeo.propTypes = {
	description: PropTypes.string,
	title: PropTypes.string,
	titleSuffix: PropTypes.string,
	url: PropTypes.string
};

const PreviewSeoContainer = ({portletNamespace, targets, titleSuffix}) => {
	const [description, setDescription] = useState('');
	const [title, setTitle] = useState('');
	const [url, setUrl] = useState('');

	useEffect(() => {
		const setPreviewState = ({defaultValue = '', type, value}) => {
			if (value === '' && defaultValue) {
				value = defaultValue;
			}

			if (type === 'description') {
				setDescription(value);
			} else if (type === 'title') {
				setTitle(value);
			} else if (type === 'canonicalURL') {
				setUrl(value);
			}
		};

		const handleInputChange = ({defaultValue, event, type}) => {
			const target = event.target;

			if (!target) {
				return;
			}

			setPreviewState({
				defaultValue,
				type,
				value: target.value
			});
		};

		const inputs = targets.map(({defaultValue, id, type}) => {
			const listener = event => {
				handleInputChange({
					defaultValue,
					event,
					type
				});
			};

			const node = document.getElementById(`_${portletNamespace}_${id}`);

			node.addEventListener('input', listener);

			setPreviewState({
				defaultValue,
				type,
				value: node.value
			});

			return {listener, node};
		});

		return () => {
			inputs.forEach(({listener, node}) =>
				node.removeEventListener('input', listener)
			);
		};
	}, [portletNamespace, targets]);

	return (
		<PreviewSeo
			description={description}
			title={title}
			titleSuffix={titleSuffix}
			url={url}
		/>
	);
};

PreviewSeoContainer.propTypes = {
	targets: PropTypes.arrayOf(
		PropTypes.shape({
			defaultValue: PropTypes.string,
			id: PropTypes.string.isRequired,
			type: PropTypes.string.isRequired
		})
	).isRequired
};

export default function(props) {
	return <PreviewSeoContainer {...props} />;
}
