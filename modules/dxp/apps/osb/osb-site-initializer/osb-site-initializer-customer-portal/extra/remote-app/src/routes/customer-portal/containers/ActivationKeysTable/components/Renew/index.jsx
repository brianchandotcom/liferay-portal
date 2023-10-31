/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import {useNavigate} from 'react-router-dom';
import i18n from '../../../../../../common/I18n';

const RenewButton = ({
	activationKeysByStatusPaginatedChecked,
	filterCheckedActivationKeys,
	identifier,
}) => {
	const navigate = useNavigate();
	const handleRedirectPage = () => {
		navigate('new', {
			state: {
				activationKeys: activationKeysByStatusPaginatedChecked,
				filterCheckedActivationKeys,
				id: identifier,
			},
		});
	};

	return (
		<>
			<ClayButton
				className="btn btn-outline-dark cp-deactivate-button mx-2 px-3 py-2 text-dark"
				onClick={() => {
					handleRedirectPage();
				}}
			>
				{i18n.translate('renew')}
			</ClayButton>
		</>
	);
};

export default RenewButton;
