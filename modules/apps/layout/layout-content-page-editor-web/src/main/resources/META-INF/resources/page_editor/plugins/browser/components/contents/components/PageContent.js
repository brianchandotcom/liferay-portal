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
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {openModal} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {EDITABLE_FRAGMENT_ENTRY_PROCESSOR} from '../../../../../app/config/constants/editableFragmentEntryProcessor';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {ITEM_TYPES} from '../../../../../app/config/constants/itemTypes';
import {
	useHoverItem,
	useHoveredItemId,
	useSelectItem,
} from '../../../../../app/contexts/ControlsContext';
import {useSelector} from '../../../../../app/contexts/StoreContext';

export default function PageContent({
	actions,
	classNameId,
	classPK,
	editableId,
	icon,
	subtype,
	title,
	type,
}) {
	const [active, setActive] = useState(false);
	const hoverItem = useHoverItem();
	const hoveredItemId = useHoveredItemId();
	const fragmentEntryLinks = useSelector((state) => state.fragmentEntryLinks);
	const [isHovered, setIsHovered] = useState(false);
	const selectItem = useSelectItem();

	let editURL = null;
	let permissionsURL = null;
	let viewUsagesURL = null;

	if (actions) {
		editURL = actions.editURL;
		permissionsURL = actions.permissionsURL;
		viewUsagesURL = actions.viewUsagesURL;
	}

	useEffect(() => {
		if (hoveredItemId) {
			if (editableId) {
				setIsHovered(editableId === hoveredItemId);
			}
			else {
				const [
					fragmentEntryLinkId,
					...editableId
				] = hoveredItemId.split('-');

				if (fragmentEntryLinks[fragmentEntryLinkId]) {
					const fragmentEntryLink =
						fragmentEntryLinks[fragmentEntryLinkId];

					const editableValue =
						fragmentEntryLink.editableValues[
							EDITABLE_FRAGMENT_ENTRY_PROCESSOR
						];

					const editable = editableValue[editableId.join('-')];

					if (editable) {
						setIsHovered(editable.classPK === classPK);
					}
				}
			}
		}
		else {
			setIsHovered(false);
		}
	}, [fragmentEntryLinks, hoveredItemId, classPK, editableId]);

	const handleMouseOver = () => {
		setIsHovered(true);

		if (editableId) {
			hoverItem(editableId, {
				itemType: ITEM_TYPES.inlineContent,
				origin: ITEM_ACTIVATION_ORIGINS.contents,
			});
		}

		if (classNameId && classPK) {
			hoverItem(`${classNameId}-${classPK}`, {
				itemType: ITEM_TYPES.mappedContent,
				origin: ITEM_ACTIVATION_ORIGINS.contents,
			});
		}
	};

	const handleMouseLeave = () => {
		setIsHovered(false);
		hoverItem(null);
	};

	const onClickEditInlineText = () => {
		selectItem(`${editableId}`, {
			itemType: ITEM_TYPES.editable,
			origin: ITEM_ACTIVATION_ORIGINS.sidebar,
		});
	};

	return (
		<li
			className={classNames('page-editor__page-contents__page-content', {
				'page-editor__page-contents__page-content--mapped-item-hovered': isHovered,
			})}
			onMouseLeave={handleMouseLeave}
			onMouseOver={handleMouseOver}
		>
			<div
				className={classNames('d-flex', {
					'align-items-center': !subtype,
				})}
			>
				<ClayIcon
					className={classNames('mr-3', {
						'mt-1': subtype,
					})}
					focusable="false"
					monospaced="true"
					role="presentation"
					symbol={icon || 'document-text'}
				/>
				<ClayLayout.ContentCol expand>
					<span className="font-weight-semi-bold text-truncate">
						{title}
					</span>

					{subtype && (
						<span className="text-secondary">{subtype}</span>
					)}
				</ClayLayout.ContentCol>

				{editURL || permissionsURL || viewUsagesURL || type ? (
					<ClayDropDown
						active={active}
						onActiveChange={setActive}
						trigger={
							<ClayButton
								className="btn-sm mr-2 text-secondary"
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
									onClick={() => {
										openModal({
											title: Liferay.Language.get(
												'permissions'
											),
											url: permissionsURL,
										});
									}}
								>
									{Liferay.Language.get('permissions')}
								</ClayDropDown.Item>
							)}

							{viewUsagesURL && (
								<ClayDropDown.Item
									key="viewUsagesURL"
									onClick={() => {
										openModal({
											title: Liferay.Language.get(
												'view-usages'
											),
											url: viewUsagesURL,
										});
									}}
								>
									{Liferay.Language.get('view-usages')}
								</ClayDropDown.Item>
							)}
						</ClayDropDown.ItemList>
					</ClayDropDown>
				) : (
					<ClayButton
						className="btn-sm mr-2 text-secondary"
						displayType="unstyled"
						onClick={onClickEditInlineText}
					>
						<span className="sr-only">
							{Liferay.Language.get('edit-inline-text')}
						</span>
						<ClayIcon symbol="pencil" />
					</ClayButton>
				)}
			</div>
		</li>
	);
}

PageContent.propTypes = {
	actions: PropTypes.object,
	icon: PropTypes.string,
	name: PropTypes.string,
	subtype: PropTypes.string,
	title: PropTypes.string.isRequired,
};
