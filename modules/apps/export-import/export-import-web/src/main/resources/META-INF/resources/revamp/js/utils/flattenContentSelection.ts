/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ContentSelection} from '../components/forms/content_selector/ContentSelector';
import {
	PreviewPortletDataHandlerControl,
	PreviewPortletDataHandlerSection,
} from '../types/portletDataHandler';
import {HandlerSelection} from './contentSelection';

interface FlattenContentSelectionInput {
	additionalFields?: Record<string, string>;
	contentSelection?: ContentSelection;
	sections: PreviewPortletDataHandlerSection[];
}

export function flattenContentSelection({
	additionalFields = {},
	contentSelection = {},
	sections,
}: FlattenContentSelectionInput) {
	const flatValues: Record<string, string> = {...additionalFields};
	const checkboxNames: string[] = [];

	const collectCheckboxNames = (
		controls: PreviewPortletDataHandlerControl[]
	) => {
		controls.forEach((control) => {
			if (control.type === 'Boolean') {
				checkboxNames.push(control.name);

				if (control.previewPortletDataHandlerControls) {
					collectCheckboxNames(
						control.previewPortletDataHandlerControls
					);
				}
			}
		});
	};

	sections.forEach((section) => {
		section.previewPortletDataHandlers.forEach((handler) => {
			checkboxNames.push(handler.name);

			if (handler.previewPortletDataHandlerControls) {
				collectCheckboxNames(handler.previewPortletDataHandlerControls);
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
