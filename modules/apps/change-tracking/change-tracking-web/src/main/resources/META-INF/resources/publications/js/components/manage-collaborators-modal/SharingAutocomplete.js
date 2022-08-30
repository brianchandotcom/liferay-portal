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

import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClaySticker from '@clayui/sticker';
import React from 'react';

const SharingAutocomplete = ({onItemClick = () => {}, sourceItems}) => {
	return (
		<ClayDropDown.ItemList>
			{sourceItems
				.sort((a, b) => {
					if (a.emailAddress < b.emailAddress) {
						return -1;
					}

					return 1;
				})
				.map((item) => {
					return (
						<ClayDropDown.Item
							data-tooltip-align="top"
							disabled={item.isOwner}
							key={item.userId}
							onClick={() => onItemClick(item)}
							title={
								item.isOwner
									? Liferay.Language.get(
											'cannot-update-permissions-for-an-owner'
									  )
									: ''
							}
						>
							<div className="autofit-row autofit-row-center">
								<div className="autofit-col mr-3">
									<ClaySticker
										className={`sticker-user-icon ${
											item.portraitURL
												? ''
												: 'user-icon-color-' +
												  (item.userId % 10)
										}`}
										size="lg"
									>
										{item.portraitURL ? (
											<div className="sticker-overlay">
												<img
													className="sticker-img"
													src={item.portraitURL}
												/>
											</div>
										) : (
											<ClayIcon symbol="user" />
										)}
									</ClaySticker>
								</div>

								<div className="autofit-col">
									<strong>{item.fullName}</strong>

									<span>{item.emailAddress}</span>
								</div>
							</div>
						</ClayDropDown.Item>
					);
				})}
		</ClayDropDown.ItemList>
	);
};

export default SharingAutocomplete;
