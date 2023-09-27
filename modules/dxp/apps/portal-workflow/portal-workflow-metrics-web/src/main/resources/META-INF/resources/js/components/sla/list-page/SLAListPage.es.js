/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayLayout from '@clayui/layout';
import React, {createContext, useContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../shared/hooks/useFetch.es';
import {usePageTitle} from '../../../shared/hooks/usePageTitle.es';
import {SLAContext} from '../SLAContainer.es';
import BlockedSLAInfo from './BlockedSLAInfo.es';
import Body from './SLAListPageBody.es';
import Header from './SLAListPageHeader.es';
import DeleteSLAModal from './modal/DeleteSLAModal.es';

function SLAListPage({page, pageSize, processId}) {
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

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [visible]);

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
}

const SLAListPageContext = createContext();

SLAListPage.Body = Body;
SLAListPage.Header = Header;
SLAListPage.DeleteSLAModal = DeleteSLAModal;

export {SLAListPageContext};
export default SLAListPage;
