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

import {TypeActivityExternalReferenceCode} from '../../../../../../../../common/enums/typeActivityExternalReferenceCode';
import useSelectedTypeActivity from '../../../../../../../../common/hooks/useSelectedTypeActivity';
import MDFRequestActivity from '../../../../../../../../common/interfaces/mdfRequestActivity';
import useGetTypeActivities from '../../../../../../../../common/services/liferay/object/type-activities/useGetTypeActivities';
import useGetTacticName from '../../../../hooks/useGetTacticName';
import ActivityContent from './components/ActivityContent';
import ContentMarket from './components/ContentMarket';
import DigitalMarket from './components/DigitalMarket';
import Event from './components/Event';
import MiscellaneousMarket from './components/MiscellaneousMarket';

interface IProps {
	mdfRequestActivity: MDFRequestActivity;
}

type TypeOfActivityComponent = {
	[key in string]?: JSX.Element;
};

const ActivityReviewEntry = ({mdfRequestActivity}: IProps) => {
	const {data: typeOfActivities} = useGetTypeActivities();

	const selectedTypeActivity = useSelectedTypeActivity(
		mdfRequestActivity,
		typeOfActivities?.items
	);

	const TacticName = useGetTacticName(
		mdfRequestActivity.r_typeActivityToActivities_c_typeActivityId,
		mdfRequestActivity.r_tacticToActivities_c_tacticId
	);

	const typeOfActivityComponents: TypeOfActivityComponent = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: (
			<DigitalMarket
				mdfRequestActivity={mdfRequestActivity}
				tacticName={TacticName}
				typeOfActivitieName={selectedTypeActivity?.name}
			/>
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: (
			<ContentMarket
				mdfRequestActivity={mdfRequestActivity}
				tacticName={TacticName}
				typeOfActivitieName={selectedTypeActivity?.name}
			/>
		),
		[TypeActivityExternalReferenceCode.EVENT]: (
			<Event
				mdfRequestActivity={mdfRequestActivity}
				tacticName={TacticName}
				typeOfActivitieName={selectedTypeActivity?.name}
			/>
		),
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarket
				mdfRequestActivity={mdfRequestActivity}
				tacticName={TacticName}
				typeOfActivitieName={selectedTypeActivity?.name}
			/>
		),
	};

	return (
		<>
			{
				typeOfActivityComponents[
					selectedTypeActivity?.externalReferenceCode || ''
				]
			}

			<ActivityContent mdfRequestActivity={mdfRequestActivity} />
		</>
	);
};
export default ActivityReviewEntry;
