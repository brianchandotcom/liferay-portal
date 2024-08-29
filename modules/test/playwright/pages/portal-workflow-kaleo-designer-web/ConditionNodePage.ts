/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {DiagramViewPage} from './DiagramViewPage';
import {NodePropertiesSidebarPage} from './NodePropertiesSidebarPage';

export class ConditionNode {
	readonly conditionNodeSideBarItem: Locator;
	readonly diagramViewPage: DiagramViewPage;
	readonly nodePropertiesSideBarPage: NodePropertiesSidebarPage;
	readonly scriptLanguageSelect: Locator;
	readonly scriptInput: Locator;

	constructor(page: Page) {
		this.conditionNodeSideBarItem = page.getByText('Condition', {
			exact: true,
		});
		this.diagramViewPage = new DiagramViewPage(page);
		this.nodePropertiesSideBarPage = new NodePropertiesSidebarPage(page);
		this.scriptLanguageSelect = page.locator('#script-language');
		this.scriptInput = page.locator('#nodeScript');
	}

	async dragConditionNodeToDiagram() {
		await this.conditionNodeSideBarItem.dragTo(
			this.diagramViewPage.diagramArea,
			{targetPosition: {x: 200, y: 200}}
		);
	}

	async fillConditionNodeFields(
		nodeLabel: string,
		scriptLanguageOption: string,
		script: string
	) {
		await this.nodePropertiesSideBarPage.nodeLabelInput.fill(nodeLabel);
		await this.scriptLanguageSelect.selectOption(scriptLanguageOption);
		await this.scriptInput.fill(script);
	}
}
