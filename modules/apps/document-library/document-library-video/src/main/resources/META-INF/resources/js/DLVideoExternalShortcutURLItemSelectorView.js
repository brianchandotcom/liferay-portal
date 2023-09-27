/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {getOpener} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DLVideoExternalShortcutInput from './components/DLVideoExternalShortcutInput';
import DLVideoExternalShortcutPreview from './components/DLVideoExternalShortcutPreview';
import {useDLVideoExternalShortcutFields} from './utils/hooks';

const DLVideoExternalShortcutURLItemSelectorView = ({
	eventName,
	getDLVideoExternalShortcutFieldsURL,
	namespace,
	returnType,
}) => {
	const [url, setUrl] = useState('');
	const {error, fields, loading} = useDLVideoExternalShortcutFields({
		getDLVideoExternalShortcutFieldsURL,
		namespace,
		url,
	});

	const isDisabled = !fields || loading;

	return (
		<form
			onSubmit={() => {
				if (isDisabled) {
					return;
				}

				getOpener().Liferay.fire(eventName, {
					data: {
						returnType,
						value: {
							html: fields.HTML,
							title: fields.TITLE || fields.URL,
						},
					},
				});
			}}
		>
			<DLVideoExternalShortcutInput
				labelTooltip={Liferay.Language.get('internal-video-tooltip')}
				onChange={setUrl}
				url={url}
			/>

			<ClayButton disabled={isDisabled} type="submit">
				{Liferay.Language.get('add')}
			</ClayButton>

			<DLVideoExternalShortcutPreview
				error={error}
				loading={loading}
				videoHTML={fields && fields.HTML}
			/>
		</form>
	);
};

DLVideoExternalShortcutURLItemSelectorView.propTypes = {
	eventName: PropTypes.string.isRequired,
	getDLVideoExternalShortcutFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
	returnType: PropTypes.string.isRequired,
};

export default DLVideoExternalShortcutURLItemSelectorView;
