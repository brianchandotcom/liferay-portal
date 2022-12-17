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
	tactic?: string;
}

const MiscellaneousMarketingFields = ({
	currentActivityIndex,
	tactic = '',
}: IProps) => {
	const CTATactics = [
		'Broadcast Advertising',
		'Campaign with Industry Publication',
		'Direct Mail',
		'Print Advertising',
	];

	const PublicationExpectedImpressionsTactics = [
		'Campaign with Industry Publication',
		'Print Advertising',
	];

	return (
		<>
			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Describe the marketing activity"
				name={`activities[${currentActivityIndex}].activityDescription.marketingActivity`}
				required
			/>

			{tactic === 'Broadcast Advertising' && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Broadcast channel"
					name={`activities[${currentActivityIndex}].activityDescription.broadcastChannel`}
					required
				/>
			)}

			{PublicationExpectedImpressionsTactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Publication"
					name={`activities[${currentActivityIndex}].activityDescription.publication`}
					required
				/>
			)}

			{tactic === 'Direct Mail' && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Target # of sends"
					name={`activities[${currentActivityIndex}].activityDescription.targetOfSends`}
					required
				/>
			)}

			{CTATactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="CTA"
					name={`activities[${currentActivityIndex}].activityDescription.cta`}
					required
				/>
			)}

			{tactic === 'Broadcast Advertising' && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="# of weeks/airing"
						name={`activities[${currentActivityIndex}].activityDescription.weeksAiring`}
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Guaranteed Impressions"
						name={`activities[${currentActivityIndex}].activityDescription.guaranteedImpressions`}
						required
					/>
				</>
			)}

			{PublicationExpectedImpressionsTactics.includes(tactic) && (
				<PRMFormik.Field
					component={PRMForm.InputText}
					label="Expected Impressions"
					name={`activities[${currentActivityIndex}].activityDescription.expectedImpressions`}
					required
				/>
			)}

			{tactic === 'Co-branded Merchandise' && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="What type of merchandise?"
						name={`activities[${currentActivityIndex}].activityDescription.typeMerchandise`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Quantity?"
						name={`activities[${currentActivityIndex}].activityDescription.quantity`}
						required
					/>
				</>
			)}

			{tactic === 'Outbound Telemarketing Sales' && (
				<>
					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Audience Target"
						name={`activities[${currentActivityIndex}].activityDescription.audienceTarget`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Source and Size of call List"
						name={`activities[${currentActivityIndex}].activityDescription.sourceAndSizeOfCallList`}
						required
					/>

					<PRMFormik.Field
						component={PRMForm.InputText}
						label="Resources necessary from Liferay"
						name={`activities[${currentActivityIndex}].activityDescription.resourcesNecessaryFromLiferay`}
					/>
				</>
			)}
		</>
	);
};

export default MiscellaneousMarketingFields;
