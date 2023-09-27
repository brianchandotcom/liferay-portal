/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {useSelectItem} from '../../../../../app/contexts/ControlsContext';
import {useSetMovementText} from '../../../../../app/contexts/KeyboardMovementContext';
import {
	FORM_ERROR_TYPES,
	getFormErrorDescription,
} from '../../../../../app/utils/getFormErrorDescription';
import updateItemStyle from '../../../../../app/utils/updateItemStyle';
import useHasRequiredChild from '../../../../../app/utils/useHasRequiredChild';

export default function VisibilityButton({
	className,
	dispatch,
	node,
	selectedViewportSize,
	visible,
}) {
	const hasRequiredChild = useHasRequiredChild(node.id);
	const selectItem = useSelectItem();
	const setText = useSetMovementText();

	return (
		<ClayButton
			aria-label={sub(
				node.hidden || node.hiddenAncestor
					? Liferay.Language.get('show-x')
					: Liferay.Language.get('hide-x'),
				[node.name]
			)}
			className={classNames(
				'page-editor__page-structure__tree-node__visibility-button ' +
					className,
				{
					'page-editor__page-structure__tree-node__visibility-button--visible': visible,
				}
			)}
			disabled={node.isMasterItem || node.hiddenAncestor}
			displayType="unstyled"
			onClick={(event) => {
				event.stopPropagation();
				updateItemStyle({
					dispatch,
					itemId: node.id,
					selectedViewportSize,
					styleName: 'display',
					styleValue: node.hidden ? 'block' : 'none',
				});

				if (!node.hidden && hasRequiredChild()) {
					const {message} = getFormErrorDescription({
						type: FORM_ERROR_TYPES.hiddenFragment,
					});

					openToast({
						message,
						type: 'warning',
					});
				}

				selectItem(node.id, {
					origin: ITEM_ACTIVATION_ORIGINS.itemActions,
				});

				setText(
					node.hidden
						? Liferay.Language.get('item-shown')
						: Liferay.Language.get('hidden-item')
				);
			}}
			tabIndex={
				document.activeElement.dataset.id?.includes(node.id)
					? '0'
					: '-1'
			}
		>
			<ClayIcon
				symbol={node.hidden || node.hiddenAncestor ? 'hidden' : 'view'}
			/>
		</ClayButton>
	);
}

VisibilityButton.propTypes = {
	dispatch: PropTypes.func,
	node: PropTypes.object.isRequired,
	selectedViewportSize: PropTypes.string,
	visible: PropTypes.bool,
};
