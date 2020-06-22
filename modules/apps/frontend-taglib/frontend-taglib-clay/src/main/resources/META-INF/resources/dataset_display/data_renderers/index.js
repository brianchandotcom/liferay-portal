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

import getJsModule from '../utilities/modules';
import ActionsLinkRenderer from './ActionLinkRenderer';
import ActionsDropdownRenderer from './ActionsDropdownRenderer';
import CheckboxRenderer from './CheckboxRenderer';
import DateRenderer from './DateRenderer';
import DefaultRenderer from './DefaultRenderer';
import ImageRenderer from './ImageRenderer';
import LabelRenderer from './LabelRenderer';
import LinkRenderer from './LinkRenderer';
import ListRenderer from './ListRenderer';
import QuantitySelectorRenderer from './QuantitySelectorRenderer';
import StatusRenderer from './StatusRenderer';
import TooltipPriceRenderer from './TooltipPriceRenderer';

const dataRenderers = {
	actionLink: ActionsLinkRenderer,
	actionsDropdown: ActionsDropdownRenderer,
	checkbox: CheckboxRenderer,
	date: DateRenderer,
	default: DefaultRenderer,
	image: ImageRenderer,
	label: LabelRenderer,
	link: LinkRenderer,
	list: ListRenderer,
	quantitySelector: QuantitySelectorRenderer,
	status: StatusRenderer,
	tooltipPrice: TooltipPriceRenderer,
};

export function getDataRendererById(id) {
	return dataRenderers[id] || DefaultRenderer;
}

export const fetchedContentRenderers = [];

export function getDataRendererByUrl(url) {
	return new Promise((resolve, reject) => {
		const addedDataRenderer = fetchedContentRenderers.find(
			(cr) => cr.url === url
		);
		if (addedDataRenderer) {
			resolve(addedDataRenderer.component);
		}

		return getJsModule(url)
			.then((fetchedComponent) => {
				fetchedContentRenderers.push({
					component: fetchedComponent,
					url,
				});

				return resolve(fetchedComponent);
			})
			.catch(reject);
	});
}
