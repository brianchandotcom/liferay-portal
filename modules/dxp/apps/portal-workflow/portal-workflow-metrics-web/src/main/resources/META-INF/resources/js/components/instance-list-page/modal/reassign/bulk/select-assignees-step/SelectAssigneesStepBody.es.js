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

import ClayModal from '@clayui/modal';
import React, {useContext, useMemo} from 'react';

import ContentView from '../../../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../../../shared/components/list/RetryButton.es';
import PaginationBar from '../../../../../../shared/components/pagination-bar/PaginationBar.es';
import {usePaginationState} from '../../../../../../shared/hooks/usePaginationState.es';
import {AppContext} from '../../../../../AppContext.es';
import {Table} from './SelectAssigneesStepTable.es';

const Body = ({data, setRetry, tasks}) => {
	const {
		deltaValues: [initialPageSize],
	} = useContext(AppContext);

	const {paginatedItems, pagination} = usePaginationState({
		initialPageSize,
		items: tasks,
	});

	const statesProps = useMemo(
		() => ({
			errorProps: {
				actionButton: (
					<RetryButton
						onClick={() => setRetry((retry) => retry + 1)}
					/>
				),
				className: 'mt-5 py-8',
				hideAnimation: true,
				message: Liferay.Language.get('failed-to-retrieve-assignees'),
				messageClassName: 'small',
			},
			loadingProps: {
				className: 'mt-5 py-8',
				message: Liferay.Language.get(
					'retrieving-all-possible-assignees'
				),
				messageClassName: 'small',
			},
		}),
		[setRetry]
	);

	return (
		<ClayModal.Body>
			<ContentView {...statesProps}>
				<Body.Table data={data} items={paginatedItems} />

				<PaginationBar
					{...pagination}
					totalCount={tasks.length}
					withoutRouting
				/>
			</ContentView>
		</ClayModal.Body>
	);
};

Body.Table = Table;

export {Body};
