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
import {TacticKeys} from '../../../../../../../../common/enums/mdfRequestTactics';

interface IProps {
	currentActivityIndex: number;
	tactic?: TacticKeys;
}

const EventFields = ({currentActivityIndex, tactic}: IProps) => (
	<>
		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Description"
			name={`activities[${currentActivityIndex}].activityDescription.description`}
			required
		/>

		{tactic === TacticKeys.WEBINAR ? (
			<>
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Webinar Topic"
					name={`activities[${currentActivityIndex}].activityDescription.webinarTopic`}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Webinar Host/Platform"
					name={`activities[${currentActivityIndex}].activityDescription.webinarHostPlatform`}
					required
				/>
			</>
		) : (
			<>
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Activity Location"
					name={`activities[${currentActivityIndex}].activityDescription.location`}
					required
				/>

				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Venue Name"
					name={`activities[${currentActivityIndex}].activityDescription.venueName`}
					required
				/>
			</>
		)}

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="What Liferay Branding is required?"
			name={`activities[${currentActivityIndex}].activityDescription.liferayBranding`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Who from Liferay will Participate/What is Required from Liferay?"
			name={`activities[${currentActivityIndex}].activityDescription.liferayParticipationRequirements`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Source and Size of Invite List"
			name={`activities[${currentActivityIndex}].activityDescription.sourceAndSizeOfInviteeList`}
			required
		/>

		<PRMFormik.Field
			component={PRMForm.InputText}
			label="Activity Promotion"
			name={`activities[${currentActivityIndex}].activityDescription.activityPromotion`}
			required
		/>
	</>
);

export default EventFields;
