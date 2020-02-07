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

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {
	useActiveItemId,
	useHoverItem,
	useHoveredItemId
} from '../../../app/components/Controls';
import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../app/config/constants/editableFragmentEntryProcessor';
import {useSelector} from '../../../app/store/index';

export default function PageContent(props) {
	const [active, setActive] = useState(false);
	const {editURL, permissionsURL, viewUsagesURL} = props.actions;
	const activeItemId = useActiveItemId();
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const fragmentEntryLinks = useSelector(state => state.fragmentEntryLinks);
	const [isHovered, setIsHovered] = useState(false);

	useEffect(() => {
		if (hoveredItemId) {
			const [fragmentEntryLinkId, ...editableId] = hoveredItemId.split(
				'-'
			);

			if (fragmentEntryLinks[fragmentEntryLinkId]) {
				const fragmentEntryLink =
					fragmentEntryLinks[fragmentEntryLinkId];

				const editableValue =
					fragmentEntryLink.editableValues[
						EDITABLE_FRAGMENT_ENTRY_PROCESSOR
					];

				const editable = editableValue[editableId.join('-')];

				if (editable) {
					setIsHovered(editable.classPK === props.classPK);
				}
			}
		}
		else {
			setIsHovered(false);
		}
	}, [fragmentEntryLinks, hoveredItemId, props.classPK]);

	const openWindow = (uri, title) => {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			title,
			uri
		});
	};

	const handleMouseOver = () => {
		if (!activeItemId) {
			return;
		}

		setIsHovered(true);

		Object.values(fragmentEntryLinks).forEach(fragmentEntryLink => {
			const editableFragmentEntryProcessor =
				fragmentEntryLink.editableValues[
					EDITABLE_FRAGMENT_ENTRY_PROCESSOR
				];

			Object.keys(editableFragmentEntryProcessor).forEach(
				editableValue => {
					hoverItem(
						`${fragmentEntryLink.fragmentEntryLinkId}-${editableValue}`
					);
				}
			);
		});
	};

	const handleMouseLeave = () => {
		setIsHovered(false);
		hoverItem(null);
	};

	return (
		<li
			className={classNames('page-editor__contents__page-content', {
				'page-editor__contents__page-content--mapped-item-hovered': isHovered
			})}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			<div className="d-flex pl-3 pr-2 py-3">
				<div className="autofit-col autofit-col-expand">
					<strong className="list-group-title text-truncate">
						{props.title}
					</strong>

					<span className="small text-secondary">{props.name}</span>

					<span className="small text-secondary">
						{props.usagesCount === 1
							? Liferay.Language.get('used-in-1-page')
							: Liferay.Util.sub(
									Liferay.Language.get('used-in-x-pages'),
									props.usagesCount
							  )}
					</span>

					<div>
						{props.status.hasApprovedVersion && (
							<ClayLabel displayType="success">
								{Liferay.Language.get('approved')}
							</ClayLabel>
						)}
						<ClayLabel displayType={props.status.style}>
							{props.status.label}
						</ClayLabel>
					</div>
				</div>

				{(editURL || permissionsURL || viewUsagesURL) && (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="btn-monospaced btn-sm text-secondary"
								displayType="unstyled"
							>
								<span className="sr-only">
									{Liferay.Language.get('open-actions-menu')}
								</span>
								<ClayIcon symbol="ellipsis-v" />
							</ClayButton>
						}
					>
						<ClayDropDown.ItemList>
							{editURL && (
								<ClayDropDown.Item href={editURL} key="editURL">
									{Liferay.Language.get('edit')}
								</ClayDropDown.Item>
							)}

							{permissionsURL && (
								<ClayDropDown.Item
									key="permissionsURL"
									onClick={() =>
										openWindow(
											permissionsURL,
											Liferay.Language.get('permissions')
										)
									}
								>
									{Liferay.Language.get('permissions')}
								</ClayDropDown.Item>
							)}

							{viewUsagesURL && (
								<ClayDropDown.Item
									key="viewUsagesURL"
									onClick={() =>
										openWindow(
											viewUsagesURL,
											Liferay.Language.get('view-usages')
										)
									}
								>
									{Liferay.Language.get('view-usages')}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				)}
			</div>
		</li>
	);
}

PageContent.propTypes = {
	actions: PropTypes.object,
	name: PropTypes.string.isRequired,
	status: PropTypes.shape({
		hasApprovedVersion: PropTypes.bool,
		label: PropTypes.string,
		style: PropTypes.string
	}),
	title: PropTypes.string.isRequired,
	usagesCount: PropTypes.number.isRequired
};
