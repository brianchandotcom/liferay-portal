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

import ClayForm, {ClayInput} from '@clayui/form';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {useIsMounted} from 'frontend-js-react-web';
import {fetch} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const ExternalVideoPreview = ({
	externalVideoHTML,
	externalVideoURL,
	getDLExternalVideoFieldsURL,
	namespace,
	onFilePickCallback,
}) => {
	const inputName = 'externalVideoURLInput';
	const [url, setUrl] = useState(externalVideoURL);
	const [loaded, setLoaded] = useState(false);
	const [HTML, setHTML] = useState(externalVideoHTML);
	const isMounted = useIsMounted();

	const isLoading = url && !loaded;

	const handleUrlChange = (event) => {
		const value = event.target.value.trim();
		setUrl(value);
		setLoaded(false);

		if (value) {
			fetch(
				`${getDLExternalVideoFieldsURL}&${namespace}dlExternalVideoURL=${value}`
			)
				.then((res) => res.json())
				.then((fields) => {
					if (isMounted()) {
						setLoaded(true);
						setHTML(fields.HTML);
						window[onFilePickCallback](fields);
					}
				});
		}
	};

	return (
		<>
			<ClayForm.Group>
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

				{HTML ? (
					<div
						className="bg-light mt-4"
						dangerouslySetInnerHTML={{__html: HTML}}
					/>
				) : (
					<div className="bg-light mt-4">
						{isLoading && <ClayLoadingIndicator />}
					</div>
				)}
			</ClayForm.Group>
		</>
	);
};

ExternalVideoPreview.propTypes = {
	getDLExternalVideoFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onFilePickCallback: PropTypes.string.isRequired,
};

export default ExternalVideoPreview;
