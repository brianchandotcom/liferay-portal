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

import ClayIcon from '@clayui/icon';

const ViewFilesPanel = ({sections = []}) => {
	if (!sections.length) {
		return null;
	}

	return (
		<>
			{sections.map((section, index) => {
				if (section.files.length) {
					return (
						<div className="view-section" key={index}>
							<h3>{section.title.toUpperCase()}</h3>

							<div className="content">
								{section.files.map((file, sectionIndex) => {
									if (file.type.includes('image')) {
										return (
											<div
												className="image"
												key={sectionIndex}
											>
												<img
													alt={file.name}
													src={file.fileURL}
												/>
											</div>
										);
									}

									return (
										<div
											className="documents"
											key={sectionIndex}
										>
											<ClayIcon
												className={file.icon}
												key={index}
												symbol={file.icon}
											/>
										</div>
									);
								})}
							</div>
						</div>
					);
				}
			})}
		</>
	);
};

export default ViewFilesPanel;
