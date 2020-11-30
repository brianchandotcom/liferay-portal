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
import React, {useEffect, useState} from 'react';

import DLExternalVideoInput from './components/DLExternalVideoInput';
import DLExternalVideoPreview from './components/DLExternalVideoPreview';
import {useDLExternalVideoFields} from './utils/hooks';

const DLExternalVideoDLFilePicker = ({
	externalVideoHTML,
	externalVideoURL,
	getDLExternalVideoFieldsURL,
	namespace,
	onFilePickCallback,
}) => {
	const [url, setUrl] = useState(externalVideoURL);
	const {error, fields, loading} = useDLExternalVideoFields({
		getDLExternalVideoFieldsURL,
		namespace,
		url,
	});

	useEffect(() => {
		if (fields) {
			window[onFilePickCallback](fields);
		}
	}, [fields, onFilePickCallback]);

	return (
		<>
			<DLExternalVideoInput onChange={setUrl} url={url} />

			<DLExternalVideoPreview
				error={error}
				loading={loading}
				small
				videoHTML={fields ? fields.HTML : externalVideoHTML}
			/>
		</>
	);
};

DLExternalVideoDLFilePicker.propTypes = {
	externalVideoHTML: PropTypes.string,
	externalVideoURL: PropTypes.string,
	getDLExternalVideoFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onFilePickCallback: PropTypes.string.isRequired,
};

export default DLExternalVideoDLFilePicker;
