/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import TestrayError from '../../TestrayError';
import Rest from '../../core/Rest';
import SearchBuilder from '../../core/SearchBuilder';
import i18n from '../../i18n';
import {State} from '../../pages/Standalone/Teams/TeamsFormModal';
import yupSchema from '../../schema/yup';
import {testrayCaseResultImpl} from './TestrayCaseResult';
import {APIResponse, TestrayComponent} from './types';

type Component = typeof yupSchema.component.__outputType;
class TestrayComponentImpl extends Rest<Component, TestrayComponent> {
	public UNASSIGNED_TEAM_ID = '0';

	constructor() {
		super({
			adapter: ({
				name,
				projectId: r_projectToComponents_c_projectId,
				teamId: r_teamToComponents_c_teamId,
			}) => ({
				name,
				r_projectToComponents_c_projectId,
				r_teamToComponents_c_teamId,
			}),
			nestedFields: 'project,team',
			transformData: (testrayComponent) => ({
				...testrayComponent,
				...testrayCaseResultImpl.normalizeCaseResultAggregation(
					testrayComponent
				),
				project: testrayComponent?.r_projectToComponents_c_project,
				team: testrayComponent?.r_teamToComponents_c_team,
				teamId: testrayComponent.r_teamToComponents_c_teamId,
			}),
			uri: 'components',
		});
	}

	public async assignTeamsToComponents(teamId: string, state: State) {
		const [unassignedItems = [], currentItems = []] = state;

		for (const unassigned of unassignedItems) {
			if (this.UNASSIGNED_TEAM_ID !== unassigned.teamId.toString()) {
				await this.update(Number(unassigned.value), {
					name: unassigned.label,
					teamId: this.UNASSIGNED_TEAM_ID,
				});
			}
		}

		for (const current of currentItems) {
			if (this.UNASSIGNED_TEAM_ID === current.teamId.toString()) {
				await this.update(Number(current.value), {
					name: current.label,
					teamId,
				});
			}
		}
	}

	protected async validate(component: Component, id?: number): Promise<void> {
		const searchBuilder = new SearchBuilder();

		if (id) {
			searchBuilder.ne('id', id).and();
		}

		const filter = searchBuilder
			.eq('name', component.name)
			.and()
			.eq('projectId', component.projectId as string)
			.build();

		const response = await this.fetcher<APIResponse<TestrayComponent>>(
			`/components?filter=${filter}`
		);

		if (response?.items?.length) {
			throw new TestrayError(
				i18n.sub('the-x-name-already-exists', 'component')
			);
		}
	}

	protected async beforeCreate(component: Component): Promise<void> {
		await this.validate(component);
	}

	protected async beforeUpdate(
		id: number,
		component: Component
	): Promise<void> {
		await this.validate(component, id);
	}

	public getComponentsByTeamId(
		teamId: number
	): Promise<APIResponse<TestrayComponent> | undefined> {
		return this.fetcher<APIResponse<TestrayComponent>>(
			`/components?filter=${SearchBuilder.eq('teamId', teamId)}`
		);
	}
}

const testrayComponentImpl = new TestrayComponentImpl();

export {testrayComponentImpl};
