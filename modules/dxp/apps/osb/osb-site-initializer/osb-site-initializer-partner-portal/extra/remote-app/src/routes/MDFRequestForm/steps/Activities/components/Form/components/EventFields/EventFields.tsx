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

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';

interface IProps {
	currentActivityIndex: number;
}

const EventFields = ({currentActivityIndex}: IProps) => (
	<>
		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Description"
			name={`activities[${currentActivityIndex}].description`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Location"
			name={`activities[${currentActivityIndex}].location`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Venue Name"
			name={`activities[${currentActivityIndex}].venueName`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Liferay Branding"
			name={`activities[${currentActivityIndex}].liferayBranding`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Liferay Participation/Requirements"
			name={`activities[${currentActivityIndex}].liferayParticipationRequirements`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Source and Size of Invite List"
			name={`activities[${currentActivityIndex}].sourceAndSizeOfInviteeList`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Promotion"
			name={`activities[${currentActivityIndex}].activityPromotion`}
			required
		/>
	</>
);

export default EventFields;
