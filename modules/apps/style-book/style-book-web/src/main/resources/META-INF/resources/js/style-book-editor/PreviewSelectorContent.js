/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import {LayoutSelector} from './LayoutSelector';
import {LayoutTypeSelector} from './LayoutTypeSelector';
import {usePreviewLayoutType} from './contexts/LayoutContext';

export function PreviewSelectorContent({isMobile}) {
	const previewLayoutType = usePreviewLayoutType();

	return (
		<>
			<span
				className={classNames('font-weight-bold', {
					'd-block mb-3': isMobile,
				})}
			>
				{Liferay.Language.get('preview')}
			</span>

			<LayoutTypeSelector
				className={isMobile ? 'w-100' : undefined}
				layoutType={previewLayoutType}
			/>

			<LayoutSelector
				className={isMobile ? 'mt-3 w-100' : undefined}
				key={previewLayoutType}
				layoutType={previewLayoutType}
			/>
		</>
	);
}

PreviewSelectorContent.propTypes = {
	isMobile: PropTypes.bool,
};
