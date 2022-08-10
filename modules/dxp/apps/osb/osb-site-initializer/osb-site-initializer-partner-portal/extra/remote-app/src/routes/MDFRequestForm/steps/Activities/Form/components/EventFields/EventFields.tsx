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

import PRMForm from '../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../common/components/PRMFormik';

interface IProps {
	index: number;
}

const EventFields = ({index}: IProps) => (
	<>
		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Description"
			name={`activities[${index}].description`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Location"
			name={`activities[${index}].location`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Venue Name"
			name={`activities[${index}].venueName`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Liferay Branding"
			name={`activities[${index}].liferayBranding`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Liferay Participation/Requirements"
			name={`activities[${index}].liferayParticipationRequirements`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Source and Size of Invite List"
			name={`activities[${index}].sourceAndSizeOfInviteeList`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Promotion"
			name={`activities[${index}].activityPromotion`}
			required
		/>
	</>
);

export default EventFields;
