/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import classNames from 'classnames';
import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

const RenewButton = ({
	activationKeysByStatusPaginatedChecked,
	activationKeysChecked,
	bulkRenewAvailable,
	children,
	currentActivationKeyModal,
	filterCheckedActivationKeys,
	filterCheckedRenewKeys,
	identifier,
	isComplimentaryKey,
	isRenewTable,
	isVisibleModal,
	keysSelected,
	project,
}) => {
	const singleKey = 1;
	const navigate = useNavigate();
	const [isDisable, setIsDisable] = useState('');

	const renewUrl = `/${project?.accountKey}/activation/dxp/new`;

	useEffect(() => {
		const isDisableRenewButton = () => {
			if (isRenewTable) {
				if (keysSelected > 0 && keysSelected < 1) {
					return setIsDisable(false);
				}

				if (keysSelected > 1 && !bulkRenewAvailable) {
					return setIsDisable(true);
				}

				if (!keysSelected) {
					return setIsDisable(true);
				}
			}

			if (isComplimentaryKey) {
				return setIsDisable(true);
			}

			return setIsDisable(false);
		};

		isDisableRenewButton();
	}, [bulkRenewAvailable, isComplimentaryKey, isRenewTable, keysSelected]);

	const handleRedirectPage = () => {
		if (isVisibleModal) {
			return navigate(renewUrl, {
				state: {
					activationKeys: [currentActivationKeyModal],
					id: identifier,
				},
			});
		}

		if (isRenewTable) {
			if (activationKeysChecked.length === singleKey) {
				return navigate(renewUrl, {
					state: {
						activationKeys: [activationKeysChecked[0]],
						id: identifier,
					},
				});
			}

			return navigate(renewUrl, {
				state: {
					activationKeys: activationKeysChecked,
					filterCheckedRenewKeys,
					id: identifier,
				},
			});
		}

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
				className={classNames('btn mx-2 px-3 py-2', {
					'btn-outline-dark cp-deactivate-button  text-dark':
						!isVisibleModal && !isRenewTable,
				})}
				disabled={isDisable}
				onClick={() => {
					handleRedirectPage();
				}}
			>
				{children}
			</ClayButton>
		</>
	);
};

export default RenewButton;
