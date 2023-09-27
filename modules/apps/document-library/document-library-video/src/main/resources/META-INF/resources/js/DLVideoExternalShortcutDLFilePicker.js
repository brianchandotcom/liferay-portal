/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
