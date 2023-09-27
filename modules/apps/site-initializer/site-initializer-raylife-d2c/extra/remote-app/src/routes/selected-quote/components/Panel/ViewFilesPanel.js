/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						<div className="mb-4 view-section" key={index}>
							<h3 className="font-weight-bolder mb-2 text-neutral-8 text-paragraph-sm">
								{section.title.toUpperCase()}
							</h3>

							<div className="content d-flex flex-row">
								{section.files.map((file, sectionIndex) => {
									if (file.type.includes('image')) {
										return (
											<div
												className="image mr-2 rounded-xs"
												key={sectionIndex}
											>
												<img
													alt={file.name}
													className="rounded-xs"
													src={file.fileURL}
												/>
											</div>
										);
									}

									return (
										<div
											className="align-items-center bg-neutral-0 d-flex documents justify-content-center mr-2 rounded-xs"
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
