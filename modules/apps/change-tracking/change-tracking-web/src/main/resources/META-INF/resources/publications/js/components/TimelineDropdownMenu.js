/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {createPortletURL, fetch, getPortletId, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {
	WORKFLOW_STATUS_DRAFT,
	WORKFLOW_STATUS_EXPIRED,
} from './WorkflowStatusLabel';

export default function TimelineDropdownMenu({
	namespace,
	navigate,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineItem,
}) {
	const ctCollectionId = timelineItem.id;
	const dropdownItems = [];
	const [ctEntryId, setCTEntryId] = useState([]);

	const createMVCRenderCommandURL = (
		mvcRenderCommandName,
		additionalParams = {}
	) => {
		return createPortletURL(
			themeDisplay.getLayoutRelativeControlPanelURL(),
			{
				ctCollectionId,
				mvcRenderCommandName,
				p_p_id: getPortletId(namespace),
				...additionalParams,
			}
		).toString();
	};

	const getCTEntryId = () => {
		fetch(
			`/o/change-tracking-rest/v1.0/ct-collections/${ctCollectionId}/ct-entries/by-model-class-name-id/${timelineClassNameId}/by-model-class-pk/${timelineClassPK}`,
			{method: 'GET'}
		)
			.then((response) => {
				return response.json();
			})
			.then((jsonResponse) => {
				setCTEntryId(jsonResponse.id);
			});

		return ctEntryId;
	};

	const discardURL = createMVCRenderCommandURL(
		'/change_tracking/view_discard',
		{modelClassNameId: timelineClassNameId, modelClassPK: timelineClassPK}
	);

	const editURL = createPortletURL(timelineEditURL, {
		ctCollectionId,
	}).toString();

	const moveURL = createMVCRenderCommandURL(
		'/change_tracking/view_move_changes',
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);
	const viewURL = createMVCRenderCommandURL('/change_tracking/view_change', {
		ctEntryId: getCTEntryId(),
	});

	if (
		timelineItem.status.code === WORKFLOW_STATUS_DRAFT &&
		!!timelineItem.actions.update &&
		editURL
	) {
		dropdownItems.push({
			action: true,
			href: editURL,
			label: sub(Liferay.Language.get('edit-in-x'), timelineItem.name),
			symbolLeft: 'pencil',
		});
	}

	if (viewURL) {
		dropdownItems.push({
			href: viewURL,
			label: Liferay.Language.get('review-change'),
			symbolLeft: 'list-ul',
		});
	}

	if (
		(timelineItem.status.code === WORKFLOW_STATUS_DRAFT ||
			timelineItem.status.code === WORKFLOW_STATUS_EXPIRED) &&
		!!timelineItem.actions.update &&
		moveURL
	) {
		dropdownItems.push({
			href: moveURL,
			label: Liferay.Language.get('move-changes'),
			symbolLeft: 'move-folder',
		});
	}

	if (
		timelineItem.status.code === WORKFLOW_STATUS_DRAFT &&
		!!timelineItem.actions.update &&
		discardURL
	) {
		dropdownItems.push({
			href: discardURL,
			label: Liferay.Language.get('discard'),
			symbolLeft: 'times-circle',
		});
	}

	return (
		<ul className="list-unstyled" role="menu">
			{dropdownItems.map((dropdownItem) => (
				<li key={dropdownItem.label} role="presentation">
					<ClayButton
						aria-label={dropdownItem.label}
						borderless
						className="dropdown-item"
						displayType="unstyled"
						onClick={() =>
							navigate(dropdownItem.href, dropdownItem.action)
						}
					>
						<span className="inline-item inline-item-before">
							<ClayIcon
								spritemap={spritemap}
								symbol={dropdownItem.symbolLeft}
							/>
						</span>

						{dropdownItem.label}
					</ClayButton>
				</li>
			))}
		</ul>
	);
}
