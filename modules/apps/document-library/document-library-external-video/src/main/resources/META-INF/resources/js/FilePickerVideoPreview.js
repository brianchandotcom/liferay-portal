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

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {addParams, fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useDebounceCallback} from './utils/hooks';
import validateUrl from './utils/validateUrl';

const FilePickerVideoPreview = ({
	externalVideoHTML = '',
	externalVideoURL = '',
	getDLExternalVideoFieldsURL,
	namespace,
	onFilePickCallback,
}) => {
	const inputName = 'externalVideoURLInput';
	const [url, setUrl] = useState(externalVideoURL);
	const [loading, setLoading] = useState(false);
	const [HTML, setHTML] = useState(externalVideoHTML);
	const isMounted = useIsMounted();

	const [getFields] = useDebounceCallback((dlExternalVideoURL) => {
		fetch(
			addParams(
				{
					[`${namespace}dlExternalVideoURL`]: dlExternalVideoURL,
				},
				getDLExternalVideoFieldsURL
			)
		)
			.then((res) => res.json())
			.then((fields) => {
				if (isMounted()) {
					setLoading(false);
					setHTML(fields.HTML);
					window[onFilePickCallback](fields);
				}
			})
			.catch(() => {
				if (isMounted()) {
					setLoading(false);
					setHTML(externalVideoURL);
				}
			});
	}, 500);

	const handleUrlChange = (event) => {
		const value = event.target.value.trim();
		setUrl(value);

		if (value && validateUrl(value)) {
			setLoading(true);
			getFields(value);
		}
		else {
			setLoading(false);
			setHTML(externalVideoURL);
		}
	};

	return (
		<>
			<label htmlFor={inputName}>
				{Liferay.Language.get('video-url')}
			</label>
			<ClayInput
				id={inputName}
				onChange={handleUrlChange}
				placeholder="http://"
				type="text"
				value={url}
			/>
			<p className="form-text">
				{Liferay.Language.get('video-url-help')}
			</p>

			<div className="file-picker-preview-video">
				{HTML ? (
					<div
						className="file-picker-preview-video-container"
						dangerouslySetInnerHTML={{__html: HTML}}
					/>
				) : (
					<div className="file-picker-preview-video-container">
						<div className="file-picker-preview-video-placeholder">
							{loading ? (
								<ClayLoadingIndicator />
							) : (
								<ClayIcon symbol="video" />
							)}
						</div>
					</div>
				)}
			</div>
		</>
	);
};

FilePickerVideoPreview.propTypes = {
	externalVideoHTML: PropTypes.string,
	externalVideoURL: PropTypes.string,
	getDLExternalVideoFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onFilePickCallback: PropTypes.string.isRequired,
};

export default FilePickerVideoPreview;
