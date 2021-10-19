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

import ClayLayout from '@clayui/layout';
import {usePrevious} from '@liferay/frontend-js-react-web';
import React, {useContext, useMemo} from 'react';

import ContentView from '../../shared/components/content-view/ContentView.es';
import ReloadButton from '../../shared/components/list/ReloadButton.es';
import PaginationBar from '../../shared/components/pagination-bar/PaginationBar.es';
import PromisesResolver from '../../shared/components/promises-resolver/PromisesResolver.es';
import {Table} from './InstanceListPageTable.es';
import {ModalContext} from './modal/ModalProvider.es';
import InstanceDetailsModal from './modal/instance-details/InstanceDetailsModal.es';
import BulkReassignModal from './modal/reassign/bulk/BulkReassignModal.es';
import SingleReassignModal from './modal/reassign/single/SingleReassignModal.es';
import BulkTransitionModal from './modal/transition/bulk/BulkTransitionModal.es';
import SingleTransitionModal from './modal/transition/single/SingleTransitionModal.es';
import BulkUpdateDueDateModal from './modal/update-due-date/BulkUpdateDueDateModal.es';
import SingleUpdateDueDateModal from './modal/update-due-date/SingleUpdateDueDateModal.es';

function Body({
	data: {items, totalCount} = {},
	fetchData,
	filtered,
	routeParams,
}) {
	const {fetchOnClose, visibleModal} = useContext(ModalContext);
	const previousFetchData = usePrevious(fetchData);

	const promises = useMemo(() => {
		if (
			(previousFetchData !== fetchData || fetchOnClose) &&
			!visibleModal
		) {
			return [fetchData()];
		}

		return [];

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [routeParams, visibleModal]);

	const statesProps = {
		emptyProps: {
			filtered,
			message: Liferay.Language.get(
				'once-there-are-active-processes-metrics-will-appear-here'
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
		loadingProps: {},
	};

	return (
		<PromisesResolver promises={promises}>
			<ClayLayout.ContainerFluid className="mt-4">
				<ContentView {...statesProps}>
					{totalCount > 0 && (
						<>
							<Body.Table items={items} totalCount={totalCount} />

							<PaginationBar
								{...routeParams}
								totalCount={totalCount}
							/>
						</>
					)}
				</ContentView>
			</ClayLayout.ContainerFluid>

			<Body.ModalWrapper />
		</PromisesResolver>
	);
}

function ModalWrapper() {
	return (
		<>
			<BulkReassignModal />

			<BulkTransitionModal />

			<BulkUpdateDueDateModal />

			<InstanceDetailsModal />

			<SingleReassignModal />

			<SingleTransitionModal />

			<SingleUpdateDueDateModal />
		</>
	);
}

Body.Table = Table;
Body.ModalWrapper = ModalWrapper;

export default Body;
