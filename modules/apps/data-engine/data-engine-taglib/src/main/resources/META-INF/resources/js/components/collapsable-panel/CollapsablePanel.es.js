/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
