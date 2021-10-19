/* eslint-disable react-hooks/exhaustive-deps */
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

import ClayIcon from '@clayui/icon';
import ClayModal, {useModal} from '@clayui/modal';
import React, {useContext, useMemo, useState} from 'react';

import PromisesResolver from '../../../../shared/components/promises-resolver/PromisesResolver.es';
import {useFetch} from '../../../../shared/hooks/useFetch.es';
import {getSLAStatusIconInfo} from '../../../../shared/util/util.es';
import {InstanceListContext} from '../../InstanceListPageProvider.es';
import {ModalContext} from '../ModalProvider.es';
import Body from './InstanceDetailsModalBody.es';

function Header({completed, id = '', slaStatus}) {
	const slaStatusIcon = getSLAStatusIconInfo(slaStatus);

	return (
		<ClayModal.Header>
			<PromisesResolver.Resolved>
				<div className="font-weight-medium">
					<span
						className={`mr-2 sticker ${
							completed
								? 'text-secondary bg-muted'
								: `${slaStatusIcon?.textColor} ${slaStatusIcon?.bgColor}`
						}`}
					>
						<span className="inline-item">
							<ClayIcon symbol={slaStatusIcon?.name} />
						</span>
					</span>

					{`${Liferay.Language.get('item')} #${id}`}
				</div>
			</PromisesResolver.Resolved>
		</ClayModal.Header>
	);
}

function InstanceDetailsModal() {
	const [retry, setRetry] = useState(0);
	const {instanceId, setInstanceId} = useContext(InstanceListContext);
	const {closeModal, processId, visibleModal} = useContext(ModalContext);

	const {data, fetchData} = useFetch({
		url: `/processes/${processId}/instances/${instanceId}`,
	});

	const promises = useMemo(() => {
		if (instanceId) {
			return [fetchData()];
		}

		return [];
	}, [instanceId, retry]);

	const {observer} = useModal({
		onClose: () => {
			closeModal();
			setInstanceId();
		},
	});

	return visibleModal === 'instanceDetails' ? (
		<ClayModal
			className="instance-details-modal"
			observer={observer}
			size="lg"
		>
			<PromisesResolver promises={promises}>
				<InstanceDetailsModal.Header {...data} />

				<InstanceDetailsModal.Body {...data} setRetry={setRetry} />
			</PromisesResolver>
		</ClayModal>
	) : (
		<></>
	);
}

InstanceDetailsModal.Body = Body;
InstanceDetailsModal.Header = Header;

export default InstanceDetailsModal;
