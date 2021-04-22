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

import ClayButton from '@clayui/button';
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

				Liferay.Util.getOpener().Liferay.fire(eventName, {
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
