/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayModal from '@clayui/modal';
import React from 'react';

import ContentView from '../../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../../shared/components/list/RetryButton.es';
import PaginationBar from '../../../../../shared/components/pagination-bar/PaginationBar.es';
import Table from './SelectTasksStepTable.es';

function Body({filtered, items, pagination, setRetry, totalCount}) {
	const statesProps = {
		emptyProps: {
			className: 'py-4',
			filtered,
			messageClassName: 'small',
		},
		errorProps: {
			actionButton: (
				<RetryButton onClick={() => setRetry((retry) => retry + 1)} />
			),
			className: 'mt-5 py-8',
			hideAnimation: true,
			message: Liferay.Language.get('unable-to-retrieve-data'),
			messageClassName: 'small',
		},
		loadingProps: {className: 'mt-5 py-8'},
	};

	return (
		<ClayModal.Body>
			<ContentView {...statesProps}>
				{totalCount > 0 && (
					<>
						<Body.Table items={items} totalCount={totalCount} />

						<PaginationBar {...pagination} withoutRouting />
					</>
				)}
			</ContentView>
		</ClayModal.Body>
	);
}

Body.Table = Table;

export default Body;
