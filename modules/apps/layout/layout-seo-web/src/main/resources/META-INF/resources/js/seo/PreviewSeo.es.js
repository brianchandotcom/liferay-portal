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
import {isObject} from 'metal';
import {PropTypes} from 'prop-types';
import React, {useEffect, useState, useCallback} from 'react';

import {previewSeoOnChange} from './PreviewSeoEvents.es';

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
	const defaultLanguage = Liferay.ThemeDisplay.getLanguageId();
	const [language, setLanguage] = useState(defaultLanguage);
	const [fields, setFields] = useState({});
	const isMounted = useIsMounted();
	const [inputTargets, setInputTargets] = useState({});

	const getDefaultValue = useCallback(
		type => {
			let defaultValue = targets[type] && targets[type].defaultValue;

			if (isObject(defaultValue)) {
				defaultValue =
					defaultValue[language] || defaultValue[defaultLanguage];
			}

			return defaultValue;
		},
		[defaultLanguage, targets, language]
	);

	useEffect(() => {
		const inputLocalizedLocaleChangedHandle = Liferay.on(
			'inputLocalized:localeChanged',
			event => {
				const newLanguage =
					event.item && event.item.getAttribute('data-value');

				if (newLanguage && isMounted()) {
					setLanguage(newLanguage);
				}
			}
		);

		return () => {
			Liferay.detach(inputLocalizedLocaleChangedHandle);
		};
	}, [isMounted]);

	useEffect(() => {
		const setFieldState = ({type, ...props}) => {
			if (!isMounted()) return;

			setFields(state => ({
				...state,
				[type]: {...props}
			}));
		};

		const inputTargets = Object.entries(targets).reduce(
			(acc, [type, {id, value}]) => {
				if (id) {
					const input = document.getElementById(
						`${portletNamespace}${id}`
					);
					const defaultLanguageInput = document.getElementById(
						`${portletNamespace}${id}_${defaultLanguage}`
					);
					acc[type] = {
						defaultLanguageInput,
						input,
						type
					};
				}
				else if (value) {
					setFieldState({type, value});
				}

				return acc;
			},
			{}
		);

		setInputTargets(inputTargets);

		const handleInputChange = ({event, type}) => {
			const target = event.target;

			if (!target) {
				return;
			}

			const {disabled, value} = target;
			setFieldState({disabled, type, value});
		};

		const inputs = Object.values(inputTargets).reduce(
			(acc, {input, type}) => {
				const listener = event => {
					handleInputChange({
						event,
						type
					});
				};

				input.addEventListener('input', listener);

				acc.push({input, listener});

				return acc;
			},
			[]
		);

		const previewSeoOnChangeHandle = previewSeoOnChange(
			portletNamespace,
			setFieldState
		);

		return () => {
			inputs.forEach(({input, listener}) =>
				input.removeEventListener('input', listener)
			);

			Liferay.detach(previewSeoOnChangeHandle);
		};
	}, [defaultLanguage, isMounted, portletNamespace, targets]);

	useEffect(() => {
		if (!isMounted()) return;

		const newFieldsState = Object.values(inputTargets).reduce(
			(acc, {input, type}) => {
				const {disabled, value} = input;
				acc[type] = {disabled, value};

				return acc;
			},
			{}
		);

		setFields(prevFieldsState => ({...prevFieldsState, ...newFieldsState}));
	}, [inputTargets, isMounted, language]);

	const getValue = type => {
		const disabled = fields[type] && fields[type].disabled;
		let value = fields[type] && fields[type].value;

		if (disabled || !value) {
			const defaultLanguageInput =
				inputTargets[type] && inputTargets[type].defaultLanguageInput;

			const defaultLanguageInputValue =
				defaultLanguageInput &&
				!defaultLanguageInput.disabled &&
				defaultLanguageInput.value;

			if (language !== defaultLanguage && defaultLanguageInputValue) {
				value = defaultLanguageInputValue;
			}
			else {
				value = getDefaultValue(type);
			}
		}

		return value || '';
	};

	return (
		<PreviewSeo
			description={getValue('description')}
			direction={Liferay.Language.direction[language]}
			displayType={displayType}
			imgUrl={getValue('imgUrl')}
			title={getValue('title')}
			titleSuffix={titleSuffix}
			url={getValue('url')}
		/>
	);
};

const targetShape = PropTypes.shape({
	defaultValue: PropTypes.oneOfType([PropTypes.string, PropTypes.object]),
	id: PropTypes.string,
	value: PropTypes.string
});

PreviewSeoContainer.propTypes = {
	targets: PropTypes.shape({
		description: targetShape,
		imgUrl: targetShape,
		title: targetShape,
		url: targetShape
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
