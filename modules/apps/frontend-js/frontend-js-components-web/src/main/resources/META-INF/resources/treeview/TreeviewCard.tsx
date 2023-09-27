/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import classNames from 'classnames';
import React, {useContext} from 'react';

import TreeviewContext, {Node} from './TreeviewContext';

export default function TreeviewCard({node}: IProps) {
	const {state} = useContext(TreeviewContext);
	const {filter, focusedNodeId} = state;

	const path =
		node.nodePath && filter ? (
			<div className="font-weight-normal h5 lfr-card-subtitle-text small text-default text-truncate treeview-node-name">
				{node.nodePath}
			</div>
		) : null;

	return (
		<div
			className={classNames({
				'card-type-directory': true,
				'disabled': node.disabled,
				'focused': node.id === focusedNodeId,
				'form-check': true,
				'form-check-card': true,
				'form-check-middle-left': true,
				'selected': node.selected,
			})}
		>
			<div className="card card-horizontal">
				<div className="card-body">
					<ClayCard.Row className="autofit-row-center">
						{node.icon && (
							<div className="autofit-col">
								<ClaySticker
									className={node.iconCssClass}
									displayType="secondary"
									inline
								>
									<ClayIcon symbol={node.icon} />
								</ClaySticker>
							</div>
						)}

						<div className="autofit-col autofit-col-expand autofit-col-gutters">
							<ClayCard.Description displayType="title">
								{node.name}
							</ClayCard.Description>
						</div>
					</ClayCard.Row>

					{path}
				</div>
			</div>
		</div>
	);
}

interface IProps {
	node: Node;
}
