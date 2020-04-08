/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
