/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback} from 'react';
import i18n from '../../../../../../../common/I18n';
import {VirtualCluster} from '../../../../../../../common/icons';
import {hasCluster} from '../../hasCluster';
import {hasVirtualCluster} from '../../index';

const KeyTypeColumn = ({activationKey}) => {
	const hasVirtualClusterForActivationKeys = hasVirtualCluster(
		activationKey?.licenseEntryType
	);

	const hasClusterForActivationKeys = hasCluster(
		activationKey?.licenseEntryType
	);

	const getColumnTitle = useCallback(() => {
		if (hasVirtualClusterForActivationKeys) {
			return i18n.translate('virtual-cluster');
		}

		if (hasClusterForActivationKeys) {
			return i18n.translate('cluster');
		}

		return i18n.translate('on-premise');
	}, [hasClusterForActivationKeys, hasVirtualClusterForActivationKeys]);

	return (
		<div className="align-items-start d-flex">
			{hasVirtualClusterForActivationKeys && (
				<VirtualCluster className="ml-n4 mr-1" />
			)}

			<div>
				<p className="font-weight-bold m-0 text-neutral-10">
					{getColumnTitle()}
				</p>

				<p className="font-weight-normal m-0 text-neutral-7 text-paragraph-sm text-truncate">
					{hasVirtualClusterForActivationKeys ||
					hasClusterForActivationKeys
						? i18n.sub('x-cluster-nodes-keys', [
								activationKey.maxClusterNodes,
						  ])
						: activationKey.hostName || '-'}
				</p>
			</div>
		</div>
	);
};

export {KeyTypeColumn};
