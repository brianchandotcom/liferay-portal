/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLink from '@clayui/link';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React from 'react';

import DefaultContent from './DefaultRenderer';

interface ILinkRendererProps {
	options?: {
		decoration?: React.ComponentProps<typeof ClayLink>['decoration'];
		displayType?: React.ComponentProps<typeof ClayLink>['displayType'];
	};
	value: {
		href: string;
		label: string;
	};
}

function LinkRenderer({options, value}: ILinkRendererProps) {
	return (
		<div
			className={classNames({'table-list-title': !options?.displayType})}
		>
			<ClayLink
				decoration={options?.decoration}
				displayType={options?.displayType}
				href={value?.href}
			>
				<DefaultContent value={value?.label} />
			</ClayLink>
		</div>
	);
}

LinkRenderer.propTypes = {
	value: PropTypes.shape({
		href: PropTypes.string,
		label: PropTypes.string,
	}),
};

export default LinkRenderer;
