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
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {openToast, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React from 'react';

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
