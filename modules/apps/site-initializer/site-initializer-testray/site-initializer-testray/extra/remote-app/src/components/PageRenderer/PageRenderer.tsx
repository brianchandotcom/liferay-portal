/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactElement} from 'react';
import TestrayError from '~/TestrayError';

import EmptyState from '../EmptyState';
import Loading from '../Loading/Loading';

export type PageRendererProps = {
	children: ReactElement;
	error?: TestrayError;
	loading: boolean;
};

const PageRenderer: React.FC<PageRendererProps> = ({
	children,
	error,
	loading,
}) => {
	if (loading) {
		return <Loading />;
	}

	if (error) {
		return (
			<EmptyState
				description={error?.info?.title}
				title={error.message}
				type="EMPTY_SEARCH"
			/>
		);
	}

	return children;
};

export default PageRenderer;
