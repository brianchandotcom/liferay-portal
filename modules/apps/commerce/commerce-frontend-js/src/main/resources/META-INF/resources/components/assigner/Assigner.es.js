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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon, {ClayIconSpriteContext} from '@clayui/icon';
import React, {useState} from 'react';

function Assigner(props) {
	const [active, setActive] = useState(false);

	return (
		<ClayIconSpriteContext.Provider value={props.spritemap}>
			<div className="d-flex align-items-center">
				<span className="mr-3">
					{Liferay.Language.get('assigned-to')}
				</span>
				<ClayDropDown
					active={active}
					alignmentPosition={Align.BottomLeft}
					onActiveChange={setActive}
					trigger={
						<ClayButton displayType="secondary">
							Admin
							<span className="inline-item inline-item-after">
								<ClayIcon symbol="caret-bottom" />
							</span>
						</ClayButton>
					}
				>
					<>
						<form>
							<div className="dropdown-section">
								<div className="input-group input-group-sm">
									<div className="input-group-item">
										<input
											className="form-control input-group-inset input-group-inset-after"
											placeholder={Liferay.Language.get(
												'search'
											)}
											type="text"
										/>
										<span className="input-group-inset-item input-group-inset-item-after">
											<button
												className="btn btn-unstyled"
												type="button"
											>
												<svg
													className="lexicon-icon lexicon-icon-search"
													focusable="false"
													role="presentation"
												>
													<use href="/images/icons/icons.svg#search" />
												</svg>
											</button>
										</span>
									</div>
								</div>
							</div>
						</form>
						<form>
							<div className="inline-scroller">
								<div
									className="dropdown-subheader"
									role="presentation"
								>
									{Liferay.Language.get('my-actions')}
								</div>
								<ul className="list-unstyled">
									<li>
										<a className="dropdown-item" href="#1">
											D Structure
										</a>
									</li>
									<li>
										<a className="dropdown-item" href="#1">
											F Structure
										</a>
									</li>
								</ul>
								<div
									className="dropdown-subheader"
									role="presentation"
								>
									{Liferay.Language.get('assign-by-role')}
								</div>
								<ul className="list-unstyled">
									<li>
										<a className="dropdown-item" href="#1">
											D Structure
										</a>
									</li>
									<li>
										<a className="dropdown-item" href="#1">
											F Structure
										</a>
									</li>
									<li>
										<a
											className="disabled dropdown-item"
											href="#1"
											tabIndex="-1"
										>
											H Structure
										</a>
									</li>
									<li>
										<a className="dropdown-item" href="#1">
											J Structure
										</a>
									</li>
									<li>
										<a className="dropdown-item" href="#1">
											L Structure
										</a>
									</li>
								</ul>
							</div>
						</form>
						<div className="dropdown-caption">
							Showing 7 of 203 Users
						</div>
						<div className="dropdown-section">
							<button className="btn btn-block btn-secondary">
								{Liferay.Language.get('more')}
							</button>
						</div>
					</>
				</ClayDropDown>
			</div>
		</ClayIconSpriteContext.Provider>
	);
}

export default Assigner;
