/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import React, {useContext} from 'react';

import ContentView from '../../../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../../../shared/components/list/RetryButton.es';
import PaginationBar from '../../../../../../shared/components/pagination-bar/PaginationBar.es';
import {usePaginationState} from '../../../../../../shared/hooks/usePaginationState.es';
import {AppContext} from '../../../../../AppContext.es';
import Table from './SelectAssigneesStepTable.es';

function Body({data, setRetry, tasks}) {
	const {
		deltaValues: [initialPageSize],
	} = useContext(AppContext);

	const {paginatedItems, pagination} = usePaginationState({
		initialPageSize,
		items: tasks,
	});

	const statesProps = {
		errorProps: {
			actionButton: (
				<RetryButton onClick={() => setRetry((retry) => retry + 1)} />
			),
			className: 'mt-5 py-8',
			hideAnimation: true,
			message: Liferay.Language.get('failed-to-retrieve-assignees'),
			messageClassName: 'small',
		},
		loadingProps: {
			className: 'mt-5 py-8',
			message: Liferay.Language.get('retrieving-all-possible-assignees'),
			messageClassName: 'small',
		},
	};

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
}

Body.Table = Table;

export default Body;
