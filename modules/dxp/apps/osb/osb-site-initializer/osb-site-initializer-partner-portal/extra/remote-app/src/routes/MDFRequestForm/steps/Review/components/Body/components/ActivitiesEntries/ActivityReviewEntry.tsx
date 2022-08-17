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
import useGetActivitiesName from '../../../../Hooks/useGetActivitiesName';
import useGetTacticsName from '../../../../Hooks/useGetTacticsName';
import ActivityContent from './components/ActivityContent';
import ContentMarket from './components/ContentMarket';
import DigitalMarket from './components/DigitalMarket';
import Event from './components/Event';
import MiscellaneousMarket from './components/MiscellaneousMarket';

interface IProps {
	values: MDFRequestActivity;
}

type TypeOfActivityComponent = {
	[key in string]?: JSX.Element;
};

const ActivityReviewEntry = ({values}: IProps) => {
	const {data: typeOfActivities} = useGetTypeActivities();

	const selectedTypeActivity = useSelectedTypeActivity(
		values,
		typeOfActivities?.items
	);

	const typeOfActivitiesName = useGetActivitiesName(
		values.r_typeActivityToActivities_c_typeActivityId
	);

	const TacticName = useGetTacticsName(
		values.r_typeActivityToActivities_c_typeActivityId,
		values.r_tacticToActivities_c_tacticId
	);

	const typeOfActivityComponents: TypeOfActivityComponent = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: (
			<DigitalMarket
				tacticName={TacticName}
				typeOfActivitieName={typeOfActivitiesName}
				values={values}
			/>
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: (
			<ContentMarket
				tacticName={TacticName}
				typeOfActivitieName={typeOfActivitiesName}
				values={values}
			/>
		),
		[TypeActivityExternalReferenceCode.EVENT]: (
			<Event
				tacticName={TacticName}
				typeOfActivitieName={typeOfActivitiesName}
				values={values}
			/>
		),
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarket
				tacticName={TacticName}
				typeOfActivitieName={typeOfActivitiesName}
				values={values}
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

			<ActivityContent values={values} />
		</>
	);
};
export default ActivityReviewEntry;
