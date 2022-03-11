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

import ClayForm from '@clayui/form';
import {Input} from '../../../../components';
import useBannedDomains from '../../../../hooks/useBannedDomains';
import {isValidEmail} from '../../../../utils/validations.form';

const IncidentReportInput = ({activation, id}) => {
	const bannedDomains = useBannedDomains(activation.email);

	return (
		<ClayForm.Group>
			<Input
				groupStyle="pb-1"
				helper="This user will be the recepient of any high priority communications."
				label="Incident Report Contact"
				name={`activations.incidentReportContact[${id}].email`}
				placeholder="user@company.com"
				required
				type="email"
				validations={[(value) => isValidEmail(value, bannedDomains)]}
			/>
		</ClayForm.Group>
	);
};

export default IncidentReportInput;
