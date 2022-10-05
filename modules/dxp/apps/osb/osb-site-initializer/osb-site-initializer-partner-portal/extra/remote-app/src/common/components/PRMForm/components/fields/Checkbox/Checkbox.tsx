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

import ClayForm, {ClayCheckbox} from '@clayui/form';

import PRMFormFieldProps from '../common/interfaces/prmFormFieldProps';
import PRMFormFieldStateProps from '../common/interfaces/prmFormFieldStateProps';

const Checkbox = ({
	field,
}: PRMFormFieldProps & PRMFormFieldStateProps<boolean>) => (
	<ClayForm.Group className="mb-0">
		<ClayCheckbox
			checked={field.value}
			name={field.name}
			onChange={field.onChange}
		/>
	</ClayForm.Group>
);
export default Checkbox;
