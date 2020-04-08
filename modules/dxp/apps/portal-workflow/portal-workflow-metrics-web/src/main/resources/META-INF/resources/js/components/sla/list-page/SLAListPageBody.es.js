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

import ContentView from '../../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../../shared/components/list/ReloadButton.es';
import PaginationBar from '../../../shared/components/pagination-bar/PaginationBar.es';
import {Table} from './SLAListPageTable.es';

const Body = ({items, page, pageSize, totalCount}) => {
	const statesProps = {
		emptyProps: {
			hideAnimation: false,
			message: Liferay.Language.get(
				'sla-allows-to-define-and-measure-process-performance'
			),
		},
		errorProps: {
			actionButton: <ReloadButton />,
			hideAnimation: true,
			message: Liferay.Language.get(
				'there-was-a-problem-retrieving-data-please-try-reloading-the-page'
			),
			messageClassName: 'small',
		},
	};

	return (
		<ContentView {...statesProps}>
			{totalCount > 0 && (
				<>
					<Table items={items} />

					<PaginationBar
						page={page}
						pageSize={pageSize}
						totalCount={totalCount}
					/>
				</>
			)}
		</ContentView>
	);
};

export {Body};
