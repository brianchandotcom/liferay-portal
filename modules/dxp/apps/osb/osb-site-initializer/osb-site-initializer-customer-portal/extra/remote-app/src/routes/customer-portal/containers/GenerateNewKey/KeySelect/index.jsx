/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import i18n from '../../../../../common/I18n';
import {Select} from '../../../../../common/components';

const KeySelect = ({
	avaliableKeysMaximumCount,
	minAvaliableKeysCount,
	selectedClusterNodes,
}) => {
	const emptyOption = {
		disabled: true,
		label: i18n.translate('select-the-option'),
		value: '',
	};

	const options = [...Array(minAvaliableKeysCount)].map((_, index) => ({
		label: index + 1,
		value: index + 1,
	}));

	return (
		<ClayInput.Group className="m-0">
			<ClayInput.GroupItem className="m-0">
				<Select
					label={
						+selectedClusterNodes === +avaliableKeysMaximumCount
							? i18n.translate('cluster-nodes-maxium')
							: i18n.translate('cluster-nodes')
					}
					name="maxClusterNodes"
					options={[emptyOption, ...options]}
					required
				/>

				<h6 className="font-weight-normal ml-3 mt-1">
					{i18n.sub(
						'cluster-nodes-may-not-exceed-the-maximum-number-of-x',
						[avaliableKeysMaximumCount]
					)}
				</h6>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default KeySelect;
