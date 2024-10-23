/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../../../utils/getRandomString';

type Step = PageElement[];

type Props = {
	id: string;
	objectDefinitionClassName?: string;
	pageElements?: PageElement[];
	steps?: Step[];
};

export default function getFormContainerDefinition({
	id,
	objectDefinitionClassName,
	pageElements = [],
	steps = [],
}: Props): PageElement {
	if (!objectDefinitionClassName) {
		return {
			definition: {},
			id,
			type: 'Form',
		};
	}

	const children = pageElements;

	if (steps.length) {
		children.push(getStepContainerDefinition(steps));
	}

	return {
		definition: {
			formConfig: {
				formReference: {
					className: objectDefinitionClassName,
					classType: 0,
				},
				formType: steps.length ? 'multistep' : 'simple',
				numberOfSteps: steps.length,
			},
		},
		id,
		pageElements: children,
		type: 'Form',
	};
}

function getStepContainerDefinition(steps: Step[]): PageElement {
	return {
		definition: {},
		id: getRandomString(),
		pageElements: steps.map(getStepDefinition),
		type: 'FormStepContainer',
	};
}

function getStepDefinition(step: Step): PageElement {
	return {
		definition: {},
		id: getRandomString(),
		pageElements: step,
		type: 'FormStep',
	};
}
