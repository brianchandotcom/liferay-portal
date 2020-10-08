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
import {InstanceListContext} from '../../InstanceListPageProvider.es';
import {ModalContext} from '../ModalProvider.es';
import {Body} from './InstanceDetailsModalBody.es';

const Header = ({completed, id = '', slaResults = [], slaStatus}) => {
	const iconClasses = {
		Empty: 'hr',
		Overdue: 'exclamation-circle',
	};

	const iconColors = {
		Empty: 'text-info',
		Overdue: 'text-danger',
	};

	slaStatus = !slaResults.length ? 'Empty' : slaStatus;
	slaStatus = completed ? 'Completed' : slaStatus;

	const iconClass = iconClasses[slaStatus] || 'check-circle';
	const iconColor = iconColors[slaStatus] || 'text-success';

	return (
		<ClayModal.Header>
			<PromisesResolver.Resolved>
				<div className="font-weight-medium">
					<span className={`modal-title-indicator ${iconColor}`}>
						<ClayIcon symbol={iconClass} />
					</span>

					{`${Liferay.Language.get('item')} #${id}`}
				</div>
			</PromisesResolver.Resolved>
		</ClayModal.Header>
	);
};

const InstanceDetailsModal = () => {
	const [retry, setRetry] = useState(0);
	const {instanceId, setInstanceId} = useContext(InstanceListContext);
	const {closeModal, processId, visibleModal} = useContext(ModalContext);

	const url = useMemo(
		() => `/processes/${processId}/instances/${instanceId}`,
		[instanceId, processId]
	);

	const {data, fetchData} = useFetch({
		url,
	});

	const promises = useMemo(() => {
		if (instanceId) {
			return [fetchData()];
		}

		return [];
	}, [instanceId, fetchData, retry]);

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
};

InstanceDetailsModal.Body = Body;
InstanceDetailsModal.Header = Header;

export default InstanceDetailsModal;
