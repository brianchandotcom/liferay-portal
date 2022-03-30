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

import {ClayInput} from '@clayui/form';
import {Select} from '../../../../../common/components';

const KeySelect = ({minAvaliableKeysCount}) => {
	const emptyOption = {
		disabled: true,
		label: 'Select the option',
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
					label="Cluster Nodes (Maximum)"
					name="maxClusterNodes"
					options={[emptyOption, ...options]}
					required
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
};

export default KeySelect;
