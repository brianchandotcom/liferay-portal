/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {Align, ClayDropDownWithItems} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {createPortletURL, fetch, getPortletId, sub} from 'frontend-js-web';
import React from 'react';

import {showNotification} from '../util/util';

const createMVCRenderCommandURL = (
	ctCollectionId,
	mvcRenderCommandName,
	namespace,
	additionalParams = {}
) => {
	return createPortletURL(themeDisplay.getLayoutRelativeControlPanelURL(), {
		ctCollectionId,
		mvcRenderCommandName,
		p_p_id: getPortletId(namespace),
		...additionalParams,
	}).toString();
};

export function createDiscardURL(
	ctCollectionId,
	namespace,
	timelineClassNameId,
	timelineClassPK
) {
	return createMVCRenderCommandURL(
		ctCollectionId,
		'/change_tracking/view_discard',
		namespace,
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);
}

export function createEditURL(ctCollectionId, timelineEditURL) {
	return createPortletURL(timelineEditURL, {
		ctCollectionId,
	}).toString();
}

export function createMoveURL(
	ctCollectionId,
	namespace,
	timelineClassNameId,
	timelineClassPK
) {
	return createMVCRenderCommandURL(
		ctCollectionId,
		'/change_tracking/view_move_changes',
		namespace,
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);
}

export function createViewURL(
	ctCollectionId,
	namespace,
	timelineClassNameId,
	timelineClassPK
) {
	return createMVCRenderCommandURL(
		ctCollectionId,
		'/change_tracking/view_change',
		namespace,
		{
			modelClassNameId: timelineClassNameId,
			modelClassPK: timelineClassPK,
		}
	);
}

export default function TimelineDropdownMenu({
	deleteURL,
	editURL,
	namespace,
	navigate,
	revertURL,
	reviewURL,
	spritemap,
	timelineClassNameId,
	timelineClassPK,
	timelineEditURL,
	timelineItem,
}) {
	const dropdownItems = [];

	if (Liferay.FeatureFlags['LPD-20556']) {
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

		const discardURL = createMVCRenderCommandURL(
			'/change_tracking/view_discard',
			{
				modelClassNameId: timelineClassNameId,
				modelClassPK: timelineClassPK,
			}
		);

		const checkoutURL = createPortletURL(timelineEditURL, {
			ctCollectionId: timelineItem.ctCollectionId,
		}).toString();

		const moveURL = createMVCRenderCommandURL(
			'/change_tracking/view_move_changes',
			{
				modelClassNameId: timelineClassNameId,
				modelClassPK: timelineClassPK,
			}
		);
		const viewURL = createMVCRenderCommandURL(
			'/change_tracking/view_change',
			{
				ctEntryId: timelineItem.id,
			}
		);

		if (!!timelineItem.actions.update && checkoutURL) {
			dropdownItems.push({
				action: true,
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

	if (editURL) {
		dropdownItems.push({
			href: editURL,
			label: Liferay.Language.get('edit'),
			symbolLeft: 'pencil',
		});
	}

	if (revertURL) {
		dropdownItems.push({
			href: revertURL,
			label: Liferay.Language.get('revert'),
			symbolLeft: 'list-ul',
		});
	}

	dropdownItems.push({
		href: reviewURL,
		label: Liferay.Language.get('review-changes'),
		symbolLeft: 'list-ul',
	});

	if (deleteURL) {
		const deleteRedirectURL = createMVCRenderCommandURL(
			timelineItem.ctCollectionId,
			'/change_tracking/view_publications',
			namespace
		);

		dropdownItems.push(
			{type: 'divider'},
			{
				label: Liferay.Language.get('delete'),
				onClick: () => {
					Liferay.Util.openConfirmModal({
						message: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-this-publication'
						),
						onConfirm: (isConfirmed) => {
							if (isConfirmed) {
								fetch(deleteURL).then((response) => {
									if (response.ok) {
										showNotification(
											sub(
												Liferay.Language.get(
													'x-was-deleted-successfully'
												),
												Liferay.Language.get(
													'publication'
												)
											),
											false,
											() => {
												setTimeout(() => {
													window.location.href =
														deleteRedirectURL;
												}, 1250);
											}
										);
									}
									else {
										showNotification(
											Liferay.Language.get(
												'an-unexpected-error-occurred'
											),
											true
										);
									}
								});
							}
						},
					});
				},
				symbolLeft: 'times-circle',
			}
		);
	}

	return (
		<ClayDropDownWithItems
			alignmentPosition={Align.BottomLeft}
			items={dropdownItems}
			spritemap={spritemap}
			trigger={
				<ClayButtonWithIcon
					displayType="unstyled"
					size="sm"
					spritemap={spritemap}
					symbol="ellipsis-v"
				/>
			}
		/>
	);
}
