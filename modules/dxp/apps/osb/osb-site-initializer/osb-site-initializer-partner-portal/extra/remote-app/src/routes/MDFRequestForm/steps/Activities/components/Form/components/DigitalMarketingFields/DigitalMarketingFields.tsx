/* eslint-disable react-hooks/exhaustive-deps */
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

import {useFormikContext} from 'formik';
import {useEffect} from 'react';

import PRMForm from '../../../../../../../../common/components/PRMForm';
import PRMFormik from '../../../../../../../../common/components/PRMFormik';
import {TacticKeys} from '../../../../../../../../common/enums/mdfRequestTactics';
import MDFRequest from '../../../../../../../../common/interfaces/mdfRequest';
import getBooleanEntries from '../../../../../../../../common/utils/getBooleanEntries';

interface IProps {
	currentActivityIndex: number;
	tactic?: TacticKeys;
}

const DigitalMarketingFields = ({currentActivityIndex, tactic}: IProps) => {
	const {setFieldValue, values} = useFormikContext<MDFRequest>();

	useEffect(() => {
		if (
			values.activities[currentActivityIndex].activityDescription
				?.nurtureDripCampaign
		) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.manySeries`,
				''
			);
		}
	}, [
		values.activities[currentActivityIndex].activityDescription
			?.nurtureDripCampaign,
	]);

	useEffect(() => {
		if (
			values.activities[currentActivityIndex].activityDescription
				?.assetsLiferayRequired
		) {
			setFieldValue(
				`activities[${currentActivityIndex}].activityDescription.assetsLiferayDescription`,
				''
			);
		}
	}, [
		values.activities[currentActivityIndex].activityDescription
			?.assetsLiferayRequired,
	]);

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Overall message/content/CTA"
				name={`activities[${currentActivityIndex}].activityDescription.overallMessageContentCTA`}
				required
			/>

			{tactic === TacticKeys.EMAIL_CAMPAIGN ? (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Landing page copy"
						name={`activities[${currentActivityIndex}].activityDescription.landingPageCopy`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.RadioGroup}
						items={getBooleanEntries()}
						label="Nurture or drip campaign?"
						name={`activities[${currentActivityIndex}].activityDescription.nurtureDripCampaign`}
						required
						small
					/>

					{values.activities[currentActivityIndex].activityDescription
						?.nurtureDripCampaign === 'true' && (
						<PRMFormik.Field
							component={PRMForm.InputText}
							label="How many in series?"
							name={`activities[${currentActivityIndex}].activityDescription.manySeries`}
							required
						/>
					)}
				</>
			) : (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Any specific sites to be used"
						name={`activities[${currentActivityIndex}].activityDescription.specificSites`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Keywords for PPC campaigns (must be approved by Liferay prior to execution)"
						name={`activities[${currentActivityIndex}].activityDescription.keywordsForPPCCampaigns`}
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Ad (any size/type)"
						name={`activities[${currentActivityIndex}].activityDescription.ad`}
					/>
				</>
			)}

			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Do you require any assets from Liferay?"
				name={`activities[${currentActivityIndex}].activityDescription.assetsLiferayRequired`}
				required
				small
			/>

			{values.activities[currentActivityIndex].activityDescription
				?.assetsLiferayRequired === 'true' && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Please describe including specifications and due dates"
					name={`activities[${currentActivityIndex}].activityDescription.assetsLiferayDescription`}
					required
				/>
			)}

			{tactic === TacticKeys.EMAIL_CAMPAIGN && (
				<PRMFormik.Field
					component={PRMForm.RadioGroup}
					items={getBooleanEntries()}
					label="Are you using any CIAB assets?"
					name={`activities[${currentActivityIndex}].activityDescription.usingCIABAssets`}
					required
					small
				/>
			)}

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="How will the Liferay brand be used in the campaign?"
				name={`activities[${currentActivityIndex}].activityDescription.howLiferayBrandUsed`}
				required
			/>
		</>
	);
};

export default DigitalMarketingFields;
