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

import {useIsMounted} from 'frontend-js-react-web';
import {PropTypes} from 'prop-types';
import React, {useEffect, useState} from 'react';

import {PreviewSeoOnChange} from './PreviewSeoEvents.es';

const MAX_LENGTH_DESCIPTION = 160;

const PreviewSeo = ({
	description = '',
	direction = 'ltr',
	displayType = 'serp',
	imgUrl = '',
	title = '',
	titleSuffix = '',
	url = ''
}) => {
	const titleUrl = [
		<div className="preview-seo-title text-truncate" key="title">
			{title}
			{titleSuffix && ` - ${titleSuffix}`}
		</div>,
		<div className="preview-seo-url text-truncate" key="url">
			{url}
		</div>
	];

	return (
		<div
			className={`preview-seo preview-seo-${displayType}`}
			dir={direction}
		>
			{imgUrl && (
				<div className="aspect-ratio aspect-ratio-191-to-100 preview-seo-image">
					<img
						alt=""
						className="aspect-ratio-item aspect-ratio-item-center-middle aspect-ratio-item-flush"
						src={imgUrl}
					/>
				</div>
			)}
			{displayType === 'og' ? titleUrl.reverse() : titleUrl}
			<div className="preview-seo-description">
				{description.length < MAX_LENGTH_DESCIPTION
					? description
					: `${description.slice(0, MAX_LENGTH_DESCIPTION)}\u2026`}
			</div>
		</div>
	);
};
PreviewSeo.propTypes = {
	description: PropTypes.string,
	direction: PropTypes.oneOf(['ltr', 'rtl']),
	displayType: PropTypes.string,
	imgUrl: PropTypes.string,
	title: PropTypes.string,
	titleSuffix: PropTypes.string,
	url: PropTypes.string
};

const PreviewSeoContainer = ({
	displayType,
	portletNamespace,
	targets,
	titleSuffix
}) => {
	const [description, setDescription] = useState(
		targets['description'] && targets['description'].defaultValue
	);
	const [imgUrl, setImgUrl] = useState(
		targets['imgUrl'] && targets['imgUrl'].defaultValue
	);
	const [language, setLanguage] = useState(
		Liferay.ThemeDisplay.getLanguageId()
	);
	const [title, setTitle] = useState(
		targets['title'] && targets['title'].defaultValue
	);
	const [url, setUrl] = useState(
		targets['url'] && targets['url'].defaultValue
	);

	const isMounted = useIsMounted();

	useEffect(() => {
		const setPreviewState = ({disabled, type, value = ''}) => {
			if (!isMounted()) return;

			const defaultValue = targets[type] && targets[type].defaultValue;
			const customizable = targets[type] && targets[type].customizable;

			if (disabled || (!customizable && !value)) {
				value = defaultValue || '';
			}

			if (type === 'description') {
				setDescription(value);
			} else if (type === 'title') {
				setTitle(value);
			} else if (type === 'url') {
				setUrl(value);
			} else if (type === 'imgUrl') {
				setImgUrl(value);
			}
		};

		const handleInputChange = ({event, type}) => {
			const target = event.target;

			if (!target) {
				return;
			}

			setPreviewState({
				type,
				value: target.value
			});
		};

		const inputs = Object.entries(targets).reduce((acc, [type, {id}]) => {
			if (id) {
				const listener = event => {
					handleInputChange({
						event,
						type
					});
				};

				const node = document.getElementById(
					`${portletNamespace}${id}`
				);

				node.addEventListener('input', listener);

				setPreviewState({
					disabled: node.disabled,
					type,
					value: node.value
				});

				acc.push({listener, node, type});
			}

			return acc;
		}, []);

		const PreviewSeoOnChangeHandle = PreviewSeoOnChange(
			portletNamespace,
			setPreviewState
		);

		const inputLocalizedLocaleChangedHandle = Liferay.on(
			'inputLocalized:localeChanged',
			event => {
				const newLanguage =
					event.item && event.item.getAttribute('data-value');

				if (newLanguage) {
					setLanguage(newLanguage);
				}

				inputs.forEach(({node, type}) =>
					setPreviewState({
						disabled: node.disabled,
						type,
						value: node.value
					})
				);
			}
		);

		return () => {
			inputs.forEach(({listener, node}) =>
				node.removeEventListener('input', listener)
			);

			Liferay.detach(inputLocalizedLocaleChangedHandle);
			Liferay.detach(PreviewSeoOnChangeHandle);
		};
	}, [isMounted, portletNamespace, targets]);

	return (
		<PreviewSeo
			description={description}
			direction={Liferay.Language.direction[language]}
			displayType={displayType}
			imgUrl={imgUrl}
			title={title}
			titleSuffix={titleSuffix}
			url={url}
		/>
	);
};

PreviewSeoContainer.propTypes = {
	targets: PropTypes.shape({
		description: PropTypes.shape({
			defaultValue: PropTypes.string,
			id: PropTypes.string
		}),
		imgUrl: PropTypes.shape({
			defaultValue: PropTypes.string,
			id: PropTypes.string
		}),
		title: PropTypes.shape({
			defaultValue: PropTypes.string,
			id: PropTypes.string
		}),
		url: PropTypes.shape({
			defaultValue: PropTypes.string,
			id: PropTypes.string
		})
	}).isRequired
};

export default function(props) {
	return (
		<PreviewSeoContainer
			{...props}
			portletNamespace={`_${props.portletNamespace}_`}
		/>
	);
}
