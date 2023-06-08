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

import ClayAlert from '@clayui/alert';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';

function SegmentsAndExperiencesSelector({
	namespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	showEmptyMessage,
}) {
	const [alertVisible, setAlertVisible] = useState(!segmentationEnabled);

	return (
		<>
			{showEmptyMessage ? (
				<p className="mb-4 mt-1 small">
					{Liferay.Language.get('no-segments-have-been-added-yet')}
				</p>
			) : (
				<form method="post" name="segmentsSimulationFm">
					<ul className="list-unstyled">
						{alertVisible && (
							<ClayAlert
								dismissible
								displayType="warning"
								onClose={() => {
									setAlertVisible(false);
								}}
							>
								<strong>
									{Liferay.Language.get(
										'experiences-cannot-be-displayed-because-segmentation-is-disabled'
									)}
								</strong>

								{segmentsCompanyConfigurationURL ? (
									<ClayLink
										href={segmentsCompanyConfigurationURL}
									>
										{Liferay.Language.get(
											'to-enable,-go-to-instance-settings'
										)}
									</ClayLink>
								) : (
									<span>
										{Liferay.Language.get(
											'contact-your-system-administrator-to-enable-it'
										)}
									</span>
								)}
							</ClayAlert>
						)}

						{segmentsEntries.map((segment) => (
							<li
								className="bg-transparent border-0 list-group-item list-group-item-flex pb-3 pt-0 px-0"
								key={segment}
							>
								<span>
									<div className="custom-checkbox">
										<label className="position-relative">
											<input
												className="custom-control-input simulated-segment"
												name={`${namespace}segmentsEntryId`}
												type="checkbox"
												value={segment.id}
											/>

											<span className="custom-control-label">
												<span className="custom-control-label-text">
													{segment.name}
												</span>
											</span>
										</label>
									</div>
								</span>
							</li>
						))}
					</ul>
				</form>
			)}
		</>
	);
}

export default SegmentsAndExperiencesSelector;
