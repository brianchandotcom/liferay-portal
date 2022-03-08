/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import {CSSTransition} from 'react-transition-group';

const DrilldownMenuItems = ({
	active,
	direction,
	header,
	items,
	onBack,
	onForward,
}) => {
	const initialClasses = classNames('transitioning', {
		'drilldown-prev-initial': direction === 'prev',
	});

	return (
		<CSSTransition
			className={classNames('drilldown-item', {
				'drilldown-current': active,
			})}
			classNames={{
				enter: initialClasses,
				enterActive: `drilldown-transition drilldown-${direction}-active`,
				exit: initialClasses,
				exitActive: `drilldown-transition drilldown-${direction}-active`,
			}}
			in={active}
			timeout={250}
		>
			<div className="drilldown-item-inner">
				{header && (
					<>
						<div className="dropdown-header" onClick={onBack}>
							<ClayButtonWithIcon
								className="component-action dropdown-item-indicator-start"
								onClick={onBack}
								symbol="angle-left"
							/>

							<span className="dropdown-item-indicator-text-start">
								{header}
							</span>
						</div>

						<div className="dropdown-divider" />
					</>
				)}

				{items && (
					<ul className="inline-scroller">
						{items.map(
							(
								{
									child,
									className,
									onClick,
									symbol,
									title,
									type,
								},
								j
							) =>
								type === 'divider' ? (
									<li
										aria-hidden="true"
										className="dropdown-divider"
										key={`${j}-divider`}
										role="presentation"
									/>
								) : type === 'component' ? (
									<React.Fragment key={`${j}-${title}`}>
										{child}
									</React.Fragment>
								) : (
									<li key={`${j}-${title}`}>
										<Button
											className={classNames(
												'dropdown-item',
												className
											)}
											displayType="unstyled"
											onClick={(event) => {
												if (onClick) {
													onClick(event);
												}

												if (title && child) {
													onForward(title, child);
												}
											}}
										>
											{symbol && (
												<span className="dropdown-item-indicator-start">
													<ClayIcon symbol={symbol} />
												</span>
											)}

											<span className="dropdown-item-indicator-text-end">
												{title}
											</span>

											{child && (
												<span className="dropdown-item-indicator-end">
													<ClayIcon symbol="angle-right" />
												</span>
											)}
										</Button>
									</li>
								)
						)}
					</ul>
				)}
			</div>
		</CSSTransition>
	);
};

export default DrilldownMenuItems;
