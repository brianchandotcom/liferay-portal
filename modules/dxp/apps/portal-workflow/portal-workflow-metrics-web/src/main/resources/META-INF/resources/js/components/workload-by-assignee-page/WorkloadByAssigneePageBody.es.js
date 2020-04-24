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

import React, {useMemo} from 'react';

import ContentView from '../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import {Table} from './WorkloadByAssigneePageTable.es';

const Body = ({
	filtered,
	items,
	page,
	pageSize,
	processId,
	taskNames,
	totalCount,
}) => {
	const statesProps = useMemo(
		() => ({
			emptyProps: {filtered},
			errorProps: {
				actionButton: <ReloadButton />,
				hideAnimation: true,
				message: Liferay.Language.get(
					'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
				),
			},
			loadingProps: {className: 'pb-6 pt-6 sheet'},
		}),
		[filtered]
	);

	return (
		<div className="container-fluid-1280 mt-4">
			<ContentView {...statesProps}>
				{totalCount > 0 && (
					<>
						<Body.Table
							items={items}
							processId={processId}
							taskNames={taskNames}
						/>

						<PaginationBar
							page={page}
							pageSize={pageSize}
							totalCount={totalCount}
						/>
					</>
				)}
			</ContentView>
		</div>
	);
};

Body.Table = Table;

export {Body};
