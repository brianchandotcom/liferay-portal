/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Rest from '~/core/Rest';

import yupSchema from '../../schema/yup';
import {JiraClientExtensionRestImpl} from './JiraClientExtension';
import {TestrayRequirement} from './types';

type Requirement = typeof yupSchema.requirement.__outputType & {
	components: string;
	projectId: number;
};

class TestrayRequirementsImpl extends Rest<Requirement, TestrayRequirement> {
	constructor() {
		super({
			adapter: ({
				componentId: r_componentToRequirements_c_componentId,
				components,
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				projectId: r_projectToRequirements_c_projectId,
				summary,
			}) => ({
				components,
				description,
				descriptionType,
				key,
				linkTitle,
				linkURL,
				r_componentToRequirements_c_componentId,
				r_projectToRequirements_c_projectId,
				summary,
			}),
			nestedFields:
				'component, team, componentToRequirements.teamToComponents',
			transformData: (testrayRequirement) => ({
				...testrayRequirement,
				component: testrayRequirement.r_componentToRequirements_c_component
					? {
							...testrayRequirement.r_componentToRequirements_c_component,
							team:
								testrayRequirement
									.r_componentToRequirements_c_component
									.r_teamToComponents_c_team,
					  }
					: undefined,
			}),
			uri: 'requirements',
		});
	}
	public async importJiraIssue(form: any) {
		const importedIssues = await JiraClientExtensionRestImpl.importIssues(
			form.issues.map((issue: any) => issue.label)
		);

		const errors = [];
		const createdIssues = [];

		for (const ticket in importedIssues) {
			if (importedIssues[ticket] !== null) {
				if (!form.id) {
					form.key = `R-${Math.ceil(Math.random() * 1000)}`;
				}

				const formData = {
					components: importedIssues[ticket]?.jiraComponents.join(
						', '
					),
					description: importedIssues[ticket]?.description,
					descriptionType: 'markdown',
					key: form?.key,
					linkTitle: importedIssues[ticket]?.key,
					linkURL: `${form?.jiraBaseURL}/${importedIssues[ticket]?.key}`,
					projectId: form?.projectId,
					summary: importedIssues[ticket]?.summary,
				};

				createdIssues.push(ticket);
				await this.create(formData as any);
			}

			if (importedIssues[ticket] === null) {
				errors.push(ticket);
			}
		}

		return {createdIssues, errors};
	}
}

const testrayRequirementsImpl = new TestrayRequirementsImpl();

export {testrayRequirementsImpl};
