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

import {OPEN_MODAL, OPEN_SIDE_PANEL} from '../eventsDefinitions';
import {liferayNavigate} from '../index';
import {resolveModalSize} from '../modals/index';
import {ACTION_ITEM_TARGETS} from './constants';

const {
	BLANK,
	EVENT,
	MODAL,
	MODAL_FULL_SCREEN,
	MODAL_LARGE,
	MODAL_SMALL,
	SIDE_PANEL,
} = ACTION_ITEM_TARGETS;

export function triggerAction(item, context) {
	const {href: actionTargetURL, target: actionTarget} = item,
		{loadData, modalId, sidePanelId} = context;

	switch (actionTarget) {
		case BLANK:
			window.open(actionTargetURL);
			break;
		case MODAL:
		case MODAL_FULL_SCREEN:
		case MODAL_LARGE:
		case MODAL_SMALL:
			Liferay.fire(OPEN_MODAL, {
				id: modalId,
				onClose: loadData,
				size: resolveModalSize(actionTarget),
				url: actionTargetURL,
			});
			break;
		case SIDE_PANEL:
			Liferay.fire(OPEN_SIDE_PANEL, {
				id: sidePanelId,
				onAfterSubmit: loadData,
				url: actionTargetURL,
			});
			break;
		case EVENT:
			Liferay.fire(actionTargetURL);
			break;
		default:
			liferayNavigate(actionTargetURL);
			break;
	}
}
