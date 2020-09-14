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

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import React, {createContext, useContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {usePageTitle} from '../../../shared/hooks/usePageTitle.es';
import {SLAContext} from '../SLAContainer.es';
import BlockedSLAInfo from './BlockedSLAInfo.es';
import {Body} from './SLAListPageBody.es';
import {Header} from './SLAListPageHeader.es';
import DeleteSLAModal from './modal/DeleteSLAModal.es';

const SLAListPage = ({page, pageSize, processId}) => {
	const {SLAUpdated, setSLAUpdated} = useContext(SLAContext);

	const [itemToRemove, setItemToRemove] = useState(null);
	const [visible, setVisible] = useState(false);

	const {data, fetchData} = useFetch({
		params: {page, pageSize},
		url: `/processes/${processId}/slas`,
	});

	usePageTitle(Liferay.Language.get('slas'));

	const slaContextState = {
		itemToRemove,
		setVisible,
		showDeleteModal: (id) => {
			setVisible(true);
			setItemToRemove(id);
		},
		visible,
	};

	const promises = useMemo(() => {
		if (!visible) {
			return [fetchData()];
		}

		return [];
	}, [fetchData, visible]);

	return (
		<SLAListPageContext.Provider value={slaContextState}>
			<SLAListPage.Header processId={processId} />

			<ClayLayout.ContainerFluid>
				<BlockedSLAInfo processId={processId} />

				{SLAUpdated && (
					<ClayAlert
						displayType="info"
						onClose={() => setSLAUpdated(false)}
						title={Liferay.Language.get('info')}
					>
						<span>
							{`${Liferay.Language.get(
								'one-or-more-slas-are-being-updated'
							)} ${Liferay.Language.get(
								'there-may-be-a-delay-before-sla-changes-are-fully-propagated'
							)}`}
						</span>
					</ClayAlert>
				)}

				<PromisesResolver promises={promises}>
					<SLAListPage.Body
						{...data}
						page={page}
						pageSize={pageSize}
					/>
				</PromisesResolver>

				<SLAListPage.DeleteSLAModal />
			</ClayLayout.ContainerFluid>
		</SLAListPageContext.Provider>
	);
};

const SLAListPageContext = createContext();

SLAListPage.Body = Body;
SLAListPage.Header = Header;
SLAListPage.DeleteSLAModal = DeleteSLAModal;

export {SLAListPageContext};
export default SLAListPage;
