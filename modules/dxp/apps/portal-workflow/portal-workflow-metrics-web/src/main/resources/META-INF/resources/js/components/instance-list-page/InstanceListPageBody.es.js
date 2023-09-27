/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
