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
import React from 'react';

import ContentView from '../../../../../shared/components/content-view/ContentView.es';
import RetryButton from '../../../../../shared/components/list/RetryButton.es';
import PaginationBar from '../../../../../shared/components/pagination-bar/PaginationBar.es';
import {Table} from './SelectTasksStepTable.es';

const Body = ({filtered, items, pagination, setRetry, totalCount}) => {
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
};

Body.Table = Table;

export {Body};
