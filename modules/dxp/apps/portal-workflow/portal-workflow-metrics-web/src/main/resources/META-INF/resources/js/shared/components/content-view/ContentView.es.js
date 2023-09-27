/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import EmptyState from '../empty-state/EmptyState.es';
import LoadingState from '../loading/LoadingState.es';
import PromisesResolver from '../promises-resolver/PromisesResolver.es';

const ContentView = ({
	children,
	emptyProps = {},
	errorProps = {},
	loadingProps = {},
}) => {
	return (
		<>
			<PromisesResolver.Pending>
				<LoadingState {...loadingProps} />
			</PromisesResolver.Pending>

			<PromisesResolver.Resolved>
				{children || <EmptyState {...emptyProps} />}
			</PromisesResolver.Resolved>

			<PromisesResolver.Rejected>
				<EmptyState {...errorProps} />
			</PromisesResolver.Rejected>
		</>
	);
};

export default ContentView;
