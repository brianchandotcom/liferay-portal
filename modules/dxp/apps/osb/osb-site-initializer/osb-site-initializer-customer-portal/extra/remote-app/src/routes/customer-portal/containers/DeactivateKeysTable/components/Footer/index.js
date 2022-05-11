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

import {Button as ClayButton} from '@clayui/core';
import {useCallback, useMemo, useState} from 'react';
import {Link} from 'react-router-dom';
import i18n from '../../../../../../common/I18n';
import DeactivateButton from '../DeactivateButton';

const ACTIVATION_ROOT_ROUTER = 'activation';

const DeactivateKeysTableFooter = ({
	accountKey,
	activationKeysByStatusPaginatedChecked,
	activationKeysState,
	productName,
	sessionId,
}) => {
	const [status, setStatus] = useState({
		deactivate: '',
	});
	const [setActivationKeys] = activationKeysState;

	const urlPreviousPage = `/${accountKey}/${ACTIVATION_ROOT_ROUTER}/${productName.toLowerCase()}`;

	const handleDeactivate = useCallback(
		() =>
			setActivationKeys((previousActivationKeys) =>
				previousActivationKeys.filter(
					(activationKey) =>
						!activationKeysByStatusPaginatedChecked.find(
							({id}) => activationKey.id === id
						)
				)
			),
		[activationKeysByStatusPaginatedChecked, setActivationKeys]
	);

	const filterCheckedActivationKeys = useMemo(
		() =>
			activationKeysByStatusPaginatedChecked.reduce(
				(
					filterCheckedActivationKeysAccumulator,
					activationKeyChecked,
					index
				) =>
					`${filterCheckedActivationKeysAccumulator}${
						index > 0 ? '&' : ''
					}licenseKeyIds=${activationKeyChecked.id}`,
				''
			),

		[activationKeysByStatusPaginatedChecked]
	);

	return (
		<div className="d-flex justify-content-between">
			<Link to={urlPreviousPage}>
				<ClayButton className="text-neutral-10" displayType="link">
					{i18n.translate('cancel')}
				</ClayButton>
			</Link>

			<DeactivateButton
				activationKeysByStatusPaginatedChecked={
					activationKeysByStatusPaginatedChecked
				}
				deactivateKeysStatus={status.deactivate}
				filterCheckedActivationKeys={filterCheckedActivationKeys}
				handleDeactivate={handleDeactivate}
				sessionId={sessionId}
				setDeactivateKeysStatus={(value) =>
					setStatus((previousStatus) => ({
						...previousStatus,
						deactivate: value,
					}))
				}
				urlPreviousPage={urlPreviousPage}
			/>
		</div>
	);
};

export default DeactivateKeysTableFooter;
