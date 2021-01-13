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

import DLVideoExternalShortcutInput from './components/DLVideoExternalShortcutInput';
import DLVideoExternalShortcutPreview from './components/DLVideoExternalShortcutPreview';
import {useDLVideoExternalShortcutFields} from './utils/hooks';

const DLVideoExternalShortcutDLFilePicker = ({
	dlVideoExternalShortcutHTML = '',
	dlVideoExternalShortcutURL = '',
	getDLVideoExternalShortcutFieldsURL,
	namespace,
	onFilePickCallback,
}) => {
	const [url, setUrl] = useState(dlVideoExternalShortcutURL);
	const {error, fields, loading} = useDLVideoExternalShortcutFields({
		getDLVideoExternalShortcutFieldsURL,
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
			<DLVideoExternalShortcutInput onChange={setUrl} url={url} />
			<DLVideoExternalShortcutPreview
				error={error}
				framed
				loading={loading}
				small
				videoHTML={fields ? fields.HTML : dlVideoExternalShortcutHTML}
			/>
		</>
	);
};

DLVideoExternalShortcutDLFilePicker.propTypes = {
	dlVideoExternalShortcutHTML: PropTypes.string,
	dlVideoExternalShortcutURL: PropTypes.string,
	getDLVideoExternalShortcutFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	onFilePickCallback: PropTypes.string.isRequired,
};

export default DLVideoExternalShortcutDLFilePicker;
