/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import i18n from '../../i18n';
import {State} from '../../pages/Standalone/Teams/TeamsFormModal';
import yupSchema from '../../schema/yup';
import {SearchBuilder, searchUtil} from '../../util/search';
import Rest from './Rest';
import {APIResponse, TestrayComponent} from './types';

type Component = typeof yupSchema.component.__outputType;
class TestrayComponentImpl extends Rest<Component, TestrayComponent> {
	private UNASSIGNED_TEAM_ID = '0';

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
			throw new Error(i18n.sub('the-x-name-already-exists', 'component'));
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
			`/components?filter=${searchUtil.eq('teamId', teamId)}`
		);
	}
}

const testrayComponentImpl = new TestrayComponentImpl();

export {testrayComponentImpl};
