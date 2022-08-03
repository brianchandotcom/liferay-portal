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

import {ClaySelect} from '@clayui/form';

type Props = {
	label: string;
	name: string;
	onChange: any;
	options: any;
};

const Select = ({label, options, ...props}: Props) => {
	return (
		<>
			<label>{label}</label>

			<ClaySelect {...props}>
				{options.map((item: any, _: any) => (
					<ClaySelect.Option
						key={item.key}
						label={item.name}
						value={item.key}
					/>
				))}
			</ClaySelect>
		</>
	);
};

export default Select;
