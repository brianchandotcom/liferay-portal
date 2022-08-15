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
import MDFRequestActivity from '../../../../../../../../common/interfaces/mdfRequestActivity';
import useDynamicFieldEntries from '../../../../../Activities/Form/hooks/useDynamicFieldEntries';
import useSelectedTypeActivity from '../../../../../Activities/Form/hooks/useSelectedTypeActivity';
import ContentMarket from './components/ContentMarket';
import DigitalMarket from './components/DigitalMarket';
import Events from './components/Events';
import MiscellaneousMarket from './components/MiscellaneousMarket';
interface IProps {
	values: MDFRequestActivity;
}

type TypeOfActivityComponent = {
	[key in string]?: JSX.Element;
};

const ActivitiesEntries = ({values}: IProps) => {
	const {typeOfActivities} = useDynamicFieldEntries();

	const selectedTypeActivity = useSelectedTypeActivity(
		values,
		typeOfActivities
	);

	const typeOfActivityComponents: TypeOfActivityComponent = {
		[TypeActivityExternalReferenceCode.DIGITAL_MARKETING]: (
			<DigitalMarket values={values} />
		),
		[TypeActivityExternalReferenceCode.CONTENT_MARKETING]: (
			<ContentMarket values={values} />
		),
		[TypeActivityExternalReferenceCode.EVENT]: <Events values={values} />,
		[TypeActivityExternalReferenceCode.MISCELLANEOUS_MARKETING]: (
			<MiscellaneousMarket values={values} />
		),
	};

	return (
		<div>
			{
				typeOfActivityComponents[
					selectedTypeActivity?.externalReferenceCode || ''
				]
			}
		</div>
	);
};
export default ActivitiesEntries;
