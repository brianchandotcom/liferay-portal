/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentSelection} from '../components/forms/content_selector/ContentSelector';
import {HandlerSelection} from '../components/forms/content_selector/PortletDataControl';
import {
	PortletDataHandlerControl,
	PortletDataHandlerSection,
} from './mockPortletDataHandlerSections';

interface FlattenContentSelectionInput {
	additionalFields?: Record<string, string>;
	contentSelection?: ContentSelection;
	sections: PortletDataHandlerSection[];
}

export function flattenContentSelection({
	additionalFields = {},
	contentSelection = {},
	sections,
}: FlattenContentSelectionInput) {
	const flatValues: Record<string, string> = {...additionalFields};
	const checkboxNames: string[] = [];

	const collectCheckboxNames = (controls: PortletDataHandlerControl[]) => {
		controls.forEach((control) => {
			if (control.type === 'boolean') {
				checkboxNames.push(control.name);

				if (control.controls) {
					collectCheckboxNames(control.controls);
				}
			}
		});
	};

	sections.forEach((section) => {
		section.portletDataHandlers.forEach((handler) => {
			checkboxNames.push(handler.name);

			if (handler.controls) {
				collectCheckboxNames(handler.controls);
			}
		});
	});

	const processSelection = (value: HandlerSelection, name: string) => {
		flatValues[name] = 'on';

		if (typeof value === 'object') {
			Object.entries(value).forEach(([childName, childValue]) => {
				if (typeof childValue === 'object' || childValue === true) {
					processSelection(childValue as HandlerSelection, childName);
				}
				else if (typeof childValue === 'string') {
					flatValues[childName] = childValue;
				}
			});
		}
	};

	Object.values(contentSelection).forEach((sectionValue) => {
		if (sectionValue) {
			Object.entries(sectionValue).forEach(
				([handlerName, handlerValue]) => {
					processSelection(handlerValue, handlerName);
				}
			);
		}
	});

	if (checkboxNames.length) {
		flatValues.checkboxNames = checkboxNames.join(',');
	}

	return flatValues;
}
