/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {
	createPortletURL,
	getPortletId,
	navigate as navigateUtil,
	sub,
} from 'frontend-js-web';
import React from 'react';

export default function TimelineDropdownMenu({
	namespace,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineItem,
}) {
	const dropdownItems = [];

	const createMVCRenderCommandURL = (
		mvcRenderCommandName,
		additionalParams = {}
	) => {
		return createPortletURL(
			themeDisplay.getLayoutRelativeControlPanelURL(),
			{
				ctCollectionId: timelineItem.ctCollectionId,
				mvcRenderCommandName,
				p_p_id: getPortletId(namespace),
				...additionalParams,
			}
		).toString();
	};

	const checkoutParameters = {
		ctCollectionId: timelineItem.ctCollectionId,
	};

	if (timelineItem.editURL) {
		checkoutParameters.redirect = createPortletURL(timelineItem.editURL);
	}

	const checkoutURL = createPortletURL(
		timelineEditURL,
		checkoutParameters
	).toString();

	const discardURL = createMVCRenderCommandURL(
		'/change_tracking/view_discard',
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);

	const moveURL = createMVCRenderCommandURL(
		'/change_tracking/view_move_changes',
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);
	const viewURL = createMVCRenderCommandURL('/change_tracking/view_change', {
		ctEntryId: timelineItem.id,
	});

	if (!!timelineItem.actions.update && checkoutURL) {
		dropdownItems.push({
			href: checkoutURL,
			label: sub(
				Liferay.Language.get('edit-in-x'),
				timelineItem.ctCollectionName
			),
			symbolLeft: 'pencil',
		});
	}

	if (!!timelineItem.actions.get && viewURL) {
		dropdownItems.push({
			href: viewURL,
			label: Liferay.Language.get('review-change'),
			symbolLeft: 'list-ul',
		});
	}

	if (!!timelineItem.actions['move-changes'] && moveURL) {
		dropdownItems.push({
			href: moveURL,
			label: Liferay.Language.get('move-changes'),
			symbolLeft: 'move-folder',
		});
	}

	if (!!timelineItem.actions['view-discard'] && discardURL) {
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
						onClick={() => navigateUtil(dropdownItem.href)}
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
