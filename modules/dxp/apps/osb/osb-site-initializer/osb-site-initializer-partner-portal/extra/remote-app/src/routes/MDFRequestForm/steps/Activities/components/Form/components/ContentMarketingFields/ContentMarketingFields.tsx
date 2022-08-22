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
import getBooleanEntries from '../../../../../../../../common/utils/getBooleanEntries';

interface IProps {
	currentActivityIndex: number;
}

const ContentMarketingFields = ({currentActivityIndex}: IProps) => {
	return (
		<>
			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Will this content be gated and have a landing page?"
				name={`activities[${currentActivityIndex}].gatedLandingPage`}
				required
				small
			/>

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Describe the primary theme or message of your content"
				name={`activities[${currentActivityIndex}].primaryThemeOrMessage`}
				required
			/>

			<PRMFormik.Field
				component={PRMForm.InputText}
				label="Goal of Content"
				name={`activities[${currentActivityIndex}].goalOfContent`}
				required
			/>

			<PRMFormik.Field
				component={PRMForm.RadioGroup}
				items={getBooleanEntries()}
				label="Are you hiring an outside writer or agency to prepare the content?"
				name={`activities[${currentActivityIndex}].hiringOutsideWriterOrAgency`}
				required
				small
			/>
		</>
	);
};

export default ContentMarketingFields;
