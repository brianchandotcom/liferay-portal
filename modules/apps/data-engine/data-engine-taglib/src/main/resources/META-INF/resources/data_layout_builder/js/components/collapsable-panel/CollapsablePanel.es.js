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
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import React from 'react';

import {useHeightTransition} from '../../hooks/index.es';
import DropDown from '../drop-down/DropDown.es';

const CollapsablePanel = ({Header, actions, children, className, title}) => {
	const panelRef = React.useRef(null);
	const [expanded, setExpanded] = React.useState(false);

	const [
		transitioning,
		handleTransitionEnd,
		startTransition,
	] = useHeightTransition(expanded, setExpanded, panelRef);

	const showIconCollapsed = !(
		(!expanded && transitioning) ||
		(expanded && !transitioning)
	);

	return (
		<div
			className={classNames(
				'collapsable-panel',
				'panel',
				'panel-unstyled',
				className
			)}
			role="tablist"
		>
			<>
				{Header ? (
					<Header expanded={expanded} setExpanded={setExpanded} />
				) : (
					<>
						<ClayButton
							aria-expanded={expanded}
							className={classNames(
								'collapse-icon',
								'collapse-icon-middle',
								'panel-header',
								'panel-header-link',
								{
									collapsed: showIconCollapsed,
								}
							)}
							displayType="unstyled"
							onClick={startTransition}
							role="tab"
						>
							<>
								<span className="panel-title">{title}</span>

								<span
									className={classNames(
										'actions',
										'collapse-icon-closed'
									)}
								>
									<ClayIcon symbol="angle-down" />
								</span>
								<span
									className={classNames(
										'actions',
										'collapse-icon-open'
									)}
								>
									<ClayIcon symbol="angle-up" />
								</span>
							</>
						</ClayButton>

						{actions && (
							<div className="collapse-icon-options">
								<DropDown actions={actions} />
							</div>
						)}
					</>
				)}

				<div
					className={classNames('panel-collapse', {
						collapse: !transitioning,
						collapsing: transitioning,
						show: expanded,
					})}
					onTransitionEnd={handleTransitionEnd}
					ref={panelRef}
					role="tabpanel"
				>
					<ClayPanel.Body>{children}</ClayPanel.Body>
				</div>
			</>
		</div>
	);
};

export default CollapsablePanel;
