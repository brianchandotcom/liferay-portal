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

import PRMForm from '../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../common/components/PRMFormik';

interface IProps {
	currentIndex: number;
}

const Form = ({currentIndex}: IProps) => {
	return (
		<>
			<div>
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Provide a name and short description of the overall campaign"
					name={`activities[${currentIndex}].name`}
					required
				/>
			</div>
		</>
	);
};

export default Form;
