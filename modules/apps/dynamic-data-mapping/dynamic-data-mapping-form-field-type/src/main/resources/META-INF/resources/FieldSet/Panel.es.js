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

import './Panel.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {useForm} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import React from 'react';

import useHeightTransition from './useHeightTransition.es';

/**
 * Alternative component for ClayPanel,
 * since the original component don't have to much flexibility
 * for adding items on the ClayPanel trigger.
 */
const Panel = ({
	children,
	name,
	readOnly,
	repeatable,
	showLabel,
	showRepeatableRemoveButton,
	title,
}) => {
	const panelRef = React.useRef(null);
	const [expanded, setExpanded] = React.useState(true);

	const dispatch = useForm();

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
				'panel-unstyled'
			)}
			role="tablist"
		>
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
						{showLabel && (
							<span className="panel-title">
								<label className="text-uppercase">
									{title}
								</label>
							</span>
						)}

						{repeatable && (
							<span className="actions collapse-icon-options">
								<div className="lfr-ddm-form-field-repeatable-toolbar">
									{showRepeatableRemoveButton && (
										<ClayButton
											className="ddm-form-field-repeatable-delete-button lfr-portal-tooltip p-0"
											disabled={readOnly}
											onClick={(event) => {
												event.stopPropagation();

												dispatch({
													payload: name,
													type:
														CORE_EVENT_TYPES.FIELD
															.REMOVED,
												});
											}}
											small
											title={Liferay.Language.get(
												'remove'
											)}
										>
											<ClayIcon symbol="hr" />
										</ClayButton>
									)}

									<ClayButton
										className="ddm-form-field-repeatable-add-button lfr-portal-tooltip p-0"
										disabled={readOnly}
										onClick={(event) => {
											event.stopPropagation();

											dispatch({
												payload: name,
												type:
													CORE_EVENT_TYPES.FIELD
														.REPEATED,
											});
										}}
										small
										title={Liferay.Language.get(
											'duplicate'
										)}
									>
										<ClayIcon symbol="plus" />
									</ClayButton>
								</div>
							</span>
						)}

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
					{children}
				</div>
			</>
		</div>
	);
};

export default Panel;
