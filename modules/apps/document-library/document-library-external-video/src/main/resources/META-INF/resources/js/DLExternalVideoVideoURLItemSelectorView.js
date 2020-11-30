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

import DLExternalVideoInput from './components/DLExternalVideoInput';
import DLExternalVideoPreview from './components/DLExternalVideoPreview';
import {useDLExternalVideoFields} from './utils/hooks';

const DLExternalVideoVideoURLItemSelectorView = ({
	eventName,
	getDLExternalVideoFieldsURL,
	namespace,
}) => {
	const [url, setUrl] = useState('');
	const {error, fields, loading} = useDLExternalVideoFields({
		getDLExternalVideoFieldsURL,
		namespace,
		url,
	});

	return (
		<form
			onSubmit={() =>
				Liferay.Util.getOpener().Liferay.fire(eventName, {
					data: {
						returnType: 'URL',
						value: fields.URL,
					},
				})
			}
		>
			<DLExternalVideoInput onChange={setUrl} url={url} />

			<ClayButton disabled={!fields || loading} type="submit">
				{Liferay.Language.get('add')}
			</ClayButton>

			<DLExternalVideoPreview
				error={error}
				loading={loading}
				videoHTML={fields && fields.HTML}
			/>
		</form>
	);
};

DLExternalVideoVideoURLItemSelectorView.propTypes = {
	eventName: PropTypes.string.isRequired,
	getDLExternalVideoFieldsURL: PropTypes.string.isRequired,
	namespace: PropTypes.string.isRequired,
};

export default DLExternalVideoVideoURLItemSelectorView;
